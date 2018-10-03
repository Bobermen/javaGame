import java.awt.event.KeyEvent;

public class PlayerControl extends Component {

    public double power = 1020000;
    public double maxVelocity = 16;
    public double radius = 720;

    private double dragK;
    private double angularK = 0;
    private RigidBody2d rigidBody2d;
    private int speed = 0; // from -3 to 5
    private int helm = 0; // from -2 to 2

    public void start() {
        dragK = power*5/maxVelocity/maxVelocity;
        rigidBody2d = gameObject.getComponent(RigidBody2d.class);
    }

    public void update() {
        getKeys();
        applyForces();
    }
    private void getKeys() {
        if (Input.isKey(KeyEvent.VK_W) && speed < 5) {
            speed ++;
        }
        if (Input.isKey(KeyEvent.VK_S) && speed > -3) {
            speed --;
        }
        if (Input.isKey(KeyEvent.VK_A) && helm > -2) {
            helm --;
        }
        if (Input.isKey(KeyEvent.VK_D) && helm < 2) {
            helm ++;
        }
    }
    private void applyForces() {
        if (rigidBody2d != null) {
            Vector2 sideVelocity = rigidBody2d.velocity
                    .sub(rigidBody2d.velocity.mul(rigidBody2d.velocity.dot(gameObject.transform.getRIGHT())));
            rigidBody2d.addForce(sideVelocity.negative().mul(sideVelocity.magnitude() * angularK));
            rigidBody2d.addForce(gameObject.transform.getRIGHT().mul(power * speed));
            rigidBody2d.addForce(rigidBody2d.velocity.negative()
                    .mul(rigidBody2d.velocity.magnitude() * dragK));
            System.out.println(rigidBody2d.velocity.magnitude());
            //System.out.println(gameObject.transform.getRIGHT().x + " " + gameObject.transform.getRIGHT().y);
        }
    }

}

