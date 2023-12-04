package com.example.demo.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;


    public List<ProductDTO> findAll(int page) {
        Pageable pageable = PageRequest.of(page, 3);
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductDTO> productDTOS = productPage.getContent().stream().map(ProductDTO::new)
                .collect(Collectors.toList());

        return productDTOS;
    }
}
