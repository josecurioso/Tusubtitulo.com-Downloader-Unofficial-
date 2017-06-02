# Tusubtitulo.com subtitle downloader (Unofficial)

----------

## Languages
* [English](#english)
* [Español](#español)

----------


## English

## Table of Contents
1. [Intro](#intro)
1. [Installation](#installation)
1. [Usage](#usage)
1. [TODO](#to-do)
1. [Known bugs](#bugs)
1. [Contributing](#contributing)
1. [License](#license)

## Intro
This is an unofficial and simple downloader for Tusubtitulo.com subtitles. The site is mainly focused on spanish subtitles and it's users quickly create new subs within the day of an episode being aired.
The aplication uses urllib paired with lxml to navigate Tusubtitulo.com and interpret the data.
If for any reason you would like to contribute with a donation head to [Tusubtitulo.com](https://Tusubtitulo.com/) and do so there since I do not accept money.
An addon for Kodi has been developed using the concepts and investigation behind this, you can check it out [here](https://github.com/josecurioso/service.subtitles.tusubtitulo).


## Installation
The repo is an Eclipse project in its whole so just clone and import. In the releases tab you will find a .jar that you can open using "java -jar name.jar" on a console.


## Usage
Write the name of the show you are looking for, then choose a season, then choose an episode and finally a subtitle, as easy as that.


## TO-DO

- Try using threading to speed up download of various episodes at the same time
- Web interface for the service (secondary)

## Bugs
- The seasons showed is a season count so if there are 1, 2 and 5 it will show 1-3

## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## License
This project is licensed under the GPL 3.0 License - see the [LICENSE.md](LICENSE.md) file for details


----------

## Español
## (OJO, LA TRADUCCIÓN NO ESTÁ ACTUALIZADA)

## Tabla de contenidos
1. [Intro](#intro)
1. [Instalación](#instalación)
1. [Uso](#uso)
1. [Por hacer](#porhacer)
1. [Bugs conocidos](#bugs)
1. [Contribuir](#contribuir)
1. [Licencia](#licencia)

## Intro
Este es un  programa no oficial  para descargar subtítulos de Tusubtitulo.com. La web está enfocada en subtítulos en castellano y sus usuarios crean rápidamente nuevos subtítulos casi el mismo día en que los episodios se estrenan. La información sobre las series es proporcionada por la API de TVdb y la aplicación utiliza urllib junto con lxml para navegar en Tusubtitulo.com e interpretar los datos.

Si por alguna razón te gustaría contribuir con una donación entra en [Tusubtitulo.com](https://Tusubtitulo.com/) y realízala allí ya que no aceptamos dinero.



## Instalación
Antes de nada, deberías comprobar que las dependencias del programa están instaladas en tu máquina `pyqt4,lxml,requests,urllib and tvdb_api`. Puedes instalarlas fácilmente con `pip install "librería"`en la linea de comandos. Pip puede descargarse desde [aquí](http://www.pip-installer.org/en/latest/installing.html).

## Uso
Escribe el nombre de la serie que buscas y haz click en el botón de buscar. Selecciona la temporada y el episodio(también puedes descargar los subtítulos de toda la serie). Selecciona tambien el directorio donde se guardarán y haz click en descargar.

## Por hacer
Sientete libre de ayudarnos con cualquiera de estas tareas.

- Koddi Addon. Estamos tratando de desarrollar un addon para Koddi pero no hemos sido capaces de entender el funcionamiento interno del servicio.
- Posibilidad de seleccionar el idioma.
- Una reestructuración del código
- Intentar acelerar la descarga usando threading
- Interfaz web para el servicio

## Bugs
- Cuando presionas la tecla Enter después de escribir el nombre de la serie no realiza la acción de buscar. Debes hacer click en buscar.

## Contribuir

1. Fork it!
2. Crea una rama nueva: `git checkout -b my-new-feature`
3. Haz un commit con tus cambios:`git commit -am 'Add some feature'`
4. Súbelo a la rama en la que estés trabajando: `git push origin my-new-feature`
5. Háznos un pull request:D

## Licencia
Este proyecto está bajo la licencia GPL 3.0 - Mira el archivo [LICENSE.md](LICENSE.md) para más detalles.
