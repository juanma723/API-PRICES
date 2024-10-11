package com.inditex.test.price.application.service;

import com.inditex.test.price.application.port.inbound.FindPriceByFilter;
import com.inditex.test.price.application.port.outbound.PriceRepository;
import com.inditex.test.price.domain.Price;
import com.inditex.test.price.domain.PriceFilter;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class FindPriceService implements FindPriceByFilter {

    private final PriceRepository priceRepository;
    private final Validator validator;

    @Override
    public Optional<Price> execute(PriceFilter priceFilter) {
        validateFilter(priceFilter);
        priceFilter.transformTimeAndDayToDateTime();
        Optional<Price> price = priceRepository.findByFilter(priceFilter);
        price.ifPresent(p -> p.transformDatesToGivenUtc(priceFilter.getUtc()));
        return price;
    }

    private void validateFilter(PriceFilter priceFilter) {
        Set<ConstraintViolation<PriceFilter>> violationSet = validator.validate(priceFilter);
        if (!violationSet.isEmpty()) {
            throw new ConstraintViolationException("Violations in price params", violationSet);
        }
    }
}
