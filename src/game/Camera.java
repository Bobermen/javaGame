package game;

import linAlg.Vector2.Vector2;

import java.awt.event.KeyEvent;
import java.security.Key;

public class Camera extends Component {

    public static Camera main = null;
    public double width = 1000, height;
    public int resolutionWidth, resolutionHeight;
    public Camera (int resolutionWidth, int resolutionHeight) {
        this.resolutionWidth = resolutionWidth;
        this.resolutionHeight = resolutionHeight;
        height = width/resolutionWidth * resolutionHeight;
        main = this;
    }

    // TODO posChange, screenToWorld, worldToScreen
    public void update() {
        height = width/resolutionWidth * resolutionHeight;
        width += Input.getScroll() * 100;
        if (width > 40000) {
            width = 40000;
        }
        if (width < 50) {
            width = 50;
        }

        if (Input.isKey(KeyEvent.VK_UP)) {
            transform.setLocalPosition(transform.getLocalPosition()
                    .add(Vector2.getVector2(0, 100*Time.detlaTime)));
        }
        if (Input.isKey(KeyEvent.VK_DOWN)) {
            transform.setLocalPosition(transform.getLocalPosition()
                    .add(Vector2.getVector2(0, -100*Time.detlaTime)));
        }
        if (Input.isKey(KeyEvent.VK_RIGHT)) {
            transform.setLocalPosition(transform.getLocalPosition()
                    .add(Vector2.getVector2(100*Time.detlaTime, 0)));
        }
        if (Input.isKey(KeyEvent.VK_LEFT)) {
            transform.setLocalPosition(transform.getLocalPosition()
                    .add(Vector2.getVector2(-100*Time.detlaTime, 0)));
        }
    }
}
