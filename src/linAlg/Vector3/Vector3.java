package linAlg.Vector3;

public abstract class Vector3
{
    public double x;
    public double y;
    public double z;

    public abstract Vector3 add(final Vector3 b);
    public abstract Vector3 add(final double b);
    public abstract Vector3 sub(final Vector3 b);
    public abstract Vector3 sub(final double b);
    public abstract Vector3 mul(final Vector3 b);
    public abstract Vector3 mul(final double b);
    public abstract Vector3 div(final Vector3 b);
    public abstract Vector3 div(final double b);
    public abstract Vector3 negative();
    public abstract Vector3 clone();
    public abstract boolean isModifieble();

    public double dot(final Vector3 b) {
        return x * b.x + y * b.y + z * b.z;
    }

    public double cross2(Vector3 b) {
        return x * b.y - y * b.x;
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double sqrMagnitude() { return x * x + y * y + z * z; }

    public Vector3 normalized() {
        double m = this.magnitude();
        return Vector3.div(this, m);
    }

    public void normalize() {
        throw new UnsupportedOperationException();
    }

    public Vector3 iadd(final Vector3 b) { throw new UnsupportedOperationException(); }

    public Vector3 iadd(final double b) {
        throw new UnsupportedOperationException();
    }

    public Vector3 isub(final Vector3 b) {
        throw new UnsupportedOperationException();
    }

    public Vector3 isub(final double b) {
        throw new UnsupportedOperationException();
    }

    public Vector3 imul(final Vector3 b) {
        throw new UnsupportedOperationException();
    }

    public Vector3 imul(final double b) {
        throw new UnsupportedOperationException();
    }

    public Vector3 idiv(final Vector3 b) {
        throw new UnsupportedOperationException();
    }

    public Vector3 idiv(final double b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() { return x + ", " + y + ", " + z; }

    public static Vector3 add(final Vector3 a, final Vector3 b) {
        return a.add(b);
    }

    public static Vector3 add(final Vector3 a, final double b) {
        return a.add(b);
    }

    public static Vector3 add(final double a, final Vector3 b) { return b.add(a); }

    public static Vector3 sub(final Vector3 a, final Vector3 b) {
        return a.sub(b);
    }

    public static Vector3 sub(final Vector3 a, final double b) {
        return a.sub(b);
    }

    public static Vector3 sub(final double a, final Vector3 b) {
        return b.add(a);
    }

    public static Vector3 mul(final Vector3 a, final Vector3 b) {
        return a.mul(b);
    }

    public static Vector3 mul(final Vector3 a, final double b) {
        return a.mul(b);
    }

    public static Vector3 mul(final double a, final Vector3 b) {
        return b.mul(a);
    }

    public static Vector3 div(final Vector3 a, final Vector3 b) {
        return a.div(b);
    }

    public static Vector3 div(final Vector3 a, final double b) {
        return a.div(b);
    }

    public static Vector3 div(final double a, final Vector3 b) {
        return b.div(a);
    }

    public static double dot(final Vector3 a, final Vector3 b) {
        return a.dot(b);
    }

    public static UnmodifiebleVector3 getUnmodifiebleVector3(double x, double y, double z) {
        return new UnmodifiebleVector3(x, y, z);
    }

    public static UnmodifiebleVector3 getUnmodifiebleVector3(Vector3 v) {
        return new UnmodifiebleVector3(v.x, v.y, v.z);
    }

    public static UnmodifiebleVector3 getUnmodifiebleVector3() {
        return new UnmodifiebleVector3();
    }

    public static ModifiebleVector3 getVector3(double x, double y, double z) {
        return new ModifiebleVector3(x, y, z);
    }

    public static ModifiebleVector3 getVector3(Vector3 v) {
        return new ModifiebleVector3(v.x, v.y, v.z);
    }

    public static ModifiebleVector3 getVector3() {
        return new ModifiebleVector3();
    }
}
