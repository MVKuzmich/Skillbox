package com.example.simpleshop.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.util.Map;

/*
- Информация о товаре состоит из:
	- Названия;
	- Описания;
	- Организации;
	- Цены;
	- Количества на складе;
	- Информации о скидах;
	- Ключевых слов;
	- Таблицы характеристик;

 */
@Value
public class ProductCreateEditDto {

    String name;
    String description;
    Long companyId;
    BigDecimal price;
    Long quantityInStore;
    DiscountCreateEditDto discount;
    String keyWords;
    String characteristics;


}
