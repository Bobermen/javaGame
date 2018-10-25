import game.PlayerControl;
import linAlg.Vector2.Vector2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
// import java.util.Scanner;

// класс в котором прописано все взаимодействие с клиентами
// для его работы на сервере достаточно запустить его как поток
// и отправлять и получать информацию

public class Server
{
    private static Server ourInstance = null;
    public ArrayList<Socket> clients = new ArrayList<>();
    // DataStream могут быть заменены на что-то более удобное при необходимости
    // Хотя он невероятно удобен
    public ArrayList<DataInputStream> clientsInput = new ArrayList<>();
    public ArrayList<DataOutputStream> clientsOutput = new ArrayList<>();
    public ArrayList<PlayerControl> clientsControl = new ArrayList<>();
    private ServerSocket server;
    public static Server getInstance()
    {
        return ourInstance;
    }
    public static void create() {
        if (ourInstance == null) {
            ourInstance = new Server();
        }
    }

    private Server() { }

    private static class IPAdress {
        public byte[] ip = new byte[4];
        public short port;
    }
/*
    private static IPAdress parceFile(String filename) {
        IPAdress result = null;
        try (Scanner in = new Scanner(new FileInputStream("filename"))) {
            result = new IPAdress();
            in.useDelimiter("[: ;\n]+");
            in.skip("[Ss]erver-ip");
            String[] ip = in.next().split(".");
            for (int i = 0; i < 4; i++) {
                result.ip[i] = Byte.parseByte(ip[i]);
            }
            in.skip("[Pp]ort");
            result.port = Short.parseShort(in.next());
        } catch (FileNotFoundException e) {
            System.out.println("file not found!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return result;
    }
*/
    public void run() {
        Socket client;
        try {
            server = new ServerSocket();
            while (!server.isClosed()) {
                client = server.accept();
                onPlayerConnected(client);
            }
        } catch (IOException e) {
            System.out.println("Failed to host server!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void onPlayerConnected(Socket client) {
        clients.add(client);
        try {
            clientsInput.add(new DataInputStream(client.getInputStream()));
            clientsOutput.add(new DataOutputStream(client.getOutputStream()));
        } catch (IOException e) {
            System.out.println("Failed to get Streams");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        // TODO сделать все что связано с появлением нового игрока.
    }

    public void sendDouble(double a) {
        try {
            for (var item : clientsOutput) {
                item.writeDouble(a);
            }
        } catch (IOException e) {
            System.out.println("failed to send doubles");
        }
    }

    public void sendInt(int a) {
        try {
            for (var item : clientsOutput) {
                item.writeInt(a);
            }
        } catch (IOException e) {
            System.out.println("failed to send doubles");
        }
    }

    public void sendVector2(Vector2 a) {
        try {
            for (var item : clientsOutput) {
                item.writeDouble(a.x);
                item.writeDouble(a.y);
            }
        } catch (IOException e) {
            System.out.println("failed to send doubles");
        }
    }

    public void flush() {
        try {
            for (var item : clientsOutput) {
                item.flush();
            }
        } catch (IOException e) {
            System.out.println("failed to send doubles");
        }
    }
}
