package game;

import linAlg.Vector2.Vector2;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.*;

public class Game implements Runnable {

    public JFrame frame;
    public static ArrayList<GameObject> root = new ArrayList<>();


    private boolean gameRunning = true;
    private GameObject mainCamera;
    private BufferStrategy strategy;
    private Color background = new Color(0, 87, 133, 255);

    public Game(JFrame frame) {
        this.frame = frame;
        this.frame.setFocusable(true);
        new Input(1).addListeners(frame);
        this.frame.requestFocus();
        this.frame.createBufferStrategy(2);
        strategy = frame.getBufferStrategy();
        this.frame.setIgnoreRepaint(true);
    }
    public void start() {
        new Thread(this).start();
    }
    @Override
    public void run() {
        //createGUI();
        if (NetworkManager.isServer) {
            createLobby();
        }
        else {
            connectToLobby();
        }
        gameLoop();
    }
    public void gameLoop() {

        System.out.println("GameLoop");
        firstInstance();

        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

        int lastFpsTime = 0;
        int fps = 0;

        while (gameRunning) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            Time.detlaTime = (double)updateLength / 1000000000;

            lastFpsTime += updateLength;
            fps++;

            // update our FPS counter if a second has passed since

            // we last recorded

            if (lastFpsTime >= 1000000000) {
                frame.setTitle(" (FPS: "+fps+")");
                lastFpsTime = 0;
                fps = 0;
            }

            Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(background);
            g.fillRect(0,0, frame.getWidth(), frame.getHeight());

            //System.out.println("Ship pos = " + ship.transform.position.x + " " + ship.transform.position.y);
            //System.out.println(game.Camera.main.resolutionHeight);

            update();

            render(g);

            g.dispose();
            strategy.show();
            Input.update();
            NetworkManager.flush();

            try {
                Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );
            }
            catch(Exception e) {}
        }
    }
    private void update() {
        for (int i = 0; i < root.size(); i++) {
            root.get(i).update();
        }
    }
    private void render(Graphics2D g) {
        Renderer.sprites.forEach(sprite -> Renderer.draw(sprite, g));
    }
    private void firstInstance() {

        GameObject ship = new GameObject();
        ship.transform.setLocalPosition(Vector2.getVector2(0, 0));
        ship.transform.setLocalRotation(0);
        ship.transform.setLocalScale(0.06);
        RigidBody2d rigidBody2d = ship.addComponent(new RigidBody2d());
        rigidBody2d.mass = 26000000;
        rigidBody2d.velocity = ship.transform.getRight().mul(16);
        //ship.addComponent(new PlayerControl());
        ship.addComponent(new SyncTransform());
        ship.addComponent(new ParticleSystem());

        mainCamera = new GameObject();
        mainCamera.addComponent(new Camera(frame));
        Game.instantiate(mainCamera);

        GameObject hull = new GameObject();
        hull.addComponent(new Sprite(new ImageIcon("Resources/Dunkerk_no_CUPs.png").getImage()));
        hull.transform.setParent(ship.transform);

        //3435x471
        //1) зад низ 912,06 359,33
        //2) зад ср 756,33 235,33
        //3) зад верх 912,23 111,15
        GameObject smallTurret = new GameObject();
        smallTurret.addComponent(new Sprite(new ImageIcon("Resources/CUP3.png").getImage())).pivot.x = 60;
        smallTurret.transform.setLocalPosition(Vector2.getVector2(-57.69, -0.33));
        smallTurret.transform.setLocalRotation(180);
        smallTurret.transform.setParent(ship.transform);

        GameObject mediumTurret = new GameObject();
        mediumTurret.addComponent(new Sprite(new ImageIcon("Resources/CUP2.png").getImage())).pivot.x = 55;
        mediumTurret.transform.setLocalPosition(Vector2.getVector2(-13.8, 10.7));
        mediumTurret.transform.setLocalRotation(30);
        mediumTurret.transform.setParent(ship.transform);

        GameObject bigTurret = new GameObject();
        bigTurret.addComponent(new Sprite(new ImageIcon("Resources/CUP1.png").getImage())).pivot.x = 133;
        bigTurret.transform.setLocalPosition(Vector2.getVector2(50.6, 0));
        bigTurret.transform.setLocalRotation(60);
        bigTurret.addComponent(new TurretControl());
        bigTurret.transform.setParent(ship.transform);

        GameObject bigTurret1 = new GameObject();
        bigTurret1.addComponent(new Sprite(new ImageIcon("Resources/CUP1.png").getImage())).pivot.x = 133;
        bigTurret1.transform.setLocalPosition(Vector2.getVector2(23, 0));
        bigTurret1.transform.setLocalRotation(60);
        bigTurret1.addComponent(new TurretControl());
        bigTurret1.transform.setParent(ship.transform);

        GameObject top = new GameObject();
        top.addComponent(new Sprite(new ImageIcon("Resources/RUF_1.png").getImage()));
        top.transform.setParent(ship.transform);

        Vector2 position = Vector2.ZERO;
        if (NetworkManager.isServer) {
            ship.addComponent(new PlayerControl());
            instantiate(ship).getComponent(PlayerControl.class).isPlayer = true;
        }
        else {
            instantiate(ship);
        }

        for (int i = 0; i < NetworkManager.playerCount - 1; i++) {
            position = position.add(Vector2.UP.negative().mul(50));
            ship.transform.setLocalPosition(position);
            if (i == NetworkManager.playerID) {
                System.out.println("Client: Player found");
                instantiate(ship).addComponent(new PlayerControl()).isPlayer = true;
            }
            else {
                GameObject clone = instantiate(ship);
                if (NetworkManager.isServer) {
                    clone.getComponent(PlayerControl.class).playerID = i;
                }
            }
        }
    }
    public static GameObject instantiate(GameObject origin, Transform parent) {
        GameObject clone = origin.clone();
        clone.transform.setParent(parent);
        clone.start();
        return clone;
    }
    public static GameObject instantiate(GameObject origin) {
        GameObject clone = origin.clone();
        Game.root.add(clone);
        clone.start();
        return clone;
    }
    private void createLobby() {
        String ip = ((JTextField) Menu.mainPanel.getComponent(2)).getText();
        String port = ((JTextField) Menu.mainPanel.getComponent(3)).getText();

        NetworkManager.startLobby(ip, port);
    }
    private void connectToLobby() {
        String ip = ((JTextField) Menu.mainPanel.getComponent(2)).getText();
        String port = ((JTextField) Menu.mainPanel.getComponent(3)).getText();

        NetworkManager.connectToLobby(ip, port);
    }
}