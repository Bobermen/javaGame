package game;

import java.awt.*;

public class Sprite extends Component {
    public Image image;
    public Vector2 pivot;

    private Sprite() {}

    public Sprite(Image image) {
        this.image = image;
        pivot = new Vector2(image.getWidth(null)/2, image.getHeight(null)/2);
        //pivot = new game.Vector2(image.getWidth(null)/2, 0);
    }

    public void start() {
        Renderer.sprites.add(this);
    }

    public void update() {

    }

    public void destroy() {
        Renderer.sprites.remove(this);
    }
}
