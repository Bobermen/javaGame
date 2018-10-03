package game;

public class RigidBody2d extends Component {

    public double mass = 1;
    public Vector2 centerOfMass = Vector2.ZERO.clone();
    public Vector2 worldCenterOfMass = Vector2.ZERO.clone();
    public Vector2 velocity = Vector2.ZERO.clone();

    private double angularVelocity = 0;
    private double angularAcceleration = 0;
    private Vector2 resultForce = Vector2.ZERO.clone();
    private Vector2 acceleration = Vector2.ZERO.clone();

    public RigidBody2d() {

    }
    @Override
    public void update() {
        //System.out.println(worldCenterOfMass = gameObject.transform.transformPosition(centerOfMass));
        //System.out.println(gameObject.transform.position);
        acceleration = resultForce.div(mass);
        gameObject.transform.position.iadd(velocity.mul(Time.detlaTime)
                .add(acceleration.mul(Time.detlaTime * Time.detlaTime / 2)));
        velocity.iadd(acceleration.mul(Time.detlaTime));
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
        //System.out.println( Math.signum(-iForce.x * rVector.y + rVector.x * iForce.y));
        //System.out.println(iForce.magnitude());
        //System.out.println(rVector);

        angularAcceleration= Math.signum(rVector.cross(iForce));
        //System.out.println(angularAcceleration);
        gameObject.transform.rotation += angularVelocity * Time.detlaTime
                + angularAcceleration * Time.detlaTime * Time.detlaTime / 2 ;
        angularVelocity += angularAcceleration * Time.detlaTime;
    }

}
