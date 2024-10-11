package test.infrastructure.outbound.repository

import com.inditex.test.price.domain.Price
import com.inditex.test.price.domain.PriceFilter
import com.inditex.test.price.infrastructure.outbound.repository.PriceRepositoryImpl
import com.inditex.test.price.infrastructure.outbound.repository.entity.PriceEntity
import com.inditex.test.price.infrastructure.outbound.repository.jpa.PriceJpaRepository
import com.inditex.test.price.infrastructure.outbound.repository.mapper.PriceRepositoryMapper
import com.inditex.test.price.infrastructure.outbound.repository.mapper.PriceRepositoryMapperImpl
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class PriceRepositoryImplSpec extends Specification {


    @Subject
    private PriceRepositoryImpl priceRepository

    private PriceJpaRepository priceJpaRepository

    private PriceRepositoryMapper priceRepositoryMapper


    def setup() {
        priceJpaRepository = Mock()
        priceRepositoryMapper = new PriceRepositoryMapperImpl()
        priceRepository = new PriceRepositoryImpl(priceJpaRepository, priceRepositoryMapper)
    }



    def "Given a filter, when findByFilter, then return price"(){
        given:

        PriceFilter priceFilter = new PriceFilter()

        PriceEntity entity = new PriceEntity()
        entity.setBrandId(1L)
        entity.setProductId(1L)
        entity.setCurrency("A")
        entity.setEndDate(LocalDateTime.now())
        entity.setStartDate(LocalDateTime.now())
        entity.setPrice(22.22)
        entity.setTariffId(1L)
        1 * priceJpaRepository.findByFilter(_, _, _) >> Optional.of(entity)

        when:
        Optional<Price> optional = priceRepository.findByFilter(priceFilter)

        then:
        def result = optional.get()
        result.getBrandId() == entity.getBrandId()
        result.getCurrency() == entity.getCurrency()
        result.getProductId() == entity.getProductId()
        result.getEndDate() == entity.getEndDate()
        result.getStartDate() == entity.getStartDate()
        result.getPrice() == entity.getPrice()
        result.getTariffId() == entity.getTariffId()
    }
}
