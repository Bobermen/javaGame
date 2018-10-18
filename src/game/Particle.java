package game;

import linAlg.Vector2.Vector2;

import java.util.Random;

public class Particle extends Component {

    public double time;
    public double lifeTime;
    public double rot;
    public Vector2 vel;

    private Sprite sprite;

    @Override
    public void start() {
        Random r = new Random();
        rot = r.nextDouble() * 60 - 30;
        vel = Vector2.getVector2(r.nextDouble()*2 - 1, r.nextDouble()*2 - 1);
        sprite = getComponent(Sprite.class);
        transform.setLocalRotation(r.nextDouble()*360);
    }

    @Override
    public void update() {
        time += Time.detlaTime;
        transform.setLocalRotation(transform.getLocalRotation() + rot*Time.detlaTime);
        transform.setLocalPosition(transform.getLocalPosition().add(vel.mul(Time.detlaTime)));
        sprite.alpha -= Time.detlaTime / lifeTime;
        if (sprite.alpha < 0) {
            sprite.alpha = 0;
        }
        transform.setLocalScale(transform.getScale() + 0.01 * Time.detlaTime);
        if (time > lifeTime) {
            gameObject.destroy();
        }
    }
}
