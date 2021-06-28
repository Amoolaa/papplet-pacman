# papplet-pacman
An implementation of pacman written using PApplet library in Java. This project uses gradle. ``gradle run`` is to run the application, ``gradle test`` runs the tests and ``gradle jacocoTestReport`` provides a report of the testing done.

![image](https://user-images.githubusercontent.com/86513920/123607162-f384b800-d840-11eb-8bf1-a9dc9c6dcb63.png) ![image](https://user-images.githubusercontent.com/86513920/123607406-36df2680-d841-11eb-800f-c709c6ee2ad9.png) ![image](https://user-images.githubusercontent.com/86513920/123607792-8c1b3800-d841-11eb-83d5-d5cd2489a374.png)

Pacman moves using arrow keys. Pressing spacebar toggles the Ghost's targets. The superfruits turn ghosts into frightened mode, where they can be consumed by Pacman and do not return until Pacman loses a life. The sodacan reduces the visibility of the ghosts and gives them erratic behaviour. The behaviour of ghosts is identical to regular pacman.

The ``config.json`` file is used to control the speed, the number of lives, the map and the length of modes which determine each ghost's behaviour.
