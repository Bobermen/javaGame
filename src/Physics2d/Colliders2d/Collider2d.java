package Physics2d.Colliders2d;


import Physics2d.Physics2d;
import game.Component;
import game.RigidBody2d;

public abstract class Collider2d extends Component
{

    public void start() {
        Physics2d.colliders.add(this);
        //attachedRigidBody = getComponentInParent(attachedRigidBody.getClass());
    }

    public void destroy() {
        Physics2d.colliders.remove(this);
    }

    public abstract RigidBody2d getAttachedRigitBody();
    // public abstract boolean isCollision(Collider2d collider);
}
