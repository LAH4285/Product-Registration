package com.example.demo.option;

import com.example.demo.core.utils.ApiUtils;
import com.example.demo.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OptionController {
    private final OptionService optionService;

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

}
