package game;

import linAlg.Matrix3x3;
import linAlg.Vector2.Vector2;
import java.awt.event.KeyEvent;

public class PlayerControl extends Component {

    public double power = 1020000;
    public double maxVelocity = 16;
    public double radius = 720;
    public double turnaroundTime = 180;

    private double dragK;
    private double maxAngularVelocity;
    private double lateralResistance = 9340000;
    private RigidBody2d rigidBody2d;
    private int speed = 0; // from -3 to 5
    private int helm = 0; // from -2 to 2
    private Vector2 helmPosition = Vector2.getVector2(-100, 0);
    private Vector2 worldHelmPosition;

    public void start() {
        dragK = power*5/maxVelocity/maxVelocity;
        maxAngularVelocity = 360 / turnaroundTime;
        rigidBody2d = gameObject.getComponent(RigidBody2d.class);
    }

    public void update() {
        worldHelmPosition = transform.transformPosition(helmPosition);
        getKeys();
        applyForces();
    }
    private void getKeys() {
        if (Input.isKeyReleased(KeyEvent.VK_W) && speed < 5) {
            speed ++;
            System.out.println("Speed = " + speed);
        }
        if (Input.isKeyReleased(KeyEvent.VK_S) && speed > -3) {
            speed --;
            System.out.println("Speed = " + speed);
        }
        if (Input.isKeyReleased(KeyEvent.VK_A) && helm > -2) {
            helm --;
            System.out.println("Helm = " + helm);
        }
        if (Input.isKeyReleased(KeyEvent.VK_D) && helm < 2) {
            helm ++;
            System.out.println("Helm = " + helm);
        }
    }
    private void applyForces() {
        if (rigidBody2d != null) {
            Vector2 sideVelocity = transform.getUp().mul(rigidBody2d.velocity.dot(transform.getUp()));
            Vector2 helmNormal = Matrix3x3.Rotate(helm * 15).dot(transform.getUp());
            Vector2 helmVelocity = helmNormal.mul(rigidBody2d.getRelativePointVelocity(helmPosition).negative()
                    .dot(helmNormal));
            Vector2 pForce = helmVelocity.mul(helmVelocity.magnitude() * 100000);
            double angularK = pForce.dot(transform.getUp()) / maxAngularVelocity / maxAngularVelocity;
            pForce = pForce.sub(transform.getUp()
                    .mul(angularK * rigidBody2d.angularVelocity * rigidBody2d.angularVelocity));
            //System.out.println("pForce = " + transform.inverseTransformDirection(pForce));
            //System.out.println("RelativeVelocity = " + transform.inverseTransformDirection(rigidBody2d.getRelativePointVelocity(helmPosition)));
            rigidBody2d.addForceAtPosition(pForce, worldHelmPosition);
            //System.out.println("HelmVelocity = " + helmVelocity);
            //System.out.println("RelativePointVelocity = " + rigidBody2d.getRelativePointVelocity(Vector2.getVector2(-100, 0)));
            rigidBody2d.addForce(transform.getRight().mul(power * speed));
            rigidBody2d.addForce(rigidBody2d.velocity.negative().mul(rigidBody2d.velocity.magnitude() * dragK));
            rigidBody2d.addForce(sideVelocity.negative().mul(sideVelocity.magnitude() * lateralResistance));

            //System.out.println("1) " + transform.getPosition());
            //System.out.println(worldHelmPosition);
            //System.out.println(transform.inverseTransformPosition(worldHelmPosition));
            //System.out.println("Velocity = " + rigidBody2d.velocity.magnitude());
            //System.out.println("AngularVelocity = "  + rigidBody2d.angularVelocity);
            //System.out.println("AngularAcceleration = " + rigidBody2d.angularAcceleration);
        }
    }

    public Component clone() {
        return new PlayerControl();
    }
}

