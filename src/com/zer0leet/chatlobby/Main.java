/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zer0leet.chatlobby;

import com.zer0leet.chatlobby.handler.ClientHandler;
import com.zer0leet.chatlobby.handler.ClientListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Deepika
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      int port = 5000;
        try {
            ServerSocket server = new ServerSocket(port);
            ClientHandler handler = new ClientHandler();
            System.out.println("Connection established at : " + port);
            while (true) {
                Socket client = server.accept();
                System.out.println("Connection request from pc : " + client.getInetAddress());
                ClientListener listener = new ClientListener(client, handler);
                listener.start();

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
    }
    

