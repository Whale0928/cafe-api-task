package app.practice.cafeapitask.product.manage.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/manage")
@RequiredArgsConstructor
public class ProductManageController {

    /**
     * 상품등록 API
     *
     * @return the response entity
     */
    @PostMapping("")
    public ResponseEntity<?> createProduct() {
        return ResponseEntity.ok().build();
    }

    /**
     * 상품정보 수정 API
     *
     * @param id the id
     * @return the response entity
     */
    @PatchMapping("/{id]")
    public ResponseEntity<?> updateProduct(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    /**
     * 상품 삭제 API
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }
}
