# -*- coding: utf-8 -*-
#  service_GUI.py
#  
#  Copyright 2016 Jose Manuel Estrada-Nora Muñoz  <jotajotanora@gmail.com>
#                 Carlos Manrique Enguita         <cmanriqueenguita@gmail.com>
#  
#  This program is free software; you can redistribute it and/or modify
#  it under the terms of the GNU General Public License as published by
#  the Free Software Foundation; either version 2 of the License, or
#  (at your option) any later version.
#  
#  This program is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.
#  
#  You should have received a copy of the GNU General Public License
#  along with this program; if not, write to the Free Software
#  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
#  MA 02110-1301, USA.
#  
# 
from PyQt4 import QtCore, QtGui
from lxml import html
import requests
import urllib
import sys
import tvdb_api

t = tvdb_api.Tvdb()

mensajeNegativo = ["No hay en Español de España", "No hay en Español Latino", "No hay en Inglés", "No se hay encontrado subtitulos"]
mensajeAfirmativo = ["Subtitulos en Español de España encontrados", "Subtitulos en Español Latino encontrados", "Subtitulos en Inglés encontrados"]
idiomasPosibles = ["Español (Latinoamérica)", 'Español (España)', 'English']
selAll = False

baseURL = 'https://www.tusubtitulo.com/'

class MyOpener(urllib.FancyURLopener):
    version = "User-Agent=Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.3) Gecko/20100401 Firefox/3.6.3 ( .NET CLR 3.5.30729)"

try:
    _fromUtf8 = QtCore.QString.fromUtf8
except AttributeError:
    def _fromUtf8(s):
        return s

try:
    _encoding = QtGui.QApplication.UnicodeUTF8

    def _translate(context, text, disambig):
        return QtGui.QApplication.translate(context, text, disambig, _encoding)
except AttributeError:
    def _translate(context, text, disambig):
        return QtGui.QApplication.translate(context, text, disambig)

########Tvdb section########

"""
Extrae el numero de el string introducido
@param text una cadena de texto
@return el numero dentro del parametro introducido como entero
"""
def extractNumber(text):
    finalList = []
    for i in range(len(list(str(text)))):
        try:
            finalList.append(str(int(list(str(text))[i])))
        except:
            pass
    return ''.join(finalList)

"""
Obtiene el numero de temporadas
@param show serie a buscar
@return el numero de temporadas
"""
def checkSeasons(show):
    return extractNumber(t[show])

"""
Obtiene el numero de capitulos en una temporada
@param el titulo de la serie
@param el numero de la temporada
@return el numero de episodios
"""
def checkEpisodes(show, season):
    return extractNumber(t[show][int(season)])

"""
Obtiene los titulos de los capitulos de una temporada
@param el titulo de la serie
@param el numero de la temporada
@return un array con los titulos de los episodios
"""
def getAllEpisodes(show, season):
    episodeNames = list()
    for i in range(int(checkEpisodes(show, season))):
        episodeNames.append(t[show][int(season)][i+1]["episodename"])
    return episodeNames

"""
Obtiene el nombre correcto de una serie
@param show el titulo de la serie
@return el titulo segun tvdb correctamente escrito
@return el mismo nombre si no encuentra nada en tvdb
"""
def getShowName(show):
    try:
        return t[show]["seriesname"]
    except:
        return show

########Tvdb section########

"""
Descarga los subtitulos.
@param link hacia la descarga
@param numero de la temporada
@param numero del episodio
@param directorio donde descargará los subtitulos
@param nombre correcto de la serie segun tvdb
@param nombre correcto del episodio segun tvdb
@param idioma de los subtitulos descargados
"""
def download(link, season_num, episode_num, folder, show_tvdb, episode_tvdb, language):
    #Creando la ruta para guardar
    filename = str(show_tvdb) + " - " + "S" + str(season_num) + "E" + str(episode_num) + " - " + str(episode_tvdb) + "_" + language + ".srt"
    local_tmp_file = folder + '\\' + filename
    print local_tmp_file

    # Obteniendo subtitulos
    my_urlopener = MyOpener()
    my_urlopener.addheader('Referer', link)
    postparams = None
    response = my_urlopener.open(link, postparams)

    # Guardando subtitulos
    local_file_handle = open(local_tmp_file, "w+")
    local_file_handle.write(response.read())
    local_file_handle.close()

