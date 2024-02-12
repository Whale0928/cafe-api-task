package app.practice.cafeapitask.product.query.service;

import app.practice.cafeapitask.global.exception.CustomException;
import app.practice.cafeapitask.global.exception.ErrorMessages;
import app.practice.cafeapitask.owner.domain.Owner;
import app.practice.cafeapitask.owner.domain.OwnerRepository;
import app.practice.cafeapitask.product.domain.Product;
import app.practice.cafeapitask.product.domain.ProductRepository;
import app.practice.cafeapitask.product.query.dto.reponse.ProductDetailResponse;
import app.practice.cafeapitask.product.query.dto.reponse.ProductListResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductQueryService {
    private final ProductRepository productRepository;
    private final OwnerRepository ownerRepository;

    public List<ProductListResponse> getAllProducts(Long ownerId, Pageable pageable) {
        ownerRepository.findById(ownerId).orElseThrow(() -> new CustomException(ErrorMessages.NOT_FOUND_OWNER));
        List<Product> list = productRepository.findAllByOwnerId(ownerId, pageable);
        return list.stream()
                .map(product -> ProductListResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .category(product.getCategory())
                        .description(product.getDescription())
                        .productStatus(product.getProductStatus())
                        .build()).toList();
    }

    @Transactional
    public ProductDetailResponse getProductDetailById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMessages.NOT_FOUND_PRODUCT));
        Owner owner = product.getOwner();

        return ProductDetailResponse.builder()
                .id(product.getId())
                .category(product.getCategory())
                .price(product.getPrice())
                .cost(product.getCost())
                .name(product.getName())
                .barcode(product.getBarcode())
                .description(product.getDescription())
                .expirationDate(product.getExpirationDate())
                .size(product.getSize())
                .productStatus(product.getProductStatus())
                .ownerId(owner.getId())
                .ownerPhoneNumber(owner.getPhoneNumber())
                .build();
    }
}
