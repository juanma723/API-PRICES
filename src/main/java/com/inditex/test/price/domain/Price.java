package com.inditex.test.price.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Price {

    private Long tariffId;

    private Long productId;

    private Long brandId;

    private double price;

    private String currency;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer utcDate;


    public void transformDatesToGivenUtc(Integer utc) {
        OffsetDateTime startDateWithUtc = startDate.atOffset(ZoneOffset.ofHours(utcDate));
        OffsetDateTime endDateWithUtc = endDate.atOffset(ZoneOffset.ofHours(utcDate));

        OffsetDateTime startDateTransformed = startDateWithUtc.withOffsetSameInstant(ZoneOffset.ofHours(utc));
        OffsetDateTime endDateTransformed = endDateWithUtc.withOffsetSameInstant(ZoneOffset.ofHours(utc));

        startDate = startDateTransformed.toLocalDateTime();
        endDate = endDateTransformed.toLocalDateTime();
    }
}
