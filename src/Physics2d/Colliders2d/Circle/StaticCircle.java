package Physics2d.Colliders2d.Circle;

import game.RigidBody2d;

public class StaticCircle extends Circle {
    @Override
    public boolean isStatic() {
        return true;
    }

    @Override
    public RigidBody2d getAttachedRigitBody() {
        return null;
    }
}
