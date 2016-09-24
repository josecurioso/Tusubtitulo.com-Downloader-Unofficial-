# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'untitled.ui'
#
# Created by: PyQt4 UI code generator 4.11.4
#
# WARNING! All changes made in this file will be lost!

from PyQt4 import QtCore, QtGui
from lxml import html
import requests
import urllib
import urllib2
import os
import sys


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
        MainWindow.resize(237, 221)
        MainWindow.setMinimumSize(QtCore.QSize(237, 221))
        MainWindow.setMaximumSize(QtCore.QSize(237, 221))
        self.centralwidget = QtGui.QWidget(MainWindow)
        self.centralwidget.setObjectName(_fromUtf8("centralwidget"))
        self.gridLayout = QtGui.QGridLayout(self.centralwidget)
        self.gridLayout.setObjectName(_fromUtf8("gridLayout"))
        self.verticalLayout = QtGui.QVBoxLayout()
        self.verticalLayout.setObjectName(_fromUtf8("verticalLayout"))
        self.label = QtGui.QLabel(self.centralwidget)
        self.label.setObjectName(_fromUtf8("label"))
        self.verticalLayout.addWidget(self.label)
        self.lineEdit = QtGui.QLineEdit(self.centralwidget)
        self.lineEdit.setObjectName(_fromUtf8("lineEdit"))
        self.lineEdit.setText("The Last Ship") 
        self.verticalLayout.addWidget(self.lineEdit)
        self.label_2 = QtGui.QLabel(self.centralwidget)
        self.label_2.setObjectName(_fromUtf8("label_2"))
        self.verticalLayout.addWidget(self.label_2)
        self.lineEdit_2 = QtGui.QLineEdit(self.centralwidget)
        self.lineEdit_2.setObjectName(_fromUtf8("lineEdit_2"))
        self.lineEdit_2.setText("2") 
        self.verticalLayout.addWidget(self.lineEdit_2)
        self.label_3 = QtGui.QLabel(self.centralwidget)
        self.label_3.setObjectName(_fromUtf8("label_3"))
        self.verticalLayout.addWidget(self.label_3)
        self.lineEdit_3 = QtGui.QLineEdit(self.centralwidget)
        self.lineEdit_3.setObjectName(_fromUtf8("lineEdit_3"))
        self.lineEdit_3.setText("7") 
        self.verticalLayout.addWidget(self.lineEdit_3)
        self.horizontalLayout = QtGui.QHBoxLayout()
        self.horizontalLayout.setObjectName(_fromUtf8("horizontalLayout"))
        self.pushButton = QtGui.QPushButton(self.centralwidget)
        self.pushButton.setObjectName(_fromUtf8("pushButton"))
        self.pushButton.clicked.connect(self.handleButton)
        self.horizontalLayout.addWidget(self.pushButton)
        self.verticalLayout.addLayout(self.horizontalLayout)
        self.gridLayout.addLayout(self.verticalLayout, 0, 0, 1, 1)
        MainWindow.setCentralWidget(self.centralwidget)
        self.menubar = QtGui.QMenuBar(MainWindow)
        self.menubar.setGeometry(QtCore.QRect(0, 0, 237, 21))
        self.menubar.setObjectName(_fromUtf8("menubar"))
        MainWindow.setMenuBar(self.menubar)
        self.statusbar = QtGui.QStatusBar(MainWindow)
        self.statusbar.setObjectName(_fromUtf8("statusbar"))
        MainWindow.setStatusBar(self.statusbar)

        self.retranslateUi(MainWindow)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)

    def retranslateUi(self, MainWindow):
        MainWindow.setWindowTitle(_translate("MainWindow", "TuSubtitulo.com", None))
        self.label.setText(_translate("MainWindow", "Serie", None))
        self.label_2.setText(_translate("MainWindow", "Temporada", None))
        self.label_3.setText(_translate("MainWindow", "Episodio", None))
        self.pushButton.setText(_translate("MainWindow", "Descargar", None))

    def handleButton(self, MainWindow):
        show = str(self.lineEdit.text())
        originalValue = show.lower()
        intermValue = list(originalValue)
        newValue = intermValue
        for i in range(len(intermValue)):
            if intermValue[i] == ' ':
                newValue[i] = '-'
            else:
                pass
        show = ''.join(newValue)
        season = self.lineEdit_2.text()
        try:
            season = int(season)
            season = str(season)
        except Exception:
            self.lineEdit_2.setText("Tiene que ser un numero.") 
            pass
        episode = self.lineEdit_3.text()
        try:
            episode = int(episode)
            episode = str(episode)
        except Exception:
            self.lineEdit_3.setText("Tiene que ser un numero.") 
            pass
        Search(show, season, episode)

    
if __name__ == "__main__":
    import sys
    app = QtGui.QApplication(sys.argv)
    MainWindow = QtGui.QMainWindow()
    ui = Ui_MainWindow()
    ui.setupUi(MainWindow)
    MainWindow.show()
    sys.exit(app.exec_())

