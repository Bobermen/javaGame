package linAlg.Vector2;

class ModifiebleVector2 extends UnmodifiebleVector2
{
    ModifiebleVector2(double x, double y) { super(x, y); }
    ModifiebleVector2() { super(); }

    public boolean isModifieble() { return true; }

    public void normalize() {
        this.idiv(this.magnitude());
    }

    @Override
    public Vector2 iadd(final Vector2 b) {
        x += b.x;
        y += b.y;
        return this;
    }

    @Override
    public Vector2 iadd(final double b) {
        x += b;
        y += b;
        return this;
    }

    @Override
    public Vector2 isub(final Vector2 b) {
        x -= b.x;
        y -= b.y;
        return this;
    }

    @Override
    public Vector2 isub(final double b) {
        x -= b;
        y -= b;
        return this;
    }

    @Override
    public Vector2 imul(final Vector2 b) {
        x *= b.x;
        y *= b.y;
        return this;
    }

    @Override
    public Vector2 imul(final double b) {
        x *= b;
        y *= b;
        return this;
    }

    @Override
    public Vector2 idiv(final Vector2 b) {
        x /= b.x;
        y /= b.y;
        return this;
    }

    @Override
    public Vector2 idiv(final double b) {
        x /= b;
        y /= b;
        return this;
    }
}
