package Physics2d.Colliders2d.Polygon;

import game.Component;
import game.RigidBody2d;

public class DynamicPolygon extends StaticPolygon
{
    private RigidBody2d attachedRigidBody;

    @Override
    public Component clone() {
        return Polygon.getDynamic().setPolygon(this);
    }


    @Override
    public void start() {
        super.start();
        attachedRigidBody = getComponentInParent(attachedRigidBody.getClass());
    }

    public boolean isStatic() {
        return false;
    }

    @Override
    public RigidBody2d getAttachedRigitBody() {
        return attachedRigidBody;
    }
}
