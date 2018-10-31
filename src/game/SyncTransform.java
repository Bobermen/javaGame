package game;

import linAlg.Vector2.Vector2;

import java.io.DataInputStream;

public class SyncTransform extends Component {

    double time = 0;

    @Override
    public void update() {
        if (time < 0.1) {
            time += Time.detlaTime;
            return;
        }
        if (NetworkManager.isServer) {
            NetworkManager.sendVector2(transform.getLocalPosition());
            NetworkManager.sendDouble(transform.getLocalRotation());
            //NetworkManager.flush();
        }
        else {
            DataInputStream in = NetworkManager.clientInput;
            try {
                transform.setLocalPosition(Vector2.getVector2(in.readDouble(), in.readDouble()));
                transform.setLocalRotation(in.readDouble());
                //System.out.println("SyncTransform read successful");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        time = 0;
    }

    @Override
    public SyncTransform clone() {
        return new SyncTransform();
    }
}
