package Physics2d.Colliders2d.Polygon;

import Physics2d.Colliders2d.Collider2d;
import game.RigidBody2d;

import java.util.ArrayList;

public class StaticPolygon extends Polygon {
    StaticPolygon() { points = new ArrayList<>(); }

    @Override
    public boolean isStatic() {
        return true;
    }

    @Override
    public RigidBody2d getAttachedRigitBody() {
        return null;
    }
}
