package co.com.bancolombia.api.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import ch.qos.logback.core.util.MD5Util;
import co.com.bancolombia.api.dto.StatsDTO;
import co.com.bancolombia.api.exceptions.InvalidHashException;
import co.com.bancolombia.api.mocks.MockStats;
import co.com.bancolombia.api.utils.HashUtil;
import co.com.bancolombia.model.stats.Stats;

public class ValidationMD5ServiceTest {
    private ValidationMD5Service validationMD5Service = new ValidationMD5Service();

    @Test
    void testHashInvalidClass() throws Exception {
        ClassWithoutField classWithoutField = new ClassWithoutField();

        Exception exception = assertThrows(InvalidHashException.class, () -> {
            validationMD5Service.hash(classWithoutField); 
        });
        
        assertEquals("El objeto no coincide!", exception.getMessage());
    }

    private class ClassWithoutField {

    }
    
    @Test
    void testHashWithNullObject() throws Exception {
        Exception exception = assertThrows(InvalidHashException.class, () -> {
            validationMD5Service.hash(null); 
        });
        
        assertEquals("Error al generar el hash!", exception.getMessage());
    }

    @Test
    void testVerify() {
        assertThrows(InvalidHashException.class, () -> {
            validationMD5Service.verify(null);
        });
    }
}
