package Physics2d.Colliders2d;


import Physics2d.Physics2d;
import game.Component;
import game.Pair;
import game.RigidBody2d;
import linAlg.Vector2.Vector2;
// TODO глобальные координаты
// layer выставляется пуками
public abstract class Collider2d extends Component
{
    public int layer;
    protected double radius;
    protected Vector2 GlobalCenter;
    protected Vector2 center;

    @Override
    public void start() {
        Physics2d.colliders.get(layer).add(this);
    }

    protected Collider2d colliderSet(Collider2d b) {
        layer = b.layer;
        radius = b.radius;
        center = b.center.clone();
        return this;
    }

    @Override
    public void update() {
        GlobalCenter = transform.transformPosition(center);
    }

    public void destroy() {
        Physics2d.colliders.get(layer).remove(this);
    }

    public abstract RigidBody2d getAttachedRigitBody();
    public abstract Pair<Vector2, Double> isCollision(Collider2d collider);
    public abstract boolean isStatic();
    public abstract Pair<Vector2, Double> isIntersecting(Vector2 p1, Vector2 p2);
    public abstract boolean isIn(Vector2 point);

    protected boolean canNotIntersect(Vector2 p1, Vector2 p2) {
        if ((p1.sub(GlobalCenter).sqrMagnitude() > radius * radius) && (p2.sub(GlobalCenter).sqrMagnitude() > radius * radius))
            return true;
        double distance = p1.sub(p2).magnitude() + radius;
        return GlobalCenter.sub(p1).sqrMagnitude() > distance * distance ||
                GlobalCenter.sub(p2).sqrMagnitude() > distance * distance;
    }

    protected boolean canNotIntersect(Collider2d collider) {
        double magn = radius + collider.radius;
        return (collider.GlobalCenter.sub(GlobalCenter).sqrMagnitude() > magn * magn);
    }

    public static Pair<Vector2, Double> isIntersecting(Vector2 a, Vector2 b, Vector2 c, Vector2 d) {
        Vector2 aSubb = a.sub(b);
        Vector2 cSubd = c.sub(d);
        // here comes checks if vectors are collinear, or have zero coordinates e.t.s.
        if ((aSubb.x == 0) && (cSubd.x == 0)) {
            if (b.x != d.x)
                return null;
            Double res = isIntersectingZero(a.y, b.y, c.y, d.y);
            if (res == null)
                return null;
            else
                return Pair.makePair(Vector2.getVector2(b.x, res), 90.);
        }
        if ((aSubb.y == 0) && (cSubd.y == 0)) {
            if (b.y != d.y)
                return null;
            Double res = isIntersectingZero(a.x, b.x, c.x, d.x);
            if (res == null)
                return null;
            else
                return Pair.makePair(Vector2.getVector2(b.y, res), 90.);
        }
        Vector2 div1 = aSubb.div(cSubd);
        if (div1.x == div1.y) {
            Vector2 div2 = a.sub(c).idiv(cSubd);
            if (div2.x != div2.y)
                return null;
            Double res = isIntersectingZero(a.x, b.x, c.x, d.x);
            if (res == null)
                return null;
            if (res == c.x)
                return Pair.makePair(c, 90.);
            else
                return Pair.makePair(d, 90.);
        }
        double a1 = aSubb.y;
        double b1 = -aSubb.x;
        double minusc1 = a.cross(aSubb);
        double a2 = cSubd.y;
        double b2 = -cSubd.x;
        double minusc2 = c.cross(cSubd);
        // my equation: a1x + b1y = minusc1
        //              a2x + b2y = minusc2
        Vector2 res = Vector2.getVector2(0, (a1 * minusc2 - a2 * minusc1) / aSubb.cross(cSubd));
        if (a1 != 0)
            res.x = (minusc1 - b1 * res.y) / a1;
        else
            res.x = (minusc2 - b2 * res.y) / a2;
        if ((res.x >= Math.min(a.x, b.x)) &&
                (res.x <= Math.max(a.x, b.x)) &&
                (res.x >= Math.min(c.x, d.x)) &&
                (res.x <= Math.max(c.x, d.x)) &&
                (res.y >= Math.min(a.y, b.y)) &&
                (res.y <= Math.max(a.y, b.y)) &&
                (res.y >= Math.min(c.y, d.y)) &&
                (res.y <= Math.max(c.y, d.y)))
            return Pair.makePair(res, getAngle(b.sub(a), d.sub(c).leftPerp()));
        return null;
    }

    // checks intersection when one of coordinates are zero.
    // taking and returning non-zero coordinates
    private static Double isIntersectingZero(double a, double b, double c, double d) {
        if ((((a < c) && (c < b)) || ((a > c) && (c > b))) &&
                (((a < d) && (d < b)) || ((a > d) && (d > b)))) {
            if (Math.abs(a - c) > Math.abs(a - d))
                return c;
            else
                return d;
        }
        if (((a < c) && (c < b)) || ((a > c) && (c > b)))
            return c;
        if (((a < d) && (d < b)) || ((a > d) && (d > b)))
            return d;
        return null;
    }

    public static double getAngle(Vector2 a, Vector2 b) {
        return Math.toDegrees(Math.acos(Math.sqrt(a.mul(a).dot(b.mul(b)) / (a.sqrMagnitude() * b.sqrMagnitude()))));
    }
}
