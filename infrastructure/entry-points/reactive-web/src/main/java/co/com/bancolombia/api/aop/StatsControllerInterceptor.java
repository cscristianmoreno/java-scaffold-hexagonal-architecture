package co.com.bancolombia.api.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import co.com.bancolombia.api.exceptions.InvalidHashException;
import co.com.bancolombia.api.services.ValidationMD5Service;
import co.com.bancolombia.api.utils.HashUtil;
import co.com.bancolombia.model.stats.Stats;

@EnableAspectJAutoProxy
@Component
@Aspect
public class StatsControllerInterceptor {
    private final String md5Hash = HashUtil.getMd5Hash();

    private final ValidationMD5Service validationMD5Service;
    // private final 

    public StatsControllerInterceptor(final ValidationMD5Service validationMD5Service) {
        this.validationMD5Service = validationMD5Service;
    }

    /**
     * Método que crea un punto de corte en la función validateStat(..)!
     */
    @Pointcut("execution(* co..StatsController.validateStat(..))")
    public void verifyMD5Interceptor() {}

    /**
     * Método que interceptará el méotdo validateStat
     * dentro del controlador StatsController
     * y verificará el Hash
     * Si el hash es correcto, continuará mutando el Objeto y añadiendo
     * el hash dentro de setHash.
     * @param joinPoint
     * @throws Exception 
     */
    @Before("verifyMD5Interceptor()")
    public void validateStat(JoinPoint joinPoint) throws Exception {
        /** Obtenemos el parámetro del método! */
        Stats stats = (Stats) joinPoint.getArgs()[0];

        /** Verificar el hash! */
        String hash = verifyMD5(stats);

        /** Añadir el hash a timestamp!  */
        stats.setHash(hash);
        stats.setTimestamp(hash);
    }

    public String verifyMD5(Stats stats) throws Exception {
        /** Si la request no contiene el campo "hash" o está vacía o contiene espacios
         * lanzar una excepción!
         */
        if (stats.getHash() == null || stats.getHash().isEmpty() || stats.getHash().isBlank()) {
            throw new InvalidHashException("El hash es inválido");
        }

        /** Obtener el hash de los campos! */
        String hash = validationMD5Service.hash(stats);    

        /** Obtener el hash de la request */
        String requestHash = stats.getHash();

        /** Si el hash de los campos (Fueron modificados) no coincide con el hash generado, 
         * lanzar una excepción el cuál es capturada por @ControllerAdvice!
         */
        if (!validationMD5Service.verify(hash)) {
            String message = String.format("Campos alterado!, se esperaba %s pero se obtuvo %s", md5Hash, requestHash);
            throw new InvalidHashException(message);
        }

        /** Si el hash de la request no coincide con el hash generado, 
         * lanzar una excepción el cuál es capturada por @ControllerAdvice!
         */
        if (!validationMD5Service.verify(requestHash) || !validationMD5Service.verify(hash)) {
            String message = String.format("Hash alterado!, se esperaba %s pero se obtuvo %s", md5Hash, requestHash);
            throw new InvalidHashException(message);
        }

        return hash;
    }
}
