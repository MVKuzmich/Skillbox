package com.example.bookshopapp.mapper;

import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "authors", source = "authors", qualifiedByName = "authorsToString")
    @Mapping(target = "status", constant = "none")
    @Mapping(target = "isBestseller", source = "book.bestseller", qualifiedByName = "integerToBoolean")
    BookDto toBookDto(BookModelDto book, List<AuthorDto> authors);


    @Mapping(target = "isBestseller", source = "book.isBestseller", qualifiedByName = "integerToBoolean")
    @Mapping(target = "authors", source = "authors", qualifiedByName = "authorsToString")
    @Mapping(target = "status", constant = "none")
    @Mapping(target = "discount", source = "book", qualifiedByName = "priceToDiscount")
    @Mapping(target = "price", source = "book.priceOld")
    @Mapping(target = "discountPrice", source = "book", qualifiedByName = "getDiscountPrice")
    BookDto toBookDto(Book book, List<AuthorDto> authors);

    @Mapping(target = "status", constant = "none")
    @Mapping(target = "isBestseller", source = "book.bestseller", qualifiedByName = "integerToBoolean")
    BookUnitDto toBookUnitDto(BookUnitModelDto book,
                              List<AuthorDto> authors,
                              List<ReviewDto> reviews,
                              List<TagDto> tags,
                              List<BookFileDto> bookFiles);

    @Named("authorsToString")
    default String authorsToString(List<AuthorDto> authors) {
        return authors.size() == 1 ? authors.get(0).getName() : authors.get(0).getName().concat(" and others");
    }

    @Named("integerToBoolean")
    default boolean integerToBoolean(Integer bestseller) {
        return bestseller == 1;
    }

    @Named("priceToDiscount")
    default Integer priceToDiscount(Book book) {
        return (int) book.getPrice() * 100;
    }

    @Named("getDiscountPrice")
    default Integer getDiscountPrice(Book book) {
        return (int) (book.getPriceOld() * (1 - book.getPrice()));
    }

}
