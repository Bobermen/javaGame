package Physics2d.Colliders2d.Square;

import game.Component;
import game.RigidBody2d;

public class StaticSquare extends Square
{
    @Override
    public Component clone() {
        return Square.getStatic().squareSet(this);
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
