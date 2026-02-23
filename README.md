# CSIContratistas
## 🏗️ Descripción del Sistema
**CSI Contratistas** es una plataforma web desarrollada para la gestión interna y la presentación pública de las obras ejecutadas por la empresa. Su propósito principal es optimizar los procesos logísticos y administrativos.  
> 💡 *Nota: El sistema **no incluye** la venta directa de viviendas u otros productos.*
En su lugar, permite a los usuarios visualizar las obras disponibles y contactar directamente con la empresa a través de medios como **WhatsApp**.
---
## 🔧 Características del Sistema
- **Administración de obras:**  
  Registrar, editar y visualizar obras con datos como nombre, estado, ubicación, descripción e imágenes.
- **Panel administrativo:**  
  Sección exclusiva para gestionar obras, usuarios y roles.
- **Visualización pública:**  
  Obras accesibles a todo visitante, con filtros y búsqueda.
- **Contacto directo por WhatsApp:**  
  Cada obra tiene un botón para contacto inmediato.
- **Gestión de roles:**  
  Control de acceso por roles, habilitables o inhabilitables sin eliminación.
---
## 🛠️ Tecnologías Implementadas
### 🔹 Frontend
| Tecnología     | Descripción                                                                 | Imagen |
|----------------|-----------------------------------------------------------------------------|--------|
| **Tailwind CSS** | Framework de utilidades CSS para diseñar interfaces de forma rápida.         | <div align="center"><img src="https://btihen.dev/posts/ruby/rails_6_1_tailwind_2_0_alpinejs/featured_hu433a55fe148527a16c05c1bced7ccba5_22627_720x2500_fit_q75_h2_lanczos_3.webp" width="120" /></div> |
| **Next.js**      | Framework de React con SSR y generación estática.                            | <div align="center"><img src="https://images-cdn.openxcell.com/wp-content/uploads/2024/07/24154156/dango-inner-2.webp" width="120" /></div> |
| **HTML**         | Estructura básica del contenido de la web.                                  | <div align="center"><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQq0ACDy02LMjEHDp4qBd78QVn-twtLpWbEGg" width="120" /></div> |
| **CSS**          | Estilos visuales para HTML.                                                 | <div align="center"><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR0apCUN6WZtfvzKVGE4j7_TimSehe-aQJ7xA" width="120" /></div> |
| **JavaScript**   | Lenguaje para interactividad en la web.                                     | <div align="center"><img src="https://cdn.iconscout.com/icon/free/png-512/free-js-logo-icon-download-in-svg-png-gif-file-formats--badge-devicons-pack-design-development-icons-458325.png?f=webp&w=256" width="120" /></div> |
| **React**        | Librería de componentes para UI.                                            | <div align="center"><img src="https://cdn.iconscout.com/icon/free/png-512/free-react-logo-icon-download-in-svg-png-gif-file-formats--programming-langugae-freebies-pack-logos-icons-1175109.png?f=webp&w=256" width="120" /></div> |
| **Figma**        | Herramienta de diseño de interfaces.                                        | <div align="center"><img src="https://cdn.iconscout.com/icon/free/png-512/free-figma-logo-icon-download-in-svg-png-gif-file-formats--technology-social-media-vol-3-pack-logos-icons-2944870.png?f=webp&w=256" width="120" /></div> |
---
### 🔸 Backend
| Tecnología       | Descripción                                                                 | Imagen |
|------------------|-----------------------------------------------------------------------------|--------|
| **Spring Boot**  | Framework Java para APIs REST.                                               | <div align="center"><img src="https://cdn.iconscout.com/icon/free/png-512/free-spring-logo-icon-download-in-svg-png-gif-file-formats--company-brand-world-logos-vol-10-pack-icons-283031.png?f=webp&w=256" width="120" /></div> |
| **Java**         | Lenguaje backend robusto y multiplataforma.                                 | <div align="center"><img src="https://cdn.iconscout.com/icon/free/png-512/free-java-logo-icon-download-in-svg-png-gif-file-formats--wordmark-programming-language-pack-logos-icons-1174953.png?f=webp&w=256" width="120" /></div> |
| **Postman**      | Herramienta para probar y documentar APIs.                                  | <div align="center"><img src="https://cdn.iconscout.com/icon/free/png-512/free-postman-logo-icon-download-in-svg-png-gif-file-formats--technology-social-media-company-brand-vol-5-pack-logos-icons-2945092.png?f=webp&w=256" width="120" /></div> |
| **Karate Labs**  | Framework de pruebas BDD para APIs REST.                                    | <div align="center"><img src="https://raw.githubusercontent.com/karatelabs/karate/master/karate-logo-icon.png" width="120" /></div> |
---
## Arquitectura Utilizada


El sistema CSI Contratistas sigue el patrón de arquitectura **MVC (Modelo - Vista - Controlador)**, que permite una separación clara de responsabilidades y mejora el mantenimiento.
---
## 📂 Estructura del Proyecto (`src`)
```plaintext
📁 src
 ┣ 📁 controller        → Controladores (manejan las peticiones).
 ┣ 📁 model             → Entidades JPA mapeadas a la BD.
 ┣ 📁 repository        → Interfaces que acceden a la BD.
 ┣ 📁 service           → Lógica del negocio.
 ┣ 📁 dto               → Transferencia de datos entre capas.
 ┣ 📁 config            → Configuración general y de seguridad.
 ┗ 📁 security          → Seguridad JWT y roles.
---
```
# Implementacion del sistema
## 📄 Conexión a la Base de Datos en Oracle Cloud
La base de datos está alojada en **Oracle Cloud** y la URL de acceso para la conexión es la siguiente:
```plaintext
jdbc:oracle:thin:@//<host>:<puerto>/<servicio>
```
## 📄 Configuración application.properties - CSI Contratistas
Este archivo configura la conexión del backend de Spring Boot con Oracle Cloud, además de definir opciones de JPA, Hibernate, el servidor y la integración con el frontend en React.
---
## 🔗 Conexión a la base de datos (Oracle Cloud)
```properties
spring.datasource.url=jdbc:oracle:thin:@//<HOST>:<PUERTO>/<SERVICIO>
spring.datasource.username=
spring.datasource.password=
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
```
## 🧠 JPA & Hibernate
```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.Oracle12cDialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true
spring.jpa.properties.hibernate.id.new_generator_mappings=true
```
## 🌐 Configuración del servidor Spring Boot
```properties
server.port=8080
server.servlet.context-path=/
```
## 🔄 CORS para integración con React
```propierties
spring.web.cors.allowed-origins=http://localhost:3000
spring.web.cors.allowed-methods=GET,POST,PUT,DELETE
```
## 🧪 Swagger (opcional para documentar la API)
```propierties
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```
## 🔐 Seguridad (Spring Security)
```properties
spring.security.user.name=
spring.security.user.password=
```
## 📋 Logging
```properties
logging.level.org.springframework=INFO
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql=TRACE
```
## Link del repositorio
Descargar [Repositorio CsiContratistas ]().
---
## Nota Final
Este avance corresponde a la segunda etapa del desarrollo del sistema.
> En el siguiente avance se presentarán:
> - Módulos desarrollados.
> - Integración completa entre backend y frontend.
> - Pruebas finales del sistema.
---
© 2026 CSI CONTRATISTAS S.A.C  
Presentación elaborada por **[NextSpringers]** – Herramientas de Desarrollo
