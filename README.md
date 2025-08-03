# Manual de Ejecución - Billy Test Application

Este manual describe los pasos necesarios para ejecutar la aplicación Billy Test en diferentes sistemas operativos.

## Windows

### Prerequisitos
- Conexión a internet (para descargar dependencias de Maven)
- Git instalado

### Pasos de instalación y ejecución

1. **Descargar JDK 17.0.12**
   - Ir a [Oracle JDK Downloads](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) o [OpenJDK](https://jdk.java.net/17/)
   - Descargar la versión JDK 17.0.12 para Windows (x64)

2. **Descomprimir JDK**
   - Crear el directorio `C:\home` si no existe
   - Descomprimir el archivo descargado en `C:\home`
   - El resultado debería ser: `C:\home\jdk-17.0.12`

3. **Abrir una consola de comandos windows. Presional Inicio y luego cmd**

4. **Configurar variable de entorno JAVA_HOME**
   ```cmd
   set JAVA_HOME=C:\home\jdk-17.0.12
   ```

5. **Clonar el repositorio**
   ```cmd
   git clone https://github.com/felipeandrespena/billy-test.git
   ```

6. **Navegar al directorio del proyecto**
   ```cmd
   cd billy-test
   ```

7. **Compilar y ejecutar la aplicación**
   ```cmd
   mvnw.cmd clean compile exec:java
   ```

### Salida esperada
La ejecución debería verse algo así:
```
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------< com.billy:billy-test >------------------------
[INFO] Building Billy Test - Document Processor 1.0.0
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- clean:3.2.0:clean (default-clean) @ billy-test ---
[INFO] Deleting C:\Users\felip\test\billy-test\target
[INFO]
[INFO] --- resources:3.3.1:resources (default-resources) @ billy-test ---
[INFO] Copying 2 resources from src\main\resources to target\classes
[INFO]
[INFO] --- compiler:3.11.0:compile (default-compile) @ billy-test ---
[INFO] Changes detected - recompiling the module! :source
[INFO] Compiling 10 source files with javac [debug target 17] to target\classes
[INFO]
[INFO] --- exec:3.1.0:java (default-cli) @ billy-test ---
============================================================
? COMENZANDO PROCESO ...
? Hora inicio: 23:56:06
============================================================
? Created output folder
? Hora fin : 23:56:06
?? Tiempo total : 0.204 seconds (204ms)

? PROCESO TERMINADO , revisar el directorio output!
```


## Ejecución con parámetros personalizados

### Windows
```cmd
mvnw.cmd exec:java -Dexec.args="mi-archivo.json mi-carpeta-salida"
```

## Archivos de salida

La aplicación genera los siguientes archivos en la carpeta de salida (por defecto `output/`):
- `output.xml` - Archivo XML con los documentos procesados
- `report.html` - Reporte HTML con la información procesada

## Solución de problemas

### Error: `JAVA_HOME not set`
- Verificar que la variable `JAVA_HOME` esté configurada correctamente
- Reiniciar la terminal después de configurar las variables

### Error: `ClassNotFoundException`
- Ejecutar primero: `mvnw.cmd clean compile` (Windows) o `./mvnw clean compile` (macOS)

### La primera ejecución es lenta
- Es normal, Maven descarga las dependencias necesarias la primera vez
- Las ejecuciones posteriores serán más rápidas