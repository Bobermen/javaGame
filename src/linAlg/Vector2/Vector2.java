package linAlg.Vector2;

public abstract class Vector2
{
    public double x = 0;
    public double y = 0;

    public static final Vector2 ONE = getUnmodifiebleVector2(1,1);
    public static final Vector2 RIGHT = getUnmodifiebleVector2(1,0);
    public static final Vector2 UP = getUnmodifiebleVector2(0,1);
    public static final Vector2 ZERO = getUnmodifiebleVector2(0,0);
    public static final Vector2 LEFT = getUnmodifiebleVector2(-1,0);
    public static final Vector2 BOTTOM = getUnmodifiebleVector2(0,-1);

    public abstract Vector2 add(final Vector2 b);
    public abstract Vector2 add(final double b);
    public abstract Vector2 sub(final Vector2 b);
    public abstract Vector2 sub(final double b);
    public abstract Vector2 mul(final Vector2 b);
    public abstract Vector2 mul(final double b);
    public abstract Vector2 div(final Vector2 b);
    public abstract Vector2 div(final double b);
    public abstract Vector2 negative();
    public abstract Vector2 clone();
    public abstract boolean isModifieble();
    public double dot(final Vector2 b) {
        return x * b.x + y * b.y;
    }

    public double cross(Vector2 b) {
        return x * b.y - y * b.x;
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public double sqrMagnitude() { return x * x + y * y; }

    public Vector2 normalized() {
        double m = this.magnitude();
        return Vector2.div(this, m);
    }

    public void normalize() {
        throw new UnsupportedOperationException();
    }

    public Vector2 iadd(final Vector2 b) { throw new UnsupportedOperationException(); }

    public Vector2 iadd(final double b) {
        throw new UnsupportedOperationException();
    }

    public Vector2 isub(final Vector2 b) {
        throw new UnsupportedOperationException();
    }

    public Vector2 isub(final double b) {
        throw new UnsupportedOperationException();
    }

    public Vector2 imul(final Vector2 b) {
        throw new UnsupportedOperationException();
    }

    public Vector2 imul(final double b) {
        throw new UnsupportedOperationException();
    }

    public Vector2 idiv(final Vector2 b) {
        throw new UnsupportedOperationException();
    }

    public Vector2 idiv(final double b) {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return x + ", " + y;
    }

    public static Vector2 add(final Vector2 a, final Vector2 b) {
        return a.add(b);
    }

    public static Vector2 add(final Vector2 a, final double b) {
        return a.add(b);
    }

    public static Vector2 add(final double a, final Vector2 b) {
        return b.add(a);
    }

    public static Vector2 sub(final Vector2 a, final Vector2 b) {
        return a.sub(b);
    }

    public static Vector2 sub(final Vector2 a, final double b) {
        return a.sub(b);
    }

    public static Vector2 sub(final double a, final Vector2 b) {
        return b.add(a);
    }

    public static Vector2 mul(final Vector2 a, final Vector2 b) {
        return a.mul(b);
    }

    public static Vector2 mul(final Vector2 a, final double b) {
        return a.mul(b);
    }

    public static Vector2 mul(final double a, final Vector2 b) {
        return b.mul(a);
    }

    public static Vector2 div(final Vector2 a, final Vector2 b) {
        return a.div(b);
    }

    public static Vector2 div(final Vector2 a, final double b) {
        return a.div(b);
    }

    public static Vector2 div(final double a, final Vector2 b) {
        return b.div(a);
    }

    public static double dot(final Vector2 a, final Vector2 b) {
        return a.dot(b);
    }

    public static UnmodifiebleVector2 getUnmodifiebleVector2(double x, double y) {
        return new UnmodifiebleVector2(x, y);
    }

    public static UnmodifiebleVector2 getUnmodifiebleVector2(Vector2 v) {
        return new UnmodifiebleVector2(v.x, v.y);
    }

    public static UnmodifiebleVector2 getUnmodifiebleVector2() {
        return new UnmodifiebleVector2();
    }

    public static ModifiebleVector2 getVector2(double x, double y) {
        return new ModifiebleVector2(x, y);
    }

    public static ModifiebleVector2 getVector2(Vector2 v) {
        return new ModifiebleVector2(v.x, v.y);
    }

    public static ModifiebleVector2 getVector2() {
        return new ModifiebleVector2();
    }
}
