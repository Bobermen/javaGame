import javax.swing.JFrame;

public class Display {


    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        Game game = new Game(frame);
        game.start();
    }

}