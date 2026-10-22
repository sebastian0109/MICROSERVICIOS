# ms-cursos — AulaNube

Microservicio REST de **cursos y módulos** de la plataforma AulaNube (JVY0101, Evaluación Parcial 2).
Spring Boot + Spring Data JPA + MariaDB. Cada microservicio de AulaNube tiene su propia base de datos.

**Autores:** _(sebastian tapia y jimber camacho)_

## Arquitectura

```
controller  -> endpoints REST (/api/cursos, /api/modulos)
service     -> reglas de negocio y transacciones
repository  -> acceso a datos (Spring Data JPA)
model       -> entidades JPA (Curso 1:N Modulo)
exception   -> manejo de errores uniforme (404, 409, 400)
```

Modelo de datos: `Curso` (1) —— (N) `Modulo`, con clave foránea `curso_id` en la tabla `modulos`.
`instructorId` en `Curso` guarda solo el id del usuario de **ms-usuarios** (no hay FK entre microservicios).

## Requisitos

- JDK 17 o superior
- Maven (o el `mvnw` del proyecto)
- MariaDB/MySQL en `localhost:3306` (por ejemplo, XAMPP con MySQL iniciado)

## Cómo ejecutarlo

```bash
git clone <URL_DEL_REPOSITORIO>
cd ms-cursos
```

1. Iniciar MariaDB (XAMPP -> Start en MySQL). La base `ms_cursos_db` se crea sola
   (`createDatabaseIfNotExist=true`).
2. Revisar usuario y clave en `src/main/resources/application.properties`
   (por defecto `root` sin contraseña, como XAMPP).
3. Compilar, probar y empaquetar:

```bash
mvn clean install        # compila, ejecuta las pruebas y genera target/ms-cursos-1.0.0.jar
```

4. Ejecutar:

```bash
mvn spring-boot:run
# o bien
java -jar target/ms-cursos-1.0.0.jar
```

La API queda en `http://localhost:8082`.

## Endpoints

| Método | Ruta | Descripción | Respuestas |
|---|---|---|---|
| GET | `/api/cursos` | Lista los cursos | 200 |
| GET | `/api/cursos/{id}` | Obtiene un curso | 200, 404 |
| GET | `/api/cursos/{id}/modulos` | Módulos del curso, por orden | 200, 404 |
| POST | `/api/cursos` | Crea un curso | 201, 400, 409 (título repetido) |
| PUT | `/api/cursos/{id}` | Actualiza un curso | 200, 400, 404, 409 |
| DELETE | `/api/cursos/{id}` | Elimina un curso | 204, 404, 409 (tiene módulos) |
| GET | `/api/modulos` | Lista los módulos | 200 |
| GET | `/api/modulos/{id}` | Obtiene un módulo | 200, 404 |
| POST | `/api/modulos` | Crea un módulo | 201, 400, 404 (curso no existe) |
| PUT | `/api/modulos/{id}` | Actualiza un módulo | 200, 400, 404 |
| DELETE | `/api/modulos/{id}` | Elimina un módulo | 204, 404 |

Ejemplos de cuerpo:

```json
// POST /api/cursos
{ "titulo": "Java con Spring Boot", "descripcion": "Microservicios desde cero", "precio": 49990, "instructorId": 1 }

// POST /api/modulos
{ "titulo": "Introducción", "orden": 1, "curso": { "id": 1 } }
```

Formato de error uniforme:

```json
{ "timestamp": "2026-10-20T20:30:00", "status": 404, "error": "Not Found", "mensaje": "Curso con id 99 no existe" }
```

## Pruebas

- **Automáticas:** `mvn test` (usan H2 en memoria, no necesitan MariaDB).
- **Manuales:** importar `postman/ms-cursos.postman_collection.json` en Postman y ejecutar en orden.

## Control de versiones

Ramas: `main` (estable), `develop` (integración), `feature/*` (cada funcionalidad).
