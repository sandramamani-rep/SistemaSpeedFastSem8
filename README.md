![Duoc UC](https://www.duoc.cl/wp-content/uploads/2022/09/logo-0.png)

# 🚚 SpeedFast — Gestión de pedidos y entregas

## 👤 Autor del proyecto

- **Nombre completo:** Sandra Mamani
- **Carrera:** Analista Programador Computacional
- **Sede:** Online

---

## 📘 Descripción general

Este proyecto corresponde a la actividad sumativa de la semana 8 de la asignatura *Desarrollo Orientado a Objetos II*. Es una aplicación de escritorio desarrollada en Java que permite gestionar repartidores, pedidos y entregas para la empresa SpeedFast.

La aplicación utiliza Java Swing para la interfaz gráfica y JDBC para conectarse con MySQL. Las operaciones de persistencia están organizadas en capas de modelo, DAO, controlador y vista.

---

## ✅ Funcionalidades

### Repartidores

- Registrar repartidores con su nombre.
- Editar y eliminar repartidores.
- Consultar los repartidores registrados en una tabla.

### Pedidos

- Registrar pedidos con dirección, tipo y estado.
- Editar y eliminar pedidos.
- Consultar los pedidos en una tabla.
- Filtrar los pedidos por tipo o mostrar todos con la opción **TODOS**.
- Tipos disponibles: `COMIDA`, `ENCOMIENDA` y `EXPRESS`.
- Estados disponibles: `PENDIENTE`, `EN_REPARTO` y `ENTREGADO`.

### Entregas

- Registrar una entrega asociando un pedido y un repartidor seleccionados desde listas desplegables.
- Consultar las entregas con sus datos asociados.
- Editar el pedido o el repartidor asignado a una entrega.
- Eliminar entregas.
- Al actualizar una entrega, se conservan su fecha y hora originales.
- Al registrar una entrega, el estado del pedido asociado cambia a `ENTREGADO`. Esta actualización se realiza al crear la entrega; editarla no modifica automáticamente los estados de los pedidos.

Después de las operaciones, las tablas y los combos relacionados se actualizan. La interfaz muestra mensajes de resultado y validación mediante cuadros de diálogo.

---

## 🧱 Estructura del proyecto

```text
SistemaSpeedFastSem8/
├── lib/
│   └── mysql-connector-j-26.7.0.jar
├── src/
│   ├── controlador/
│   │   ├── ControladorEntregas.java
│   │   ├── ControladorPedidos.java
│   │   └── ControladorRepartidores.java
│   ├── dao/
│   │   ├── EntregaDAO.java
│   │   ├── PedidoDAO.java
│   │   ├── RepartidorDAO.java
│   │   └── impl/
│   │       ├── EntregaDAOImpl.java
│   │       ├── PedidoDAOImpl.java
│   │       └── RepartidorDAOImpl.java
│   ├── main/
│   │   └── Main.java
│   ├── modelo/
│   │   ├── Entrega.java
│   │   ├── EstadoPedido.java
│   │   ├── Pedido.java
│   │   ├── Repartidor.java
│   │   └── TipoPedido.java
│   ├── sql/
│   │   └── speedfast_db.sql
│   ├── util/
│   │   └── ConexionBD.java
│   └── vista/
│       ├── VentanaPrincipal.java
│       └── VentanaPrincipal.form
└── README.md
```

### Organización por capas

- **modelo:** representa pedidos, repartidores, entregas, tipos y estados.
- **dao:** declara las operaciones de persistencia por entidad.
- **dao.impl:** implementa las operaciones CRUD mediante JDBC.
- **controlador:** valida datos y coordina la comunicación entre la vista y los DAO.
- **vista:** contiene la interfaz gráfica desarrollada con Swing.
- **util:** contiene la clase de conexión a la base de datos.
- **sql:** incluye el script del esquema de la base de datos.

---

## 🗃️ Base de datos

La aplicación utiliza la base de datos MySQL `speedfast_db`, con las tablas `pedido`, `repartidor` y `entrega`. La tabla `entrega` relaciona los pedidos con los repartidores mediante sus identificadores.

El script SQL se encuentra en:

```text
src/sql/speedfast_db.sql
```

Ejecuta el script entregado para crear la base de datos y sus tablas, según las instrucciones de la actividad.

---

## ⚙️ Requisitos

- IntelliJ IDEA.
- MySQL Server.
- Conector JDBC de MySQL incluido en `lib/mysql-connector-j-26.7.0.jar`.

---

## 🔌 Configuración de la conexión

La conexión está implementada en:

```text
src/util/ConexionBD.java
```

La configuración utiliza la base de datos local `speedfast_db`. Antes de ejecutar el proyecto, revisa en esa clase la URL, el usuario y la contraseña de MySQL y ajústalos a tu entorno.

En IntelliJ IDEA, verifica que el conector JDBC esté agregado como dependencia del proyecto. Si no está configurado, agrégalo desde:

```text
File > Project Structure > Modules > Dependencies > + > JARs or Directories
```

Selecciona:

```text
lib/mysql-connector-j-26.7.0.jar
```

---

## ▶️ Instrucciones para ejecutar

1. Clona el repositorio:

   ```bash
   git clone https://github.com/sandramamani-rep/SistemaSpeedFastSem8.git
   ```

2. Abre la carpeta `SistemaSpeedFastSem8` en IntelliJ IDEA.
3. Configura JDK 25 como SDK del proyecto.
4. Ejecuta el script `src/sql/speedfast_db.sql` en MySQL.
5. Revisa la configuración de conexión en `src/util/ConexionBD.java`.
6. Comprueba que el conector JDBC esté agregado al proyecto.
7. Ejecuta la clase `src/main/Main.java`.

---

## 🖥️ Uso de la aplicación

1. En la pestaña **Pedidos**, registra pedidos indicando dirección, tipo y estado. Puedes utilizar el filtro por tipo para ver solo los pedidos que correspondan o seleccionar **TODOS**.
2. En la pestaña **Repartidores**, registra, selecciona, edita o elimina repartidores.
3. En la pestaña **Entregas**, selecciona un pedido y un repartidor para registrar la entrega.
4. Selecciona una entrega de la tabla para editar la asociación de pedido y repartidor o eliminarla.

---

## 💾 Persistencia y manejo de errores

Los DAO realizan operaciones CRUD utilizando `PreparedStatement` y `ResultSet`. Las conexiones y demás recursos JDBC se gestionan con `try-with-resources`, y las excepciones SQL se capturan para informar los errores.

La interfaz valida los campos y selecciones antes de solicitar las operaciones, y muestra mensajes de resultado al usuario.

---

## 🔗 Repositorio

- **GitHub:** [SistemaSpeedFastSem8](https://github.com/sandramamani-rep/SistemaSpeedFastSem8)

---

© Duoc UC | Escuela de Informática y Telecomunicaciones | Semana 8
