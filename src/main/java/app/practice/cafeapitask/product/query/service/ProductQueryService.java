package app.practice.cafeapitask.product.query.service;

import app.practice.cafeapitask.global.exception.CustomException;
import app.practice.cafeapitask.global.exception.ErrorMessages;
import app.practice.cafeapitask.owner.domain.OwnerRepository;
import app.practice.cafeapitask.product.domain.Product;
import app.practice.cafeapitask.product.domain.ProductRepository;
import app.practice.cafeapitask.product.query.dto.reponse.ProductListResponse;
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
}
