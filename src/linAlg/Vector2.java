package linAlg;

// package (packageName)
public class Vector2
{
    public double x = 0;
    public double y = 0;
    public Vector2(double x, double y) {
        this.x = x; this.y = y;
    }

    public static final Vector2 ONE = new Vector2(1,1);
    public static final Vector2 RIGHT = new Vector2(1,0);
    public static final Vector2 UP = new Vector2(0,1);
    public static final Vector2 ZERO = new Vector2(0,0);
    public static final Vector2 LEFT = new Vector2(-1,0);
    public static final Vector2 BOTTOM = new Vector2(0,-1);

    public Vector2() {
        this(0.0, 0.0);
    }

    public Vector2 add(final Vector2 b) {
        return new Vector2(x + b.x, y + b.y);
    }

    public Vector2 add(final double b) {
        return new Vector2(x + b, y + b);
    }

    public Vector2 sub(final Vector2 b) {
        return new Vector2(x - b.x, y - b.y);
    }

    public Vector2 sub(final double b) {
        return new Vector2(x - b, y - b);
    }

    public Vector2 mul(final Vector2 b) {
        return new Vector2(x * b.x, y * b.y);
    }

    public Vector2 mul(final double b) {
        return new Vector2(x * b, y * b);
    }

    public Vector2 div(final Vector2 b) {
        return new Vector2(x / b.x, y / b.y);
    }

    public Vector2 div(final double b) {
        return new Vector2(x / b, y / b);
    }

    public double dot(final Vector2 b) {
        return x * b.x + y * b.y;
    }

    public double cross(Vector2 b) {
        return x * b.y - y * b.x;
    }

    public Vector2 negative() {
        return new Vector2(-x, -y);
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public double sqrMagnitude() { return x * x + y * y; }

    public void normalize() {
        double m = this.magnitude();
    }

    public Vector2 normalized() {
        double m = this.magnitude();
        return Vector2.div(this, m);
    }

    public Vector2 Rotate(double angle) {
        angle = Math.toRadians(angle);
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        double x = this.x * cos + this.y * sin;
        double y = - this.x * sin + this.y * cos;
        return new Vector2(x,y);
    }

    public Vector2 iadd(final Vector2 b) {
        x += b.x;
        y += b.y;
        return this;
    }

    public Vector2 iadd(final double b) {
        x += b;
        y += b;
        return this;
    }

    public Vector2 isub(final Vector2 b) {
        x -= b.x;
        y -= b.y;
        return this;
    }

    public Vector2 isub(final double b) {
        x -= b;
        y -= b;
        return this;
    }

    public Vector2 imul(final Vector2 b) {
        x *= b.x;
        y *= b.y;
        return this;
    }

    public Vector2 imul(final double b) {
        x *= b;
        y *= b;
        return this;
    }

    public Vector2 idiv(final Vector2 b) {
        x /= b.x;
        y /= b.y;
        return this;
    }

    public Vector2 idiv(final double b) {
        x /= b;
        y /= b;
        return this;
    }

    public Vector2 iRotate(double angle) {
        angle = Math.toRadians(angle);
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        double x = this.x * cos + this.y * sin;
        double y = - this.x * sin + this.y * cos;
        //System.out.println("Angle = " + angle + " x = " + x + " y = " + y);
        //System.out.println("Cos = " + cos + " Sin = " + sin);
        this.x = x;
        this.y = y;
        return this;
    }

    public static Vector2 add(final Vector2 a, final Vector2 b) {
        return a.add(b);
    }

    public static Vector2 add(final Vector2 a, final double b) {
        return a.add(b);
    }

    public static Vector2 add(final double a, final Vector2 b) {
        return new Vector2(a + b.x, a + b.y);
    }

    public static Vector2 sub(final Vector2 a, final Vector2 b) {
        return a.sub(b);
    }

    public static Vector2 sub(final Vector2 a, final double b) {
        return a.sub(b);
    }

    public static Vector2 sub(final double a, final Vector2 b) {
        return new Vector2(a - b.x, a - b.y);
    }

    public static Vector2 mul(final Vector2 a, final Vector2 b) {
        return a.mul(b);
    }

    public static Vector2 mul(final Vector2 a, final double b) {
        return a.mul(b);
    }

    public static Vector2 mul(final double a, final Vector2 b) {
        return new Vector2(a * b.x, a * b.y);
    }

    public static Vector2 div(final Vector2 a, final Vector2 b) {
        return a.div(b);
    }

    public static Vector2 div(final Vector2 a, final double b) {
        return a.div(b);
    }

    public static Vector2 div(final double a, final Vector2 b) {
        return new Vector2(a / b.x, a / b.y);
    }

    public static double dot(final Vector2 a, final Vector2 b) {
        return a.x * b.x + a.y * b.y;
    }

    public static Vector2 Rotate(Vector2 v, double angle) {
        angle = Math.toRadians(angle);
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        double x = v.x * cos + v.y * sin;
        double y = - v.x * sin + v.y * cos;
        return new Vector2(x, y);
    }
    public static double cross(Vector2 a, Vector2 b) {
        return a.x * b.y - a.y * b.x;
    }

    public Vector2 clone() {
        return new Vector2(x, y);
    }

    public String toString() {
        return new String("(" + x + ", " + y + ")");
    }
}
