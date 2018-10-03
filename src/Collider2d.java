public class Collider2d extends Component {
    public RigidBody2d attachedRigidBody;

    public void start() {
        Physics2d.colliders.add(this);
        attachedRigidBody = getComponentInParent(attachedRigidBody.getClass());
    }
    public void destroy() {
        Physics2d.colliders.remove(this);
    }
}
