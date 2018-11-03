import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class StreamTest {
    public static void main(String[] args) throws Exception {
        byte[] buf = "Съешь моих мягких французских булочек да выпей чаю".getBytes();
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        DataInputStream din = new DataInputStream(in);
        int a = din.readInt();
        int b = din.readInt();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        in.reset();
        out.write(in.readAllBytes());
    }
}
