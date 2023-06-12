package com.example.bookshopapp.service;

import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.data.bookrate.BookRateEntity;
import com.example.bookshopapp.dto.RateRangeDto;
import com.example.bookshopapp.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RatingService {

    private final RatingRepository ratingRepository;

    @Transactional
    public BookRateEntity save(Book book, Integer value) {
        return ratingRepository.saveAndFlush(new BookRateEntity(book, value));
    }

    public List<RateRangeDto> getBookRateRangeList(String bookSlug) {
        List<RateRangeDto> rateRangeList = ratingRepository.findRateRangeForBook(bookSlug);
        IntStream.rangeClosed(1, 5).forEach(item -> {
            if (rateRangeList.stream().noneMatch(rateRangeDto -> rateRangeDto.getRateValue() == item)) {
                rateRangeList.add(new RateRangeDto() {
                    @Override
                    public Integer getRateValue() {
                        return item;
                    }

                    @Override
                    public Integer getRateCount() {
                        return 0;
                    }
                });
            }
        });
        rateRangeList.sort(Comparator.comparing(RateRangeDto::getRateValue).reversed());
        return rateRangeList;
    }
}
