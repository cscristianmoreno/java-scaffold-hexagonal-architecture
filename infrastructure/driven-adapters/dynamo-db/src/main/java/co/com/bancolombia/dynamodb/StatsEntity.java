package co.com.bancolombia.dynamodb;

import lombok.Setter;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

/* Enhanced DynamoDB annotations are incompatible with Lombok #1932
         https://github.com/aws/aws-sdk-java-v2/issues/1932*/
@DynamoDbBean
@Setter
@ToString
public class StatsEntity {
    private String timestamp;
    private int totalContactoClientes;
    private int motivoReclamo;
    private int motivoGarantia;
    private int motivoDuda;
    private int motivoCompra;
    private int motivoFelicitaciones;
    private int motivoCambio;
    private String hash;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    @DynamoDbAttribute("totalContactoClientes")
    public int getTotalContactoClientes() {
        return totalContactoClientes;
    }

    @DynamoDbAttribute("motivoReclamo")
    public int getMotivoReclamo() {
        return motivoReclamo;
    }

    @DynamoDbAttribute("motivoGarantia")
    public int getMotivoGarantia() {
        return motivoGarantia;
    }

    @DynamoDbAttribute("motivoDuda")
    public int getMotivoDuda() {
        return motivoDuda;
    }

    @DynamoDbAttribute("motivoCompra")
    public int getMotivoCompra() {
        return motivoCompra;
    }

    @DynamoDbAttribute("motivoFelicitaciones")
    public int getMotivoFelicitaciones() {
        return motivoFelicitaciones;
    }

    @DynamoDbAttribute("motivoCambio")
    public int getMotivoCambio() {
        return motivoCambio;
    }

    @DynamoDbAttribute("hash")
    public String getHash() {
        return hash;
    }
}
