package game;

import linAlg.Matrix3x3;
import linAlg.Vector2.Vector2;

import java.util.ArrayList;
import java.util.Iterator;

public class Transform extends Component implements Iterable<Transform> {

    public Matrix3x3 localToWorldMatrix = new Matrix3x3();
    public Matrix3x3 worldToLocalMatrix = new Matrix3x3();
    private Vector2 position = Vector2.ZERO;
    private double rotation = 0;
    private double scale = 1;
    private Vector2 localPosition = Vector2.ZERO;
    private double localRotation = 0;
    private double localScale = 1;

    private Transform parent = null;
    private ArrayList<Transform> children = new ArrayList<>();
    private Vector2 right = Vector2.getVector2(1, 0);
    private Vector2 up = Vector2.getVector2(0, 1);

    public Vector2 getPosition() {
        return position.clone();
    }
    public double getRotation() {
        return rotation;
    }
    public double getScale() {
        return scale;
    }
    public Vector2 getLocalPosition() {
        return localPosition.clone();
    }
    public double getLocalRotation() {
        return localRotation;
    }
    public double getLocalScale() {
        return localScale;
    }


    public Transform() {
        transform = this;
    }

    public void setPosition(Vector2 position) {
        if (parent != null) {
            setLocalPosition(this.parent.inverseTransformPosition(position));
        }
        else {
            setLocalPosition(position);
        }
    }
    public void setRotation(double rotation) {
        if (parent != null) {
            setLocalRotation(rotation - this.parent.rotation);
        }
        else {
            setLocalRotation(rotation);
        }
    }
    public void setPositionAndRotation(Vector2 position, double rotation)
    {
        this.localPosition = this.parent.inverseTransformPosition(position);
        this.localRotation = rotation - this.parent.rotation;
        update();
    }

    public void setLocalPosition(Vector2 position) {
        this.localPosition = position;
        update();
    }
    public void setLocalRotation(double rotation) {
        this.localRotation = rotation;
        update();
    }
    public void setLocalScale(double scale) {
        this.localScale = scale;
        update();
    }

    public Vector2 getUp() {
        return up.clone();
    }

    public Vector2 getRight() {
        return right.clone();
    }

    @Override
    public void start() {
        for (Transform child : this) {
            child.gameObject.start();
        }
    }

    @Override
    public void update() {
        if (parent != null) {
            localToWorldMatrix = parent.localToWorldMatrix
                    .dot(Matrix3x3.Translate(localPosition)
                            .dot(Matrix3x3.Rotate(localRotation)));
            worldToLocalMatrix = Matrix3x3.Rotate(-localRotation)
                    .dot(Matrix3x3.Translate(localPosition.negative()))
                    .dot(parent.worldToLocalMatrix);
            position = parent.transformPosition(localPosition);
            rotation = parent.rotation + localRotation;
            scale = parent.scale * localScale;
        }
        else {
            localToWorldMatrix = Matrix3x3.Translate(localPosition)
                    .dot(Matrix3x3.Rotate(localRotation));
            worldToLocalMatrix = Matrix3x3.Rotate(-localRotation)
                    .dot(Matrix3x3.Translate(localPosition.negative()));
            position = localPosition;
            rotation = localRotation;
            scale = localScale;
        }
        right = transformDirection(Vector2.RIGHT);
        up = transformDirection(Vector2.UP);
        //System.out.println(right);
        for (Transform child : transform) {
            child.update();
        }
    }

    public void destroy() {
        for (Transform child : transform) {
            child.gameObject.destroy();
        }
    }

    @Override
    public Component clone() {
        Transform res = new Transform();
        res.parent = parent;
        res.position = position.clone();
        res.rotation = rotation;
        res.scale = scale;
        res.localPosition = localPosition.clone();
        res.localRotation = localRotation;
        res.localScale = localScale;
        // update();
        res.localToWorldMatrix = localToWorldMatrix.clone();
        res.worldToLocalMatrix = worldToLocalMatrix.clone();
        res.right = right.clone();
        res.up = up.clone();
        return res;
    }

    public Transform getParent() { return parent; }

    public void setParent(Transform parent) {
        setParent(parent, false);
    }
    public void setParent(Transform parent, boolean worldPositionStays) {
        this.parent = parent;
        if (this.parent != null) {
            this.parent.children.add(this);
            if (!worldPositionStays) {
                update();
            }
            else {
                setPositionAndRotation(this.position, this.rotation);
            }
        }
        update();
    }
    //TODO inverseVector inverseDirection (from world to local space)
    // From local to world space
    public Vector2 inverseTransformPosition(Vector2 position) {
        return worldToLocalMatrix.dot(position);
    }
    public Vector2 inverseTransformDirection(Vector2 direction) {return Matrix3x3.Rotate(-rotation).dot(direction);}
    public Vector2 transformPosition(Vector2 position) { // Not affected by scale
        return localToWorldMatrix.dot(position);
    }
    public Vector2 transformPoint(Vector2 point) { // Affected by position and scale
        Vector2 result = point;
        //TODO
        return result;
    }
    public Vector2 transformDirection(Vector2 direction) { // Not affected by scale and position
        return Matrix3x3.Rotate(rotation).dot(direction);
    }

    @Override
    public Iterator<Transform> iterator() {
        return children.iterator();
    }
}
