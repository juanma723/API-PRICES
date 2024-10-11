package test.application.service

import com.inditex.test.price.application.port.outbound.PriceRepository
import com.inditex.test.price.application.service.FindPriceService
import com.inditex.test.price.domain.Price
import com.inditex.test.price.domain.PriceFilter
import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validator
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class FindPriceServiceSpec extends Specification {


    @Subject
    private FindPriceService priceService
    private PriceRepository priceRepository
    private Validator validator


    def setup() {
        priceRepository = Mock()
        validator = Mock()
        priceService = new FindPriceService(priceRepository, validator)
    }


    def "Given a filter, when find price, then return price"() {
        given:
        PriceFilter filter = new PriceFilter()
        filter.setUtc(2)
        filter.setDay("2020-06-14")
        filter.setProductId(35455L)
        filter.setTime("10:00")
        filter.setBrandId(1L)
        Set<ConstraintViolation<PriceFilter>> violationSet = new HashSet<>()
        1 * validator.validate(_) >> violationSet

        def price = Price.builder()
                .productId(35455L)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .utcDate(2)
                .brandId(1L)
                .tariffId(1L)
                .build()

        1 * priceRepository.findByFilter(filter) >> Optional.of(price)
        when:
        Optional<Price> optional = priceService.execute(filter)
        then:
        def result = optional.get()
        result != null
        filter.getDateTime() != null
        result.getProductId() == price.getProductId()
        result.getBrandId() == price.getBrandId()
        result.getStartDate() == price.getStartDate()
        result.getEndDate() == price.getEndDate()
        result.getUtcDate() == price.getUtcDate()
        result.getTariffId() == price.getTariffId()
    }



    def "Given a filter, when find price with constraint violation, then throw exception"() {
        given:
        PriceFilter filter = new PriceFilter()
        filter.setUtc(2)
        filter.setDay("2020-06-14")
        filter.setProductId(35455L)
        filter.setTime("10:00")
        filter.setBrandId(1L)
        Set<ConstraintViolation<PriceFilter>> violationSet = new HashSet<>()
        violationSet.add(Mock(ConstraintViolation.class))
        1 * validator.validate(_) >> violationSet

        0 * priceRepository.findByFilter(filter)
        when:
        priceService.execute(filter)
        then:
        thrown ConstraintViolationException
    }
}
