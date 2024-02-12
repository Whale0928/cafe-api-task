package app.practice.cafeapitask.product.query.service;

import app.practice.cafeapitask.global.exception.CustomException;
import app.practice.cafeapitask.global.exception.ErrorMessages;
import app.practice.cafeapitask.owner.domain.Owner;
import app.practice.cafeapitask.owner.domain.OwnerRepository;
import app.practice.cafeapitask.product.domain.Product;
import app.practice.cafeapitask.product.domain.ProductRepository;
import app.practice.cafeapitask.product.query.dto.reponse.ProductDetailResponse;
import app.practice.cafeapitask.product.query.dto.reponse.ProductListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static app.practice.cafeapitask.global.util.sound.InitialSound.isAlreadyInitialSound;
import static app.practice.cafeapitask.global.util.sound.InitialSound.matchInitialSound;

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

    public List<ProductListResponse> searchProductsByName(Long ownerId, String name, Pageable pageable) {
        List<Product> products;

        if (isAlreadyInitialSound(name)) {
            products = productRepository.findAllLikeName(name, pageable);
        } else {
            List<Product> paginatedProducts = productRepository.findAllByOwnerId(ownerId)
                    .stream()
                    .filter(product -> matchInitialSound(product.getName(), name))
                    .toList();
            int start = pageable.getPageNumber() * pageable.getPageSize();
            int end = Math.min((start + pageable.getPageSize()), paginatedProducts.size());
            products = paginatedProducts.subList(start, end);
        }
        return products.stream()
                .map(product -> ProductListResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .category(product.getCategory())
                        .description(product.getDescription())
                        .productStatus(product.getProductStatus())
                        .build()).toList();
    }
}
