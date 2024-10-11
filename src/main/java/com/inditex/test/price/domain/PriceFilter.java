package com.inditex.test.price.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ValidPriceFilter
public class PriceFilter {

    private LocalDateTime dateTime;

    private Long productId;

    private Long brandId;

    private Integer utc;

    private String day;

    private String time;


    public void transformTimeAndDayToDateTime() {
        LocalTime localTime = LocalTime.parse(time);
        LocalDate localDate = LocalDate.parse(day);
        dateTime = LocalDateTime.of(localDate, localTime);
    }

}
