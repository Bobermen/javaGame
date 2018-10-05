package linAlg.Vector3;

public class ModifiebleVector3 extends UnmodifiebleVector3
{
    ModifiebleVector3(double x, double y, double z) { super(x, y, z); }
    ModifiebleVector3() { super(); }

    public boolean isModifieble() { return true; }

    public void normalize() {
        this.idiv(this.magnitude());
    }

    @Override
    public Vector3 iadd(final Vector3 b) {
        x += b.x;
        y += b.y;
        z += b.z;
        return this;
    }

    @Override
    public Vector3 iadd(final double b) {
        x += b;
        y += b;
        z += b;
        return this;
    }

    @Override
    public Vector3 isub(final Vector3 b) {
        x -= b.x;
        y -= b.y;
        z -= b.z;
        return this;
    }

    @Override
    public Vector3 isub(final double b) {
        x -= b;
        y -= b;
        z -= b;
        return this;
    }

    @Override
    public Vector3 imul(final Vector3 b) {
        x *= b.x;
        y *= b.y;
        z *= b.z;
        return this;
    }

    @Override
    public Vector3 imul(final double b) {
        x *= b;
        y *= b;
        z *= b;
        return this;
    }

    @Override
    public Vector3 idiv(final Vector3 b) {
        x /= b.x;
        y /= b.y;
        z /= b.z;
        return this;
    }

    @Override
    public Vector3 idiv(final double b) {
        x /= b;
        y /= b;
        z /= b;
        return this;
    }
}
