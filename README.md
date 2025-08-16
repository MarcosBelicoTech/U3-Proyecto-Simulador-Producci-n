# U3 Proyecto Simulador Producción
# 🏭 Simulador de Producción y Logística

Universidad Politécnica De Victoria  
⮕ Ingeniería en Tecnologías de la Información  
⮕ Materia: Programación Orientada a Objetos  
⮕ Grupo: ITI 6-1  
⮕ Periodo: Mayo – Agosto 2025  
⮕ Ciudad Victoria, Tamaulipas  

---

## 🧑‍💻 Integrantes

- Marcos García Vázquez  
- José Ángel Alfaro Zurita  
- Sandra Yasmín Gómez Rodríguez  
- Elias Rodríguez Oliva  

**Docente**: M.I. Lidia Ivaanery García Juárez  

---

## 📌 Descripción del Proyecto

Este sistema es un **simulador de producción y logística** que permite modelar el funcionamiento de una fábrica con procesos de manufactura, transporte y almacenamiento.  

La aplicación está desarrollada en **Java** y utiliza una interfaz gráfica para que el usuario pueda:  

- 🏭 Simular procesos de **producción** con diferentes tipos de máquinas.  
- 📦 Gestionar **productos** y su flujo dentro de la fábrica.  
- 🚚 Administrar **vehículos de transporte** y su logística.  
- 🏢 Controlar un **almacén** de insumos y productos terminados.  
- 📊 Generar **reportes estadísticos** del proceso productivo.  

---

## 🧠 Justificación y Algoritmo

### 🔹 ¿Por qué este proyecto?  
El objetivo es mostrar cómo la **programación orientada a objetos (POO)** puede aplicarse en un entorno real de simulación industrial, modelando entidades como máquinas, productos, vehículos y almacenes.  

### 🔹 Lógica de Simulación  
- Se representan **máquinas** que procesan productos.  
- Los **vehículos** trasladan materiales entre almacén y planta.  
- Se generan **estadísticas** de eficiencia, tiempos y recursos.  

### 🔹 Complejidad Computacional  
> **O(n × m)**  
Donde `n` es el número de productos y `m` la cantidad de procesos o movimientos que atraviesa cada uno.  

---

## 📂 Explicación del Código

- **`App.java`** → Clase principal que inicia la simulación.  
- **`SimulatorController.java`** → Coordina producción, transporte y almacenamiento.  

📦 **Paquete `model`**  
- `Machine` → Máquinas de producción.  
- `ProductItem` y `ProductType` → Productos e insumos.  
- `Vehicle` y `VehicleType` → Vehículos de transporte.  
- `Warehouse` → Almacén de materiales y productos.  
- `Stats` → Métricas y estadísticas.  

🖥️ **Paquete `ui`**  
- `ProductionWindow` → Control de máquinas.  
- `LogisticsWindow` → Gestión de transporte y logística.  
- `ReportsWindow` → Visualización de estadísticas y reportes.  

---

## 🧰 Estructuras de Datos Utilizadas

- `ArrayList` → Manejo dinámico de productos y vehículos.  
- `HashMap` → Relación entre productos y máquinas.  
- `TableModel` y renderers → Para mostrar tablas interactivas en la interfaz.  

---

## 📚 Librerías Usadas

- **Java Swing** → Interfaz gráfica (`JFrame`, `JPanel`, `JTable`, `JDialog`).  
- **AWT** → Renderizado de gráficos y tablas.  

---

## 💻 Ejecución del Programa

### ✔️ Requisitos

- Java JDK 8 o superior  
- IDE como IntelliJ, NetBeans o Eclipse  
- También puede ejecutarse el archivo `Producion.jar` desde la terminal  

### ⚙️ Pasos para compilar y ejecutar

```bash
# 1. Clonar el repositorio
git clone https://github.com/usuario/Proyecto-Final-ProdSim.git
cd Proyecto-Final-ProdSim

# 2. Compilar el código
javac -d bin src/com/factorysim/*.java

# 3. Ejecutar el programa
java -cp bin com.factorysim.App
