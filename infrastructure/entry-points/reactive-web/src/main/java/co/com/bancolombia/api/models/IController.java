package co.com.bancolombia.api.models;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.bancolombia.api.dto.HttpResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** 
 * Contrato padre que usarán todos los controladores (A futuro) 
*/
public interface IController<T, DTO> {
   /** Futura implementación de un CRUD
    * save(), update(), findById(), findAll(), deleteById()
    * ....
    */
}
