**Pokémon App con Clean Architecture y MVVM**  

**Descripción**  
Esta aplicación permite consultar información detallada de una base de datos de Pokémon utilizando **PokeAPI**. 
Implementa las mejores prácticas de **Clean Architecture**, **JetPack Compose** y el patrón **MVVM** 
para garantizar una estructura modular y escalable.

**Diseño y Arquitectura**  

**¿Por qué elegí Clean Architecture?**  
Clean Architecture permite **separar responsabilidades en diferentes capas, logrando:  
**Modularidad:** Cada capa es independiente, facilitando modificaciones sin afectar el resto del código.  
**Testabilidad:** Se pueden escribir pruebas unitarias sin depender de Android Frameworks.  
**Escalabilidad:** Es fácil agregar nuevas funciones sin romper la estructura del código.  

**¿Cómo se integra MVVM en la capa de presentación?**  
El patrón **MVVM** ayuda a que la UI se mantenga reactiva y desacoplada de la lógica de negocio.  
**ViewModel maneja el estado y la lógica de la pantalla.**  
**LiveData y StateFlow permiten una actualización automática de datos.**  
**Los View observan los cambios y reaccionan de inmediato.**  

**Principales características implementadas**  
Consulta de Pokémon desde la API** utilizando Retrofit.  
Gestión de caché con Room** para almacenar datos localmente.  
Paginación de listas** optimizada con la arquitectura de Jetpack Paging.  
Manejo eficiente de estados** con ViewModel y LiveData.  
Inyección de dependencias con Hilt** para desacoplar componentes.  
Implementación de TypeConverters en Room** para manejar listas dentro de la base de datos.  

**Mejoras futuras**  
Si tuviera más tiempo, trabajaría en:    
**Mejor gestión de errores en API** con una estrategia más robusta.  
**Animaciones fluidas** al cambiar de pantalla y mostrar detalles.    
**Optimización de UI con Jetpack Compose**, reemplazando Views convencionales. 
**Dark Mode** para mejorar la experiencia de usuario.
