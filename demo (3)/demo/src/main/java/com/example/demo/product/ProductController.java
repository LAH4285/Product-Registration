package com.example.demo.product;

import com.example.demo.core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

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


    // 업데이트 폼을 사용하지 않고 바로 /update/{id}로 이동하여 업데이트 수행
    @PostMapping("/update/{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid ProductResponse.FindAllDTO productDTO) {
        // 여기에서 업데이트 로직 수행

        productService.update(productDTO);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok( ApiUtils.success(null) );
    }
}
