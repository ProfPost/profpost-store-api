## Introducción

<img src="https://github.com/user-attachments/assets/65d10cc3-eba4-40ef-9036-9dab6beb6b62" alt="ProfPost Logo" align="left" width="200" style="margin-right: 20px;"/>

**PROFPOST** es una plataforma web diseñada para facilitar la publicación y monetización de contenidos educativos en el entorno académico universitario. Con ProfPost, los creadores de contenido académico pueden compartir sus conocimientos a través de artículos especializados, al mismo tiempo que monetizan su trabajo a través de suscripciones y donaciones. La plataforma permite a los usuarios crear cuentas, gestionar sus perfiles, y acceder a herramientas de análisis que les ayudarán a optimizar su contenido y a comprender mejor su audiencia.

El propósito de ProfPost es ofrecer un entorno seguro y accesible para la publicación y monetización de contenidos educativos. Nuestro enfoque es fortalecer la colaboración académica y facilitar la creación de valor a partir del intercambio de conocimiento especializado.

### Colaboradores del Proyecto

| **Nombre**                        | **Rol**                                     | 
|-----------------------------------|---------------------------------------------|
| Berrospi Reyes, Hernán Mauriccio Gaston     | Scrum Master | 
| Llaure Calipuy, Oscar Segundo      | Scrum Team | 
| Oliden Agreda, Alejandro del Piero     | Scrum Team | 
| Ramos Cotrina, Vania Melissa      | Scrum Team | 
| Velasquez Reyes, Sergio Emanuel      | Scrum Team | 

### Revisa el Progreso del Proyecto PROFPOST

| **Columna**          | **Descripción**                                                                                                                                    |
|:-----------------:|----------------------------------------------------------------------------------------------------------------------------------------------------|
| **Backlog**       | Contiene todas las historias de usuario, tareas y características que deben desarrollarse. Es el listado de todo el trabajo pendiente.              |
| **En Progreso**   | Incluye las tareas que están actualmente en desarrollo. Visualiza el trabajo en curso para asegurar el flujo continuo de trabajo.                   |
| **Revisión**      | Después de completar una tarea, se mueve aquí para una revisión de código y revisión por pares (peer review). Esta fase incluye la creación de **pull requests** para asegurar que el código cumpla con los estándares de calidad antes de integrarse al proyecto principal. |
| **En Pruebas**    | Contiene las tareas que han pasado la revisión de código y necesitan pruebas exhaustivas (unitarias, de integración y de aceptación) para garantizar su calidad. |
| **Hecho**         | Las tareas completamente desarrolladas, revisadas y probadas se mueven aquí, indicando que están listas y finalizadas.                               |

Mira cómo va avanzando nuestro trabajo visitando el siguiente enlace: [Tablero de Jira](https://postpro.atlassian.net/jira/software/projects/SCRUM/boards/1?atlOrigin=eyJpIjoiN2ViNDRiMWZjNzI4NDc5NGFiMjYzOGUyY2Q4NjU4MDgiLCJwIjoiaiJ9).

### Funcionalidades de la Aplicación PROFPOST

#### **Módulo de Gestión de Usuarios**

- **Registro y Perfil:**
    - Permitir a los usuarios registrarse y crear perfiles donde podrán mostrar sus artículos, intereses y experiencias.
    - Gestionar la edición de perfiles para que los usuarios puedan actualizar su información personal y preferencias.
    - Mantener la seguridad de las credenciales de los usuarios.

#### **Módulo de Creación y Publicación de Contenidos**

- **Gestión de Publicaciones:**
    - Implementar un editor de texto enriquecido para dar formato a las publicaciones.
    - Permitir la inserción de imágenes, videos y enlaces externos en los artículos.
    - Previsualizar el contenido antes de publicarlos para asegurar la calidad del contenido.
    - Publicar, editar o eliminar publicaciones de manera eficiente.
    - Programar la publicación para fechas futuras, facilitando una planificación flexible del contenido.

#### **Módulo de Ingresos y Monetización**

- **Suscripciones y Donaciones:**
    - Ofrecer la opción de suscribirse a los connsumidores interesados en apoyar a los creadores de contenido.
    - Permitir donaciones únicas a través de la plataforma, fomentando el soporte directo de los consumidores a los creadores.
    - Facilitar el retiro de las ganancias a través de plataformas de pago como PayPal, asegurando transacciones seguras y rápidas.

#### **Módulo de Reporte**

- **Suscripciones y Donaciones:**
    - Los creadores tendrán acceso a reportes gráficos que presentarán estadísticas detalladas de sus artículos, tales como las interacciones, y ganancias generadas.
      
#### **Módulo de Comunidad**

- **Suscripciones y Donaciones:**
    - Los consumidores podrán comentar las publicaciones, suscribirse a sus creadores favoritos, fomentando la participación activa y el crecimiento de la comunidad académica.

## Diagramas de la Aplicación

Para entender mejor la estructura y diseño de la aplicación "BookHub", revisa los siguientes diagramas:

### Diagrama de Clases

![Diagrama de clases](https://github.com/user-attachments/assets/dc35b74d-9fbe-4ede-ac95-1ee62e08e111)


### Diagrama de Base de Datos

![Diagrama de Base de Datos](https://github.com/user-attachments/assets/a5cfec33-7f51-4ba9-b1a3-d7759190643e)

Este diagrama ilustra el esquema de la base de datos utilizada por la aplicación, mostrando las tablas, columnas, y relaciones entre las entidades.

### Descripción de Capas del Proyecto

| Capa        | Descripción                                                                                  |
|:------------:|----------------------------------------------------------------------------------------------|
| api         | Contiene los controladores REST que manejan las solicitudes HTTP y las respuestas.            |
| config      | Gestiona la configuración general del proyecto.      |
| dto      | Define los objetos de transferencia de datos, representan la estructura de cómo se enviarán los datos en formato JSON en un request y cómo se recibirán en la respuesta.|
| mapper      | Mapea y convierte las entidades entre DTOs y entidades del modelo de datos.      |
| entity      | Define las entidades del modelo de datos que se mapean a las tablas de la base de datos.      |
| repository  | Proporciona la interfaz para las operaciones y la interacción con la base de datos.      |
| service     | Declara la lógica de negocio y las operaciones que se realizarán sobre las entidades.         |
| service impl| Implementa la lógica de negocio definida en los servicios, utilizando los repositorios necesarios. |
