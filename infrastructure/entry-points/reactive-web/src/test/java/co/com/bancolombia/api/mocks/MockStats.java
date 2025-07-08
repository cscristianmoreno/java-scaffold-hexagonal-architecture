package co.com.bancolombia.api.mocks;

import co.com.bancolombia.api.utils.HashUtil;
import co.com.bancolombia.model.stats.Stats;

/** 
 * Mock para las estadísticas que debería devolver
 * "Stats"
*/
public abstract class MockStats {
    
    public static Stats get() {
        Stats stats = new Stats();
        stats.setTotalContactoClientes(250);
        stats.setMotivoReclamo(25);
        stats.setMotivoGarantia(10);
        stats.setMotivoDuda(100);
        stats.setMotivoCompra(100);
        stats.setMotivoFelicitaciones(7);
        stats.setMotivoCambio(8);
        stats.setHash(HashUtil.getMd5Hash());
        return stats;
    }
}
