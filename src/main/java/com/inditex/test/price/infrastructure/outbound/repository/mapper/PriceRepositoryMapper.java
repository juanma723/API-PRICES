package com.inditex.test.price.infrastructure.outbound.repository.mapper;

import com.inditex.test.price.domain.Price;
import com.inditex.test.price.infrastructure.outbound.repository.entity.PriceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceRepositoryMapper {


    Price toDomain(PriceEntity priceEntity);
}
