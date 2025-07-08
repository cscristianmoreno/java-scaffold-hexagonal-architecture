package co.com.bancolombia.usecase.statrepository;

import co.com.bancolombia.model.stats.Stats;
import co.com.bancolombia.model.stats.gateways.IStatsRepository;
import reactor.core.publisher.Mono;

public class StatsRepositoryUseCase  {
    private final IStatsRepository drivenAdapter;

    public StatsRepositoryUseCase(final IStatsRepository drivenAdapter) {
        this.drivenAdapter = drivenAdapter;
    }

    // @Override
    public Mono<Stats> save(Stats stats) {
        return drivenAdapter.save(stats);
    }
}
