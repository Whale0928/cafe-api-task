package app.practice.cafeapitask.product.query.controller;

import app.practice.cafeapitask.global.exception.GlobalExceptionHandler;
import app.practice.cafeapitask.product.query.dto.reponse.ProductListResponse;
import app.practice.cafeapitask.product.query.service.ProductQueryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Collections;
import java.util.List;

import static app.practice.cafeapitask.product.domain.ProductStatus.ACTIVE;
import static app.practice.cafeapitask.product.domain.ProductStatus.INACTIVE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class ProductQueryControllerTest {
    protected ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private ProductQueryService queryService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(1L, null, Collections.emptyList());
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();


        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        this.mockMvc = MockMvcBuilders.standaloneSetup(new ProductQueryController(queryService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(resolver)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("모든 제품 목록을 조회 할 수 있는 API")
    void getAllProducts() throws Exception {
        Pageable pageable = PageRequest.of(0, 15);
        // given
        ProductListResponse response1 = ProductListResponse.builder()
                .id(1L)
                .name("아메리카노")
                .category("음료")
                .description("커피")
                .productStatus(INACTIVE)
                .build();
        ProductListResponse response2 = ProductListResponse.builder()
                .id(2L)
                .name("카페라떼")
                .category("음료")
                .description("커피")
                .productStatus(ACTIVE)
                .build();
        List<ProductListResponse> ResponseList = List.of(response1, response2);

        // when
        when(queryService.getAllProducts(anyLong(), any(Pageable.class))).thenReturn(ResponseList);
        // then
        mockMvc.perform(get("/api/product/query")
                        .param("page", "0")
                        .param("size", "15"))
                .andExpect(status().isOk());

    }

}