package game;

import linAlg.Vector2.Vector2;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Camera extends Component {

    public static Camera main = null;
    public double width = 1000, height;
    public int resolutionWidth, resolutionHeight;
    public double cameraSpeed = 0.1;
    public double cameraScroll = 0.1;
    public JFrame frame;


    public Camera (JFrame frame) {
        main = this;
        main.frame = frame;
    }

    // TODO posChange, screenToWorld, worldToScreen

    @Override
    public void start() {
        this.resolutionWidth = frame.getWidth();
        this.resolutionHeight = frame.getHeight();
        height = width/resolutionWidth * resolutionHeight;
    }
    @Override
    public void update() {
        resolutionWidth = frame.getWidth();
        resolutionHeight = frame.getHeight();
        height = width/resolutionWidth * resolutionHeight;
        width += Input.getScroll() * width * cameraScroll;
        if (width > 40000) {
            width = 40000;
        }
        if (width < 50) {
            width = 50;
        }

        if (Input.isKey(KeyEvent.VK_UP)) {
            transform.setLocalPosition(transform.getLocalPosition()
                    .add(Vector2.getVector2(0, cameraSpeed*width*Time.detlaTime)));
        }
        if (Input.isKey(KeyEvent.VK_DOWN)) {
            transform.setLocalPosition(transform.getLocalPosition()
                    .add(Vector2.getVector2(0, -cameraSpeed*width*Time.detlaTime)));
        }
        if (Input.isKey(KeyEvent.VK_RIGHT)) {
            transform.setLocalPosition(transform.getLocalPosition()
                    .add(Vector2.getVector2(cameraSpeed*width*Time.detlaTime, 0)));
        }
        if (Input.isKey(KeyEvent.VK_LEFT)) {
            transform.setLocalPosition(transform.getLocalPosition()
                    .add(Vector2.getVector2(-cameraSpeed*width*Time.detlaTime, 0)));
        }
    }

    public Vector2 screenToWorld(Vector2 pos) {
        Vector2 result = Vector2.getVector2(pos.x - resolutionWidth/2, -pos.y + resolutionHeight/2);
        return transform.getPosition().add(result.mul(width/resolutionWidth));
    }
    //TODO
    public Vector2 worldToscreen(Vector2 pos) {
        return pos;
    }

    @Override
    public Component clone() {
        return this;
    }
}
