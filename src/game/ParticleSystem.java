package game;

import javax.swing.*;

public class ParticleSystem extends Component {

    public double lifeTime = 10;
    public double time;
    public double delay = 0.2;
    GameObject particle;

    @Override
    public void start() {
        particle = new GameObject();
        particle.addComponent(new Particle()).lifeTime = lifeTime;
        particle.addComponent(new Sprite(new ImageIcon("Resources/Smoke.png").getImage()));
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
