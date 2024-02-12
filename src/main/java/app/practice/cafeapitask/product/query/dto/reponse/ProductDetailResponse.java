package app.practice.cafeapitask.product.query.dto.reponse;

import app.practice.cafeapitask.product.domain.ProductStatus;
import app.practice.cafeapitask.product.domain.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString(of = {"id", "name", "category", "description", "productStatus"})
public class ProductDetailResponse {
    private final Long id;
    private final String category;
    private final Double price;
    private final Double cost;
    private final String name;
    private final String barcode;
    private final String description;
    private final LocalDate expirationDate;
    private final Size size;
    private final ProductStatus productStatus;
    private final Long ownerId;
    private final String ownerPhoneNumber;

    @Builder
    public ProductDetailResponse(Long id, String category, Double price, Double cost, String name, String barcode, String description, LocalDate expirationDate, Size size, ProductStatus productStatus, Long ownerId, String ownerPhoneNumber) {
        this.id = id;
        this.category = category;
        this.price = price;
        this.cost = cost;
        this.name = name;
        this.barcode = barcode;
        this.description = description;
        this.expirationDate = expirationDate;
        this.size = size;
        this.productStatus = productStatus;
        this.ownerId = ownerId;
        this.ownerPhoneNumber = ownerPhoneNumber;
    }
}
