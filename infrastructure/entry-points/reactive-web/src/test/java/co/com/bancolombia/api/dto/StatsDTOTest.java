package co.com.bancolombia.api.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatsDTOTest {

    void testStatDTO() {
        StatsDTO statsDTO = new StatsDTO();

        int totalContactoClientes = 1;
        int motivoReclamo = 2;
        int motivoGarantia = 3;
        int motivoDuda = 4;
        int motivoCompra = 5;
        int motivoFelicitaciones = 6;
        int motivoCambio = 7;
        String hash = "...";

        statsDTO.setTotalContactoClientes(totalContactoClientes);
        statsDTO.setMotivoCambio(motivoCambio);
        statsDTO.setMotivoGarantia(motivoGarantia);
        statsDTO.setMotivoDuda(motivoDuda);
        statsDTO.setMotivoCompra(motivoCompra);
        statsDTO.setMotivoFelicitaciones(motivoFelicitaciones);
        statsDTO.setMotivoCambio(motivoCambio);
        statsDTO.setHash(hash);
        statsDTO.setMotivoReclamo(motivoReclamo);

        assertEquals(totalContactoClientes, statsDTO.getTotalContactoClientes());
        assertEquals(motivoCambio, statsDTO.getMotivoCambio());
        assertEquals(motivoGarantia, statsDTO.getMotivoGarantia());
        assertEquals(motivoDuda, statsDTO.getMotivoDuda());
        assertEquals(motivoCompra, statsDTO.getMotivoCompra());
        assertEquals(motivoFelicitaciones, statsDTO.getMotivoFelicitaciones());
        assertEquals(motivoCambio, statsDTO.getMotivoCambio());
        assertEquals(hash, statsDTO.getHash());
        assertEquals(motivoReclamo, statsDTO.getMotivoReclamo());
    }
}
