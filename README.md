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
1. [TODO](#todo)
1. [Known bugs](#bugs)
1. [Contributing](#contributing)
1. [License](#license)

## Intro
This is an unofficial and simple downloader for Tusubtitulo.com subtitles. The site is mainly focused on spanish subtitles and it's users quickly create new subs within the day of an episode being aired.
The series information is provided by TVdb's API and the aplication uses urllib paired with lxml to navigate Tusubtitulo.com and interpret the data.
If for any reason you would like to contribute with a donation head to [Tusubtitulo.com](https://Tusubtitulo.com/) and do so there since I do not accept money.


## Installation
First of all, you should check if the required dependencies are installed in your machine`pyqt4,lxml,requests,urllib and tvdb_api`. You can easily install them with `pip install "library"` in the command line. Pip can be downladed [here.](http://www.pip-installer.org/en/latest/installing.html)


## Usage
Write the name of the show you are looking for, then click on search. Select the season and the episode(you can also download the entire show).Select also the place where you want to save the subtitles and click on download.

## TODO
Feel free to help us with any of these issues.

- Koddi Addon. I'm also trying to develop a subtitles service addon for Kodi but so far I have been unable to understand the inner workings of such services so if you can and want to help with that feel free to do so.
- Select the subtitle language
- A whole code facelift
- Try using threading to speed up download
- Web interface for the service

## Bugs
- When you press Enter key after writing the name of the show the program does not search it. Must click on the button

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
