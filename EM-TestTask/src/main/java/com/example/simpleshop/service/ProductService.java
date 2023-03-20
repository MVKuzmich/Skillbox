package com.example.simpleshop.service;

import com.example.simpleshop.dto.DiscountCreateEditDto;
import com.example.simpleshop.dto.ProductCreateEditDto;
import com.example.simpleshop.dto.ProductReadDto;
import com.example.simpleshop.mapper.ProductCreateEditMapper;
import com.example.simpleshop.mapper.ProductReadMapper;
import com.example.simpleshop.repository.DiscountRepository;
import com.example.simpleshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
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
    private final DiscountRepository discountRepository;
    private final ProductReadMapper productReadMapper;
    private final ProductCreateEditMapper productCreateEditMapper;

    public List<ProductReadDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productReadMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<ProductReadDto> getById(Long id) {
        return productRepository.findById(id)
                .map(productReadMapper::map);

    }

    @Transactional
    public ProductReadDto create(ProductCreateEditDto productDto) {
        return Optional.of(productDto)
                .map(productCreateEditMapper::map)
                .map(productRepository::save)
                .map(productReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<ProductReadDto> update(Long productId, ProductCreateEditDto productDto) {
        return productRepository.findById(productId)
                .map(entity -> productCreateEditMapper.map(productDto, entity))
                .map(productRepository::saveAndFlush)
                .map(productReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return productRepository.findById(id)
                .map(entity -> {
                    productRepository.delete(entity);
                    productRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public Optional<ProductReadDto> updateDiscount(Long productId, DiscountCreateEditDto discountDto) {

        return productRepository.findById(productId)
                .map(entity -> productCreateEditMapper.mapWithDiscountOnly(discountDto, entity))
                .map(productRepository::saveAndFlush)
                .map(productReadMapper::map);

    }

}

