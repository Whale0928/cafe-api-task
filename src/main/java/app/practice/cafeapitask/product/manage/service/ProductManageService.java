package app.practice.cafeapitask.product.manage.service;

import app.practice.cafeapitask.global.exception.CustomException;
import app.practice.cafeapitask.owner.domain.Owner;
import app.practice.cafeapitask.owner.domain.OwnerRepository;
import app.practice.cafeapitask.product.domain.Product;
import app.practice.cafeapitask.product.domain.ProductRepository;
import app.practice.cafeapitask.product.manage.dto.ProductMessage;
import app.practice.cafeapitask.product.manage.dto.request.ProductCreateRequest;
import app.practice.cafeapitask.product.manage.dto.request.ProductUpdateRequest;
import app.practice.cafeapitask.product.manage.dto.response.ProductCreateResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static app.practice.cafeapitask.global.exception.ErrorMessages.NOT_ALLOWED_PRODUCT_UPDATE;
import static app.practice.cafeapitask.global.exception.ErrorMessages.NOT_FOUND_OWNER;
import static app.practice.cafeapitask.global.exception.ErrorMessages.NOT_FOUND_PRODUCT;
import static app.practice.cafeapitask.product.domain.ProductStatus.INACTIVE;

@RequiredArgsConstructor
@Service
public class ProductManageService {
    private final ProductRepository productRepository;
    private final OwnerRepository ownerRepository;

    private static Product ProductOf(Owner owner, ProductCreateRequest request) {
        return Product.builder()
                .owner(owner)
                .category(request.getCategory())
                .price(request.getPrice())
                .cost(request.getCost())
                .name(request.getName())
                .description(request.getDescription())
                .barcode(request.getBarcode())
                .expirationDate(request.getExpirationDate())
                .size(request.getSize())
                .build();
    }

    @Transactional
    public ProductCreateResponse createProduct(Long ownerId, ProductCreateRequest request) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_OWNER));
        Product product = productRepository.saveAndFlush(ProductOf(owner, request));
        return ProductCreateResponse.of(product.getId());
    }

    @Transactional
    public ProductMessage updateProduct(Long id, Long ownerId, ProductUpdateRequest request) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_OWNER));

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT));

        if (!owner.getId().equals(product.getOwner().getId()))
            throw new CustomException(NOT_ALLOWED_PRODUCT_UPDATE);

        return product.updateProduct(request);
    }

    @Transactional
    public ProductMessage deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT));
        return product.updateProductStatus(INACTIVE);
    }


}
