package co.com.bancolombia.api.controllers;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.BodyContentSpec;
import co.com.bancolombia.api.dto.StatsDTO;
import co.com.bancolombia.api.mocks.MockStats;
import co.com.bancolombia.api.services.ValidationMD5Service;
import co.com.bancolombia.model.stats.Stats;
import co.com.bancolombia.usecase.eventpublish.EventPublishUseCase;
import co.com.bancolombia.usecase.statrepository.StatsRepositoryUseCase;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ContextConfiguration(classes = StatsController.class)
@WebFluxTest
public class StatsControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private StatsRepositoryUseCase statsRepositoryUseCase;

    @MockitoBean
    private EventPublishUseCase eventPublishUseCase;

    private Stats stats;

    // /**
    //  * Antes de cada prueba unitaria 
    //  * setear el valor de stats y webTestClient! 
    // */
    @BeforeEach
    void setup() {
        /** OBtiene el mock de StatsDTO! */
        stats = MockStats.get();     

        
        /** Simular que statsRepositoryUseCase
         * Devuelve un resultado exitoso!
         */
        when(statsRepositoryUseCase.save(stats)).thenReturn(Mono.just(stats));

        /** Simular que eventPublishUseCase
         * emite un mensaje!
         */
        when(eventPublishUseCase.emit(stats)).thenReturn(Mono.empty());
    }

    @Test
    void testFindStat() {
        BodyContentSpec bodyContentSpec = webTestClient
        .post()
        .uri("/stats")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(stats)
        .exchange()
        .expectBody();

        bodyContentSpec.consumeWith((res) -> {
            /**
             * Verificar que la respuesta no sea nula!
             */
            assertNotNull(res);

            /**
             * Verificar que se recibió un cuerpo!
            */
            assertNotNull(res.getResponseBody());
        });
    }
}
