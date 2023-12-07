package com.example.demo.product;

import com.example.demo.core.error.exception.Exception404;
import com.example.demo.core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    // ** 상품 등록
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody @Valid ProductResponse.FindAllDTO findAllDTO, Error error){
        productService.save(findAllDTO);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok( ApiUtils.success(null) );
    }

    // ** 전체 상품 확인
    @GetMapping("/products")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page){
        List<ProductResponse.FindAllDTO> productResponses = productService.findAll(page);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(productResponses);
        return ResponseEntity.ok(apiResult);
    }
    // ** 개별 상품 확인
    @GetMapping("/products/{id}")
    public ResponseEntity<?> findByid(@PathVariable Long id){
        ProductResponse.FindByIdDTO productResponses = productService.findByid(id);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(productResponses);
        return ResponseEntity.ok(apiResult);
    }
    // ** 상품 수정
    @PostMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductResponse.FindByIdDTO requestDTO) {
        try {
            // 기존 상품 조회
            ProductResponse.FindByIdDTO existingProduct = productService.findByid(id);

            // 기존 상품의 정보를 새로운 DTO 정보로 업데이트
            existingProduct.setProductName(requestDTO.getProductName());
            existingProduct.setDescription(requestDTO.getDescription());
            existingProduct.setImage(requestDTO.getImage());
            existingProduct.setPrice(requestDTO.getPrice());

            // 상품 업데이트
            productService.update(existingProduct);

            return ResponseEntity.ok("상품 업데이트 성공");
        } catch (Exception404 e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // ** 상품 삭제
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok( ApiUtils.success(null) );
    }
}
