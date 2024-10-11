package test.infrastructure.inbound.rest

import com.inditex.test.price.infrastructure.inbound.rest.ExceptionHandlerController
import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.context.request.WebRequest
import spock.lang.Specification
import spock.lang.Subject

class ExceptionHandlerControllerSpec extends Specification {


    @Subject
    private ExceptionHandlerController controller


    def setup() {
        controller = new ExceptionHandlerController()
    }


    def "Given an Exception, when handleAllExceptions, then return status 500 "() {
        given:
        Exception ex = Mock()

        WebRequest request = Mock()

        when:
        def result = controller.handleAllExceptions(ex, request)
        then:
        result.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR

    }

    def "Given an MissingServletRequestParameterException, when missingServletRequestParameterException, then return status 400 "() {
        given:
        MissingServletRequestParameterException ex = Mock()
        ex.getStatusCode() >> HttpStatus.BAD_REQUEST
        when:
        def result = controller.missingServletRequestParameterException(ex)
        then:
        result.getStatusCode() == HttpStatus.BAD_REQUEST

    }


    def "Given an RuntimeException, when handleRuntimeException, then return status 400 "() {
        given:
        RuntimeException ex = Mock()
        when:
        def result = controller.handleRuntimeException(ex)
        then:
        result.getStatusCode() == HttpStatus.BAD_REQUEST

    }


    def "Given an ConstraintViolationException, when handleConstraintViolationException, then return 400 "() {
        given:
        ConstraintViolationException ex = Mock()
        Set<ConstraintViolation<Object>> constraintViolations = new HashSet<>()
        ex.getConstraintViolations() >> constraintViolations

        when:
        def result = controller.handleConstraintViolationException(ex)
        then:
        result.getStatusCode() == HttpStatus.BAD_REQUEST
    }
}
