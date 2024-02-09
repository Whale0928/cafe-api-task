package app.practice.cafeapitask.product.manage.Controller;

import app.practice.cafeapitask.global.Object.GlobalResponse;
import app.practice.cafeapitask.global.exception.GlobalExceptionHandler;
import app.practice.cafeapitask.product.domain.Size;
import app.practice.cafeapitask.product.manage.dto.request.ProductCreateRequest;
import app.practice.cafeapitask.product.manage.dto.response.ProductCreateResponse;
import app.practice.cafeapitask.product.manage.service.ProductManageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class ProductManageControllerTest {

    protected ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private ProductManageService productManageService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(1L, null, Collections.emptyList());
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        this.mockMvc = MockMvcBuilders.standaloneSetup(new ProductManageController(productManageService))
                .setControllerAdvice(new GlobalExceptionHandler()) // GlobalExceptionHandler 인스턴스 직접 생성
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터 추가
                .alwaysDo(print())
                .build();
    }


    @Test
    @DisplayName("상품을 등록할 수 있다.")
    void test_() throws Exception {
        Long id = 1L;

        // given
        ProductCreateRequest req = ProductCreateRequest.builder()
                .category("음료")
                .name("아메리카노")
                .price(3000.0)
                .cost(1000.0)
                .description("시원하고 맛있는 아메리카노")
                .barcode("lllliili11l1")
                .expirationDate(LocalDate.now().plusDays(7))
                .size(Size.LARGE)
                .build();
        var res = ProductCreateResponse.of(1L);
        GlobalResponse<ProductCreateResponse> success = GlobalResponse.success(res);

        // when
        when(productManageService.createProduct(id, req)).thenReturn(res);

        // then
        mockMvc.perform(post("/api/product/manage")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}