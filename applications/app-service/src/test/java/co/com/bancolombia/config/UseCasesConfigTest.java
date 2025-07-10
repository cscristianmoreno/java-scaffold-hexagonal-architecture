package co.com.bancolombia.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import co.com.bancolombia.model.events.gateways.EventsGateway;
import co.com.bancolombia.model.stats.gateways.IStatsRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class UseCasesConfigTest {

    /** Como los UsesCases tienen inyección de dependencias,
     * no es posible acceder a ellas así nomas, para ello se desactiva
     * este test y se hace test manual a cada clase!
     */
    @Test
    void testUseCaseBeansExist() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class)) {
            String[] beanNames = context.getBeanDefinitionNames();

            boolean useCaseBeanFound = false;
            for (String beanName : beanNames) {
                if (beanName.endsWith("UseCase")) {
                    useCaseBeanFound = true;
                    break;
                }
            }

            assertTrue(useCaseBeanFound, "No beans ending with 'Use Case' were found");
        }
    }

    @Configuration
    @Import(UseCasesConfig.class)
    static class TestConfig {

        @Bean
        public IStatsRepository statPersistenceGateway() {
            return mock(IStatsRepository.class);
        }

        @Bean
        public EventsGateway statEventPublisher() {
            return mock(EventsGateway.class);
        }

        // @Bean
        // public MyUseCase myUseCase() {
        //     return new MyUseCase();
        // }
    }
}