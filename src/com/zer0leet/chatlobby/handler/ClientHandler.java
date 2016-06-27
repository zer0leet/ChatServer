package com.zer0leet.chatlobby.handler;

import com.zer0leet.chatlobby.entity.Client;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler {

    List<Client> clients = new ArrayList<>();

    public ClientHandler() {
    }

    public void addClient(Client client) {
        this.clients.add(client);

    }

    public Client getByUserName(String userName) {
        for (Client c : clients) {
            if (c.getUserName().equalsIgnoreCase(userName)) {
                return c;
            }
        }
        return null;
    }

    public Client getBySocket(Socket socket) {
        for (Client c : clients) {
            if (c.getSocket() == socket) {
                return c;
            }
        }
        return null;

    }

    public List<Client> getAll() {
        return clients;
    }

    public void broadcastMessage(Client client, String message) throws IOException {
        for (Client c : client.getValidDestinations()) {
            if (!c.equals(client)) {
                PrintStream ps = new PrintStream(c.getSocket().getOutputStream());
                ps.println(message);
            }
        }
    }

    public void broadcastPrivateMessage(Client client, Client destination, String message) throws IOException {
        for (Client c : client.getValidDestinations()) {
            if (c.equals(destination)) {
                PrintStream ps = new PrintStream(c.getSocket().getOutputStream());
                ps.println(message);
            } else {
                PrintStream ps = new PrintStream(client.getSocket().getOutputStream());
                ps.println("This user is not available or has muted you");
            }
        }
    }

    public void removeFromValidDestinations(Client source, Client destination) throws IOException {
        try {
            for (Client c : source.getValidDestinations()) {
                if (c.equals(destination)) {
                    source.getValidDestinations().remove(destination);

                } else {
                    PrintStream ps = new PrintStream(destination.getSocket().getOutputStream());
                    ps.println("Muting not successful");
                }
            }
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
