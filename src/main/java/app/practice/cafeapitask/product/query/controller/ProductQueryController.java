package app.practice.cafeapitask.product.query.controller;

import app.practice.cafeapitask.global.Object.GlobalResponse;
import app.practice.cafeapitask.product.query.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/api/product/query")
@RequiredArgsConstructor
public class ProductQueryController {

    private final ProductQueryService queryService;


    /**
     * 모든 제품 목록 조회 API
     *
     * @param pageable the pageable
     * @return the all products
     */
    @GetMapping
    public ResponseEntity<?> getAllProducts(@PageableDefault(size = 15, sort = "id", direction = DESC) Pageable pageable) {
        Long ownerId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity
                .ok(GlobalResponse.success(queryService.getAllProducts(ownerId, pageable)));
    }

    /**
     * 제품 ID를 기반으로 제품의 상세 정보를 조회하는 기능.
     *
     * @param id the id
     * @return the product by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductDetailById(@PathVariable Long id) {
        return ResponseEntity.ok(GlobalResponse.success(queryService.getProductDetailById(id)));
    }

    /**
     * 제품 이름을 포함하는 검색 기능
     *
     * @param name     the name
     * @param pageable the pageable
     * @return the response entity
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchProductsByName(@RequestParam String name,
                                                  @PageableDefault(size = 15, sort = "id", direction = DESC) Pageable pageable) {
        Long ownerId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(GlobalResponse.success(queryService.searchProductsByName(ownerId, name, pageable)));
    }
}
