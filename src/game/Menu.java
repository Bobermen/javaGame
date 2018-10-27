package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {

    public static Game game;
    public static JPanel panel;
    public static JPanel panel2;

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

        panel = new JPanel();
        panel.setLayout(null);

        JButton button1 = new JButton("Host");
        button1.setActionCommand("Host");
        button1.setSize(100, 20);
        button1.setLocation(10, 50);
        panel.add(button1);

        JButton button2 = new JButton("Connect");
        button2.setActionCommand("Connect");
        button2.setSize(100, 20);
        button2.setLocation(120, 50);
        panel.add(button2);


        JTextField textField1 = new JTextField();
        textField1.setText("localhost");
        textField1.setSize(new Dimension(150, 20));
        textField1.setLocation(10,10);
        panel.add(textField1);

        JTextField textField2 = new JTextField();
        textField2.setText("22222");
        textField2.setSize(new Dimension(50, 20));
        textField2.setLocation(170,10);
        panel.add(textField2);

        //panel.setSize(400, 400);
        panel2 = new JPanel();
        panel2.setLayout(null);

        JButton button3 = new JButton("Start");
        button3.setActionCommand("Start");
        button3.setSize(100, 20);
        button3.setLocation(170, 10);
        panel2.add(button3);

        JLabel label1 = new JLabel();
        label1.setText("Player 0");
        label1.setSize(new Dimension(150, 400));
        label1.setLocation(10,10);
        label1.setVerticalAlignment(JLabel.TOP);
        panel2.add(label1);

        ActionListener actionListener = new MenuActionListener();

        button1.addActionListener(actionListener);
        button2.addActionListener(actionListener);
        button3.addActionListener(actionListener);
        //getContentPane().add(panel2);
        getContentPane().add(panel);
    }

    public class MenuActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Host") || e.getActionCommand().equals("Connect")) {
                getContentPane().remove(panel);
                getContentPane().add(panel2);
                getContentPane().revalidate();
                getContentPane().repaint();
                if (e.getActionCommand().equals("Host")) {
                    System.out.println("Host");
                    return;
                }
                if (e.getActionCommand().equals("Connect")) {
                    System.out.println("Connect");

                    return;
                }
            }
            if (e.getActionCommand().equals("Start")) {
                System.out.println("Start");
                getContentPane().removeAll();
                getContentPane().revalidate();
                getContentPane().repaint();
                Game.isServer = true;
                game.start();
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