package co.com.bancolombia.api.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import co.com.bancolombia.api.mocks.MockStats;
import co.com.bancolombia.model.stats.Stats;

public class HttpResponseEntityTest {

    @Test
    public void testHttpResponseEtity() {
        HttpResponseEntity<Stats>  httpResponseEntity = new HttpResponseEntity<Stats>();

        HttpStatus status = HttpStatus.OK;
        Stats result = MockStats.get();

        httpResponseEntity.setResult(result);
        httpResponseEntity.setStatus(status);

        assertEquals(result, httpResponseEntity.getResult());
        assertEquals(HttpStatus.OK, httpResponseEntity.getStatus());
    }
}
