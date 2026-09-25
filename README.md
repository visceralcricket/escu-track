# **EscuTrack**
> *Programa de gestión hospitalaria.*

<img src="https://img.shields.io/badge/version-0.2.3-blue" alt="version">

[![Last Commit](https://img.shields.io/github/last-commit/visceralcricket/escu-track/main)](https://github.com/visceralcricket/infinity-escu-track/commits/main)

## **Premisa**
* **"EscuTrack"** es un sistema de gestión de salud cuyo objetivo es agilizar y optimizar los trabajos y distintas necesidades del staff médico de un establecimiento hospitalario respecto a la información de pacientes, control de disponibilidad de camas y estado de pacientes.

## **Cómo compilar y ejecutar el programa**

### Requisitos
* JDK 8 o superior (el proyecto está compilado con compliance 1.8, pero corre sin problemas en versiones más nuevas como 11, 17 o 21).
* No requiere librerías externas ni gestor de dependencias (Maven/Gradle): solo usa la biblioteca estándar de Java (`java.util`, `java.io`, `java.time`, `javax.swing`, `java.awt`).

### Importante: directorio de trabajo
El programa lee y guarda datos en `src/core/escutrack/resources/datos_hospital.csv` mediante una **ruta relativa**. Por eso, sin importar el IDE, **siempre debe ejecutarse desde la carpeta raíz del proyecto** (`escu-track/`). Si se ejecuta desde otra carpeta, no va a encontrar ni guardar correctamente los datos.

### Codificación de caracteres (tildes, ñ, bordes ASCII)
El código fuente usa tildes y caracteres especiales. Para evitar errores al compilar o texto ilegible en consola:

* Compilar siempre con el flag `-encoding UTF-8`.
* En Windows (CMD/PowerShell), antes de ejecutar, correr:
  > `[Console]::OutputEncoding = [System.Text.Encoding]::UTF8`
* En Linux/macOS normalmente no hace falta, ya que la terminal usa UTF-8 por defecto.

### Opción A: Ejecutar desde Eclipse
1. `File → Import... → Git → Projects from Git` (o `Existing Projects into Workspace` si ya se clonó el repositorio).
2. Eclipse reconoce el proyecto automáticamente gracias a los archivos `.project` y `.classpath` ya incluidos.
3. Clic derecho sobre `Main.java` → `Run As → Java Application`.
   * Eclipse usa por defecto la raíz del proyecto como directorio de trabajo, así que no hay que configurar nada más.

### Opción B: Ejecutar desde otro IDE (IntelliJ, VS Code, NetBeans)
1. Abrir la carpeta `escu-track/` como proyecto.
2. Marcar `src` como carpeta de código fuente (*Source Root* / *Sources Folder*).
3. Configurar la clase principal: `core.escutrack.Main`.
4. **Importante:** en la configuración de ejecución (*Run Configuration*), fijar el *Working directory* en la carpeta raíz del proyecto (`escu-track/`), no en `src` ni en la carpeta del IDE.

### Opción C: Compilar y ejecutar por línea de comandos
Desde la carpeta raíz del proyecto (`escu-track/`):

```bash
# Compilar (Linux/macOS)
javac -encoding UTF-8 -d bin $(find src -name "*.java")

# Ejecutar
java -cp bin core.escutrack.Main
```

```powershell
# Compilar (Windows PowerShell)
javac -encoding UTF-8 -d bin (Get-ChildItem -Recurse -Filter *.java -Path src).FullName

# Ejecutar
java -cp bin core.escutrack.Main
```

### Notas sobre plataforma
* El **Modo Consola** funciona en cualquier sistema operativo sin restricciones.
* El **Modo Ventana** usa `javax.swing`/`java.awt`, por lo que necesita un entorno gráfico (no funciona en servidores sin interfaz gráfica / modo headless).
* El script `extract_version.bat` (actualiza automáticamente el número de versión) **solo funciona en Windows**. En Linux/macOS el programa muestra una advertencia en consola y sigue funcionando con normalidad, usando el número ya guardado en `resources/version.txt`.

**IMPORTANTE:** Al intentar añadir un nuevo departamento en el submenú de gestión correspondiente, se debe agregar por lo menos **1** elemento a dicho departamento: es decir, por lo menos **una cama**. De lo contrario, al cerrar y guardar el programa, este **NO** preservará el nombre del departamento (que a fines prácticos está vacío) en el archivo CSV de datos_hospital.

## **Distribución de directorios**
<pre><code><i><span style="color: #00fed4ed;">Cómo se organiza el código?</span></i></code></pre>

* <code><b><span style="color: #23c523d4;">src/</span></b></code>: Directorio principal del código fuente. Contiene las funcionalidades clave del programa y la lógica central del mismo.

  * <code><b><span style="color: #009dff;">core/escutrack/</span></b></code>

    * <code><b><span style="color: #f43009da;">Main.java</span></b></code>: Archivo principal del programa.

    * <code><b><span style="color: #009dff;">controller/</span></b></code>
        * <code><b><span style="color: #f43009da;">ControladorHospital.java</span></b></code>: 

    * <code><b><span style="color: #009dff;">model/</span></b></code>
        * <code><b><span style="color: #f43009da;">Paciente.java</span></b></code>: 
        
## **SIAS implementados**
``` diff
+ SIA-1: Análisis de datos y funcionalidades.
+ SIA-2: Diseño conceptual UML y codificación.
+ SIA-3: Buenas prácticas (encapsulamiento, inicialización de datos de prueba).
+ SIA-4: Colecciones anidadas del JCF (Map<String, Map<String, Cama>>).
+ SIA-5: Sobrecarga de métodos (Cama.setPaciente, Controlador.registrarPaciente).
+ SIA-6: Sobreescritura de métodos (toString en Cama y Paciente).
+ SIA-7: Menú con Inserción y Mostrar implementado para colecciones anidadas de Departamentos y Camas de forma independiente.
+ SIA-8: Menú de Consola con Edición, Eliminación y Búsqueda operando bajo validación estricta de regex.
+ SIA-9: Funcionalidad de negocio propia (filtrado de pacientes por gravedad).
+ SIA-10: Modos de Consola y Ventana (ahora con 'WindowBuilder Current').
+ SIA-11: Implementada persistencia de datos por batch ->  guardado el estado del hospital en un archivo .csv al salir y abrir el programa de forma automática.
+ SIA-12: Implementación de 2 excepciones personalizadas con try-catch (CamaOcupadaException, EntidadNoEncontradaException).
```

## **Funcionalidades pendientes** 
```diff
@@ A nivel de renderizado / visual @@
+ front-end en archivo Main.java

@@ A nivel de lógica de procedimientos / sistemas @@
+ [RESUELTO]: Añadir funcionalidad de alternar Modo Ventana con modo consola (preguntar al iniciar el programa cuál modo se desea usar y alternar front-end dependiendo de esto mismo).
```
## **Problemas conocidos**

```diff
@@ A nivel de renderizado / visual @@
> @@[Nada aún]@@

@@ A nivel de lógica de procedimientos / sistemas @@
+ [BUG ARREGLADO]: El programa arroja una excepción NullPointerException y se cuelga a la hora de, en el Modo Ventana, cerrar la ventana de diálogo forzosamente (clickear "cancel") o presionando ESC.
```
# **Changelog - EscuTrack**
<small>*Nota: Este changelog utiliza fechas en ISO estándar: YY-MM-DD.*</small>

## [0.2.3] - 2026-09-24
> Añadidas funcionalidades faltantes de versión anterior a modo Ventana

### Añadido

+ Funcionalidades previamente mencionadas de búsqueda de Departamento/Cama y edición de Paciente a sus respectivas Ventanas.
+ Mejoras considerables a las visuales de las Ventanas -> ahora tienen dimensiones estándares de mayor tamaño (512x512) para mejor visibilidad y claridad del sistema para el usuario.
+ Arreglado **bug** que surgió durante el desarrollo donde el plugin de WindowBuilder instalado en el proyecto **NO** permitía abrir `VentanaPrincipal` en modo *Diseño*: esto parece haber ocurrido debido a que el parseador del plugin tuvo problemas a la hora de leer sintaxis de llamadas a métodos, por ejemplo los métodos que se encontraban anidados en el código fuente de `VentanaPrincipal` -> por esto, se reestructuró el código fuente de dicha Ventana mediante `CardLayout` para mitigar este error

### Cambios

+ Arreglos generales para mejores prácticas a lo largo de los códigos fuentes de las Ventanas: renombrar botones de `btnRegistrarPaciente` a `btnRegistrar`, `btnEliminarDepartamento` a `btnEliminar`, etc -> esto con el fin de estandarizar los descriptores de las funcionalidades, reservando nombres más específicos a las funcionalidades más complejas como `btnFiltrarPorGravedad`.

## [0.2.2] - 2026-09-24
> Implementadas funcionalidades faltantes al modo consola

### Añadido

+ Métodos de búsqueda de entidades Departamento, Cama y edición de datos de Paciente.
+ Actualizado `RenderizadorConsola` para mostrar dichas funciones nuevas.
+ Lógica de programa para crear automáticamente (hard-codear) un archivo CSV *predeterminado* en caso de que el archivo almacenado por el programa se elimine, corrompa o le ocurra cualquier eventualidad que imposibilite su procesamiento por el sistema.

### Pendiente
```diff
- Implementar dichas funciones en el modo Ventana (`VentanaDepartamentos`, `VentanaCamas` y `VentanaPacientes`).
- Modificar SIA-6 -> método toString puesto que no aplica polimorfismo real.
```

## [0.2.1 - 2026-09-11
> Finalizada la implementación de todos los botones faltantes para el modo ventana.

### Añadido

+ Archivos para cada submenú dentro del módulo de visualización `view` (VentanaPacientes, VentanaCamas, VentanaDepartamentos) para una modularización más limpia.

## [0.2.0] - 2026-09-11
> Consolidado flujo de la consola, cierre de requerimientos CRUD y back-end afinado.

### Añadido

+ Módulo `RenderizadorConsola` para encapsular y separar la interfaz de texto del flujo principal del sistema.
+ Submenús independientes y funcionales para la gestión completa (SIA-7 y SIA-8) de las entidades Departamento, Cama y Paciente.
+ Programación defensiva aplicada a `ControladorHospital` (`IllegalStateException`) para prevenir la eliminación de departamentos con camas *o* camas ocupadas.

### Cambios

+ Descentralización de la función utilitaria `solicitarEntrada` -> trasladada al módulo de renderizado visual (`view`).

## [0.1.5] - 2026-09-06
> Transición a patrón MVC para interfaz gráfica y refactorización de flujos de ejecución.

### Añadido

+ Versión inicial de `VentanaPrincipal.java` en el (nuevo) módulo `core.escutrack.view` utilizando *WindowBuilder Current* para construir y gestionar el entorno gráfico de forma independiente del flujo principal.

### Cambios

+ Refactorización del flujo principal en `Main.java` para adaptarlo al nuevo entorno gráfico autónomo y aislar los hilos de ejecución del Modo Consola y el Modo Ventana.
+ Eliminadas validaciones de Modo Actual en el ciclo base obsoletas tras la separación del la interfaz gráfica del Modo Consola.
+ Eliminada excepción personalizada en pro de mantener simplicidad y abstracción mediante la asignación de `IllegalArgumentException` para todos los campos leídos por el programa que presenten formatos no válidos.

## [0.1.4] - 2026-09-02
> Creación de funcionalidad filtrarPorGravedad

### Añadido

+ Función temprana creada de filtrarPorGravedad que se encarga de mostrar un listado de los pacientes que tengan un nivel de gravedad especificado junto con los datos del paciente y el departamento al que pertenece.
+ Implementación de función filtrarPorGravedad a archivo Main.java

## [0.1.3] - 2026-09-02
> Consolidación de front-end, interfaz dual y manejo seguro de excepciones.

### Añadido

+ Estructura `enum EscutrackMode` para controlar dinámicamente el estado visual del programa (Modo Consola y Ventana)
+ Wrappers de I/O en `Main.java` mediante `JOptionPane` y `BufferedReader` para permitir ejecución híbrida del sistema (SIA-10).
+ Excepciones personalizadas (`CamaOcupadaException` y `EntidadNoEncontradaException`) añadidas al módulo de utilidades para aislar errores lógicos (SIA-12).
+ Función modular `verificarCampo` de validación para evitar procesamiento de cadenas vacías o nulas introducidas por el usuario sin romper el flujo lógico del programa.

### Cambios

+ Refactorización de `ConotroladorHospital.java`: se eliminaron las dependencias de impresión directa en consola para respetar estrictamente el patrón **MVC**, delegando el renderizado al front-end.
+ Estilización de la interfaz de Ventanas mediante `UIManager`, reemplazando la fuente por defecto por "Consolas" para mayor legibilidad.
+ Resolución del bug crítico `NullPointerException` provocado al cancelar forzosamente los cuadros de diálogo en el Modo Ventana.
+ El bucle principal ahora evalúa y maneja correctamente entradas vacías (`""`) enviadas accidentalmente mediante la tecla Enter en la consola.

## [0.1.2] - 2026-08-18
> Implementacion de la nueva entidad Cama

### Añadido

+ Cambio en el funcionamiento de la clase  ControladorHospital para utilizar la nueva entidad
+ Culmino de requisitos minimos para SIA 5 y SIA 6

> Cambios y mejoras generales realizadas

### Cambios

  + Archivo principal del programa renombrado a `Main.java` para que este mismo sea más descriptivo.
  + Añadida documentación de carácter organizativo a distintos archivos para agilizar el desarrollo de los mismos.

## [0.1.1] - 2026-08-16
> Añadida automatización de número de versión en el ciclo principal del programa

### Añadido

+ Script `extract_version.bat` creado para transmitir el número de versión actual del sistema al ciclo principal del mismo en `Main.java`
+ Front-end básico en `Main.java`.
+ Añadido paquete `src/ core.escutrack.resources` que contiene el archivo `version.txt` donde se almacena el número de versión del programa *EscuTrack*.
+ Metodos para eliminar y mostrar paciente.
+ Parametros para fecha de ingreso y egreso para cada paciente.
+ Creacion de la clase cama.

## [0.1.0] - 2026-08-16
> Implementadas entidades principales y funcionalidades clave.
### Añadido
  
  + **Arquitectura base:** Estructuración de paquetes bajo el patrón de separación de responsabilidades lógicas para mantener modularización.
  + **Entidad `Paciente`:** Implementado mediante encapsulamiento estricto: atributos clínicos, parseo automatizado de grado de gravedad (String a Integer) utilizando `HashMap`.
  + **Entidad `ControladorHospital`:** Configuración del nivel de gestión con mapas anidados del JCF (`Map<String, Map<String, Paciente>>`) para el rastreo espacial (departamento -> idCama -> Paciente).
  + **Esqueleto `Main`:** Estructura inicial del `BufferedReader` para la lectura del flujo de entrada en consola.
