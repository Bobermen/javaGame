package Physics2d.Colliders2d.Polygon;

import Physics2d.Colliders2d.Collider2d;
import game.Pair;
import linAlg.Vector2.Vector2;

import java.util.*;

public abstract class Polygon extends Collider2d implements Iterable<Pair<Vector2, Vector2>> {
    private ArrayList<Vector2> globalPoints = new ArrayList<>();
    private ArrayList<Vector2> points = new ArrayList<>();
    public static StaticPolygon getStatic() {
        return new StaticPolygon();
    }

    public static DynamicPolygon getDynamic() {
        return new DynamicPolygon();
    }

    protected Polygon setPolygon(Polygon b) {
        colliderSet(b);
        points.clear();
        points.addAll(b.points);
        return this;
    }

    @Override
    public void update() {
        globalPoints.clear();
        points.stream().map(transform::transformPosition).forEach(globalPoints::add);
    }

    @Override
    public void start() {
        super.start();
        initCenter();
        initRadius();
    }

    private void initCenter() {
        center = points.stream().reduce(Vector2.ZERO.clone(), Vector2::iadd).idiv(points.size());
    }

    private void initRadius() {
        radius = Collections.max(points, Comparator.comparing(item -> item.sub(center).sqrMagnitude())).sub(center).magnitude();
    }

    public Polygon addPoint(Vector2 point) {
        points.add(point);
        initCenter();
        initRadius();
        return this;
    }

    public Polygon addPoints(Iterable<Vector2> points) {
        points.forEach(this.points::add);
        initCenter();
        initRadius();
        return this;
    }

    @Override
    public Pair<Vector2, Double> isIntersecting(Vector2 p1, Vector2 p2) {
        if (canNotIntersect(p1, p2))
            return null;
        Pair<Vector2, Double> res;
        for (var pair : this) {
            res = Collider2d.isIntersecting(pair.first, pair.second, p1, p2);
            if (res != null)
                return res;
        }
        return null;
    }

    @Override
    public Pair<Vector2, Double> isCollision(Collider2d a) {
        if (canNotIntersect(a))
            return null;
        Pair<Vector2, Double> res;
        for (var pair : this) {
            res = a.isIntersecting(pair.first, pair.second);
            if (res != null)
                return res;
        }
        return null;
    }

    @Override
    public boolean isIn(Vector2 point) {
        if (!points.get(0).equals(points.get(points.size() - 1)))
            return false;
        for (var pair : this)
            if (pair.second.sub(pair.first).cross(point.sub(pair.first)) > 0)
                return false;
        return true;
    }

    private class PolygonIterator implements Iterator<Pair<Vector2, Vector2>> {
        private Iterator<Vector2> iterator;
        private Vector2 prev;
        private PolygonIterator() {
            iterator = globalPoints.iterator();
            prev = iterator.next();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Pair<Vector2, Vector2> next() {
            Vector2 temp = prev;
            prev = iterator.next();
            return Pair.makePair(temp, prev);
        }
    }

    public Iterator<Pair<Vector2, Vector2>> iterator() {
        return new PolygonIterator();
    }

}
