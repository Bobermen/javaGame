package game;

import linAlg.Vector2.Vector2;

import java.io.DataInputStream;

public class SyncTransform extends Component {

    @Override
    public void update() {
        if (NetworkManager.isServer) {
            NetworkManager.sendVector2(transform.getLocalPosition());
            NetworkManager.sendDouble(transform.getLocalRotation());
            NetworkManager.flush();
        }
        else {
            DataInputStream in = NetworkManager.clientInput;
            try {
                transform.setLocalPosition(Vector2.getVector2(in.readDouble(), in.readDouble()));
                transform.setLocalRotation(in.readDouble());
                System.out.println("SyncTransform read successful");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public SyncTransform clone() {
        return new SyncTransform();
    }
}
