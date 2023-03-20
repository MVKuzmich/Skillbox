package com.example.simpleshop.rest;

import com.example.simpleshop.dto.DiscountCreateEditDto;
import com.example.simpleshop.dto.ProductCreateEditDto;
import com.example.simpleshop.dto.ProductReadDto;

import com.example.simpleshop.service.ProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductRestController {

    private final ProductService productService;

    @GetMapping
    public List<ProductReadDto> findAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProductReadDto findById(@PathVariable("id") Long id) {
        return productService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProductReadDto create(@RequestBody ProductCreateEditDto productDto) {
        return productService.create(productDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProductReadDto update(@PathVariable("id") Long id, @RequestBody ProductCreateEditDto productDto) {
        return productService.update(id, productDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProductReadDto updateDiscountOnly(@PathVariable("id") Long id, @RequestBody DiscountCreateEditDto discountDto) {
        return productService.updateDiscount(id, discountDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable("id") Long id) {
        if (!productService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


}
