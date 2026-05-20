# CartTddU9 - Unidad 9 Post Contenido 1

Estudiante: Nicolas Sanchez
Curso: Aplicaciones Moviles
Unidad: Unidad 9 - Testing y Aseguramiento de Calidad en Movil
Actividad: Post Contenido 1 - TDD para CartViewModel con MockK

## Descripcion

Este repositorio implementa un laboratorio de pruebas unitarias usando TDD en Android con Kotlin.

Se construyo un CartViewModel aplicando el ciclo Red - Green - Refactor, usando MockK para simular dependencias y validando estados con pruebas unitarias.

## Estados manejados

- Loading: estado inicial o de carga.
- Success: estado exitoso con productos y total calculado.
- Error: estado de error cuando ocurre una excepcion.

## Ciclo TDD aplicado

### RED

Primero se escribieron los tests antes de completar la implementacion.

Commit:
Agrega tests RED (TDD paso 1)

### GREEN

Luego se implemento el codigo minimo para que los tests pasaran.

Commit:
Implementa CartViewModel GREEN (TDD paso 2)

### REFACTOR

Finalmente se mejoro el codigo sin romper los tests.

Commit:
Refactoriza CartViewModel (TDD paso 3)

## Tests implementados

1. loadCart emits Success state with items and total
2. loadCart emits Error when repository throws IOException
3. loadCart emits Loading before Success
4. calculateTotal returns zero when cart is empty

## Comando usado

.\gradlew testDebugUnitTest

## Resultado esperado

4 tests completed, 0 failed
BUILD SUCCESSFUL

## Evidencias

Las capturas van en la carpeta evidencias.

Archivos sugeridos:

01_tests_verdes_android_studio.png
02_terminal_tests_ok.png
03_commits_tdd_github_desktop.png

## Tecnologias

Android Studio
Kotlin
Gradle
JUnit5
MockK
Coroutines Test
Turbine
MVVM
StateFlow

