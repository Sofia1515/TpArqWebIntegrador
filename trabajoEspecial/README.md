# TicketYa - Trabajo Práctico Especial (Entrega 1)

Sistema para la gestion y venta de entradas a eventos, desarrollado con Java 21 y Spring Boot utilizando una arquitectura de microservicios.

## Servicios

El proyecto cuenta con cuatro servicios, cada uno ejecutándose en un puerto diferente y con su propia base de datos H2 en memoria.

* API Gateway: Puerto 8080
* Usuario Service: Puerto 8081 (Base de datos: usuario_db)
* Evento Service: Puerto 8082 (Base de datos: evento_db)
* Reserva Service: Puerto 8083 (Base de datos: reserva_db)

## Cómo ejecutar el proyecto

Para iniciar el sistema, levantar los servicios en este orden:

1. usuario-servicio
2. evento-servicio
3. reserva-service
4. api-gateway

## Datos de prueba

### Usuarios

* ID 1 - Rol: Cliente - Email: juan.perez@example.com
* ID 2 - Rol: Organizador - Email: organizador@example.com
* ID 3 - Rol: Admin - Email: admin@example.com

### Recinto

* ID: 1
* Nombre: Estadio San Martín
* Ciudad: Tandil
* Capacidad: 5000 personas

### Evento

* ID: 1
* Nombre: Rock en Tandil 2026
* Estado: PUBLICADO
* Organizador ID: 2

### Sector

* ID: 1
* Nombre: Platea Preferencial
* Tipo: Numerado
* Filas: 10
* Asientos por fila: 10
* Precio: $15000

## Pruebas de la API

### Crear una reserva

POST `/api/reservas`

Ejemplo del cuerpo de la petición:

```json
{
  "idUsuario": 1,
  "idEvento": 1,
  "idSector": 1,
  "fila": 2,
  "asiento": 5
}
```

### Confirmar una reserva

**PUT** `/api/reservas/{id}/confirmar`

Para confirmar la compra se debe enviar el siguiente parámetro:

- medioDePago: MERCADOPAGO

## Swagger

Se puede acceder a la documentación de cada servicio desde las siguientes direcciones:

- Usuarios: http://localhost:8081/swagger-ui.html
- Eventos: http://localhost:8082/swagger-ui.html
- Reservas: http://localhost:8083/swagger-ui.html

## Tests Unitarios y de Concurrencia

El proyecto cuenta con pruebas automatizadas para garantizar la lógica de negocio y la seguridad del sistema.

* **`ReservaDobleTest` (Concurrencia):** Simula 15 compras simultáneas del mismo asiento. Verifica que solo 1 sea exitosa (HTTP 201) y las 14 restantes fallen con HTTP 409 (Conflict), validando el mecanismo contra la doble venta.
* **`DevolucionTest` (Lógica):** Verifica que las devoluciones se permitan solo si faltan más de 72 horas para el evento.
* **`ValidarQrTest` (Lógica):** Verifica que una entrada no pueda ser escaneada dos veces (el segundo intento devuelve HTTP 409).

**¿Cómo correr los tests?**
Ejecutar desde la carpeta del `reserva-service`:
```bash
mvn test

## Fila virtual para eventos de alta demanda

La fila virtual se implementa en `reserva-service` utilizando Redis Sorted Sets, permitiendo administrar el orden de llegada de los usuarios mediante una política FIFO (First In, First Out).

### Funcionamiento

1. El usuario ingresa a la fila virtual de un evento publicado y marcado como de alta demanda.
2. Redis registra su ingreso y asigna una posición según el orden de llegada.
3. El usuario puede consultar su posición.
4. El organizador o administrador habilita una tanda de usuarios para comprar.
5. Los usuarios habilitados disponen de 10 minutos para realizar su reserva.
6. Al crear una reserva, la habilitación se consume de manera atómica para evitar su reutilización.
7. Si la habilitación vence, el usuario vuelve al final de la fila.

### Endpoints

| Método | Endpoint | Función |
|---|---|---|
| POST | `/api/fila/eventos/{idEvento}/ingresar` | Ingresar a la fila |
| GET | `/api/fila/eventos/{idEvento}/posicion` | Consultar posición |
| POST | `/api/fila/eventos/{idEvento}/habilitar?cantidad=10` | Habilitar una tanda |

El ingreso y la consulta utilizan el encabezado `X-User-Id`, enviado por el API Gateway luego de validar el JWT. La habilitación de tandas está restringida a los roles ORGANIZADOR y ADMINISTRADOR en el Gateway.

### Estructuras Redis

- `fila:evento:{idEvento}`: usuarios que esperan, ordenados por puntuación.
- `fila:secuencia:evento:{idEvento}`: contador de posiciones.
- `fila:habilitados:evento:{idEvento}`: usuarios habilitados y vencimiento de su permiso.
- `fila:ingresados:evento:{idEvento}`: usuarios que ya ingresaron al proceso.

### Ventajas

- Orden FIFO.
- Acceso rápido mediante Redis.
- Liberación de usuarios por tandas.
- Prevención del ingreso duplicado.
- Habilitaciones con vencimiento.
- Consumo atómico del permiso de compra.


