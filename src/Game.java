import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.*;

public class Game extends Canvas implements Runnable {

    private JFrame frame;
    private boolean gameRunning = true;
    private GameObject mainCamera;
    private BufferStrategy strategy;



    public Game(JFrame frame) {
        this.frame = frame;
        new Input(1).addListeners(this);
        frame.add(this);
        frame.setVisible(true);
        requestFocus();
        createBufferStrategy(2);
        strategy = getBufferStrategy();
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {

        init();

        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

        while (gameRunning) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            Time.detlaTime = (double)updateLength / 1000000000;
            Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(Color.blue);
            g.fillRect(0,0,frame.getWidth(),frame.getHeight());

            //System.out.println("Ship pos = " + ship.transform.position.x + " " + ship.transform.position.y);
            //System.out.println(Camera.main.resolutionHeight);

            update();


            render(g);

            g.dispose();
            strategy.show();
            Input.update();

            try {
                Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );
            }
            catch(Exception e) {}
        }
    }
    private void init() {
        mainCamera = new GameObject();
        mainCamera.addComponent(new Camera(frame.getWidth(), frame.getHeight()));
        GameObject.instantiate(mainCamera);
        System.out.println(frame.getWidth());

        GameObject ship = new GameObject();
        ship.transform.position = new Vector2(0, 100);
        ship.transform.rotation = 0;
        ship.transform.scale = 0.06;
        RigidBody2d rigidBody2d = ship.addComponent(new RigidBody2d());
        rigidBody2d.mass = 35000000;
        ship.addComponent(new PlayerControl());
        Sprite sprite = ship.addComponent(new Sprite(new ImageIcon("Resources/Dunkerk.png").getImage()));
        GameObject.instantiate(ship);
    }

    private void update() {
        GameObject.callUpdate();
    }

    private void render(Graphics2D g) {
        Renderer.sprites.forEach(sprite -> Renderer.draw(sprite, g));
        //Renderer.sprites.clear();
    }
}