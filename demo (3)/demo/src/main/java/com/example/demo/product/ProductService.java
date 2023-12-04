package com.example.demo.product;

import com.example.demo.core.error.exception.Exception400;
import com.example.demo.core.error.exception.Exception404;
import com.example.demo.core.error.exception.Exception500;
import com.example.demo.option.Option;
import com.example.demo.option.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    @Transactional
    public void save(ProductResponse.FindAllDTO productDTO){
        Product product = productDTO.toEntity();
        productRepository.save(product);
    }

    // ** 전체상품 검색
    public List<ProductResponse.FindAllDTO> findAll(int page) {
        Pageable pageable = PageRequest.of(page, 3);
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductResponse.FindAllDTO> productDTOs =
                productPage.getContent().stream().map(ProductResponse.FindAllDTO::new)
                .collect(Collectors.toList());

        return productDTOs;
    }

    // ** 개별상품 검색
    // DTO안에 리스트가 반환되기 떄문에 ProductResponse.FindByIdDTO사용
    public ProductResponse.FindByIdDTO findByid(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new Exception404("해당 상품을 찾을 수 없습니다> : " + id));

        // ** product.getId() 로 Option 상품 검색.
        List<Option> optionList = optionRepository.findByProductId(product.getId());

        // ** 검색이 완료된 제품 반환.
        return new ProductResponse.FindByIdDTO(product, optionList);
    }

    @Transactional
    public void update(ProductResponse.FindAllDTO productDTO){
        Optional<Product> optionalProduct = productRepository.findById(productDTO.getId());

        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.updateFromDTO(productDTO);

            productRepository.save(product);
        }

    }

    @Transactional
    public void delete(Long id){
        productRepository.deleteById(id);
    }
}
