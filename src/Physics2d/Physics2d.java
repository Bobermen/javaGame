package Physics2d;

import Physics2d.Colliders2d.Collider2d;
import game.RigidBody2d;
import game.Vector2;

import java.util.ArrayList;

public class Physics2d {
    public static ArrayList<Collider2d> colliders = new ArrayList<Collider2d>();

    public static Collider2d rayCast(Vector2 position, Vector2 ray) {
        return null;
    }
    public static Collider2d rayCast(Vector2 position, Vector2 direction, double length) {
        return null;
    }
    public static Collider2d rayCast(Vector2 position, Vector2 ray, RigidBody2d attachedRigidBody) {
        return null;
    }
    public static Collider2d rayCast(Vector2 position, Vector2 direction, double length, RigidBody2d attachedRigidBody) {
        return null;
    }
}
