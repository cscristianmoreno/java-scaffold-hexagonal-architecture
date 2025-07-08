package co.com.bancolombia.api.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import co.com.bancolombia.api.dto.HttpResponseEntity;
import co.com.bancolombia.api.utils.HttpResponseEntityUtil;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class ControllerException {
    
    /** Método que captura todas las excepciones
     * Y devuelve un flujo reactivo con la respuesta
     */
    @ExceptionHandler({Exception.class})
    public Mono<ResponseEntity<HttpResponseEntity<String>>> exception(Exception exception) {
        String message = exception.getMessage();
        return HttpResponseEntityUtil.unauthorized(message);
    }
}
