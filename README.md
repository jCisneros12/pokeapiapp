
# Pokeapi App

_Aplicación que se conecta a la API de pokemon (https://pokeapi.co/) la cual permite buscar información sobre pokemones, regiones y crear equipos de pokemones. Utiliza firebase para almacenar la información del usuario y la API de pokemon para obtener la infor macion de los Pokemons_

## Screenshots

![](https://github.com/jCisneros12/aplicacion-web-peliculas/blob/main/capturas/photo5062155645020645834.jpg?raw=true)

![](https://github.com/jCisneros12/aplicacion-web-peliculas/blob/main/capturas/photo5062155645020645833.jpg?raw=true)

![](https://github.com/jCisneros12/aplicacion-web-peliculas/blob/main/capturas/photo5062155645020645844.jpg?raw=true)

## Arquitectura

#### DataSource
La capa DataSource se encarga de hacer las peticiones/llamadas directas a los servicios (Local, REST, Realtime DB), mediante entidades/modelos de datos
#### ViewModel
ViewModel se encarga de procesar la información obtenida por **DataSource** para ser enviada y presentada a la **VIEW**
#### VIEW (UI)
En **View** es donde presentamos la informacion requeria por la aplicación de manera amigable e intuitiva para el usuario

![](https://github.com/jCisneros12/aplicacion-web-peliculas/blob/main/capturas/architecture%20app.png?raw=true)

---
⌨️ Por [Juan Cisneros](https://github.com/jCisneros12) 😊
