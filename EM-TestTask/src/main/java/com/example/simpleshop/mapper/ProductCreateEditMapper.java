package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.DiscountCreateEditDto;
import com.example.simpleshop.dto.ProductCreateEditDto;
import com.example.simpleshop.dto.ProductReadDto;
import com.example.simpleshop.entity.Company;
import com.example.simpleshop.entity.Discount;
import com.example.simpleshop.entity.Product;
import com.example.simpleshop.repository.CompanyRepository;
import com.example.simpleshop.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductCreateEditMapper implements Mapper<ProductCreateEditDto, Product> {

    private final CompanyRepository companyRepository;
    private final DiscountRepository discountRepository;

    @Override
    public Product map(ProductCreateEditDto fromObject) {
        Product product = new Product();
        copy(fromObject, product);
        return product;
    }

    @Override
    public Product map(ProductCreateEditDto productDto, Product product) {
        copy(productDto, product);
        return product;
    }

    public Product mapWithDiscountOnly(DiscountCreateEditDto discountDto, Product product) {
        product.setDiscount(getDiscount(discountDto.getDiscountValue(), discountDto.getDiscountValue()));
        return product;
    }

    private void copy(ProductCreateEditDto productDto, Product product) {
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCompany(companyRepository.findById(productDto.getCompanyId()).orElseThrow());
        product.setPrice(productDto.getPrice());
        product.setQuantityInStore(productDto.getQuantityInStore());
        product.setDiscount(getDiscount(productDto.getDiscount().getDiscountValue(), productDto.getDiscount().getDiscountDuration()));
        product.setKeyWords(productDto.getKeyWords());
        product.setCharacteristics(productDto.getCharacteristics());
    }

    private Discount getDiscount(Integer discountValue, Integer discountDuration) {
        return discountRepository.findByValueAndDuration(discountValue, discountDuration)
                .orElse(discountRepository.saveAndFlush(new Discount(discountValue, discountDuration)));
    }
}
