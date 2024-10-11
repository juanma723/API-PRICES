package com.inditex.test.price.infrastructure.inbound.rest.mapper;

import com.inditex.test.price.domain.Price;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.openapitools.model.PriceResponse;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface PriceRestMapper {


    PriceResponse toResponse(Price price);





    @AfterMapping
    default void localDateTimeToString(Price source, @MappingTarget PriceResponse target) {
        if (source.getStartDate() != null && source.getEndDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            target.setStartDay(source.getStartDate().format(formatter));
            target.setStartTime(source.getStartDate().format(timeFormatter));
            target.setEndDay(source.getEndDate().format(formatter));
            target.setEndTime(source.getEndDate().format(timeFormatter));
        }
    }


}
