package com.zer0leet.chatlobby.entity;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {

    String userName;
    Socket socket;
    List<Client> validDestinations;

    public Client() {
    }

    public Client(String userName, Socket socket,List<Client> arr ) {
        this.userName = userName;
        this.socket = socket;
        this.validDestinations = arr;
    }

    public String getUserName() {
        return userName;
    }

    public List<Client> getValidDestinations() {
        return validDestinations;
    }

    public void setValidDestinations(List<Client> validDestinations) {
        this.validDestinations = validDestinations;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

}
