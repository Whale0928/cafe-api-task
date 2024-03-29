package app.practice.cafeapitask.product.query.service;

import app.practice.cafeapitask.global.exception.CustomException;
import app.practice.cafeapitask.global.util.sound.InitialSound;
import app.practice.cafeapitask.owner.domain.Owner;
import app.practice.cafeapitask.owner.domain.OwnerRepository;
import app.practice.cafeapitask.product.domain.Product;
import app.practice.cafeapitask.product.domain.ProductRepository;
import app.practice.cafeapitask.product.query.dto.reponse.ProductDetailResponse;
import app.practice.cafeapitask.product.query.dto.reponse.ProductListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static app.practice.cafeapitask.product.domain.ProductStatus.ACTIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductQueryServiceTest {
    private List<ProductListResponse> productListResponse;
    private PageRequest pageRequest;
    private Owner owner;

    @InjectMocks
    private ProductQueryService productQueryService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OwnerRepository ownerRepository;

    @BeforeEach
    void setUp() {
        ProductListResponse response_1 = ProductListResponse.builder()
                .id(1L)
                .name("name_0")
                .category("category_0")
                .description("description_0")
                .productStatus(ACTIVE)
                .build();
        ProductListResponse response_2 = ProductListResponse.builder()
                .id(2L)
                .name("name")
                .category("category")
                .description("description")
                .productStatus(ACTIVE)
                .build();

        productListResponse = List.of(response_1, response_2);

        owner = Owner.builder()
                .id(1L)
                .phoneNumber("01012345678")
                .build();
        pageRequest = PageRequest.of(0, 10);
    }

    @Test
    @DisplayName("모든 제품 목록 조회할 수 있다.")
    void test_getAllProducts() {
        // given
        Product product1 = Product.builder()
                .id(1L)
                .name("name_0")
                .category("category_0")
                .description("description_0")
                .build();
        Product product2 = Product.builder()
                .id(2L)
                .name("name")
                .category("category")
                .description("description")
                .build();
        List<Product> productList = List.of(product1, product2);

        when(ownerRepository.findById(owner.getId())).thenReturn(java.util.Optional.of(owner));
        when(productRepository.findAllByOwnerId(owner.getId(), pageRequest)).thenReturn(productList);

        // when
        List<ProductListResponse> result = productQueryService.getAllProducts(owner.getId(), pageRequest);

        // then
        assertEquals(productListResponse.size(), result.size());
        assertEquals(result.get(0).getName(), productListResponse.get(0).getName());
        assertEquals(result.get(0).getCategory(), productListResponse.get(0).getCategory());
        assertEquals(result.get(0).getDescription(), productListResponse.get(0).getDescription());
    }

    @Test
    @DisplayName("제품 목록이 비어있을 때, 빈 목록을 반환한다.")
    void test_getAllProducts_empty() {
        // given
        List<Product> emptyProductList = new ArrayList<>();

        when(ownerRepository.findById(owner.getId())).thenReturn(java.util.Optional.of(owner));
        when(productRepository.findAllByOwnerId(owner.getId(), pageRequest)).thenReturn(emptyProductList);

        // when
        List<ProductListResponse> result = productQueryService.getAllProducts(owner.getId(), pageRequest);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Owner가 존재하지 않을 때 CustomException 발생")
    void test_getAllProducts_ownerNotFound() {
        // given
        Long nonExistentOwnerId = 999L;

        when(ownerRepository.findById(nonExistentOwnerId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(CustomException.class, () -> productQueryService.getAllProducts(nonExistentOwnerId, pageRequest));
    }

    @Test
    @DisplayName("getAllProducts가 ProductListResponse의 리스트를 올바르게 반환한다.")
    void test_getAllProducts_correctResponse() {
        // given
        Product product1 = Product.builder()
                .id(1L)
                .name("name_0")
                .category("category_0")
                .description("description_0")
                .build();
        Product product2 = Product.builder()
                .id(2L)
                .name("name")
                .category("category")
                .description("description")
                .build();
        List<Product> productList = List.of(product1, product2);

        when(ownerRepository.findById(owner.getId())).thenReturn(java.util.Optional.of(owner));
        when(productRepository.findAllByOwnerId(owner.getId(), pageRequest)).thenReturn(productList);

        // when
        List<ProductListResponse> result = productQueryService.getAllProducts(owner.getId(), pageRequest);

        // then
        assertEquals(productListResponse.size(), result.size());
        for (int i = 0; i < result.size(); i++) {
            assertEquals(productListResponse.get(i).getId(), result.get(i).getId());
            assertEquals(productListResponse.get(i).getName(), result.get(i).getName());
            assertEquals(productListResponse.get(i).getCategory(), result.get(i).getCategory());
            assertEquals(productListResponse.get(i).getDescription(), result.get(i).getDescription());
            assertEquals(productListResponse.get(i).getProductStatus(), result.get(i).getProductStatus());
        }
    }

    @Test
    @DisplayName("제품 ID를 기반으로 제품의 상세 정보를 조회할 수 있다.")
    void test_getProductById() {
        // given
        Long productId = 1L;
        Product product = Product.builder()
                .id(productId)
                .owner(owner)
                .name("name_0")
                .price(1000.0)
                .cost(500.0)
                .barcode("barcode_0")
                .category("category_0")
                .description("description_0")
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // when
        ProductDetailResponse response = productQueryService.getProductDetailById(productId);

        // then
        assertEquals(product.getId(), response.getId());
        assertEquals(product.getName(), response.getName());
    }

    @Test
    @DisplayName("제품 ID를 기반으로 제품의 상세 정보를 조회할 때, 제품이 존재하지 않으면 CustomException 발생")
    void test_getProductById_productNotFound() {
        // given
        Long nonExistentProductId = 999L;

        when(productRepository.findById(nonExistentProductId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(CustomException.class, () -> productQueryService.getProductDetailById(nonExistentProductId));
    }

    @Test
    @DisplayName("이름을 기반으로 제품을 검색할 수 있다.")
    void searchProductsByName_exactName() {
        // given
        Long ownerId = 1L;
        String searchName = "크림";
        Pageable pageable = PageRequest.of(0, 10);

        Product product1 = Product.builder().id(1L).owner(owner).name(searchName).build();
        Product product2 = Product.builder().id(2L).owner(owner).name(searchName).build();
        List<Product> products = List.of(product1, product2);

        // when
        when(productRepository.findAllByOwnerIdAndNameContaining(ownerId, searchName, pageable)).thenReturn(products);
        List<ProductListResponse> list = productQueryService.searchProductsByName(ownerId, searchName, pageable);

        // then
        assertEquals(products.size(), list.size());
    }

    @Test
    @DisplayName("이름을 기반으로 제품을 검색할 때, 초성이 포함된 제품을 반환한다.")
    void searchProductsByName_containName() {
        // given
        Long ownerId = 1L;
        String searchName = "ㅋㄹ";
        Pageable pageable = PageRequest.of(0, 10);

        Product product1 = Product.builder().id(1L).owner(owner).name("크림").build();
        Product product2 = Product.builder().id(2L).owner(owner).name("크림").build();
        List<Product> products = List.of(product1, product2);

        // when
        when(productRepository.findAllByOwnerId(ownerId)).thenReturn(products);
        List<ProductListResponse> list = productQueryService.searchProductsByName(ownerId, searchName, pageable);

        // then
        assertEquals(products.size(), list.size());

        for (ProductListResponse product : list) {
            assertTrue(InitialSound.matchInitialSound(product.getName(), searchName));
        }
    }

    @Test
    @DisplayName("제품 이름이 null인 경우, 빈 리스트를 반환한다.")
    void searchProductsByName_nullName() {
        // given
        Long ownerId = 1L;
        String searchName = null;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        List<ProductListResponse> list = productQueryService.searchProductsByName(ownerId, searchName, pageable);

        // then
        assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("제품 이름이 빈 문자열인 경우, 빈 리스트를 반환한다.")
    void searchProductsByName_emptyName() {
        // given
        Long ownerId = 1L;
        String searchName = "";
        Pageable pageable = PageRequest.of(0, 10);

        // when
        List<ProductListResponse> list = productQueryService.searchProductsByName(ownerId, searchName, pageable);

        // then
        assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("제품 이름이 초성이 아닌 문자열인 경우, 해당 문자열을 포함하는 제품을 반환한다.")
    void searchProductsByName_nonInitialSoundName() {
        // given
        Long ownerId = 1L;
        String searchName = "크림";
        Pageable pageable = PageRequest.of(0, 10);

        Product product1 = Product.builder().id(1L).owner(owner).name("크림").build();
        Product product2 = Product.builder().id(2L).owner(owner).name("크림").build();
        List<Product> products = List.of(product1, product2);

        // when
        when(productRepository.findAllByOwnerIdAndNameContaining(ownerId, searchName, pageable)).thenReturn(products);
        List<ProductListResponse> list = productQueryService.searchProductsByName(ownerId, searchName, pageable);

        // then
        assertEquals(products.size(), list.size());
    }

}