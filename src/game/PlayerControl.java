package game;

import linAlg.Matrix3x3;
import linAlg.Vector2.Vector2;
import java.awt.event.KeyEvent;

public class PlayerControl extends Component {

    public double power = 1020000;
    public double maxVelocity = 16;
    public double radius = 720;

    private double dragK;
    private double angularK = 20000;
    private double lateralResistance = 9340000;
    private RigidBody2d rigidBody2d;
    private int speed = 0; // from -3 to 5
    private int helm = 0; // from -2 to 2
    private Vector2 helmPosition = Vector2.getVector2(-100, 0);
    private Vector2 worldHelmPosition;

    public void start() {
        dragK = power*5/maxVelocity/maxVelocity;
        rigidBody2d = gameObject.getComponent(RigidBody2d.class);
        worldHelmPosition = transform.transformPosition(helmPosition);
    }

    public void update() {
        getKeys();
        applyForces();
        helmPosition = transform.getRIGHT().negative().mul(100);
    }
    private void getKeys() {
        if (Input.isKey(KeyEvent.VK_W) && speed < 5) {
            speed ++;
            //System.out.println(speed);
        }
        if (Input.isKey(KeyEvent.VK_S) && speed > -3) {
            speed --;
            //System.out.println(speed);
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
            Vector2 sideVelocity = transform.getUP().mul(rigidBody2d.velocity.dot(transform.getUP()));
            Vector2 helmNormal = Matrix3x3.Rotate(helm * 15).dot(transform.getUP().negative());
            Vector2 helmVelocity = helmNormal.mul(rigidBody2d.getRelativePointVelocity(helmPosition).negative()
                    .dot(helmNormal));
            rigidBody2d.addForceAtPosition(helmVelocity.mul(helmVelocity.magnitude() * 10000)
                    , worldHelmPosition);
            //System.out.println("HelmVelocity = " + helmVelocity);
            //System.out.println("RelativePointVelocity = " + rigidBody2d.getRelativePointVelocity(Vector2.getVector2(-100, 0)));
            rigidBody2d.addForce(transform.getRIGHT().mul(power * speed));
            rigidBody2d.addForce(rigidBody2d.velocity.negative().mul(rigidBody2d.velocity.magnitude() * dragK));
            rigidBody2d.addForce(sideVelocity.negative().mul(sideVelocity.magnitude() * lateralResistance));

            //System.out.println("Velocity = " + rigidBody2d.velocity.magnitude());
        }
    }
}

