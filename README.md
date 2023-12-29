# sp-data-consumption-service

Responsable de habilitar dos endpoints "API" los cuales son usados cada uno para los siguientes fines:
1. GET/dataConsumptionFilterList > responsable de devolver la lista de valores para el filtro "Servicio" del fronted. Esta
   API estará dedicada a consultar desde la tabla PREPAY_PARAMETERS, 2 listas de servicios de los cuales cada uno define un
   grupo de servicios ofrecidos por CLARO en sus líneas prepago, estas listas se dividen en dos grupos:
   - **ratingGroupList**: este grupo define los servicios generales ofrecidos por CLARO.
   - **ratingGroupSocialNetworkList**: este grupo define la lista de servicios asociados a redes sociales ofrecidos por 
   CLARO.
2. POST/dataConsumption > responsable de devolver los consumos de datos de una línea determinada, para un rango de fecha,
   con determinados filtros. Esta API está dedicada a consultar desde la tabla DATA_DAILY_CONSUMPTION una lista de todos
   los consumos registrados por una línea telefónica específica.

### Estructura

| Package           | Descripcion                                   |
|-------------------|-----------------------------------------------|
| annotations/      | Anotaciones de logueo y auditoria             |
| business/         | Clases relacionadas a la logica del negocio   |
| commons/          | Clases comunes al proyecto                    |
| config/           | Configuraciones                               |
| controller/       | Controladores                                 |
| dao/              | Acceso a datos                                |
| exception/        | Excepciones personalizadas                    |
| mapper/           | Clases para el mapeo de objetos               |
| model/            | Modelos de clases                             |
| services/         | Servicios                                     |
| utility/          | Clases con utilidades o auxiliares            |

**annotations**

* **auditable**
   * `AuditableApi`: Anotacion utilizada para almacenar la informacion relacionada a los parametros de entrada, cuerpo
     y respuesta de una api.
   * `AuditableParam`: Anotacion utilizada para almacenar la informacion relacionada a los parametros de entrada de una
     api.
   * `AuditableParamIgnore`: Anotacion utilizada para almacenar la informacion relacionada a los parametros de entrada
     a ignorar de una api.
   * `AuditableReturn`: Anotacion utilizada para almacenar la informacion relacionada a la respuesta de una api.

* **log**
   * `LogBO`: Anotación utilizada para indicar que la capa de negocio va a ser considerar para el logueo por
     aspecto.
   * `LogDAO`: Anotación utilizada para indicar que la capa de acceso a datos va a ser considerar para el logueo por
     aspecto.
   * * `LogManager`: Anotación utilizada para indicar que la capa de manager va a ser considerar para el logueo por
       aspecto.
   * * `LogService`: Anotación utilizada para indicar que la capa de servicio va a ser considerar para el logueo por
       aspecto.
   * `LogException`: Anotación utilizada para utilizada para indicar que la excepcion va a ser considerada para el
     logueo por aspecto.
   * `LogIgnore`: Anotación utilizada para indicar que el metodo **NO** va a ser considerar para el logueo por aspecto.

**bo**

Componentes relacionados a la logica de negocio cuyo fin es verificar y validar los datos de cada objeto/tabla de la
base.

**config**

* `DataSourceConfig`: Contiene la configuracion de los datasource de la base CCARD y PROD.
* `MyBatisConfig`: Contiene la configuracion y administracion de los mappers de cada base CCARD y PROD, como asi tambien
  la generacion de su sesion.

**dao**

Componentes relacionados al acceso de cada objeto/tabla de la base.

**exception**

* `DataBaseException`: Exception generada para errores de conexion a la base de datos.
* `BusinessException`: Exception generada para errores de negocio.
* `TechnicalException`: Exception generada para errores tecnicos, ej: configuracion de datos incorrectos en la base,
  etc.
* `ExternalException`: Exception generada para errores de conexion a servicios externos.
* `InternalException`: Exception generada para errores internos, ej: servidor no disponible, errores no capturados o de
  codigo, etc.

**mapper**

Mappers encargado de convertir cada objeto/tabla de la base configurada en formato XML a clases JAVA.

**model**

Modelo de clases de cada objeto/tabla de la base.

**utility**

Contiene constantes, metodos estaticos que pueden ser reutilizados en el proyecto como asi tambien una clase de logueo
estandar pre-configurada.

### Arquitectura

![img.png](img.png)

### Logica

- Para el primer endpoint: Busca en la cache de REDIS por la key, si la encuentra devuelve el valor almacenado en cache con 
dicha key, de lo contrario accede a BD CCARD/UYCCARD/PYCCARD, tabla **PREPAY_PARAMETERS**, filtrando por nombre de parámetros 
**RATING_GROUPS_FILTERS** y **RATING_GROUPS_SOCIAL_NETWORKS** y almacena el resultado en la cache de REDIS. Posteriormente 
obtiene los rating groups ids de dichos parámetros, una vez obtenidos eso ids, busca en la cache de REDIS por la key, 
si la encuentra devuelve el valor almacenado en cache con dicha key, de lo contrario accede a BD PROD, UYPROD, PYPROD, 
vista **V_RATING_GROUP_DESCRIPTION**, filtrando por los rating groups ids obtenidos y almacena el resultado en cache de REDIS.

