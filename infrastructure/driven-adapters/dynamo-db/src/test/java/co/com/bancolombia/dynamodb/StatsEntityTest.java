package co.com.bancolombia.dynamodb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StatsEntityTest {
    @Test
    public void testStatEntity() {
        // Arrange
        StatsEntity entity = new StatsEntity();

        int motivoReclamo = 1;
        int motivoGarantia = 2;
        int motivoDuda = 3;
        int totalContactoClientes = 4;
        int motivoCompra = 5;
        int motivoFelicitaciones = 6;
        int motivoCambio = 7;

        entity.setTotalContactoClientes(totalContactoClientes);
        entity.setMotivoReclamo(motivoReclamo);
        entity.setMotivoGarantia(motivoGarantia);
        entity.setMotivoDuda(motivoDuda);
        entity.setMotivoCompra(motivoCompra);
        entity.setMotivoFelicitaciones(motivoFelicitaciones);
        entity.setMotivoCambio(motivoCambio);

        assertEquals(totalContactoClientes, entity.getTotalContactoClientes());
        assertEquals(motivoReclamo, entity.getMotivoReclamo());
        assertEquals(motivoGarantia, entity.getMotivoGarantia());
        assertEquals(motivoDuda, entity.getMotivoDuda());
        assertEquals(motivoCompra, entity.getMotivoCompra());
        assertEquals(motivoFelicitaciones, entity.getMotivoFelicitaciones());
        assertEquals(motivoCambio, entity.getMotivoCambio());
    }
}
