package com.example.demo.option;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OptionService {
    private final OptionRepository optionRepository;

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
}
