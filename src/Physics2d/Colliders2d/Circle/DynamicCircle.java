package Physics2d.Colliders2d.Circle;

import game.RigidBody2d;

public class DynamicCircle extends StaticCircle {
    private RigidBody2d attachedRigidBody;

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
