package co.com.bancolombia.usecase.eventpublish;

import co.com.bancolombia.model.events.gateways.EventsGateway;
import co.com.bancolombia.model.stats.Stats;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

public class EventPublishUseCase implements EventsGateway {

    private final EventsGateway eventsGateway;

    public EventPublishUseCase(final EventsGateway eventsGateway) {
        this.eventsGateway = eventsGateway;
    }

    @Override
    public Mono<Void> emit(Object event) {
        return eventsGateway.emit(event);
    }
}