"""
Prepara el nombre de la serie para la URL de descarga cambiando espacios por guiones y en minuscula.
@param text el nombre de la serie
@return text el nombre de la serie formato URL.
"""
def urlBuilder(show_tvdb):
    text_list = list(str(show_tvdb).lower())
    for i in range(len(text_list)):
        if text_list[i] == ' ':
            text_list[i] = '-'
        else:
            pass
    return ''.join(text_list)

"""
Busca los subtitulos, elige los correctos y los manda a download()
@param numero de la temporada
@param numero del episodio
@param directorio donde descargará los subtitulos
@param nombre correcto de la serie segun tvdb
@param nombre correcto del episodio segun tvdb
"""
def Search(season_num, episode_num, folder, show_tvdb, episode_tvdb):
    lang = []
    name = []
    nameDEF = []
    hayESP = []
    hayESPL = []
    hayEN = []

    episodeURL = baseURL+ 'serie/' + urlBuilder(show_tvdb) + '/' + season_num + '/' + episode_num + '/*'

    for i in range(int(html.fromstring(requests.get(episodeURL).content).xpath('count(//*[@class="sslist"]/*[@class="li-idioma"]/b/text())'))):
        lang.append(html.fromstring(requests.get(episodeURL).content).xpath('//*[@class="sslist"][' + str(i+1) + ']/*[@class="li-idioma"]/b/text()'))
        name.append(html.fromstring(requests.get(episodeURL).content).xpath('//*[@class="sslist"][' + str(i+1) + ']/*[@class="rng download green"]/a/@href'))

    for i in range(len(lang)):
        #Se asegura de que hay link de desarga y crea la URL, si no lo hay escribe False
        try:
            namePROV = name[i][1]
            nameMIDDLE = list(namePROV)
            original = list('original')
            updated = list('updated')
            if nameMIDDLE[0:8] == original or nameMIDDLE[0:7] == updated:
                nameDEF.append(baseURL + namePROV)
        except:
            nameDEF.append(False)
        #Mira en la lista de idiomas en busca de los deseados, crea una lista para cada uno con true o false y la posicion del array de links en el que se encuentra
        if lang[i][0].encode('utf-8') == idiomasPosibles[0] and nameDEF[i] != False:
            hayESP.append(True)
            hayESP.append(i)
        if lang[i][0].encode('utf-8') == idiomasPosibles[1] and nameDEF[i] != False:
            hayESPL.append(True)
            hayESPL.append(i)
        if lang[i][0].encode('utf-8') == idiomasPosibles[2] and nameDEF[i] != False:
            hayEN.append(True)
            hayEN.append(i)

    try:
        if hayESP[0]:
            print mensajeAfirmativo[0]
            download(nameDEF[hayESP[1]], season_num, episode_num, folder, show_tvdb, episode_tvdb, "ES")
    except:
        print mensajeNegativo[0]

    try:
        if hayESPL[0]:
            print mensajeAfirmativo[1]
            download(nameDEF[hayESPL[1]], season_num, episode_num, folder, show_tvdb, episode_tvdb, "ESL")
    except:
        print mensajeNegativo[1]

    try:
        if hayEN[0]:
            print mensajeAfirmativo[2]
            download(nameDEF[hayEN[1]], season_num, episode_num, folder, show_tvdb, episode_tvdb, "ENG")
    except:
        print mensajeNegativo[2]
        print mensajeNegativo[3]


