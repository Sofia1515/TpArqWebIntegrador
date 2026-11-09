# HISTORIAS DE USUARIO Y CRITERIOS DE ACEPTACIÓN (ENTREGA 1 - CORE)
**PROYECTO: TICKETYA**

---

### FEATURE 1: GESTIÓN DE EVENTOS, RECINTOS Y SECTORES (`evento-service`)

#### US-01: Alta de Lugares y Recintos
**Como** Administrador / Organizador,  
**Quiero** registrar recintos (estadios, teatros, salas) indicando su dirección, ubicación geográfica y capacidad total,  
**Para** poder asociarlos como sedes de los eventos.

* **Criterio de Aceptación 1:**
    * **Dado que** envío una solicitud `POST` a `/api/lugares` con los datos del recinto (nombre, dirección, ciudad, latitud, longitud y capacidad total),
    * **Cuando** los datos son válidos,
    * **Entonces** el sistema guarda el recinto en la base de datos y me devuelve un estado HTTP `201 Created` con el DTO del lugar generado.

---

#### US-02: Creación de Eventos en Modo Borrador
**Como** Organizador,  
**Quiero** registrar un evento definiendo su tipo, participantes, fecha/hora y asociándole un lugar con sus sectores (numerados o no numerados) y precios,  
**Para** preparar la grilla del espectáculo antes de su salida a la venta.

* **Criterio de Aceptación 1:**
    * **Dado que** envío una solicitud `POST` a `/api/eventos` especificando el `idLugar`, tipo de evento (recital, teatro, deporte, conferencia), participantes (artistas, equipos, disertantes), fecha/hora y la lista de sectores (nombre, si es numerado, precio, capacidad máxima, filas y asientos por fila),
    * **Cuando** se valida la información técnica del evento,
    * **Entonces** el sistema registra el evento en estado `BORRADOR` y retorna un estado HTTP `201 Created`.

---

#### US-03: Publicación y Cambio de Estado de Eventos
**Como** Organizador / Administrador,  
**Quiero** cambiar el estado de un evento (`BORRADOR` a `PUBLICADO` o `CANCELADO`),  
**Para** habilitar o restringir la visibilidad y compra de entradas al público general.

* **Criterio de Aceptación 1 (Publicación por Organizador):**
    * **Dado que** un evento existe en estado `BORRADOR`,
    * **Cuando** realizo una petición `PUT` a `/api/eventos/{id}/estado?nuevoEstado=PUBLICADO`,
    * **Entonces** el estado del evento se actualiza a `PUBLICADO` y queda habilitado para la compra.

---

#### US-04: Búsqueda Multi-criterio de Eventos
**Como** Cliente,  
**Quiero** buscar eventos filtrando por ciudad, rango de fechas, tipo de evento, organizador o participante, y ordenarlos por fecha o precio,  
**Para** encontrar espectáculos de mi interés de forma rápida.

* **Criterio de Aceptación 1:**
    * **Dado que** especifico parámetros de búsqueda en `GET /api/eventos/buscar` (por ejemplo ciudad, rango de fechas `fechaInicio` a `fechaFin`, o participante),
    * **Cuando** ejecuto la consulta,
    * **Entonces** el sistema me devuelve la lista de eventos filtrados que se encuentran en estado `PUBLICADO`, permitiendo ordenar los resultados por `fecha` o `precio` (HTTP `200 OK`).

---

### FEATURE 2: GESTIÓN DE USUARIOS Y ROLES (`usuario-service`)

#### US-05: Registro de Usuarios
**Como** Usuario,  
**Quiero** registrarme en la plataforma ingresando mi nombre, apellido, email, contraseña y rol (CLIENTE, ORGANIZADOR, ADMINISTRADOR, PERSONAL_ACCESO),  
**Para** poder interactuar con el sistema según mis atribuciones.

* **Criterio de Aceptación 1:**
    * **Dado que** envío una solicitud `POST` a `/api/usuarios` con un email que no existe en el sistema,
    * **Cuando** se efectúa el registro,
    * **Entonces** se guarda la entidad Usuario y me devuelve un estado HTTP `201 Created` sin exponer la contraseña.

* **Criterio de Aceptación 2:**
    * **Dado que** intento registrarme con un email registrado previamente,
    * **Cuando** el sistema procesa la solicitud,
    * **Entonces** interrumpe la operación y retorna un error de duplicidad HTTP `400 Bad Request` o `409 Conflict`.

---

#### US-06: Consulta e Identificación de Usuarios
**Como** Sistema / Administrador,  
**Quiero** obtener la información de un usuario por su ID, email o rol,  
**Para** validar la identidad del comprador u organizador mediante parámetros/encabezados en las peticiones.

* **Criterio de Aceptación 1:**
    * **Dado** un email o ID de usuario registrado,
    * **Cuando** solicito un `GET` a `/api/usuarios/{id}` o `/api/usuarios/email/{email}`,
    * **Entonces** el sistema responde con HTTP `200 OK` y la información del usuario correspondiente.

---

