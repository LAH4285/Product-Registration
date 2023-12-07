package com.example.demo.order;

import com.example.demo.core.security.CustomUserDetails;
import com.example.demo.core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
// 결제명세
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders/save")
    public ResponseEntity<?> save(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        OrderResponse.FindByIdDTO findByIdDTO = orderService.save(customUserDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(findByIdDTO));
    }

    // **
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        OrderResponse.FindByIdDTO findByIdDTO = orderService.findById(id);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(findByIdDTO);
        return ResponseEntity.ok(ApiUtils.success(apiResult));
    }

    @PostMapping("/orders/clear")
    public ResponseEntity<?> clear(){
        orderService.clear();
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(ApiUtils.success(apiResult));
    }

}
