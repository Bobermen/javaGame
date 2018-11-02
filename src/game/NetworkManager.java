package game;

import linAlg.Vector2.Vector2;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.HashMap;

public class NetworkManager {
    //TCP часть
    public static ArrayList<Socket> clients = new ArrayList<>();
    public static ArrayList<DataInputStream> clientsInput = new ArrayList<>();
    public static ArrayList<DataOutputStream> clientsOutput = new ArrayList<>();
    public static DataInputStream clientInput;
    public static DataOutputStream clientOutput;

    //private static String ip;
    //private static String port;
    private static ServerSocket serverSocket;
    private static Socket socket;

    //UDP часть
    //Данные можно бросать через 1 сокет, но с разными пакетами
    private static DatagramChannel UDPSocket;
    private static ArrayList<DatagramChannel> UDPSockets;
    private static ByteArrayOutputStream toServerPacket;
    private static ByteBuffer toClientPacket;
    private static ArrayList<ByteArrayOutputStream> toClientsPackets;
    private static ArrayList<ByteBuffer> toServerPackets;

    public static boolean isServer = false;
    public static int playerID = -1;
    public static int playerCount;

    private static int defaultSize = 256 * (playerCount + 1);

    private NetworkManager() { }


    public static void startLobby(String ip, String port) {
        //NetworkManager.ip = ip;
        //NetworkManager.port = port;
        //Комментирую, потому что при необходимости данные можно извлечь через serverSocket
        //И это для работы с UDP удобнее
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
                    serverStartGame();
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
        //NetworkManager.ip = ip;
        //NetworkManager.port = port;
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
                        clientStartGame();
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
            Socket fakeSocket = new Socket(serverSocket.getInetAddress(), serverSocket.getLocalPort());
            DataOutputStream out = new DataOutputStream(fakeSocket.getOutputStream());
            out.writeUTF("closeLobby");
        } catch (IOException e) {
            System.out.println("error in closing server");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public static void update() {
        flush();
        if (isServer)
            serverUpdate();
        else
            clientUpdate();
    }

    private static void serverUpdate() {
        try {
            for (int i = 0; i < clients.size(); i++) {
                UDPSockets.get(i).send(ByteBuffer.wrap(toClientsPackets.get(i).toByteArray()), clients.get(i).getRemoteSocketAddress());
                toClientsPackets.get(i).reset();
            }
            for (int i = 0; i < clients.size(); i++) {
                toServerPackets.get(i).clear();
                SocketAddress sender = UDPSockets.get(i).receive(toServerPackets.get(i));
                while (sender != null) {
                    sender = UDPSockets.get(i).receive(toServerPackets.get(i));
                }
                clientsInput.get(i).reset();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void clientUpdate() {
        try {
            UDPSocket.send(ByteBuffer.wrap(toServerPacket.toByteArray()), socket.getRemoteSocketAddress());
            toServerPacket.reset();
            toClientPacket.clear();
            SocketAddress sender = UDPSocket.receive(toClientPacket);
            while (sender != null) {
                sender = UDPSocket.receive(toClientPacket);
            }
            clientInput.reset();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void clientStartGame() {
        try {
            socket.close();
            UDPSocket = DatagramChannel.open();
            UDPSocket.configureBlocking(false);
            UDPSocket.bind(new InetSocketAddress(socket.getLocalAddress(), socket.getLocalPort()));
            UDPSocket.connect(new InetSocketAddress(socket.getInetAddress(), socket.getPort()));
            toServerPacket = new ByteArrayOutputStream();
            toClientPacket = ByteBuffer.wrap(new byte[256]);
            clientInput = new DataInputStream(new ByteArrayInputStream(toClientPacket.array()));
            clientOutput = new DataOutputStream(toServerPacket);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private static void serverStartGame() {
        try {
            serverSocket.close();
            UDPSockets = new ArrayList<>();
            toClientsPackets = new ArrayList<>();
            toServerPackets = new ArrayList<>();
            DatagramChannel curChannel;
            ByteArrayOutputStream curStream;
            ByteBuffer curBuffer;
            for (int i = 0; i < clients.size(); i++) {
                clients.get(i).close();
                curChannel = DatagramChannel.open();
                curChannel.configureBlocking(false);
                curChannel.bind(new InetSocketAddress(serverSocket.getInetAddress(), serverSocket.getLocalPort()));
                curChannel.connect(clients.get(i).getRemoteSocketAddress());
                UDPSockets.add(curChannel);
                curBuffer = ByteBuffer.wrap(new byte[16]);
                toServerPackets.add(curBuffer);
                clientsInput.set(i, new DataInputStream(new ByteArrayInputStream(curBuffer.array())));
                curStream = new ByteArrayOutputStream();
                toClientsPackets.add(curStream);
                clientsOutput.set(i, new DataOutputStream(curStream));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}
