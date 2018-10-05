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
    private Vector2 RIGHT = Vector2.getVector2(1, 0);
    private Vector2 UP = Vector2.UP;

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
        setLocalPosition(this.parent.inverseTransformPosition(position));
    }
    public void setRotation(double rotation) {
        setLocalRotation(rotation - this.parent.rotation);
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

    public Vector2 getUP() {
        return UP.clone();
    }

    public Vector2 getRIGHT() {
        return RIGHT.clone();
    }

    // TODO clone()

    public void update() {
        System.out.println(RIGHT);
        RIGHT = transformDirection(Vector2.RIGHT);
        UP = transformDirection(Vector2.UP);
        if (parent != null) {
            localToWorldMatrix = parent.localToWorldMatrix
                    .dot(Matrix3x3.Translate(localPosition)
                            .dot(Matrix3x3.Rotate(localRotation)));
            worldToLocalMatrix = Matrix3x3.Rotate(-localRotation)
                    .dot(Matrix3x3.Translate(localPosition.negative()))
                    .dot(parent.worldToLocalMatrix);
            position = transformPosition(localPosition);
            rotation = localRotation + parent.rotation;
            scale = localScale * parent.scale;
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
        for (Transform child : transform) {
            child.update();
        }
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
    //TODO inversePosition inverseVector inverseDirection (from world to local space)
    // From local to world space
    public Vector2 inverseTransformPosition(Vector2 position) {
        return worldToLocalMatrix.dot(position);
    }
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
