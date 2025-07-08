package co.com.bancolombia.model.events.gateways;

import co.com.bancolombia.model.stats.Stats;
import reactor.core.publisher.Mono;

public interface EventsGateway {
    Mono<Void> emit(Stats event);
}
