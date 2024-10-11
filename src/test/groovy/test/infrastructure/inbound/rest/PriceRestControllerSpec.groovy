package test.infrastructure.inbound.rest

import com.inditex.test.price.application.port.inbound.FindPriceByFilter
import com.inditex.test.price.domain.Price
import com.inditex.test.price.infrastructure.inbound.rest.PriceRestController
import com.inditex.test.price.infrastructure.inbound.rest.mapper.PriceRestMapper
import com.inditex.test.price.infrastructure.inbound.rest.mapper.PriceRestMapperImpl
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class PriceRestControllerSpec extends Specification {

    @Subject
    private PriceRestController priceController
    private FindPriceByFilter findPriceByFilter
    private PriceRestMapper priceRestMapper

    def setup() {
        findPriceByFilter = Mock()
        priceRestMapper = new PriceRestMapperImpl()
        priceController = new PriceRestController(findPriceByFilter, priceRestMapper)
    }


    def "Given queryparams, when find prices returns a price, then return a price"() {
        given:
        def time = "10:00"
        def day = "2020-06-14"
        def productId = 35455L
        def brandId = 1L
        def utc = 2

        def price = Price.builder()
                .productId(35455L)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .utcDate(2)
                .brandId(1L)
                .tariffId(1L)
                .build()

        price.transformDatesToGivenUtc(2)
        1 * findPriceByFilter.execute(_) >> Optional.of(price)

        when:
        def result = priceController.getPrice(time, day,
                productId, brandId, utc)
        then:
        result != null
        result.getBody().getProductId() == price.getProductId()
        result.getBody().getBrandId() == price.getBrandId()
        result.getBody().getStartDay() != null
        result.getBody().getEndDay() != null
        result.getBody().getStartTime() != null
        result.getBody().getEndTime() != null
        result.getBody().tariffId == 1L

    }


    def "Given queryparams, when find price but its not found, then return empty response"() {
        given:
        def time = "10:00"
        def day = "2020-06-14"
        def productId = 35455L
        def brandId = 1L
        def utc = 2


        1 * findPriceByFilter.execute(_) >> Optional.empty()

        when:
        def result = priceController.getPrice(time, day,
                productId, brandId, utc)
        then:
        result != null
        result.getStatusCode().value() == 200
    }


}
