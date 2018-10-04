package linAlg;

public class Vector3
{
    public double x;
    public double y;
    public double z;

    public Vector3(double a, double b, double c)
    {
        x = a;
        y = b;
        z = c;
    }

    public Vector3() { this(0, 0, 0);}

    public Vector3 add(final Vector3 b) {
        return new Vector3(x + b.x, y + b.y, z + b.z);
    }

    public Vector3 add(final double b) {
        return new Vector3(x + b, y + b, z + b);
    }

    public Vector3 sub(final Vector3 b) {
        return new Vector3(x - b.x, y - b.y, z - b.z);
    }

    public Vector3 sub(final double b) {
        return new Vector3(x - b, y - b, z - b);
    }

    public Vector3 mul(final Vector3 b) {
        return new Vector3(x * b.x, y * b.y, z * b.z);
    }

    public Vector3 mul(final double b) {
        return new Vector3(x * b, y * b, z * b);
    }

    public Vector3 div(final Vector3 b) {
        return new Vector3(x / b.x, y / b.y, z / b.z);
    }

    public Vector3 div(final double b) {
        return new Vector3(x / b, y / b, z / b);
    }

    public double dot(final Vector3 b) {
        return x * b.x + y * b.y + z * b.z;
    }

    public double cross(Vector3 b) {
        return x * b.y - y * b.x;
    }

    public Vector3 negative() {
        return new Vector3(-x, -y, -z);
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    static Vector3 Cross(Vector3 a, Vector3 b) { return new Vector3(a.y * b.z - b.y * a.z, a.x * b.z - b.x * a.z, a.x * b.y - b.x * a.y); }

    @Override
    public String toString() { return Double.toString(x) + " " + Double.toString(y) + " " + Double.toString(z) + "\n"; }
}