- Para el segundo enpoint: Accede a la BD CCARD/UYCCARD/PYCCARD, tabla **DATA_DAILY_CONSUMPTION**. Además accede a la 
BD PROD/UYPROD/PYPROD, vista **V_RATING_GROUP_DESCRIPTION**, para obtener la descripción de los servicios consumidos y 
también a la tabla **PREPAY_PARAMETERS** para verificar si son de tipo social network o no.

### Informacion adicional

**Validaciones**

Las validaciones de datos que reciben los modelos se realizan a traves de anotaciones. Ej:

````
	@NotEmpty(message = "Error el campo dateTime es requerido.")
    private String dateTime;
    
	@Pattern(regexp = "^(true|false)$", message = "El campo roaming debería ser true, false o null")
    private String roaming;
````

Otras validaciones de datos de entrada se realiza mediante las siguientes clases y metodos.

Ruta de la clase: `commons/resolver/CustomHeadersResolver`

Metodo: `public static void validateHeaders(Map<String, String> headersMap)`

Ruta de la clase: `commons/resolver/CustomInputResolver`

Metodo: `public static void validateInput(RechargeOfferRequest rechargeOfferRequest)`

**Resilience4j**

La clase donde se utiliza las configuraciones de la libreria resilience4j.

Ruta de la clase: `commons/resilience4j/Resilience4jService`

Ejemplo:

```
        resilience4jService.executeDataConsumption(() -> dataConsumptionService
                .getDataConsumption(headersMap.get(BILL_NUMBER), request));
```

Donde:
> * resilience4jService.executeDataConsumption: Metodo generado para asignar una configuración personalizada de la libreria resilience4j.
> * dataConsumptionService.getDataConsumption: Metodo a encapsular con el fin de ejecutar las configuraciones realizadas en la libreria resilience4j.
> * BILL_NUMBER y request: Parámetros de entrada del metodo.

* **Circuit Breaker**

Para utilizar esta propiedad se debe asignar la anotacion `@CircuitBreaker` indicando tambien el nombre de la
configuracion en el metodo que corresponda encapsular.

Ejemplo:

```
    @CircuitBreaker(name = FILTER_LIST_API)
    public <T> T executeFilterList(Supplier<T> operation) {
        return operation.get();
    }
```

* **Rate Limiter**

Para utilizar esta propiedad se debe asignar la anotacion `@RateLimiter` indicando tambien el nombre de la configuracion
en el metodo que corresponda encapsular.

Ejemplo:

```
    @RateLimiter(name = DATA_CONSUMPTION_API)
    public <T> T executeDataConsumption(Supplier<T> operation) {
        return operation.get();
    }
```

* **Bulkhead**

Para utilizar esta propiedad se debe asignar la anotacion `@Bulkhead` indicando tambien el nombre de la configuracion en
el metodo que corresponda encapsular.

Ejemplo:

```
    @Bulkhead(name = FILTER_LIST_API)
    public <T> T executeFilterList(Supplier<T> operation) {
        return operation.get();
    }
```

* **Retry**

Para utilizar esta propiedad se debe asignar la anotacion `@Retry` indicando tambien el nombre de la configuracion en el
metodo que corresponda encapsular.

Ejemplo:

```
    @Retry(name = FILTER_LIST_API)
    public <T> T executeFilterList(Supplier<T> operation) {
        return operation.get();
    }
```

**Excepciones y Log**

Todas las excepciones y los tiempos de ejecución son capturados a traves del logueo por aspecto.

Ruta de la clase: `commons/aop/LogAspect`

Ademas las excepciones deben generarse utilizando las siguientes clases `BusinessException`, `TechnicalException`
, `InternalException`, `ExternalException`, una personalizada como `CustomException` o `ValidateException` generada para
este servicio con un código de error y un mensaje que informe el error. En el caso que sea necesario tambien se puede
ingresar la excepcion capturada. De esta forma la clase LogAspect podra capturar el error y que el mismo se registre en
el servicio.

* **Error**

El servicio tiene una estructura generica de respuesta para los casos de error, la misma esta compuesta de la siguiente
informacion:

* `resultCode`: Registra el codigo de error/ejecucion.
* `resultMessage`: Registra el mensaje de error/ejecucion.

Ejemplo:

````
{
    "resultCode": "100003",
    "resultMessage": "Error el campo Channel-Id es nulo o vacio"
}
````

* **Estructura de Logueo**

La estructura de logueo se define en la clase `LogAspect` en el constructor de la clase.

`LogUtil.initializeStructure(EnumSet.allOf(Logs.Header.class), EnumSet.allOf(Logs.Basic.class));`

Donde los Enums Logs.Header y Logs.Basic, poseen los siguientes valores:

````
    Logs.Header:
        TRANSACTION_ID,
        SESSION_ID,
        SERVICE_ID,
        CHANNEL_ID;
    Logs.Basic
        OPERATION,
        CODE,
        DESCRIPTION,
        ELAPSED,
        REQUEST,
        RESPONSE;
````

Dando asi por ejemplo esta estructura final de logueo:

`[TRANSACTION_ID=XX | SESSION_ID=XX | SERVICE_ID=XX | CHANNEL_ID=XX | OPERATION=XX | CODE=XX | DESCRIPTION=XX | ELAPSED=XX | REQUEST=XX | RESPONSE=XX]`

### Versiones

* **1.0.0 (Actual)**