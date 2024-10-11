package com.inditex.test.price.application.port.outbound;

import com.inditex.test.price.domain.Price;
import com.inditex.test.price.domain.PriceFilter;

import java.util.Optional;

public interface PriceRepository {
    Optional<Price> findByFilter(PriceFilter priceFilter);
}
