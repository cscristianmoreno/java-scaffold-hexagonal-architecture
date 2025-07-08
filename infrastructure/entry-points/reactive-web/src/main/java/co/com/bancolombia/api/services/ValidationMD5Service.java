package co.com.bancolombia.api.services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import co.com.bancolombia.api.models.services.IValidationMD5Service;
import co.com.bancolombia.api.utils.HashUtil;

@Service
public class ValidationMD5Service implements IValidationMD5Service {

    /** Hash válido! El cuál representa a 250,25,10,100,100,7,8
     */
    private final String hashToVerified = HashUtil.getMd5Hash();

    /*
     * Se utiliza Java Reflection para acceder a cada campo de la clase
     * luego acceder al valor del mismo
     * y verificar el hash!
     */
    @Override
    public <T> String hash(T object) throws Exception {
        /** Obtener la clase! */
        Class<?> clazz = object.getClass();

        /** Obtener los campos de la clase! */
        Field[] fields = clazz.getDeclaredFields();

        /** Matriz de integers
         * en donde fields.length alude la tamaño de la matriz!
         */
        int[] hashList = new int[fields.length]; 
        int pos = 0;

        /** Iterar sobre cada field! */
        for (Field field: fields) {
            /** Hacer accesible los métodos que están en privado! */
            field.setAccessible(true);

            /** Acceder al tipo de la clase! */
            Class<?> type = field.getType();

            /** Si el tipo de la clase es un string, ignorar! */
            if (type.isAssignableFrom(String.class)) {
                continue;
            }

            /** Obtener el valor de cada field */
            int value = (int) field.get(object);
            hashList[pos] = value;
            pos++;
        }
        
        /** Se convierte el array de integers a un array de strings delimitándolo a través de una coma!
         * Si contiene 0 (Valores que son ignorados), emitir
         */
        String arrayToString = Arrays.stream(hashList)
        .filter((i) -> i != 0)
        .mapToObj(String::valueOf).collect(Collectors.joining(","));
        
        /** Se convierte el string en MD5 Hasheado! */
        return DigestUtils.md5Hex(arrayToString);
    }

    @Override
    public <T> boolean verify(String hash) {
        return hash.matches(hashToVerified);
    }
    
}
