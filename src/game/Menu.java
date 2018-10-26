package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {

    public static Game game;

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

        JPanel panel = (JPanel) getContentPane();
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

        ActionListener actionListener = new MenuActionListener();

        button1.addActionListener(actionListener);
        button2.addActionListener(actionListener);

        panel.setSize(400, 400);
    }

    public class MenuActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Host")) {
                System.out.println("Host");
                getContentPane().removeAll();
                getContentPane().revalidate();
                //frame.getContentPane().repaint();
                Game.isServer = true;
                game.start();
                return;
            }
            if (e.getActionCommand().equals("Connect")) {
                System.out.println("Connect");
                return;
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