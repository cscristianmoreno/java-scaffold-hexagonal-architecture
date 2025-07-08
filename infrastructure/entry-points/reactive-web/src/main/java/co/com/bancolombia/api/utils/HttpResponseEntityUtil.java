package co.com.bancolombia.api.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import co.com.bancolombia.api.dto.HttpResponseEntity;
import reactor.core.publisher.Mono;

public abstract class HttpResponseEntityUtil {
    
    /**
     * Método que devuelve una respuesta correcta de un objeto!
     * @param <T>
     * @param body
     * @return
     */
    public static <T> Mono<ResponseEntity<HttpResponseEntity<T>>> ok(T body) {
        return sendMessage(body, HttpStatus.OK);
    }

    /**
     * Método que devuelve una respuesta fallida de un objeto
     * En este caso se pone unauthorized
     * Devolver BAD_REQUEST!
     */
    public static Mono<ResponseEntity<HttpResponseEntity<String>>> unauthorized(String message) {
        return sendMessage(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Método que envía la estructura de la respuesta!
     * @param <T>
     * @param result
     * @param status
     * @return
     */
    private static <T> Mono<ResponseEntity<HttpResponseEntity<T>>> sendMessage(T result, HttpStatus status) {
        HttpResponseEntity<T> response = new HttpResponseEntity<>();
        response.setResult(result);
        response.setStatus(status);
        
        return Mono.just(new ResponseEntity<HttpResponseEntity<T>>(response, status));
    }
}
