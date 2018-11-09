package Physics2d;

import Physics2d.Colliders2d.Collider2d;
import game.Pair;
import game.RigidBody2d;
import linAlg.Vector2.Vector2;

import java.util.ArrayList;

public class Physics2d {
    public static ArrayList<ArrayList<Collider2d>> colliders = null;

    public static void addCollider(Collider2d collider, int layer) {
        if (colliders == null)
            colliders = new ArrayList<>();
        if (colliders.size() < layer)
            for (int i = colliders.size(); i < layer; i++)
                colliders.add(new ArrayList<>());
        colliders.get(layer).add(collider);
    }

    public static void addCollider(Collider2d collider) {
        addCollider(collider, 0);
    }

    public static Collider2d getCollider(Vector2 point) {
        return getCollider(point, new LayerMask());
    }

    public static Collider2d getCollider(Vector2 point, LayerMask layerMask) {
        for (int layer : layerMask)
            for (Collider2d collider : colliders.get(layer)) {
                if (collider.isIn(point))
                    return collider;
            }
        return null;
    }

    public static RayCastHit rayCast(Vector2 position, Vector2 ray) {
        return rayCast(position, ray, new LayerMask());
    }

    public static RayCastHit rayCast(Vector2 position, Vector2 direction, double length) {
        return rayCast(position, direction, length, new LayerMask());
    }

    public static RayCastHit rayCast(Vector2 position, Vector2 ray, RigidBody2d attachedRigidBody) {
        return rayCast(position, ray, attachedRigidBody, new LayerMask());
    }

    public static RayCastHit rayCast(Vector2 position, Vector2 direction, double length, RigidBody2d attachedRigidBody) {
        return rayCast(position, direction, length, attachedRigidBody, new LayerMask());
    }

    public static RayCastHit rayCast(Vector2 position, Vector2 ray, LayerMask layerMask) {
        Pair<Vector2, Double> point = Pair.makePair(Vector2.getVector2(Double.NaN, Double.NaN), Double.NaN);
        Collider2d resCollider = null;
        Vector2 secondPosition = position.add(ray);
        Pair<Vector2, Double> tmpRes;
        for (int layer : layerMask) {
            for (Collider2d collider : colliders.get(layer)) {
                tmpRes = collider.isIntersecting(position, secondPosition);
                if ((tmpRes != null) && (tmpRes.first.sub(position).sqrMagnitude() < point.first.sub(position).sqrMagnitude())) {
                    resCollider = collider;
                    point = tmpRes;
                }
            }
        }
        if (point.second.isNaN())
            return null;
        return new RayCastHit(point.first, resCollider ,point.second);
    }

    public static RayCastHit rayCast(Vector2 position, Vector2 direction, double length, LayerMask layerMask) {
        return rayCast(position, direction.mul(length), layerMask);
    }

    public static RayCastHit rayCast(Vector2 position, Vector2 ray, RigidBody2d attachedRigidBody, LayerMask layerMask) {
        Pair<Vector2, Double> point = Pair.makePair(Vector2.getVector2(Double.NaN, Double.NaN), Double.NaN);
        Collider2d resCollider = null;
        Vector2 secondPosition = position.add(ray);
        Pair<Vector2, Double> tmpRes;
        for (int layer : layerMask) {
            for (Collider2d collider : colliders.get(layer)) {
                if (collider.getAttachedRigitBody() == attachedRigidBody)
                    continue;
                tmpRes = collider.isIntersecting(position, secondPosition);
                if ((tmpRes != null) && (tmpRes.first.sub(position).sqrMagnitude() < point.first.sub(position).sqrMagnitude())) {
                    resCollider = collider;
                    point = tmpRes;
                }
            }
        }
        if (point.second.isNaN())
            return null;
        return new RayCastHit(point.first, resCollider ,point.second);
    }

    public static RayCastHit rayCast(Vector2 position, Vector2 direction, double length, RigidBody2d attachedRigidBody, LayerMask layerMask) {
        return rayCast(position, direction.mul(length), attachedRigidBody, layerMask);
    }
}
