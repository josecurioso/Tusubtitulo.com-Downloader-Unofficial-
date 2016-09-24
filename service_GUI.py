# -*- coding: utf-8 -*-
from PyQt4 import QtCore, QtGui
from lxml import html
import requests
import urllib
import urllib2
import os
import sys
import tvdb_api

t = tvdb_api.Tvdb()

showNum = '*'

exts = [".srt", ".sub", ".txt", ".smi", ".ssa", ".ass"]
done = "false"

baseURL = 'https://www.tusubtitulo.com/serie/'
baseDOWNLOAD = 'https://www.tusubtitulo.com/' 


lang = list()
name = list()
nameDEF = list()
hayESP = list()
hayESPL = list()
hayEN = list()

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

########


def stringNormalizer(text):
    finalList = list()
    listText = list(str(text))
    for i in range(len(listText)):
        try:
            numero = int(listText[i])
            finalList.append(str(numero))
        except:
            pass
    final = ''.join(finalList)
    return final

def checkSeasons(show):
    data = t[show]
    return stringNormalizer(data)

def checkEpisodes(show, season):
    data = t[show][int(season)]
    return stringNormalizer(data)

def getAllEpisodes(show, season):
    episodeNames = list()
    for i in range(int(checkEpisodes(show, season))):
        data = t[show][int(season)][i+1]
        episodeNames.append(data["episodename"])
    return episodeNames

#######

def download(link, show, season, episode):
    route = []
    filename = show + "S" + season + "E" + episode + ".srt"
    if link:
        my_urlopener = MyOpener()
        my_urlopener.addheader('Referer', link)
        postparams = None
        
        #Obteniendo subtitulos
        response = my_urlopener.open(link, postparams)
        local_tmp_file = "D:/Escritorio/tusubtitulo/" + filename
        
        #Guardando subtitulos
        local_file_handle = open(local_tmp_file, "w+")
        local_file_handle.write(response.read())
        local_file_handle.close()
        route.append(local_tmp_file)

def normalizeString(text):
    originalValue = str(text).lower()
    intermValue = list(originalValue)
    newValue = intermValue
    for i in range(len(intermValue)):
        if intermValue[i] == ' ':
            newValue[i] = '-'
        else:
            pass
    text = ''.join(newValue)
    return text

def checkLink(i):
    if nameDEF[i] != "none":
        return "true"

