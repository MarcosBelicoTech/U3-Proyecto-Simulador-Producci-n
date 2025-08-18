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

Este sistema es un **simulador de producción y logística** desarrollado en **Java** que permite modelar el funcionamiento de una fábrica con procesos de manufactura, transporte y almacenamiento.  

El simulador cuenta con una **interfaz gráfica (Swing)** que facilita al usuario:  

- 🏭 Simular procesos de producción con diferentes tipos de máquinas.  
- 📦 Gestionar productos y su flujo dentro de la fábrica.  
- 🚚 Administrar vehículos de transporte y su logística.  
- 🏢 Controlar un almacén de insumos y productos terminados.  
- 📊 Generar reportes estadísticos del proceso productivo.  

---

## 🧠 Justificación y Algoritmo

Para replicar el paralelismo real de una fábrica, se utilizó **programación concurrente y multihilo**, donde las máquinas producen mientras los vehículos transportan y los almacenes gestionan inventario de forma simultánea.  

### 🔹 Patrones de diseño implementados
- **Producer–Consumer** → sincroniza la producción y el consumo de productos en el almacén.  
- **Observer** → mantiene la interfaz gráfica actualizada en tiempo real.  

### 🔹 Arquitectura
El sistema sigue el modelo **MVC (Modelo–Vista–Controlador)**:  
- **Modelo**: Máquinas, productos, almacenes y vehículos.  
- **Vista**: Ventanas gráficas de producción, logística y reportes.  
- **Controlador**: `SimulatorController` coordina la simulación.  

### 🔹 Complejidad computacional
> **O(n × m)**  
- `n` = número de productos.  
- `m` = cantidad de procesos o movimientos.  

Las operaciones de colas bloqueantes (`LinkedBlockingQueue`) se ejecutan en **O(1)**, garantizando eficiencia incluso con múltiples hilos.  

---

## 📂 Explicación del Código

- **`App.java`** → Clase principal que inicia la simulación.  
- **`SimulatorController.java`** → Coordina producción, transporte y almacenamiento.  

📦 **Paquete `model`**  
- `Machine` → Máquinas de producción.  
- `ProductItem` y `ProductType` → Productos e insumos.  
- `Vehicle` y `VehicleType` → Vehículos de transporte.  
- `Warehouse` → Almacén con colas bloqueantes.  
- `Stats` → Métricas y estadísticas de simulación.  

🖥️ **Paquete `ui`**  
- `ProductionWindow` → Estado de máquinas y métricas.  
- `LogisticsWindow` → Control de vehículos y entregas.  
- `ReportsWindow` → Estadísticas acumuladas y gráficas dinámicas.  

---

## 🧰 Estructuras de Datos Utilizadas

- `ArrayList` → Productos, vehículos y listas dinámicas.  
- `HashMap` → Asociación de productos con estadísticas y máquinas.  
- `LinkedList` / `LinkedBlockingQueue` → Flujo de productos en almacenes.  
- `Enum` → Tipos de productos y vehículos.  
- `TableModel` y **renderers** → Tablas interactivas en Swing.  
- **Hilos y concurrencia** → Ejecución paralela de máquinas y vehículos.  

---

## 📚 Librerías Usadas

- **Java Swing** → Interfaz gráfica (`JFrame`, `JPanel`, `JTable`, `JDialog`).  
- **AWT** → Renderizado de gráficos y tablas.  
- **java.util.concurrent** → Hilos, `ExecutorService`, `LinkedBlockingQueue`.  

---

## 💻 Ejecución del Programa

### ✔️ Requisitos

- Java JDK 8 o superior (recomendado JDK 11 o 17).  
- Git 2.30 o superior.  
- IDE como IntelliJ, Eclipse, NetBeans, VS Code.  
- También puede ejecutarse el archivo `Producion.jar` directamente.  

### ⚙️ Pasos para compilar y ejecutar

```bash
# 1. Clonar el repositorio
git clone https://github.com/MarcosBelicoTech/U3-Proyecto-Simulador-Producci-n.git
cd U3-Proyecto-Simulador-Producci-n/U3-Proyecto-Simulador-Producción

# 2. Compilar el código fuente
javac -d out -sourcepath src src/com/factorysim/App.java

# 3. Ejecutar el programa
java -cp out com.factorysim.App
