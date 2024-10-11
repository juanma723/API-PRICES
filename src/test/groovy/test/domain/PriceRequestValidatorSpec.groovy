package test.domain

import com.inditex.test.price.domain.PriceFilter
import com.inditex.test.price.domain.PriceRequestValidator
import jakarta.validation.ConstraintValidatorContext
import spock.lang.Specification
import spock.lang.Subject

class PriceRequestValidatorSpec extends Specification {


    @Subject
    private PriceRequestValidator validator


    def setup() {
        validator = new PriceRequestValidator()
    }


    def "Given an invalid null filter, when isValid, then return false"() {
        given:
        PriceFilter filter = new PriceFilter()
        ConstraintValidatorContext context = Mock()
        ConstraintValidatorContext.ConstraintViolationBuilder builder = Mock()
        ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext builderCustomizableContext = Mock()

        context.buildConstraintViolationWithTemplate(_) >> builder
        builder.addPropertyNode(_) >> builderCustomizableContext
        builderCustomizableContext.addConstraintViolation() >> context
        when:
        def result = validator.isValid(filter, context)
        then:
        !result
    }

    def "Given a invalid filter, when isValid, then return false"() {
        given:
        PriceFilter filter = new PriceFilter()
        filter.setProductId(-1L)
        filter.setBrandId(-1L)
        filter.setTime("10:0")
        filter.setDay("2024-10-1")
        filter.setUtc(-2)
        ConstraintValidatorContext context = Mock()
        ConstraintValidatorContext.ConstraintViolationBuilder builder = Mock()
        ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext builderCustomizableContext = Mock()

        context.buildConstraintViolationWithTemplate(_) >> builder
        builder.addPropertyNode(_) >> builderCustomizableContext
        builderCustomizableContext.addConstraintViolation() >> context
        when:
        def result = validator.isValid(filter, context)
        then:
        !result
    }

    def "Given a valid filter, when isValid, then return false"() {
        given:
        PriceFilter filter = new PriceFilter()
        filter.setProductId(1L)
        filter.setBrandId(1L)
        filter.setTime("10:00")
        filter.setDay("2024-10-10")
        filter.setUtc(2)
        ConstraintValidatorContext context = Mock()
        ConstraintValidatorContext.ConstraintViolationBuilder builder = Mock()
        ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext builderCustomizableContext = Mock()

        context.buildConstraintViolationWithTemplate(_) >> builder
        builder.addPropertyNode(_) >> builderCustomizableContext
        builderCustomizableContext.addConstraintViolation() >> context
        when:
        def result = validator.isValid(filter, context)
        then:
        result
    }
}
