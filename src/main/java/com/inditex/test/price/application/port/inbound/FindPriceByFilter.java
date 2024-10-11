package com.inditex.test.price.application.port.inbound;

import com.inditex.test.price.domain.Price;
import com.inditex.test.price.domain.PriceFilter;

import java.util.Optional;

public interface FindPriceByFilter {
    Optional<Price> execute(PriceFilter priceFilter);
}
