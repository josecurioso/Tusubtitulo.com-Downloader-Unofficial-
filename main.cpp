#include "mainwindow.h"
#include <QApplication>
#include <socketapi.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>

#define PORT 80
#define IP_ADRESS "75.126.101.229"

int connectToTvdb();
int sendToTvdb();

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    MainWindow w;
    w.show();
    connectToTvdb();
    return a.exec();
}

int connectToTvdb(){
    int mySocket = socket(AF_INET, SOCK_STREAM, 0);
    if (mySocket == -1){
        printf("Could not create socket\n");
        return 1;
    }
    printf("Socket created.\n");

    struct sockaddr_in server;
    server.sin_addr.saddr = inet_addr(IP_ADRESS);
    server.sin_family = AF_INET;
    server.sin_port = htons(PORT);

    if(connect(mySocket, (struct sockaddr*)&server , sizeof(server)) < 0){
        printf("Connect error.\n");
        return 1;
    }
    printf("Connected.\n");

    return 0;
}


int sendToTvdb(){
    char *message = "hola";
    if (send(mySocket, message, strlen(message), 0) <0){
        printf("Send failed.\n");
        return 1;
    }
    printf("Data sent.\n");

}

int recieveFromTvdb(){


}

















