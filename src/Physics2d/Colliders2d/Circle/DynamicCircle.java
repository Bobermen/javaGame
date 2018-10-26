package Physics2d.Colliders2d.Circle;

import game.Component;
import game.RigidBody2d;

public class DynamicCircle extends StaticCircle {
    private RigidBody2d attachedRigidBody;

    @Override
    public Component clone() {
        return Circle.getDynamic().colliderSet(this);
    }

    @Override
    public void start() {
        super.start();
        attachedRigidBody = getComponentInParent(attachedRigidBody.getClass());
    }

    public boolean isStatic() {
        return false;
    }

    @Override
    public RigidBody2d getAttachedRigitBody() {
        return attachedRigidBody;
    }
}
