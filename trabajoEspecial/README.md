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
