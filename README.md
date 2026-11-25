# Space_invader
Este videojuego retro se desarrollo con el lenguaje de programación Java utilizando interfaz grafica de Swing. El jugador debe de controlar una nave la cual tiene la capacidad de disparar, y su objetivo es eliminar a los alienígenas contra los que lucha, que se encuentran en la parte superior. Existen 4 tipos de enemigos, el básico el cual solo se mueve de izquierda a derecha y protege a los tiradores, los tiradores al igual que los básicos se desplazan de izquierda a derecha, y pueden disparar, el enemigo kamikaze se mueve descendientemente en zigzag hacia donde esta el jugador, y finalmente el jefe final el cual tiene 3 fases contra las que pelea, y cada vez que le baje la vida la dificultad sube.

- Integrantes: Carlos Martínez, Luis Vinet, Javier Toledo, Alex Laura, Vicente Guzman.
# Requisitos antes de jugar: 
  - Tener el JDK 22.
  - Maven 3.8 o superior.
# ¿Cómo ejecutamos el codigo? 
Descargar el archivo [DefensoresDeLaGalaxia.jar]
- Luego desde la terminal de donde se ubica el .jar copiamos y pegamos el siguiente comando:
    - java -jar DefensoresDeLaGalaxia.jar
- o también podemos ejecutar el archivo directamente desde la carpeta:
  - (donde este la carpeta del archivo)\Space_Invaders\target
- y desde aquí buscamos el .jar

# Controles
  - Mover la nave a la izquierda o derecha: <- / -> (o A / D).
  - Disparar: Espacio.
  Pausar: P.
  Volver al menú: Esc.

# Reglas del juego:
- El jugador inicia con 3 vidas.
- Existen 3 niveles por lo que pasar, uno mas difícil que el otro, luego de terminar el tercer nivel se pasa al jefe final.
- Cuando se destruye un enemigo existe la probabilidad del 20% de que caiga un PoweUp.
- Después de cada disparo el escudo desaparece.
- El puntaje máximo se muestra en el menu.
- El jugador pierde cuando se queda sin vidas.
- Las barreras que protegen al jugador se desgastan por cada disparo.
- Los PowerUp disponibles son: escudo temporal, disparo doble y vida extra.

# Estructura del proyecto
- Contratos: Colisionable, Disparador, EfectoPowerUp, EstrategiaMovimiento, Puntuable.
- Logica: GestorNiveles, Partida.
- Modelo.Enemigo: Enemigos y el jefe final (clase anonima).
- Modelo: PowerUp y EfectosPowerUp, Jugador, ProyectilEnemigo y ProyectilJugador, EntidadJuego y Barrera.
- UI: PanelGameOver, PanelJuego, PanelMenu, VentanaPrincipal
- Main: Donde se ejecuta el codigo
