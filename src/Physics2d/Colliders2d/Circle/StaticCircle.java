package Physics2d.Colliders2d.Circle;

import game.Component;
import game.RigidBody2d;

public class StaticCircle extends Circle {

    @Override
    public Component clone() {
        return Circle.getStatic().colliderSet(this);
    }

    @Override
    public boolean isStatic() {
        return true;
    }

    @Override
    public RigidBody2d getAttachedRigitBody() {
        return null;
    }
}
