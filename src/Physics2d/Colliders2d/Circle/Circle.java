package Physics2d.Colliders2d.Circle;

import Physics2d.Colliders2d.Collider2d;
import Physics2d.Colliders2d.Pair;
import Physics2d.Colliders2d.Square.Square;
import linAlg.Vector2.Vector2;

import java.util.Collections;
import java.util.List;

public abstract class Circle extends Collider2d {

    public void setRadius(double rad) {
        radius = rad;
    }

    public void setCenter(Vector2 vec) {
        center = vec.clone();
    }

    @Override
    public Pair<Vector2, Double> isCollision(Collider2d collider) {
        if (collider instanceof Square)
            return isCollisionSquare((Square)collider);
        if (collider instanceof Circle)
            return isCollisionCircle((Circle)collider);
        return collider.isCollision(this);
    }

    public Pair<Vector2, Double> isIntersecting(Vector2 p1, Vector2 p2) {
        double sqrR = radius * radius;
        if ((p1.sub(GlobalCenter).sqrMagnitude() > sqrR) && (p2.sub(GlobalCenter).sqrMagnitude() > sqrR))
            return null;
        // here comes the optimisation, so it can be unreadable
        Vector2 p1SubC = p1.sub(GlobalCenter);
        Vector2 p2Subp1 = p2.sub(p1);
        if ((p1SubC.magnitude() > radius + p2Subp1.magnitude()) || (GlobalCenter.sub(p2).magnitude() > radius + p2Subp1.magnitude()))
            return null;
        double a = p2Subp1.sqrMagnitude();
        double b = p1SubC.dot(p2Subp1);
        double c = p1SubC.sqrMagnitude() - radius * radius;
        double D = b * b - a * c;
        if (D < 0)
            return null;
        D = Math.sqrt(D);
        double bDivA = b / a;
        double x1 = bDivA - D;
        double x2 = bDivA + D;
        if ((x1 > 1) || (x2 < 0))
            return null;
        Vector2 res = null;
        if (x1 > 0)
            res = p2Subp1.mul(x1).iadd(p1);
        if (x2 < 1)
            res = p2Subp1.mul(x2).iadd(p1);
        if (res == null)
            return null;
        return Pair.makePair(res, getAngle(GlobalCenter.sub(res), p2Subp1));
    }

    @Override
    public boolean isIn(Vector2 point) {
        return GlobalCenter.sub(point).sqrMagnitude() < radius * radius;
    }

    public static StaticCircle getStatic() {
        return new StaticCircle();
    }

    public static DynamicCircle getDynamic() {
        return new DynamicCircle();
    }

    private Pair<Vector2, Double> isCollisionCircle(Circle a) {
        if (GlobalCenter.sub(a.GlobalCenter).sqrMagnitude() < radius * radius + a.radius * a.radius + 2 * radius * a.radius)
            return Pair.makePair(a.GlobalCenter.sub(GlobalCenter).idiv(2).iadd(GlobalCenter), (double)0);
        return null;
    }

    private Pair<Vector2, Double> isCollisionSquare(Square a) {
        List<Vector2> points = a.getPoints();
        if (points.stream().anyMatch(point -> point.sub(GlobalCenter).sqrMagnitude() > radius * radius))
            return null;
        Vector2 min = Collections.min(points,
                (p1, p2) -> (int)(p1.sub(GlobalCenter).sqrMagnitude() - p2.sub(GlobalCenter).sqrMagnitude()));
        int index = points.indexOf(min);
        Pair<Vector2, Double> res;
        if (index == 0) {
            res = isIntersecting(points.get(0), points.get(1));
            if (res != null)
                return res;
            res = isIntersecting(points.get(3), points.get(0));
            if (res != null)
                return res;
            return null;
        }
        if (index == 3) {
            res = isIntersecting(points.get(2), points.get(3));
            if (res != null)
                return res;
            res = isIntersecting(points.get(3), points.get(0));
            if (res != null)
                return res;
            return null;
        }
        res = isIntersecting(points.get(index), points.get(index + 1));
        if (res != null)
            return res;
        return isIntersecting(points.get(index), points.get(index - 1));
    }
}
