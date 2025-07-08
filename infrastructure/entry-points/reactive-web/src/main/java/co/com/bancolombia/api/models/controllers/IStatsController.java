package co.com.bancolombia.api.models.controllers;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import co.com.bancolombia.api.dto.HttpResponseEntity;
import co.com.bancolombia.api.dto.StatsDTO;
import co.com.bancolombia.api.models.IController;
import co.com.bancolombia.model.stats.Stats;
import reactor.core.publisher.Mono;

/**
 * Contrato que usará únicamente el controlador de Stats
 * el cuál hereda los métodos del contrato padre 
*/
public interface IStatsController extends IController<Stats, StatsDTO> {
    /**
     * Método que validará el campo Stat
     * @return
     */
    @PostMapping("")
    Mono<ResponseEntity<HttpResponseEntity<Stats>>> validateStat(@RequestBody Stats stats);
}
