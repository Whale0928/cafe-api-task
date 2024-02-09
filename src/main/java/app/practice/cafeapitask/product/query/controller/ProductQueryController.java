package app.practice.cafeapitask.product.query.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product/query")
@RequiredArgsConstructor
public class ProductQueryController {

    /**
     * 모든 제품 목록 조회 API
     * todo 페이지네이션을 적용하여 모든 제품의 목록을 조회하는 기능을 구현해야 합니다.
     *
     * @param page the page
     * @param size the size
     * @return the all products
     */
    @GetMapping
    public ResponseEntity<?> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok().build();
    }

    /**
     * todo 제품 ID를 기반으로 제품의 상세 정보를 조회하는 기능을 구현해야 합니다.
     *
     * @param id the id
     * @return the product by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    /**
     * todo 제품 이름으로 검색 API
     * 제품 이름을 포함하는 검색 기능을 구현해야 한다. 이름으로 제품을 검색할 때는 페이징을 적용해야 합니다.
     *
     * @param name the name
     * @param page the page
     * @param size the size
     * @return the response entity
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchProductsByName(@RequestParam String name,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok().build();
    }
}
