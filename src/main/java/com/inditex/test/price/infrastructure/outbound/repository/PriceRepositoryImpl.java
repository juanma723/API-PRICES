package com.inditex.test.price.infrastructure.outbound.repository;

import com.inditex.test.price.application.port.outbound.PriceRepository;
import com.inditex.test.price.domain.Price;
import com.inditex.test.price.domain.PriceFilter;
import com.inditex.test.price.infrastructure.outbound.repository.jpa.PriceJpaRepository;
import com.inditex.test.price.infrastructure.outbound.repository.mapper.PriceRepositoryMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@AllArgsConstructor
public class PriceRepositoryImpl implements PriceRepository {


    private final PriceJpaRepository priceJpaRepository;

    private final PriceRepositoryMapper priceRepositoryMapper;


    @Override
    public Optional<Price> findByFilter(PriceFilter priceFilter) {
        return priceJpaRepository.findByFilter(priceFilter.getBrandId(),
                priceFilter.getDateTime(),
                priceFilter.getProductId()).map(priceRepositoryMapper::toDomain);
    }
}
