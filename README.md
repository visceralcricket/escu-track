# **EscuTrack**
> *Programa de gestión hospitalaria.*

<img src="https://img.shields.io/badge/version-0.1.1-blue" alt="version">

[![Last Commit](https://img.shields.io/github/last-commit/visceralcricket/escu-track/main)](https://github.com/visceralcricket/infinity-escu-track/commits/main)

## **Premisa**
* **"EscuTrack"** es un sistema de gestión de salud cuyo objetivo es agilizar y optimizar los trabajos y distintas necesidades del staff médico de un establecimiento hospitalario respecto a la información de pacientes, control de disponibilidad de camas y estado de pacientes.

## **Cómo compilar y ejecutar el programa**

1. **Paso:** texto...

2. **Paso:** texto...
  ```bash
  comando ruta/hacia/escu-track
  ```

  * Configurar codificación (Obligatorio en Windows): Para que la terminal dibuje correctamente el arte ASCII, las tildes y los bordes del mapa, ejecute:
  > [Console]::OutputEncoding = [System.text.Encoding]::UTF8

## **Distribución de directorios**
<pre><code><i><span style="color: #00fed4ed;">Cómo se organiza el código?</span></i></code></pre>

* <code><b><span style="color: #23c523d4;">src/</span></b></code>: Directorio principal del código fuente. Contiene las funcionalidades clave del programa y la lógica central del mismo.

  * <code><b><span style="color: #009dff;">core/escutrack/</span></b></code>

    * <code><b><span style="color: #f43009da;">Main.java</span></b></code>: Archivo principal del programa.

    * <code><b><span style="color: #009dff;">controller/</span></b></code>
        * <code><b><span style="color: #f43009da;">ControladorHospital.java</span></b></code>: 

    * <code><b><span style="color: #009dff;">model/</span></b></code>
        * <code><b><span style="color: #f43009da;">Paciente.java</span></b></code>: 

## **Funcionalidades pendientes** 
```diff
@@ A nivel de renderizado / visual @@
> @@[Nada aún]@@

@@ A nivel de lógica de procedimientos / sistemas @@
> @@[Nada aún]@@
```
## **Problemas conocidos**

```diff
@@ A nivel de renderizado / visual @@
> @@[Nada aún]@@

@@ A nivel de lógica de procedimientos / sistemas @@
> @@[Nada aún]@@
```
# **Changelog - EscuTrack**
<small>*Nota: Este changelog utiliza fechas en ISO estándar: YY-MM-DD.*</small>

## [0.1.1] - 2026-08-16
> Añadida automatización de número de versión en el ciclo principal del programa
### Añadido
+ Script `extract_version.bat` creado para transmitir el número de versión actual del sistema al ciclo principal del mismo en `Main.java`
+ Front-end básico en `Main.java`.
+ Añadido paquete `src/ core.escutrack.resources` que contiene el archivo `version.txt` donde se almacena el número de versión del programa *EscuTrack*.

## [0.1.0] - 2026-08-16
> Implementadas entidades principales y funcionalidades clave.
### Añadido
+ **Arquitectura base:** Estructuración de paquetes bajo el patrón de separación de responsabilidades lógicas para mantener modularización.
+ **Entidad `Paciente`:** Implementado mediante encapsulamiento estricto: atributos clínicos, parseo automatizado de grado de gravedad (String a Integer) utilizando `HashMap`.
+ **Entidad `ControladorHospital`:** Configuración del nivel de gestión con mapas anidados del JCF (`Map<String, Map<String, Paciente>>`) para el rastreo espacial (departamento -> idCama -> Paciente).
+ **Esqueleto `Main`:** Estructura inicial del `BufferedReader` para la lectura del flujo de entrada en consola.
