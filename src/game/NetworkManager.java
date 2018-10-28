package game;

import linAlg.Vector2.Vector2;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkManager {

    public static ArrayList<Socket> clients = new ArrayList<>();
    public static ArrayList<DataInputStream> clientsInput = new ArrayList<>();
    public static ArrayList<DataOutputStream> clientsOutput = new ArrayList<>();

    public static DataInputStream clientInput;
    public static DataOutputStream clientOutput;

    private static String ip;
    private static String port;
    private static ServerSocket serverSocket;
    private static Socket socket;

    public static boolean isServer = false;
    public static int playerID = -1;
    public static int playerCount;

    private NetworkManager() { }


    public static void startLobby(String ip, String port) {
        NetworkManager.ip = ip;
        NetworkManager.port = port;
        Socket client;
        try {
            serverSocket = new ServerSocket(Short.parseShort(port), 0, InetAddress.getByName(ip));
            while (true) {
                client = serverSocket.accept();

                InputStream lin  = client.getInputStream();
                OutputStream lout = client.getOutputStream();

                DataInputStream in  = new DataInputStream (lin);
                DataOutputStream out = new DataOutputStream(lout);

                String message = in.readUTF();
                if (message.equals("connect")) {
                    onPlayerConnected(client, in, out);
                }
                if (message.equals("closeLobby")) {
                    return;
                }
                playerCount = clients.size() +1;
                System.out.println("server started");
            }
        } catch (IOException e) {
            System.out.println("Failed to host server!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public static void connectToLobby(String ip, String port) {
        NetworkManager.ip = ip;
        NetworkManager.port = port;
        //NetworkManager.playerID = clients.size() - 1;
        try {
            socket = new Socket(InetAddress.getByName(ip), Integer.parseInt(port));

            InputStream lin  = socket.getInputStream();
            OutputStream lout = socket.getOutputStream();

            DataInputStream in  = new DataInputStream (lin);
            DataOutputStream out = new DataOutputStream(lout);

            NetworkManager.clientInput = in;
            NetworkManager.clientOutput = out;

            out.writeUTF("connect");
            out.flush();
            while (true) {
                String message = in.readUTF();
                try {
                    int n = Integer.parseInt(message);
                    if (playerID == -1) {
                        playerID = n - 2;
                        System.out.println("PlayerID = " + playerID);
                    }
                    playerCount = n;
                    StringBuilder str = new StringBuilder("<html>");
                    for (int i = 0; i < n; i++) {
                        str.insert(str.length(), "Player " + i + "<br />");
                    }
                    str.insert(str.length(), "</html>");
                    JLabel label = (JLabel) Menu.playerLobbyPanel.getComponent(0);
                    label.setText(str.toString());
                } catch (Exception e) {
                    if (message.equals("startGame")) {
                        return;
                    }
                }
            }



        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }
    }

    private static void onPlayerConnected(Socket client, DataInputStream in, DataOutputStream out) {
        clients.add(client);
        clientsInput.add(in);
        clientsOutput.add(out);
        //
        int n = clients.size() + 1;
        StringBuilder str = new StringBuilder("<html>");
        for (int i = 0; i < n; i++) {
            str.insert(str.length(), "Player " + i + "<br />");
        }
        str.insert(str.length(), "</html>");
        JLabel label = (JLabel)Menu.serverLobbyPanel.getComponent(0);
        label.setText(str.toString());
        send(String.valueOf(n));
        flush();
        //

        // TODO сделать все что связано с появлением нового игрока.
    }

    public static void send(String str) {
        try {
            for (var item : clientsOutput) {
                item.writeUTF(str);
            }
        } catch (IOException e) {
            System.out.println("failed to send");
        }
    }

    public static void sendDouble(double a) {
        try {
            for (var item : clientsOutput) {
                item.writeDouble(a);
            }
        } catch (IOException e) {
            System.out.println("failed to send doubles");
        }
    }

    public static void sendInt(int a) {
        try {
            for (var item : clientsOutput) {
                item.writeInt(a);
            }
        } catch (IOException e) {
            System.out.println("failed to send doubles");
        }
    }

    public static void sendVector2(Vector2 a) {
        try {
            for (var item : clientsOutput) {
                item.writeDouble(a.x);
                item.writeDouble(a.y);
            }
        } catch (IOException e) {
            System.out.println("failed to send doubles");
        }
    }

    public static void flush() {
        try {
            for (var item : clientsOutput) {
                item.flush();
            }
        } catch (IOException e) {
            System.out.println("failed to send doubles");
        }
    }

    public static void closeLobby() {
        try {
            Socket socket = new Socket(InetAddress.getByName(ip), Integer.parseInt(port));
            InputStream lin  = socket.getInputStream();
            OutputStream lout = socket.getOutputStream();

            DataInputStream in  = new DataInputStream (lin);
            DataOutputStream out = new DataOutputStream(lout);

            out.writeUTF("closeLobby");
        } catch (IOException e) {
            System.out.println("error in closing server");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}
