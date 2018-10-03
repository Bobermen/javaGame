import java.util.ArrayList;
import java.util.Iterator;

public class Transform extends Component implements Iterable<Transform>{
    public Vector2 position = Vector2.ZERO;
    public double rotation = 0;
    public double scale = 1;


    private Transform parent = null;
    private ArrayList<Transform> children = new ArrayList<>();
    private Vector2 RIGHT = Vector2.RIGHT.clone();
    private Vector2 UP = Vector2.UP.clone();

    public Vector2 getUP() {
        return UP;
    }

    public Vector2 getRIGHT() {
        return RIGHT;
    }

    // TODO clone()

    public void update() {
        RIGHT = transformDirection(Vector2.RIGHT);
        UP = transformDirection(Vector2.UP);

    }

    public Transform getParent() { return parent; }

    public void setParent(Transform parent) {
        this.parent = parent;
        if (parent != null) {
            parent.children.add(this);
        }
    }
    //TODO inversePosition inverseVector inverseDirection (from world to local space)
    public Vector2 transformPosition(Vector2 position) {
        Vector2 result = position.clone();
        Transform obj = this;
        while (obj != null) {
            result.iadd(obj.position);
            obj = obj.parent;
        }
        return result;
    }
    public Vector2 transformVector(Vector2 vector) {
        Vector2 result = vector.clone();
        Transform obj = this;
        while (obj != null) {
            result.iRotate(-obj.rotation);
            obj = obj.parent;
        }
        return result;
    }
    public Vector2 transformDirection(Vector2 direction) {
        Vector2 result = direction.clone();
        Transform obj = this;
        while (obj != null) {
            result.iRotate(-obj.rotation);
            obj = obj.parent;
        }
        return result.normalized();
    }

    @Override
    public Iterator<Transform> iterator() {
        return children.iterator();
    }
}
