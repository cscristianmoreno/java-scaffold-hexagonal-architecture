package co.com.bancolombia.model.stats;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
//import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
//@NoArgsConstructor
// @AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class Stats {
    private String timestamp;
    private int totalContactoClientes;
    private int motivoReclamo;
    private int motivoGarantia;
    private int motivoDuda;
    private int motivoCompra;
    private int motivoFelicitaciones;
    private int motivoCambio;
    private String hash;
}

