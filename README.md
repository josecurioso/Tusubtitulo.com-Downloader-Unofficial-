# Tusubtitulo.com subtitle downloader (Unofficial)
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
- A hole code facelift
- Create a python executable(py2exe, pyinstall)

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

