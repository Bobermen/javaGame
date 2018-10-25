package game;

import linAlg.Vector2.Vector2;

import java.awt.*;

public class Sprite extends Component {
    public Image image;
    public Vector2 pivot;
    public float alpha = 1;

    private Sprite() {}

    public Sprite(Image image) {
        this.image = image;
        pivot = Vector2.getVector2(image.getWidth(null)/2, image.getHeight(null)/2);
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

    @Override
    public Component clone() {
        Sprite res = new Sprite();
        res.image = image;
        res.pivot = pivot.clone();
        res.alpha = alpha;
        return res;
    }
}
