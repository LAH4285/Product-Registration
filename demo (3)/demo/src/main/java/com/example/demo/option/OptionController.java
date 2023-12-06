package com.example.demo.option;

import com.example.demo.core.error.exception.Exception404;
import com.example.demo.core.utils.ApiUtils;
import com.example.demo.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OptionController {
    private final OptionService optionService;

    @PostMapping("/option/save")
    public ResponseEntity save(@RequestBody @Valid OptionResponse.FindAllDTO optionDTO) {

        Option option = optionService.save(optionDTO);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(option);
        if(option != null) {
            return ResponseEntity.ok(apiResult);
        } else {
            return new ResponseEntity<>("상품없음.", HttpStatus.NOT_FOUND);
        }
    }
    /**
    * @Param id
    * (ProductId)
    * @return
    * (List<OptionResponse.FindByProductIdDTO>)
    **/
    @GetMapping("/products/{id}/options")
    public ResponseEntity<?> findByid(@PathVariable Long id){
        List<OptionResponse.FindByProductIdDTO> optionResponses = optionService.findByProductId(id);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(optionResponses);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/options")
    public ResponseEntity<?> findAll(){
        List<OptionResponse.FindAllDTO> optionResponses =
                optionService.findAll();
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(optionResponses);
        return ResponseEntity.ok(apiResult);
    }
    @PostMapping("/option/update/{id}")
    public ResponseEntity<String> updateOption(
            @PathVariable Long id, @RequestBody OptionResponse.FindByProductIdDTO requestDTO) {
        try {
            requestDTO.setId(id);
            optionService.update(requestDTO);
            return ResponseEntity.ok("옵션 업데이트 성공");
        } catch (Exception404 e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/option/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        optionService.delete(id);
        return ResponseEntity.ok("삭제 성공");
    }
}
