package co.com.bancolombia.usecase.statrepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.bancolombia.model.stats.Stats;
import co.com.bancolombia.model.stats.gateways.IStatsRepository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class StatsRepositoryUseCaseTest {

    @Mock
    private IStatsRepository iStatsRepository;

    @InjectMocks
    private StatsRepositoryUseCase statsRepositoryUseCase;

    @Test
    void testSave() {
        Stats stats = new Stats();

        when(iStatsRepository.save(stats)).thenReturn(Mono.just(stats));
        
        
        StepVerifier.create(statsRepositoryUseCase.save(stats))
        .expectNext(stats)
        .verifyComplete();
    }
}
