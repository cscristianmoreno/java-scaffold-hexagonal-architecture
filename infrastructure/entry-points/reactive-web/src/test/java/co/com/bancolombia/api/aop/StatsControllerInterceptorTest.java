package co.com.bancolombia.api.aop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.configuration.injection.scanner.MockScanner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import co.com.bancolombia.api.exceptions.InvalidHashException;
import co.com.bancolombia.api.mocks.MockStats;
import co.com.bancolombia.api.services.ValidationMD5Service;
import co.com.bancolombia.api.utils.HashUtil;
import co.com.bancolombia.model.stats.Stats;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StatsControllerInterceptorTest {

    @Mock
    private ValidationMD5Service validationMD5Service;

    @InjectMocks
    private StatsControllerInterceptor statsControllerInterceptor;

    private Stats stats;

    @BeforeEach 
    public void setup() throws Exception {
        stats = MockStats.get();

        /** Instancia las clases! */
        // validationMD5Service = new ValidationMD5Service();

        when(validationMD5Service.hash(stats)).thenReturn(HashUtil.getMd5Hash());
    }

    @Test
    void testVerifyMD5InterceptorSuccess() throws Exception {
        String hash = validationMD5Service.hash(stats);

        assertNotNull(hash);
        assertTrue(hash.equalsIgnoreCase(HashUtil.getMd5Hash()));
    }

    @Test
    void testVerifyMD5InterceptorFailed() throws Exception {
        stats.setHash(stats.getHash() + ".");

        assertThrows(InvalidHashException.class, () -> {
            statsControllerInterceptor.verifyMD5(stats);
        });
    }

    // @Test
    // void testVerifyMD5IsNull() {
    //     stats.setHash(null);

    //     Exception exception = assertThrows(InvalidHashException.class, () -> {
    //         statsControllerInterceptor.verifyMD5(stats);
    //     });

    //     assertEquals("El hash es inválio", exception.getMessage());
    // }

    @Test
    void testInterceptorisNull() throws Exception {
        JoinPoint joinPoint = mock(JoinPoint.class);

        Object[] object = { null };
        when(joinPoint.getArgs()).thenReturn(object);

        Exception exception = assertThrows(InvalidHashException.class, () -> {
            statsControllerInterceptor.validateStat(joinPoint);
        });

        assertEquals("El objeto del interceptor devolvió null!", exception.getMessage());
    }
}
