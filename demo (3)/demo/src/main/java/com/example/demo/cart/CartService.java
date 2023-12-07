package com.example.demo.cart;

import com.example.demo.User.User;
import com.example.demo.core.error.exception.Exception400;
import com.example.demo.core.error.exception.Exception404;
import com.example.demo.core.error.exception.Exception500;
import com.example.demo.option.Option;
import com.example.demo.option.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final OptionRepository optionRepository;

    public CartResponse.FindAllDTO findAll() {
        List<Cart> cartList = cartRepository.findAll();
        return new CartResponse.FindAllDTO(cartList);
    }

    // ** 카트 상품 추가
    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> saveDTO, User user) {
        // ** 동일한 데이터를 묶어줌
        // ** 동일한 상품 예외처리
        Set<Long> optionsid = new HashSet<>();
        // saveDTO의 cart의 옵션아이디가 중복이라면 예외처리
        for(CartRequest.SaveDTO cart : saveDTO){
            if(!optionsid.add(cart.getOptionId()));
            throw new Exception400("이미 동일한 상품 옵션이 있습니다" + cart.getOptionId());
        }

        List<Cart> cartList = saveDTO.stream().map( cartDTO ->{
            Option option = optionRepository.findById(cartDTO.getOptionId()).orElseThrow(
                    () -> new Exception404("해당 상품 옵션을 찾을 수 없습니다." + cartDTO.getOptionId())
            );
            return cartDTO.toEntity(option, user);
        }).collect(Collectors.toList());

        cartList.forEach( cart -> {
            try{
                cartRepository.save(cart);
            }catch (Exception e){
                throw new Exception500("카트에 담던 중 오류가 발생했습니다." + e.getMessage());
            }
        });
    }

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTO, User user) {
        List<Cart> cartList = cartRepository.findAllByUserId(user.getId());

        List<Long> cartIds = cartList.stream().map(cart -> cart.getId()).collect(Collectors.toList());
        List<Long> requestIds = requestDTO.stream().map(dto -> dto.getCartId()).collect(Collectors.toList());

        if(cartIds.size() == 0){
            throw new Exception404("주문 가능한 상품이 없습니다.");
        }

        if(requestIds.stream().distinct().count() != requestIds.size()){
            throw new Exception400("동일한 카트 아이디를 주문할 수 없습니다.");
        }

        for(Long requestid : requestIds){
            if(!cartIds.contains(requestid)){
                throw new Exception400("카트에 없는 상품을 주문할 수 없습니다." + requestid);
            }
        }

        for(CartRequest.UpdateDTO updateDTO : requestDTO){
            for(Cart cart : cartList){
                if(cart.getId() == updateDTO.getCartId()){
                    cart.update(updateDTO.getQuantity(), cart.getPrice() * cart.getQuantity());
                }
            }
        }

        return new CartResponse.UpdateDTO(cartList);
    }
}
