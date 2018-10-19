package game;

import linAlg.Matrix3x3;
import linAlg.Vector2.Vector2;

public class RigidBody2d extends Component {

    public double mass = 1;
    public Vector2 centerOfMass = Vector2.ZERO.clone();
    public Vector2 worldCenterOfMass = Vector2.ZERO.clone();
    public Vector2 velocity = Vector2.ZERO.clone();
    public Vector2 acceleration = Vector2.ZERO.clone();
    public double angularVelocity = 0;
    public double angularAcceleration = 0;

    private Vector2 resultForce = Vector2.ZERO.clone();

    public RigidBody2d() {

    }
    @Override
    public void update() {
        worldCenterOfMass = transform.transformPosition(centerOfMass);
        //System.out.println(gameObject.transform.position);
        acceleration = resultForce.div(mass);
        transform.setPosition(transform.getPosition().add(velocity.mul(Time.detlaTime)
                .add(acceleration.mul(Time.detlaTime * Time.detlaTime / 2))));
        velocity = velocity.add(acceleration.mul(Time.detlaTime));
        resultForce = acceleration = Vector2.ZERO;

        //System.out.println(velocity.x);
    }
    public void addForce(Vector2 force) {
        resultForce = resultForce.add(force);
    }
    public void addForceAtPosition(Vector2 force, Vector2 position) {
        Vector2 rVector = worldCenterOfMass.sub(position);
        Vector2 rForce = rVector.normalized().mul(rVector.normalized().dot(force));
        Vector2 iForce = force.sub(rForce);
        addForce(rForce);

        angularAcceleration = Math.toDegrees(3 * Math.signum(iForce.cross(rVector)) * iForce.magnitude() * rVector.magnitude()
                / mass / 2 / 10225);
        transform.setRotation(transform.getRotation() + angularVelocity * Time.detlaTime
                + angularAcceleration * Time.detlaTime * Time.detlaTime / 2 );
        angularVelocity += angularAcceleration * Time.detlaTime;
    }
    public Vector2 getRelativePointVelocity(Vector2 point) {
        //System.out.println(point);
        //System.out.println("AngularVelocity = " + angularVelocity);
        //System.out.println(velocity);
        return velocity.add(transform.transformDirection(
                Matrix3x3.Rotate(90).dot(point).mul(Math.toRadians(angularVelocity))));
    }
}
