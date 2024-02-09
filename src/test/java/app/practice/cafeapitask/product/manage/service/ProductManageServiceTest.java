package app.practice.cafeapitask.product.manage.service;

import app.practice.cafeapitask.global.exception.CustomException;
import app.practice.cafeapitask.owner.domain.Owner;
import app.practice.cafeapitask.owner.domain.OwnerRepository;
import app.practice.cafeapitask.product.domain.Product;
import app.practice.cafeapitask.product.domain.ProductRepository;
import app.practice.cafeapitask.product.domain.Size;
import app.practice.cafeapitask.product.manage.dto.request.ProductCreateRequest;
import app.practice.cafeapitask.product.manage.dto.response.ProductCreateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductManageServiceTest {

    private final Long ownerId = 1L;
    private final Long productId = 1L;
    @InjectMocks
    private ProductManageService productManageService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OwnerRepository ownerRepository;
    private ProductCreateRequest request;

    @BeforeEach
    void setUp() {
        request = ProductCreateRequest.builder()
                .category("음료")
                .name("아메리카노")
                .price(3000.0)
                .cost(1000.0)
                .description("시원하고 맛있는 아메리카노")
                .barcode("lllliili11l1")
                .expirationDate(LocalDate.now().plusDays(7))
                .size(Size.LARGE)
                .build();
    }

    @Test
    @DisplayName("상품 생성 성공 시 ProductCreateResponse 반환한다.")
    void whenCreateProduct_thenReturnProductCreateResponse() {
        // Given
        // When
        when(ownerRepository.findById(ownerId))
                .thenReturn(Optional.of(Owner.builder().id(ownerId).build()));

        when(productRepository.saveAndFlush(any()))
                .thenReturn(Product.builder().id(productId).build()); // 상품 ID 설정을 위한 간단한 구현

        ProductCreateResponse response = productManageService.createProduct(ownerId, request);

        // Then
        assertNotNull(response);
        assertEquals(productId, response.getId());
        verify(productRepository).saveAndFlush(any());
    }

    @Test
    @DisplayName("Owner가 존재하지 않을 때 CustomException 발생")
    void whenOwnerNotFound_thenThrowCustomException() {
        // Given
        when(ownerRepository.findById(ownerId)).thenReturn(Optional.empty());
        // When & Then
        assertThrows(CustomException.class, () -> productManageService.createProduct(ownerId, request));
    }

}