package game;

import javax.swing.*;

public class ParticleSystem extends Component {

    public double lifeTime = 10;
    public double time;
    public double delay = 0.2;
    @Override
    public void update() {
        time += Time.detlaTime;
        if (time > delay) {
            time = 0;
            GameObject particle = new GameObject();
            particle.addComponent(new Particle()).lifeTime = lifeTime;
            particle.transform.setLocalPosition(transform.getPosition().sub(transform.getRight().mul(21)));
            particle.addComponent(new Sprite(new ImageIcon("Resources/Smoke.png").getImage()));
            particle.transform.setLocalScale(0.02);
            instantiate(particle);
        }
    }

    public Component clone() {
        ParticleSystem res = new ParticleSystem();
        res.time = time;
        return res;
    }
}
