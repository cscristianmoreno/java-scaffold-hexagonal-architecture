package co.com.bancolombia.model.stats.gateways;

import co.com.bancolombia.model.stats.Stats;
import reactor.core.publisher.Mono;

public interface IStatsRepository {
    Mono<Stats> save(Stats stats);
}
