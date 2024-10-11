package test.infrastructure.inbound.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.inditex.test.TestApplication
import com.inditex.test.price.application.port.inbound.FindPriceByFilter
import com.inditex.test.price.infrastructure.inbound.rest.PriceRestController
import com.inditex.test.price.infrastructure.inbound.rest.mapper.PriceRestMapper
import org.openapitools.model.ErrorResponse
import org.openapitools.model.PriceResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification
import spock.lang.Subject

@SpringBootTest(classes = [TestApplication.class])
@AutoConfigureMockMvc
@ActiveProfiles('test')
@Subject([PriceRestController])
class PriceRestControllerISpec extends Specification {


    public static final String PRICES_PATH = "/api/v1/prices"
    @Autowired
    private FindPriceByFilter priceService
    @Autowired
    private PriceRestMapper priceRestMapper

    @Autowired
    private ObjectMapper mapper

    @Autowired
    private MockMvc mockMvc


    def "given time 10:00,day 2020-06-14 ,productId 35455,brandId 1, when /prices, then check result is correct "() {
        given:
        def time = "10:00"
        def day = "2020-06-14"
        def productId = "35455"
        def brandId = "1"
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get(PRICES_PATH)
                .param("time", time)
                .param("day", day)
                .param("productId", productId)
                .param("brandId", brandId))
                .andReturn().response

        then:
        PriceResponse responseObject = mapper.readValue(response.getContentAsString(), PriceResponse.class)

        responseObject.brandId == 1
        responseObject.productId == 35455
        responseObject.startDay == "2020-06-14"
        responseObject.endDay == "2020-12-31"
        responseObject.price == 35.5
        responseObject.currency == "EUR"
        responseObject.startTime == "00:00"
        responseObject.endTime == "23:59"
        responseObject.tariffId == 1L
    }


    def "given time 16:00,day 2020-06-14 ,productId 35455,brandId 1, when /prices, then check result is correct "() {
        given:
        def time = "16:00"
        def day = "2020-06-14"
        def productId = "35455"
        def brandId = "1"
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get(PRICES_PATH)
                .param("time", time)
                .param("day", day)
                .param("productId", productId)
                .param("brandId", brandId)
                .param("utc", "2"))
                .andReturn().response

        then:
        PriceResponse responseObject = mapper.readValue(response.getContentAsString(), PriceResponse.class)


        responseObject.brandId == 1
        responseObject.productId == 35455
        responseObject.startDay == "2020-06-14"
        responseObject.endDay == "2020-06-14"
        responseObject.price == 25.45
        responseObject.currency == "EUR"
        responseObject.startTime == "15:00"
        responseObject.endTime == "18:30"
        responseObject.tariffId == 2L


    }



    def "given time 21:00,day 2020-06-14 ,productId 35455,brandId 1, when /prices, then check result is correct "() {
        given:
        def time = "21:00"
        def day = "2020-06-14"
        def productId = "35455"
        def brandId = "1"
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get(PRICES_PATH)
                .param("time", time)
                .param("day", day)
                .param("productId", productId)
                .param("brandId", brandId)
                .param("utc", "2"))
                .andReturn().response

        then:
        PriceResponse responseObject = mapper.readValue(response.getContentAsString(), PriceResponse.class)


        responseObject.brandId == 1
        responseObject.productId == 35455
        responseObject.startDay == "2020-06-14"
        responseObject.endDay == "2020-12-31"
        responseObject.price == 35.50
        responseObject.currency == "EUR"
        responseObject.startTime == "00:00"
        responseObject.endTime == "23:59"
        responseObject.tariffId == 1L

    }


    def "given time 10:00,day 2020-06-15 ,productId 35455,brandId 1, when /prices, then check result is correct "() {
        given:
        def time = "10:00"
        def day = "2020-06-15"
        def productId = "35455"
        def brandId = "1"
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get(PRICES_PATH)
                .param("time", time)
                .param("day", day)
                .param("productId", productId)
                .param("brandId", brandId)
                .param("utc", "2"))
                .andReturn().response

        then:
        PriceResponse responseObject = mapper.readValue(response.getContentAsString(), PriceResponse.class)


        responseObject.brandId == 1
        responseObject.productId == 35455
        responseObject.startDay == "2020-06-15"
        responseObject.endDay == "2020-06-15"
        responseObject.price == 30.5
        responseObject.currency == "EUR"
        responseObject.startTime == "00:00"
        responseObject.endTime == "11:00"
        responseObject.tariffId == 3L

    }


    def "given time 21:00,day 2020-06-16 ,productId 35455,brandId 1, when /prices, then check result is correct "() {
        given:
        def time = "21:00"
        def day = "2020-06-16"
        def productId = "35455"
        def brandId = "1"
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get(PRICES_PATH)
                .param("time", time)
                .param("day", day)
                .param("productId", productId)
                .param("brandId", brandId)
                .param("utc", "2"))
                .andReturn().response

        then:
        PriceResponse responseObject = mapper.readValue(response.getContentAsString(), PriceResponse.class)


        responseObject.brandId == 1
        responseObject.productId == 35455
        responseObject.startDay == "2020-06-15"
        responseObject.endDay == "2020-12-31"
        responseObject.price == 38.95
        responseObject.currency == "EUR"
        responseObject.startTime == "16:00"
        responseObject.endTime == "23:59"
        responseObject.tariffId == 4L

    }




    def "given time 21, when /prices, then exception is thrown "() {
        given:
        def time = "21"
        def day = "2020-06-16"
        def productId = "35455"
        def brandId = "2"
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get(PRICES_PATH)
                .param("time", time)
                .param("day", day)
                .param("productId", productId)
                .param("brandId", brandId))
                .andReturn().response

        then:
        ErrorResponse responseObject = mapper.readValue(response.getContentAsString(), ErrorResponse.class)
        responseObject.code == "400"
    }



    def "given time 21:00,day 2025-06-16 ,productId 35455,brandId 1, when /prices, then return status 204 "() {
        given:
        def time = "21:00"
        def day = "2025-06-16"
        def productId = "35455"
        def brandId = "1"
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get(PRICES_PATH)
                .param("time", time)
                .param("day", day)
                .param("productId", productId)
                .param("brandId", brandId)
                .param("utc", "2"))
                .andReturn().response

        then:
        response.getStatus() == 200

    }
}