class Ui_MainWindow(object):
    def setupUi(self, MainWindow):
        MainWindow.setObjectName(_fromUtf8("MainWindow"))
        MainWindow.resize(272, 303)
        MainWindow.setMinimumSize(QtCore.QSize(272, 303))
        self.centralwidget = QtGui.QWidget(MainWindow)
        self.centralwidget.setObjectName(_fromUtf8("centralwidget"))
        self.gridLayout_2 = QtGui.QGridLayout(self.centralwidget)
        self.gridLayout_2.setObjectName(_fromUtf8("gridLayout_2"))
        self.horizontalLayout_6 = QtGui.QHBoxLayout()
        self.horizontalLayout_6.setContentsMargins(-1, -1, -1, 3)
        self.horizontalLayout_6.setObjectName(_fromUtf8("horizontalLayout_6"))
        self.label_3 = QtGui.QLabel(self.centralwidget)
        self.label_3.setObjectName(_fromUtf8("label_3"))
        self.horizontalLayout_6.addWidget(self.label_3)
        self.verticalLayout_3 = QtGui.QVBoxLayout()
        self.verticalLayout_3.setObjectName(_fromUtf8("verticalLayout_3"))
        self.episodes = QtGui.QComboBox(self.centralwidget)
        self.episodes.setObjectName(_fromUtf8("episodes"))
        self.verticalLayout_3.addWidget(self.episodes)
        self.radioButton = QtGui.QRadioButton(self.centralwidget)
        self.radioButton.setObjectName(_fromUtf8("radioButton"))
        self.radioButton.toggled.connect(self.selectAll)
        self.verticalLayout_3.addWidget(self.radioButton)
        self.horizontalLayout_6.addLayout(self.verticalLayout_3)
        self.gridLayout_2.addLayout(self.horizontalLayout_6, 2, 0, 1, 1)
        self.gridLayout = QtGui.QGridLayout()
        self.gridLayout.setSizeConstraint(QtGui.QLayout.SetDefaultConstraint)
        self.gridLayout.setContentsMargins(-1, -1, -1, 5)
        self.gridLayout.setObjectName(_fromUtf8("gridLayout"))
        self.label_5 = QtGui.QLabel(self.centralwidget)
        self.label_5.setObjectName(_fromUtf8("label_5"))
        self.gridLayout.addWidget(self.label_5, 0, 0, 1, 1)
        self.show_field = QtGui.QLineEdit(self.centralwidget)
        self.show_field.setObjectName(_fromUtf8("show_field"))
        self.gridLayout.addWidget(self.show_field, 0, 1, 1, 1)
        self.pushButton_2 = QtGui.QPushButton(self.centralwidget)
        self.pushButton_2.setObjectName(_fromUtf8("pushButton_2"))
        self.pushButton_2.clicked.connect(self.handleSearch)
        self.gridLayout.addWidget(self.pushButton_2, 0, 2, 1, 1)
        self.gridLayout_2.addLayout(self.gridLayout, 0, 0, 1, 1)
        self.horizontalLayout_5 = QtGui.QHBoxLayout()
        self.horizontalLayout_5.setContentsMargins(-1, -1, -1, 3)
        self.horizontalLayout_5.setObjectName(_fromUtf8("horizontalLayout_5"))
        self.label_2 = QtGui.QLabel(self.centralwidget)
        self.label_2.setObjectName(_fromUtf8("label_2"))
        self.horizontalLayout_5.addWidget(self.label_2)
        self.seasons = QtGui.QComboBox(self.centralwidget)
        self.seasons.setObjectName(_fromUtf8("seasons"))
        self.seasons.currentIndexChanged.connect(self.handleSeasonChange)
        self.horizontalLayout_5.addWidget(self.seasons)
        self.gridLayout_2.addLayout(self.horizontalLayout_5, 1, 0, 1, 1)
        self.verticalLayout_2 = QtGui.QVBoxLayout()
        self.verticalLayout_2.setObjectName(_fromUtf8("verticalLayout_2"))
        self.pushButton = QtGui.QPushButton(self.centralwidget)
        self.pushButton.setObjectName(_fromUtf8("pushButton"))
        self.verticalLayout_2.addWidget(self.pushButton)
        self.pushButton.clicked.connect(self.handleDownload)
        self.progressBar = QtGui.QProgressBar(self.centralwidget)
        self.progressBar.setProperty("value", 0)
        self.progressBar.setObjectName(_fromUtf8("progressBar"))
        self.verticalLayout_2.addWidget(self.progressBar)
        self.gridLayout_2.addLayout(self.verticalLayout_2, 3, 0, 1, 1)
        self.verticalLayout = QtGui.QVBoxLayout()
        self.verticalLayout.setObjectName(_fromUtf8("verticalLayout"))
        self.label_4 = QtGui.QLabel(self.centralwidget)
        self.label_4.setObjectName(_fromUtf8("label_4"))
        self.label_4.setAlignment(QtCore.Qt.AlignCenter)
        # myPixmap = QtGui.QPixmap(_fromUtf8('D:/Imágenes/nofiltro.jpg'))
        # self.label_4.setPixmap(QtGui.QPixmap(_fromUtf8('D:/Imágenes/nofiltro.jpg')).scaled(self.label_4.size()*5, QtCore.Qt.KeepAspectRatio, QtCore.Qt.SmoothTransformation))
        # self.label_4.setScaledContents(True)
        self.verticalLayout.addWidget(self.label_4)
        self.gridLayout_2.addLayout(self.verticalLayout, 4, 0, 1, 1)
        MainWindow.setCentralWidget(self.centralwidget)
        self.menubar = QtGui.QMenuBar(MainWindow)
        self.menubar.setGeometry(QtCore.QRect(0, 0, 272, 21))
        self.menubar.setObjectName(_fromUtf8("menubar"))
        MainWindow.setMenuBar(self.menubar)
        self.statusbar = QtGui.QStatusBar(MainWindow)
        self.statusbar.setObjectName(_fromUtf8("statusbar"))
        MainWindow.setStatusBar(self.statusbar)

        self.retranslateUi(MainWindow)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)

    def retranslateUi(self, MainWindow):
        MainWindow.setWindowTitle(_translate("MainWindow", "TuSubtitulo.com", None))
        self.label_3.setText(_translate("MainWindow", "Episodio", None))
        self.radioButton.setText(_translate("MainWindow", "Descargar todos", None))
        self.label_5.setText(_translate("MainWindow", "Serie", None))
        self.pushButton_2.setText(_translate("MainWindow", "Buscar", None))
        self.label_2.setText(_translate("MainWindow", "Temporada", None))
        self.pushButton.setText(_translate("MainWindow", "Descargar", None))
        # self.label_4.setText(_translate("MainWindow", "Fanart Image", None))

    def handleDownload(self, MainWindow):
        folder = QtGui.QFileDialog.getExistingDirectory(None, 'Select a folder:', 'C:\\', QtGui.QFileDialog.ShowDirsOnly)
        self.progressBar.setProperty("value", 25)
        show_tvdb = getShowName(str(self.show_field.text()))
        season_num = str(self.seasons.currentText())
        self.progressBar.setProperty("value", 50)
        if selAll:
            for i in range(int(checkEpisodes(show_tvdb, season_num))):
                print "###########"
                print i
                print "###########"
                episode_data = t[show_tvdb][int(season_num)][i+1]["episodename"]
                episode_tvdb = str(episode_data)
                Search(season_num, str(i), folder, show_tvdb, episode_tvdb)
                ratio = 50/int(checkEpisodes(show_tvdb, season_num))
                self.progressBar.setProperty("value", str(int(50)+int(ratio)))
        elif not selAll:
            episode_num = extractNumber(str(self.episodes.currentText()))
            episode_data = t[show_tvdb][int(season_num)][int(episode_num)]["episodename"]
            episode_tvdb = str(episode_data)
            Search(season_num, episode_num, folder, show_tvdb, episode_tvdb)
        self.progressBar.setProperty("value", 100)

    def handleSearch(self, MainWindow):
        show = str(self.show_field.text())
        self.seasons.clear()
        for i in range(int(checkSeasons(show))):
            self.seasons.addItem(str(i+1))

    def handleSeasonChange(self, MainWindow):
        season_num = str(self.seasons.currentText())
        show = str(self.show_field.text())
        self.episodes.clear()
        for i in range(int(checkEpisodes(show, season_num))):
            self.episodes.addItem(str(i+1) + " - " + getAllEpisodes(show, season_num)[i])

    def selectAll(self, MainWindow):
        global selAll
        if selAll:
            selAll = False
        else:
            selAll = True


if __name__ == "__main__":
    app = QtGui.QApplication(sys.argv)
    MainWindow = QtGui.QMainWindow()
    ui = Ui_MainWindow()
    ui.setupUi(MainWindow)
    MainWindow.show()
    sys.exit(app.exec_())
