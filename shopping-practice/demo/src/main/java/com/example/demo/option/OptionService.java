package com.example.demo.option;

import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OptionService {
    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Option save(OptionResponse.FindByProductIdDTO optionDTO){
        Optional<Product> optionalProduct =
                productRepository.findById(optionDTO.getProductId());
        if(optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            Option entity = optionDTO.toEntity();
            entity.toUpdate(product);
            return optionRepository.save(entity);
        } else {
            return null;
        }
    }

    // ** 옵션 개별상품 검색
    public List<OptionResponse.FindByProductIdDTO> findByProductId(Long id){
        List<Option> optionList = optionRepository.findByProductId(id);
        List<OptionResponse.FindByProductIdDTO> optionResponses =
                optionList.stream().map(OptionResponse.FindByProductIdDTO::new)
                        .collect(Collectors.toList());
        return optionResponses;
    }
    // ** 옵션 전체상품 검색
    public List<OptionResponse.FindAllDTO> findAll() {
        List<Option> optionList = optionRepository.findAll();
        List<OptionResponse.FindAllDTO> findAllDTOS =
                optionList.stream().map(OptionResponse.FindAllDTO::new)
                        .collect(Collectors.toList());
        return findAllDTOS;
    }

    @Transactional
    public void update(OptionResponse.FindByProductIdDTO optionIdDTO){
        Optional<Option> optionalProduct = optionRepository.findById(optionIdDTO.getId());

        if(optionalProduct.isPresent()){
            Option option = optionalProduct.get();
            option.updateFromDTO(optionIdDTO);

            optionRepository.save(option);
        }
    }
    @Transactional
    public void delete(Long id){
        optionRepository.deleteById(id);
    }
}
