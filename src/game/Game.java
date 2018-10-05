package game;

import linAlg.Vector2.Vector2;

import java.awt.*;
import java.awt.image.BufferStrategy;

import javax.swing.*;

public class Game extends Canvas implements Runnable {

    private JFrame frame;
    private boolean gameRunning = true;
    private GameObject mainCamera;
    private BufferStrategy strategy;

    private static Scene scene;

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
            //System.out.println(game.Camera.main.resolutionHeight);

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
        scene = new Scene();

        mainCamera = new GameObject();
        mainCamera.addComponent(new Camera(frame.getWidth(), frame.getHeight()));
        mainCamera.scene = scene;
        GameObject.instantiate(mainCamera);

        GameObject ship = new GameObject();
        ship.transform.setLocalPosition(Vector2.getVector2(0, 100));
        System.out.println(ship.transform.getPosition());
        ship.transform.setLocalRotation(45);
        ship.transform.setLocalScale(0.06);
        RigidBody2d rigidBody2d = ship.addComponent(new RigidBody2d());
        rigidBody2d.mass = 35000000;
        ship.addComponent(new PlayerControl());
        Sprite sprite = ship.addComponent(new Sprite(new ImageIcon("Resources/Dunkerk.png").getImage()));
        GameObject.instantiate(ship);
    }

    public static Scene getActiveScene() {
        return scene;
    }

    private void update() {
        scene.root.forEach(transform -> transform.gameObject.update());
    }

    private void render(Graphics2D g) {
        Renderer.sprites.forEach(sprite -> Renderer.draw(sprite, g));
        //game.Renderer.sprites.clear();
    }
}