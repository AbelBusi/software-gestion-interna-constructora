## Flujo de trabajo con Git

Usamos un esquema basado en Git Flow.

Ramas principales:
- main: versión estable
- develop: rama de integración

Ramas de trabajo:
- feature/*: nuevas funcionalidades

Proceso:
1. Crear rama feature desde develop.
2. Realizar commits siguiendo la convención.
3. Subir la rama al repositorio remoto.
4. Crear Pull Request hacia develop.
5. Revisar y aprobar el Pull Request.
6. Hacer merge a develop.
7. Para versiones estables, hacer merge de develop a main.

Reglas:
- main está protegida.
- No se permiten pushes directos a main.
- Todo cambio debe entrar por Pull Request.
