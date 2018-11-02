package Physics2d.Colliders2d.Polygon;

import game.Component;
import game.RigidBody2d;

import java.util.ArrayList;

public class StaticPolygon extends Polygon {

    @Override
    public Component clone() {
        return Polygon.getStatic().setPolygon(this);
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
