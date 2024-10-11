package com.inditex.test.price.infrastructure.inbound.rest;

import com.inditex.test.price.application.port.inbound.FindPriceByFilter;
import com.inditex.test.price.domain.Price;
import com.inditex.test.price.domain.PriceFilter;
import com.inditex.test.price.infrastructure.inbound.rest.mapper.PriceRestMapper;
import lombok.AllArgsConstructor;
import org.openapitools.api.PricesApi;
import org.openapitools.model.PriceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class PriceRestController implements PricesApi {

    private final FindPriceByFilter findPriceByFilter;
    private final PriceRestMapper priceRestMapper;


    @Override
    public ResponseEntity<PriceResponse> getPrice(
            @RequestParam(value = "time") String time,
            @RequestParam(value = "day") String day,
            @RequestParam(value = "productId") Long productId,
            @RequestParam(value = "brandId") Long brandId,
            @RequestParam(value = "utc", required = false, defaultValue = "2") Integer utc) {
        Optional<Price> price = findPriceByFilter.execute(PriceFilter.builder()
                .brandId(brandId)
                .utc(utc)
                .productId(productId)
                .day(day)
                .time(time)
                .build());

        return price.map(value ->
                new ResponseEntity<>(priceRestMapper.toResponse(value),
                        HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new PriceResponse(), HttpStatus.OK));

    }


}