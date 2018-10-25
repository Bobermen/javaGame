package Physics2d.Colliders2d.Polygon;

import Physics2d.Colliders2d.Collider2d;
import Physics2d.Colliders2d.Square.DynamicSquare;
import game.Component;
import game.RigidBody2d;
import linAlg.Vector2.Vector2;

import java.util.ArrayList;

public class StaticPolygon extends Polygon {
    StaticPolygon() { points = new ArrayList<>(); }

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
