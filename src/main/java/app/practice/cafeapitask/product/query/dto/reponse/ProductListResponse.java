package app.practice.cafeapitask.product.query.dto.reponse;

import app.practice.cafeapitask.product.domain.ProductStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(of = {"id", "name", "category", "description", "productStatus"})
public class ProductListResponse {
    private final Long id;
    private final String name;
    private final String category;
    private final String description;
    private final ProductStatus productStatus;

    @Builder
    private ProductListResponse(Long id, String name, String category, String description, ProductStatus productStatus) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.productStatus = productStatus;
    }
}
