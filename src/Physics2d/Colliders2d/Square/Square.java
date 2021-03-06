package Physics2d.Colliders2d.Square;

import Physics2d.Colliders2d.Collider2d;
import game.Pair;
import linAlg.Vector2.Vector2;

import java.util.*;

public abstract class Square extends Collider2d
{
    public Vector2 d1;
    public Vector2 d2;
    private Vector2 globalD1;
    private Vector2 globalD2;

    @Override
    public void start() {
        super.start();
        List<Vector2> points = getPoints();
        center = points.stream().reduce(Vector2.ZERO.clone(), Vector2::iadd).idiv(points.size());
        radius = Collections.max(points, Comparator.comparing(item -> item.sub(center).sqrMagnitude())).sub(center).magnitude();
    }

    protected Square squareSet(Square b) {
        colliderSet(b);
        d1 = b.d1;
        d2 = b.d2;
        return this;
    }

    @Override
    public void update() {
        super.update();
        globalD1 = transform.transformPosition(d1);
        globalD2 = transform.transformPosition(d2);
    }

    @Override
    public Pair<Vector2, Double> isCollision(Collider2d collider) {
        return collider.isCollision(this);
    }

    public static StaticSquare getStatic() {
        return new StaticSquare();
    }

    public static DynamicSquare getDynamic() {
        return new DynamicSquare();
    }

    public List<Vector2> getPoints() {
        List<Vector2> result = new ArrayList<>();
        result.add(globalD1);
        Vector2 dSub = globalD2.sub(globalD1).idiv(2);
        Vector2 perp = Vector2.getVector2(-dSub.y, +dSub.x);
        dSub.iadd(globalD1);
        result.add(dSub.add(perp));
        result.add(globalD2);
        result.add(dSub.sub(perp));
        return result;
    }

    public Pair<Vector2, Double> isIntersecting(Vector2 p1, Vector2 p2) {
        if (canNotIntersect(p1, p2))
            return null;
        List<Vector2> points = getPoints();
        Iterator<Vector2> firstIterator = points.iterator();
        Iterator<Vector2> secondIterator = points.iterator();
        secondIterator.next();
        Pair<Vector2, Double> res;
        while (secondIterator.hasNext()) {
            res = Collider2d.isIntersecting(firstIterator.next(), secondIterator.next(), p1, p2);
            if (res != null)
                return res;
        }
        return Collider2d.isIntersecting(points.get(0), points.get(points.size() - 1), p1, p2);
    }

    public boolean isIn(Vector2 point) {
        List<Vector2> points = getPoints();
        return ((points.get(1).sub(points.get(0)).cross(point.sub(points.get(0))) <= 0) &&
                (points.get(2).sub(points.get(1)).cross(point.sub(points.get(1))) <= 0) &&
                (points.get(3).sub(points.get(2)).cross(point.sub(points.get(2))) <= 0) &&
                (points.get(0).sub(points.get(3)).cross(point.sub(points.get(3))) <= 0));
    }
}
