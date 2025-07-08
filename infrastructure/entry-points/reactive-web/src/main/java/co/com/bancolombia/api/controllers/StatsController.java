package co.com.bancolombia.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import co.com.bancolombia.api.dto.HttpResponseEntity;
import co.com.bancolombia.api.dto.StatsDTO;
import co.com.bancolombia.api.models.controllers.IStatsController;
import co.com.bancolombia.api.services.ValidationMD5Service;
import co.com.bancolombia.api.utils.HttpResponseEntityUtil;
import co.com.bancolombia.model.stats.Stats;
import co.com.bancolombia.usecase.eventpublish.EventPublishUseCase;
import co.com.bancolombia.usecase.statrepository.StatsRepositoryUseCase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/stats")
public class StatsController implements IStatsController {
    private final StatsRepositoryUseCase statsRepositoryUseCase;
    private final EventPublishUseCase eventPublishUseCase;

    public StatsController(final StatsRepositoryUseCase statsRepositoryUseCase, final EventPublishUseCase eventPublishUseCase) {
        this.statsRepositoryUseCase = statsRepositoryUseCase;
        this.eventPublishUseCase = eventPublishUseCase;
    }

    @Override
    public Mono<ResponseEntity<HttpResponseEntity<Stats>>> validateStat(Stats stats) {
        return statsRepositoryUseCase.save(stats)
        .map((s) -> eventPublishUseCase.emit(stats))
        .flatMap((res) -> {
            return HttpResponseEntityUtil.ok(stats);
        });
    }
}
