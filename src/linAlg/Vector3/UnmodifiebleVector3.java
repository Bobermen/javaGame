package linAlg.Vector3;

class UnmodifiebleVector3 extends Vector3
{
    public double x;
    public double y;
    public double z;

    UnmodifiebleVector3(double a, double b, double c)
    {
        x = a;
        y = b;
        z = c;
    }

    UnmodifiebleVector3() { this(0, 0, 0);}

    public boolean isModifieble() { return false; }

    public Vector3 add(final Vector3 b) {
        return new ModifiebleVector3(x + b.x, y + b.y, z + b.z);
    }

    public Vector3 add(final double b) {
        return new ModifiebleVector3(x + b, y + b, z + b);
    }

    public Vector3 sub(final Vector3 b) {
        return new ModifiebleVector3(x - b.x, y - b.y, z - b.z);
    }

    public Vector3 sub(final double b) {
        return new ModifiebleVector3(x - b, y - b, z - b);
    }

    public Vector3 mul(final Vector3 b) {
        return new ModifiebleVector3(x * b.x, y * b.y, z * b.z);
    }

    public Vector3 mul(final double b) {
        return new ModifiebleVector3(x * b, y * b, z * b);
    }

    public Vector3 div(final Vector3 b) {
        return new ModifiebleVector3(x / b.x, y / b.y, z / b.z);
    }

    public Vector3 div(final double b) {
        return new ModifiebleVector3(x / b, y / b, z / b);
    }

    public double dot(final Vector3 b) {
        return x * b.x + y * b.y + z * b.z;
    }

    public double cross2(Vector3 b) {
        return x * b.y - y * b.x;
    }

    public static double cross2(Vector3 a, Vector3 b) {
        return a.x * b.y - a.y * b.x;
    }

    public Vector3 negative() {
        return new ModifiebleVector3(-x, -y, -z);
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    static Vector3 cross(Vector3 a, Vector3 b) {
        return new ModifiebleVector3(a.y * b.z - b.y * a.z, a.x * b.z - b.x * a.z, a.x * b.y - b.x * a.y);
    }

    public Vector3 cross(Vector3 b) {
        return new ModifiebleVector3(y * b.z - b.y * z, x * b.z - b.x * z, x * b.y - b.x * y);
    }

    @Override
    public String toString() { return Double.toString(x) + " " + Double.toString(y) + " " + Double.toString(z) + "\n"; }

    @Override
    public Vector3 clone() {
        return new ModifiebleVector3(x, y, z);
    }
}
