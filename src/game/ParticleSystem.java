package game;

import javax.swing.*;
import java.awt.*;

public class ParticleSystem extends Component {

    public double lifeTime = 10;
    public double time;
    public double delay = 0.2;
    GameObject particle;
    private static final Image smoke = new ImageIcon("Resources/Smoke.png").getImage();

    @Override
    public void start() {
        particle = new GameObject();
        particle.addComponent(new Particle()).lifeTime = lifeTime;
        particle.addComponent(new Sprite(smoke));
        particle.transform.setLocalScale(0.02);
    }
    @Override
    public void update() {
        time += Time.detlaTime;
        if (time > delay) {
            time = 0;
            particle.transform.setLocalPosition(transform.getPosition().sub(transform.getRight().mul(21)));
            Game.instantiate(particle);
        }
    }

    public Component clone() {
        ParticleSystem res = new ParticleSystem();
        res.time = time;
        return res;
    }
}
