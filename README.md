## Bancocolombia Clean Architecture

Projecto utilizando Clean Architecture de Bancocolombia

## ¿Cómo funciona?

<details>
<summary>Más Detalles</summary>

### Exponer un endpoint HTTP POST `/stats` usando Spring WebFlux.

<details>
<summary>Detalles</summary>
Se utiliza Spring `Spring MVC Reactivo` por la sencillez de implementación del controlador, el cuál devuelve un flujo reactivo.

```java
@Controller
@RequestMapping("/stats")
public class StatsController implements IStatsController {
    private final StatsRepositoryUseCase statsRepositoryUseCase;
    private final EventPublishUseCase eventPublishUseCase;

    public StatsController(final StatsRepositoryUseCase statsRepositoryUseCase, final EventPublishUseCase eventPublishUseCase) {
        this.statsRepositoryUseCase = statsRepositoryUseCase;
        this.eventPublishUseCase = eventPublishUseCase;
    }

    @Override
    public Mono<ResponseEntity<HttpResponseEntity<Stats>>> validateStat(Stats stats) {
        return statsRepositoryUseCase.save(stats)
        .map((s) -> eventPublishUseCase.emit(stats))
        .flatMap((res) -> {
            return HttpResponseEntityUtil.ok(stats);
        });
    }
}
```
</details>

### El cuerpo del request será un JSON con la siguiente estructura:
<details>
<summary>Detalles</summary>

```java
{
    "totalContactoClientes": 250,
    "motivoReclamo": 25,
    "motivoGarantia": 10,
    "motivoDuda": 100,
    "motivoCompra": 100,
    "motivoFelicitaciones": 7,
    "motivoCambio": 8,
    "hash": "02946f262f2eb0d8d5c8e76c50433ed8"
}
```
</details>

### Validar que el campo `hash` sea un `MD5 válido` generado con los campos anteriores concatenados en el orden y formato especificado:

<details>
<summary>Detalles</summary>

Se utiliza un aspecto para interceptar el método <b>validateStat()</b> antes de que sea invocado y así validar el <b>MD5 Hash</b>.
```java
@EnableAspectJAutoProxy
@Component
@Aspect
public class StatsControllerInterceptor {
    private String md5Hash = HashUtil.getMd5Hash();

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
    public void verifyMD5(JoinPoint joinPoint) throws Exception {
        /** Obtenemos el parámetro del método! */
        Stats stats = (Stats) joinPoint.getArgs()[0];

        ...

        stats.setHash(hash);
        stats.setTimestamp(hash);
    }
}
```

Se interceptar el método del controlador para  validar el hash antes de que llegue al controlador

El MD5 hash devuelve:<br/>
<b>5484062a4be1ce5645eb414663e14f59</b>

#### Generando el hash a través de reflexión

```java
...
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
    ...
}
```
</details>

### Guardar la estadística en una tabla DynamoDB con clave primaria `timestamp`

<details>
<summary>Detalles</summary>

```java
@DynamoDbBean
@Setter
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

    ...
}
```

Tabla no relacional que apunta a <b>stats_entity</b>

<b>Almacenando el valor el cuál devuelve un flujo reactivo</b>

```java
public Mono<E> save(E model) {
    return Mono.fromFuture(table.putItem(toEntity(model))).thenReturn(model);
}
```
</details>

### Publicar el evento completo a la cola `event.stats.validated` en RabbitMQ

<details>
<summary>Detalles</summary>
La propia arquitectura de <b>Bancocolombia</b> presenta un modelo de publicación de eventos.

```java
@Log
@RequiredArgsConstructor
@EnableDomainEventBus
public class ReactiveEventsGateway implements EventsGateway {
    public static final String SOME_EVENT_NAME = "event.stats.validated";
    private final DomainEventBus domainEventBus;

    @Override
    public Mono<Void> emit(Stats event) {
        log.log(Level.INFO, "Sending domain event: {0}: {1}", new String[]{SOME_EVENT_NAME, event.toString()});
        return from(domainEventBus.emit(new DomainEvent<>(SOME_EVENT_NAME, UUID.randomUUID().toString(), event)));
    }
}
```
</details>
</details>

### Levantar aplicación

La aplicación corre a través de un contenedor de Docker. Debes clonar este repositorio, abrir y ejecutar este comando en la consola.


<b>Instalar dependencias (Sin tests)</b>
```
gradle clean build -x test
```

<b>Ejecutar contenedor de docker</b>
```java
docker compose up -d
```

### Este proyecto incluye

<!-- <b> -->
* Java Reflection
* Clean Architecture Bancocolombia
* Spring AOP
* Spring WebFlux
* DynamoDB
* RabbitMQ