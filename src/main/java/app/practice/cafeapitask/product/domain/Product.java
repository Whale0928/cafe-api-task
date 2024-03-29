package app.practice.cafeapitask.product.domain;

import app.practice.cafeapitask.owner.domain.Owner;
import app.practice.cafeapitask.product.manage.dto.ProductMessage;
import app.practice.cafeapitask.product.manage.dto.request.ProductUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

import static app.practice.cafeapitask.product.manage.dto.ProductMessage.ALREADY_CHANGED_PRODUCT_STATUS;
import static app.practice.cafeapitask.product.manage.dto.ProductMessage.CHANGE_PRODUCT_STATUS;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("사장님")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @Comment("카테고리")
    @Column(nullable = false)
    private String category;

    @Comment("가격")
    @Column(nullable = false)
    private Double price;

    @Comment("원가")
    @Column(nullable = false)
    private Double cost;

    @Comment("상품명")
    @Column(nullable = false)
    private String name;

    @Comment("상품 설명")
    @Column(nullable = false)
    private String description;

    @Comment("바코드")
    @Column(nullable = false)
    private String barcode;

    @Comment("유통기한")
    @Column(nullable = false)
    private LocalDate expirationDate;

    @Comment("사이즈")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Size size;

    @Comment("상품 상태")
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @Builder
    private Product(Long id, Owner owner, String category, Double price, Double cost, String name, String description, String barcode, LocalDate expirationDate, Size size) {
        this.id = id;
        this.owner = owner;
        this.category = category;
        this.price = price;
        this.cost = cost;
        this.name = name;
        this.description = description;
        this.barcode = barcode;
        this.expirationDate = expirationDate;
        this.size = size;
        this.productStatus = ProductStatus.ACTIVE;
    }

    public ProductMessage updateProductStatus(ProductStatus productStatus) {
        if (this.productStatus.equals(ProductStatus.INACTIVE)) {
            return ALREADY_CHANGED_PRODUCT_STATUS;
        }
        this.productStatus = productStatus;
        return CHANGE_PRODUCT_STATUS;
    }


    public ProductMessage updateProduct(ProductUpdateRequest request) {
        this.category = request.getCategory();
        this.price = request.getPrice();
        this.cost = request.getCost();
        this.name = request.getName();
        this.description = request.getDescription();
        this.barcode = request.getBarcode();
        this.expirationDate = request.getExpirationDate();
        this.size = request.getSize();
        return ProductMessage.SUCCESS_UPDATE_PRODUCT;
    }

}

