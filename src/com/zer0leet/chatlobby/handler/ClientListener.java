package com.zer0leet.chatlobby.handler;

import com.zer0leet.chatlobby.dao.UserDAO;
import com.zer0leet.chatlobby.dao.impl.UserDAOImpl;
import com.zer0leet.chatlobby.entity.Client;
import com.zer0leet.chatlobby.entity.User;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientListener extends Thread {

    private Socket socket;
    private ClientHandler handler;
    private Client client;
    private UserDAO userDAO = new UserDAOImpl();
    private PrintStream pstream;
    private BufferedReader reader;

    public ClientListener(Socket client, ClientHandler handler) throws IOException {
        this.socket = client;
        this.handler = handler;
        pstream = new PrintStream(socket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            pstream.println("You are now connected to the server, server time " + new Date());
            while (!doLogin()) {
                pstream.println("Invalid userName or password or not Activated ");
            }

            while (true) {
                pstream.print(" > ");
                String line = reader.readLine();
                String[] messageArray = line.split(";;");

                if (messageArray[0].equalsIgnoreCase("pm")) {
                    Client destination = handler.getByUserName(messageArray[1]);
                    handler.broadcastPrivateMessage(client, destination, client.getUserName() + " personally says " + messageArray[2]);

                } else if (messageArray[0].equalsIgnoreCase("list")) {
                    List<Client> c = handler.getAll();
                    for (Client cl : c) {
                        pstream.println(cl.getUserName() + " is @ " + cl.getSocket().getInetAddress());
                    }

                } else if (messageArray[0].equalsIgnoreCase("email")) {
                    Client destination = handler.getByUserName(messageArray[1]);
                    handler.broadcastPrivateMessage(client, destination, "You got an Email from this person : " + client.getUserName());
                    pstream.println("Your Email has been sent");

                } else if (messageArray[0].equalsIgnoreCase("url")) {
                    grabAndWrite(messageArray[1]);
                    //pstream.println(messageArray[1]);

                } else if (messageArray[0].equalsIgnoreCase("block")) {
                    pstream.println("Please enter the Master Password that the ADMIN must have");
                    String masterPassword = "masterpassword";
                    String password = reader.readLine();
                    if (password.equalsIgnoreCase(masterPassword)) {
                        try {
                            Client toBeBlocked = handler.getByUserName(messageArray[1]);
                            for (int i = 0; i < toBeBlocked.getValidDestinations().size(); i++) {
                                toBeBlocked.getValidDestinations().set(0, null);
                            }
                        } catch (NullPointerException ex) {
                            pstream.println("No such client exists");
                        }

                    } else {
                        pstream.println("You did not enter the right master password");
                    }

                } else if (messageArray[0].equalsIgnoreCase("mute")) {
                    try {
                        Client toBeRemovedDestination = this.client;
                        Client source = handler.getByUserName(messageArray[1]);
                        handler.removeFromValidDestinations(source, toBeRemovedDestination);

                    } catch (NullPointerException ex) {
                        pstream.println("No such client exists");
                    }

                } else {
                    handler.broadcastMessage(client, client.getUserName() + " says > " + line);
                }

            }
        } catch (IOException e) {
            pstream.println(e.getMessage());
        }
    }


    private boolean doLogin() throws IOException {
        pstream.println("Enter the userName : ");
        String userName = reader.readLine();
        pstream.println("Enter the password : ");
        String password = reader.readLine();

        User user = userDAO.login(userName, password);
        if (user == null) {
            return false;
        } else if (user.isStatus() == false) {
            return false;
        }
        List<Client> validDestinations = handler.getAll();
        client = new Client(userName, socket, validDestinations);
        handler.addClient(client);
        return true;
    }

    
    private void grabAndWrite(String u) {
        try {
            URL url = new URL(u);
            // pstream.println(u);
            URLConnection conn = url.openConnection();
            StringBuilder content = new StringBuilder();
            BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while (r.readLine() != null) {
                content.append(r.readLine());
            }
            pstream.println(content);
            /*FileWriter writer = new FileWriter("d:/Browser.txt");
             writer.write("Oe batti khai");
             writer.close();  */
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage() + " Could not Connect");

        }
    }

}
