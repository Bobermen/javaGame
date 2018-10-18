package Physics2d;

import Physics2d.Colliders2d.Collider2d;
import linAlg.Vector2.Vector2;

public class RayCastHit {

    public Vector2 hitPoint;
    public Collider2d collider;
    public double angle;

    public RayCastHit(Vector2 hitPoint, Collider2d collider, double angle) {
        this.hitPoint = hitPoint;
        this.collider = collider;
        this.angle = angle;
    }
}
