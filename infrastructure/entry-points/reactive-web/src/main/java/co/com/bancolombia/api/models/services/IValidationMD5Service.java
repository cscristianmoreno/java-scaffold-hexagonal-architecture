package co.com.bancolombia.api.models.services;

public interface IValidationMD5Service {
    /**
     * Método el cuál creará el hash MD5 a través de reflexión
     * Recibe un argumento y dentro del método se accede a la clase
     * generando así el hash
     * @param <T>
     * @param clazz
     * @return
     * @throws Exception 
     */
    <T> String hash(T object) throws Exception;    

    /**
     * Método el cuál validará el hash MD5
     */
    <T> boolean verify(String hash);
}
