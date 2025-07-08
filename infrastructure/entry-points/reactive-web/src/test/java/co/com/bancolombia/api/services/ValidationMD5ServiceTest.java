package co.com.bancolombia.api.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.com.bancolombia.api.dto.StatsDTO;
import co.com.bancolombia.api.mocks.MockStats;
import co.com.bancolombia.model.stats.Stats;

public class ValidationMD5ServiceTest {
    private ValidationMD5Service validationMD5Service = new ValidationMD5Service();
    private Stats stats;
    private String hash;

    @BeforeEach
    void setup() throws Exception {
        stats = MockStats.get();

        /** Obtener el hash en caad prueba! */
        hash = validationMD5Service.hash(stats);
    }

    @Test
    void testHash() throws Exception {
        /** Verificar que el hash no es nulo! */
        assertNotNull(hash);

        /** Verificar que sea un MD5 válido de 32 dígitos */
        assertTrue(hash.matches("[a-fA-F0-9]{32}"));
    }

    @Test
    void testVerify() throws Exception {
        /** Obtener el resultado verificado del hash! */
        boolean hashResult = validationMD5Service.verify(hash);

        System.out.println(stats);

        /** Verificar que el hash no sea nulo! */
        assertNotNull(hashResult);

        /** Verificar que el hash no ha sido manipulado! */
        assertTrue(hashResult);
    }
}
