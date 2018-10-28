package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Menu extends JFrame {

    public static Game game;
    public static JPanel mainPanel;
    public static JPanel serverLobbyPanel;
    public static JPanel playerLobbyPanel;

    public Menu() {
        super("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
        setLocationRelativeTo(null);
        createGUI();
        setVisible(true);
        game = new Game(this);

    }

    public void createGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //mainPanel
        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        JButton button1 = new JButton("Host");
        button1.setActionCommand("Host");
        button1.setSize(100, 20);
        button1.setLocation(10, 50);
        mainPanel.add(button1);

        JButton button2 = new JButton("Connect");
        button2.setActionCommand("Connect");
        button2.setSize(100, 20);
        button2.setLocation(120, 50);
        mainPanel.add(button2);


        JTextField textField1 = new JTextField();
        textField1.setText("localhost");
        textField1.setSize(new Dimension(150, 20));
        textField1.setLocation(10,10);
        mainPanel.add(textField1);

        JTextField textField2 = new JTextField();
        textField2.setText("22222");
        textField2.setSize(new Dimension(50, 20));
        textField2.setLocation(170,10);
        mainPanel.add(textField2);

        //serverLobbyPanel
        serverLobbyPanel = new JPanel();
        serverLobbyPanel.setLayout(null);

        JLabel label1 = new JLabel();
        label1.setText("Player 0");
        label1.setSize(new Dimension(150, 400));
        label1.setLocation(10,10);
        label1.setVerticalAlignment(JLabel.TOP);
        serverLobbyPanel.add(label1);

        JButton button3 = new JButton("Start");
        button3.setActionCommand("Start");
        button3.setSize(100, 20);
        button3.setLocation(170, 10);
        serverLobbyPanel.add(button3);

        //playerLobbyPanel
        playerLobbyPanel = new JPanel();
        playerLobbyPanel.setLayout(null);

        JLabel label2 = new JLabel();
        label2.setSize(new Dimension(150, 400));
        label2.setLocation(10,10);
        label2.setVerticalAlignment(JLabel.TOP);
        playerLobbyPanel.add(label2);

        ActionListener actionListener = new MenuActionListener();

        button1.addActionListener(actionListener);
        button2.addActionListener(actionListener);
        button3.addActionListener(actionListener);
        //getContentPane().add(serverLobbyPanel);
        getContentPane().add(mainPanel);
    }

    public class MenuActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Host")) {
                System.out.println("Host");
                NetworkManager.isServer = true;
                game.start();

                getContentPane().remove(mainPanel);
                getContentPane().add(serverLobbyPanel);
                getContentPane().revalidate();
                getContentPane().repaint();
                return;
            }
            if (e.getActionCommand().equals("Connect")) {
                System.out.println("Connect");
                NetworkManager.isServer = false;
                game.start();

                getContentPane().remove(mainPanel);
                getContentPane().add(playerLobbyPanel);
                getContentPane().revalidate();
                getContentPane().repaint();
                return;
            }
            if (e.getActionCommand().equals("Start")) {
                System.out.println("Start");
                getContentPane().removeAll();
                getContentPane().revalidate();
                getContentPane().repaint();
                NetworkManager.closeLobby();
                NetworkManager.send("startGame");
                NetworkManager.flush();
                //game.start();
            }
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Menu frame = new Menu();
            }
        });
    }
}