package linAlg.Vector2;

class UnmodifiebleVector2 extends Vector2
{
    UnmodifiebleVector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    UnmodifiebleVector2() { this(0.0, 0.0); }

    public boolean isModifieble() { return false; }

    @Override
    public Vector2 rightPerp() { return new ModifiebleVector2(y, -x); }

    @Override
    public Vector2 leftPerp() { return new ModifiebleVector2(-y, x); }

    @Override
    public Vector2 add(Vector2 b) {
        return new ModifiebleVector2(x + b.x, y + b.y);
    }

    @Override
    public Vector2 add(double b)
    {
        return new ModifiebleVector2(x - b, y - b);
    }

    @Override
    public Vector2 sub(Vector2 b)
    {
        return new ModifiebleVector2(x - b.x, y - b.y);
    }

    @Override
    public Vector2 sub(double b)
    {
        return new ModifiebleVector2(x - b, y - b);
    }

    @Override
    public Vector2 mul(Vector2 b)
    {
        return new ModifiebleVector2(x * b.x, y * b.y);
    }

    @Override
    public Vector2 mul(double b) { return new ModifiebleVector2(x * b, y * b); }

    @Override
    public Vector2 div(Vector2 b)
    {
        return new ModifiebleVector2(x / b.x, y / b.y);
    }

    @Override
    public Vector2 div(double b)
    {
        return new ModifiebleVector2(x / b, y / b);
    }

    @Override
    public Vector2 negative()
    {
        return new ModifiebleVector2(-x, -y);
    }

    @Override
    public Vector2 clone()
    {
        return new ModifiebleVector2(x, y);
    }
}
