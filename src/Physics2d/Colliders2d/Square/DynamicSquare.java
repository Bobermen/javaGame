package Physics2d.Colliders2d.Square;

import game.RigidBody2d;

public class DynamicSquare extends StaticSquare
{
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