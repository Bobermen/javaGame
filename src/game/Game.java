package game;

import linAlg.Vector2.Vector2;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

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
    private void init() {
        scene = new Scene();

        mainCamera = new GameObject();
        mainCamera.addComponent(new Camera(frame.getWidth(), frame.getHeight()));
        mainCamera.scene = scene;
        GameObject.instantiate(mainCamera);

        GameObject ship = new GameObject();
        ship.transform.setLocalPosition(Vector2.getVector2(0, 0));
        ship.transform.setLocalRotation(15);
        ship.transform.setLocalScale(0.06);
        RigidBody2d rigidBody2d = ship.addComponent(new RigidBody2d());
        rigidBody2d.mass = 35000000;
        rigidBody2d.velocity = Vector2.RIGHT.mul(0);
        ship.addComponent(new PlayerControl());
        ship.addComponent(new Sprite(new ImageIcon("Resources/Dunkerk.png").getImage()));
        GameObject.instantiate(ship);

        //3435x471
        //1) зад низ 912,06 359,33
        //2) зад ср 756,33 235,33
        //3) зад верх 912,23 111,15
        GameObject smallTurret = new GameObject();
        smallTurret.addComponent(new Sprite(new ImageIcon("Resources/CUP3.png").getImage())).pivot.x = 60;
        smallTurret.transform.setLocalPosition(Vector2.getVector2(-57.69, -0.33));
        smallTurret.transform.setLocalRotation(180);
        GameObject.instantiate(smallTurret, ship.transform);

        GameObject mediumTurret = new GameObject();
        mediumTurret.addComponent(new Sprite(new ImageIcon("Resources/CUP2.png").getImage())).pivot.x = 55;
        mediumTurret.transform.setLocalPosition(Vector2.getVector2(-13.8, 10.7));
        mediumTurret.transform.setLocalRotation(30);
        GameObject.instantiate(mediumTurret, ship.transform);

        GameObject bigTurret = new GameObject();
        bigTurret.addComponent(new Sprite(new ImageIcon("Resources/CUP1.png").getImage())).pivot.x = 134;
        bigTurret.transform.setLocalPosition(Vector2.getVector2(50, 0));
        bigTurret.transform.setLocalRotation(60);
        GameObject.instantiate(bigTurret, ship.transform);
    }

}