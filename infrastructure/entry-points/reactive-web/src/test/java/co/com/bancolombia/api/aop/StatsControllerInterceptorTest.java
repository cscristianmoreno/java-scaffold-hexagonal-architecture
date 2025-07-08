package co.com.bancolombia.api.aop;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.internal.configuration.injection.scanner.MockScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import co.com.bancolombia.api.mocks.MockStats;
import co.com.bancolombia.api.services.ValidationMD5Service;
import co.com.bancolombia.api.utils.HashUtil;
import co.com.bancolombia.model.stats.Stats;

public class StatsControllerInterceptorTest {
    private ValidationMD5Service validationMD5Service;
    private StatsControllerInterceptor statsControllerInterceptor;

    private Stats stats;

    @BeforeEach 
    public void setup() {
        stats = MockStats.get();

        /** Instancia las clases! */
        validationMD5Service = new ValidationMD5Service();
        statsControllerInterceptor = new StatsControllerInterceptor(validationMD5Service);
    }

    @Test
    void testVerifyMD5Interceptor() throws Exception {
        String hash = statsControllerInterceptor.verifyMD5(stats);

        assertNotNull(hash);
        assertTrue(hash.equals(HashUtil.getMd5Hash()));
    }
}
