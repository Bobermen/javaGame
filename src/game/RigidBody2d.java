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

    private double previousY = Double.POSITIVE_INFINITY;

    //private Vector2 resultForce = Vector2.ZERO.clone();

    public RigidBody2d() {

    }
    @Override
    public void update() {
        worldCenterOfMass = transform.transformPosition(centerOfMass);
        //translate
        transform.setPosition(transform.getPosition().add(velocity.mul(Time.detlaTime)
                .add(acceleration.mul(Time.detlaTime * Time.detlaTime / 2))));
        velocity = velocity.add(acceleration.mul(Time.detlaTime));
        //rotate
        transform.setRotation(transform.getRotation() + angularVelocity * Time.detlaTime
                + angularAcceleration * Time.detlaTime * Time.detlaTime / 2 );
        angularVelocity += angularAcceleration * Time.detlaTime;
        //update
        acceleration = Vector2.ZERO;
        angularAcceleration = 0;
    }
    public void addForce(Vector2 force) {
        acceleration = acceleration.add(force.div(mass));
    }
    public void addForceAtPosition(Vector2 force, Vector2 position) {
        Vector2 rVector = worldCenterOfMass.sub(position);
        Vector2 rForce = rVector.normalized().mul(rVector.normalized().dot(force));
        Vector2 iForce = force.sub(rForce);
        addForce(rForce);

        //System.out.println("AngularAcceleration = " + angularAcceleration);
        angularAcceleration += Math.toDegrees(3 * Math.signum(iForce.cross(rVector)) * iForce.magnitude() * rVector.magnitude()
                / mass / 2 / 10225);

        if (previousY > transform.transformDirection(force).y) {
            previousY = transform.transformDirection(force).y;
        }
        else {
            //System.out.println("worldCenter = " + worldCenterOfMass);
            //System.out.println("rVector = " + rVector);
            //System.out.println("rVectorMag = " + rVector.magnitude());
            //System.out.println("rForce = " + rForce);
            //System.out.println("rForceMag = " + rForce.magnitude());
            //System.out.println("iForce = " + iForce);
            //System.out.println("iForceMag = " + iForce.magnitude());
            //System.out.println("AngularVelocity = " + angularVelocity);
            //System.out.println("AngularAcceleration = " + angularAcceleration);
        }
    }
    public Vector2 getRelativePointVelocity(Vector2 point) {
        //System.out.println(point);
        //System.out.println("AngularVelocity = " + angularVelocity);
        //System.out.println(velocity);
        return velocity.add(transform.transformDirection(
                Matrix3x3.Rotate(90).dot(point).mul(Math.toRadians(angularVelocity))));
    }

    @Override
    public Component clone() {
        RigidBody2d res = new RigidBody2d();
        res.acceleration = acceleration.clone();
        res.angularAcceleration = angularAcceleration;
        res.angularVelocity = angularVelocity;
        res.centerOfMass = centerOfMass.clone();
        res.worldCenterOfMass = worldCenterOfMass.clone();
        res.mass = mass;
        res.previousY = previousY;
        res.velocity = velocity.clone();
        return res;
    }
}