def Search(show, season, episode):
    finalURL = baseURL + show + '/' + season + '/' + episode + '/' + showNum
    page = requests.get(finalURL)
    pageContent = html.fromstring(page.content)
    options = pageContent.xpath('count(//*[@class="sslist"]/*[@class="li-idioma"]/b/text())')
    for i in range(int(options)):
        langROUTE = '//*[@class="sslist"][' + str(i+1) + ']/*[@class="li-idioma"]/b/text()'
        nameROUTE = '//*[@class="sslist"][' + str(i+1) + ']/*[@class="rng download green"]/a/@href'
        lang.append(pageContent.xpath(langROUTE))
        name.append(pageContent.xpath(nameROUTE))

    for i in range(len(lang)):
        if lang[i][0].encode('utf-8') == "Español (Latinoamérica)":
            lang[i] = "esl"
        elif lang[i][0].encode('utf-8') == 'Español (España)':
            lang[i] = "ese"
        elif lang[i][0].encode('utf-8') == 'English':
            lang[i] = "en"
        try:
            namePROV = name[i][1]
            nameMIDDLE = list(namePROV)
            original = list('original')
            updated = list('updated')
            if nameMIDDLE[0:8] == original or nameMIDDLE[0:7] == updated:
                nameDEF.append(namePROV)
        except:
            nameDEF.append('none')
        if nameDEF[i] != 'none':
            namePROV = nameDEF[i]
            nameDEF[i] = baseDOWNLOAD + namePROV

    print lang
    print nameDEF


    for i in range(len(lang)):
        if lang[i] == "ese" and checkLink(i) == "true":
            hayESP.append("T")
            hayESP.append(i)
            pass    
        if lang[i] == "esl" and checkLink(i) == "true":
            hayESPL.append("T")
            hayESPL.append(i)
            pass    
        if lang[i] == "en" and checkLink(i) == "true":
            hayEN.append("T")
            hayEN.append(i)
            pass
    done = "false"
    if done != "true":
        try:
            if hayESP[0] == "T":
                print "Subtitulos en Español de España encontrados"
                download(nameDEF[hayESP[1]], show, season, episode)
                done = "true"
        except:
            print "No hay en Español de España"
            
    if done != "true":
        try:
            if hayESPL[0] == "T":
                print "Subtitulos en Español Latino encontrados"
                download(nameDEF[hayESPL[1]], show, season, episode)
                done = "true"
        except:
            print "No hay en Español Latino"
            
    if done != "true":
        try:
            if hayEN[0] == "T":
                print "Subtitulos en Inglés encontrados"
                download(nameDEF[hayEN[1]], show, season, episode)
                done = "true"
        except:
            print "No hay en Inglés"
            print "No se hay encontrado subtitulos"

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
        self.comboBox_2 = QtGui.QComboBox(self.centralwidget)
        self.comboBox_2.setObjectName(_fromUtf8("comboBox_2"))
        self.horizontalLayout_6.addWidget(self.comboBox_2)
        self.gridLayout_2.addLayout(self.horizontalLayout_6, 2, 0, 1, 1)
        self.gridLayout = QtGui.QGridLayout()
        self.gridLayout.setSizeConstraint(QtGui.QLayout.SetDefaultConstraint)
        self.gridLayout.setContentsMargins(-1, -1, -1, 5)
        self.gridLayout.setObjectName(_fromUtf8("gridLayout"))
        self.label_5 = QtGui.QLabel(self.centralwidget)
        self.label_5.setObjectName(_fromUtf8("label_5"))
        self.gridLayout.addWidget(self.label_5, 0, 0, 1, 1)
        self.lineEdit = QtGui.QLineEdit(self.centralwidget)
        self.lineEdit.setObjectName(_fromUtf8("lineEdit"))
        self.gridLayout.addWidget(self.lineEdit, 0, 1, 1, 1)
        self.pushButton_2 = QtGui.QPushButton(self.centralwidget)
        self.pushButton_2.setObjectName(_fromUtf8("pushButton_2"))
        self.pushButton_2.clicked.connect(self.handleBuscar)
        self.gridLayout.addWidget(self.pushButton_2, 0, 2, 1, 1)
        self.gridLayout_2.addLayout(self.gridLayout, 0, 0, 1, 1)
        self.horizontalLayout_5 = QtGui.QHBoxLayout()
        self.horizontalLayout_5.setContentsMargins(-1, -1, -1, 3)
        self.horizontalLayout_5.setObjectName(_fromUtf8("horizontalLayout_5"))
        self.label_2 = QtGui.QLabel(self.centralwidget)
        self.label_2.setObjectName(_fromUtf8("label_2"))
        self.horizontalLayout_5.addWidget(self.label_2)
        self.comboBox = QtGui.QComboBox(self.centralwidget)
        self.comboBox.setObjectName(_fromUtf8("comboBox"))
        self.comboBox.currentIndexChanged.connect(self.handleSeason)
        self.horizontalLayout_5.addWidget(self.comboBox)
        self.gridLayout_2.addLayout(self.horizontalLayout_5, 1, 0, 1, 1)
        self.verticalLayout_2 = QtGui.QVBoxLayout()
        self.verticalLayout_2.setObjectName(_fromUtf8("verticalLayout_2"))
        self.pushButton = QtGui.QPushButton(self.centralwidget)
        self.pushButton.setObjectName(_fromUtf8("pushButton"))
        self.verticalLayout_2.addWidget(self.pushButton)
        self.pushButton.clicked.connect(self.handleButton)
        self.progressBar = QtGui.QProgressBar(self.centralwidget)
        self.progressBar.setProperty("value", 24)
        self.progressBar.setObjectName(_fromUtf8("progressBar"))
        self.verticalLayout_2.addWidget(self.progressBar)
        self.gridLayout_2.addLayout(self.verticalLayout_2, 3, 0, 1, 1)
        self.verticalLayout = QtGui.QVBoxLayout()
        self.verticalLayout.setObjectName(_fromUtf8("verticalLayout"))
        self.label_4 = QtGui.QLabel(self.centralwidget)
        self.label_4.setObjectName(_fromUtf8("label_4"))
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
        self.label_5.setText(_translate("MainWindow", "Serie", None))
        self.pushButton_2.setText(_translate("MainWindow", "Buscar", None))
        self.label_2.setText(_translate("MainWindow", "Temporada", None))
        self.pushButton.setText(_translate("MainWindow", "Descargar", None))
        self.label_4.setText(_translate("MainWindow", "Fanart Image", None))
      
        
    def handleButton(self, MainWindow):
        show = normalizeString(str(self.lineEdit.text()))
        season = str(self.comboBox.currentText())
        episode = str(self.comboBox_2.currentText())
        Search(show, season, episode)

    def handleBuscar(self, MainWindow):
        show = str(self.lineEdit.text())
        for i in range(int(checkSeasons(show))):
            self.comboBox.addItem(str(i+1))

    def handleSeason(self, MainWindow):
        season = str(self.comboBox.currentText())
        show = str(self.lineEdit.text())
        self.comboBox_2.clear()
        for i in range(int(checkEpisodes(show, season))):
            self.comboBox_2.addItem(str(i+1))
        



if __name__ == "__main__":
    import sys
    app = QtGui.QApplication(sys.argv)
    MainWindow = QtGui.QMainWindow()
    ui = Ui_MainWindow()
    ui.setupUi(MainWindow)
    MainWindow.show()
    sys.exit(app.exec_())