### FEATURE 3: RESERVA TEMPORAL, COMPRA Y CONTROL DE DOBLE VENTA (`reserva-service`)

#### US-07: Consulta de Disponibilidad de Sectores y Asientos
**Como** Cliente,  
**Quiero** consultar los sectores y asientos disponibles (libres, reservados, ocupados) de un evento,  
**Para** seleccionar la ubicación deseada antes de iniciar el proceso de reserva.

* **Criterio de Aceptación 1:**
    * **Dado que** consulto `GET /api/eventos/{id}/disponibilidad`,
    * **Cuando** el evento está `PUBLICADO`,
    * **Entonces** el sistema retorna el estado de ocupación de cada sector y asiento en tiempo real (HTTP `200 OK`).

---

#### US-08: Reserva Temporal de Asientos y Control de Límite por Comprador
**Como** Cliente,  
**Quiero** seleccionar una ubicación (asiento específico o cupo en sector no numerado) y mantenerla bloqueada por 10 minutos,  
**Para** asegurar mi lugar mientras realizo la simulación del pago sin superar el tope máximo de entradas permitido por persona.

* **Criterio de Aceptación 1 (Reserva exitosa):**
    * **Dado que** elijo una ubicación libre y no supero el límite máximo de entradas permitido por evento para mi usuario,
    * **Cuando** solicito la reserva mediante `POST /api/reservas`,
    * **Entonces** el sistema bloquea temporalmente la ubicación por 10 minutos y retorna la reserva en estado `PENDIENTE` (HTTP `201 Created`).

* **Criterio de Aceptación 2 (Exceso de límite por cliente):**
    * **Dado que** un cliente ya posee la cantidad máxima de entradas/reservas permitidas para un evento específico,
    * **Cuando** intenta realizar una nueva reserva para ese mismo evento,
    * **Entonces** el sistema rechaza la solicitud indicando que superó el límite por comprador (HTTP `400 Bad Request`).

---

#### US-09: Garantía de No Doble Venta ante Concurrencia Simultánea
**Como** Sistema,  
**Quiero** procesar las peticiones concurrentes mediante bloqueos a nivel de base de datos o restricciones de unicidad,  
**Para** garantizar que un mismo asiento o el último lugar de un sector general jamás sea reservado o vendido a dos personas distintas al mismo tiempo.

* **Criterio de Aceptación 1:**
    * **Dado que** múltiples peticiones concurrentes intentan reservar la misma ubicación (misma fila/asiento o el último cupo de un sector no numerado) en el mismo instante,
    * **Cuando** el backend procesa las solicitudes,
    * **Entonces** exactamente 1 solicitud resulta exitosa (reserva aprobada) y las restantes son rechazadas informando que la ubicación ya no está disponible (HTTP `409 Conflict`).

---

#### US-10: Confirmación de Pago y Emisión de Entradas con QR
**Como** Cliente,  
**Quiero** confirmar mi reserva activa simulando el pago antes de que expire el tiempo límite,  
**Para** recibir mis entradas emitidas con su correspondiente código QR único.

* **Criterio de Aceptación 1 (Pago aprobado):**
    * **Dado que** poseo una reserva vigente en estado `PENDIENTE`,
    * **Cuando** ejecuto el pago simulado exitoso mediante `POST /api/compras`,
    * **Entonces** la reserva cambia a estado `CONFIRMADA`, se genera el registro de Compra y se emite la Entrada con un hash/código QR único guardado en la base de datos (HTTP `200 OK`).

* **Criterio de Aceptación 2 (Pago rechazado o expirado):**
    * **Dado que** el pago es rechazado o transcurren los 10 minutos de la reserva sin confirmación,
    * **Cuando** el sistema procesa el evento o expiración,
    * **Entonces** la reserva pasa a `CANCELADA` / `EXPIRADA` y las ubicaciones asociadas quedan automáticamente liberadas para otros compradores.

---

#### US-11: Devolución de Entradas y Cancelación de Evento
**Como** Cliente / Administrador,  
**Quiero** solicitar la devolución de una entrada (hasta 72hs antes del evento) o procesar la cancelación completa de un evento,  
**Para** liberar las ubicaciones y gestionar el reintegro simulado del dinero.

* **Criterio de Aceptación 1 (Devolución por cliente):**
    * **Dado que** un cliente solicita la devolución con más de 72 horas de anticipación a la fecha del evento,
    * **Cuando** un Administrador aprueba la solicitud (`POST /api/devoluciones/{id}/aprobar`),
    * **Entonces** la entrada pasa a estado `DEVUELTA`, el asiento/lugar se libera en la base de datos y se registra la solicitud de reembolso simulada.

* **Criterio de Aceptación 2 (Cancelación masiva por evento):**
    * **Dado que** un Administrador cancela un evento (`PUT /api/eventos/{id}/estado?nuevoEstado=CANCELADO`),
    * **Cuando** la cancelación es procesada,
    * **Entonces** todas las entradas emitidas para dicho evento pasan a estado `REINTEGRO` y las reservas pendientes se anulan.