import java.util.ArrayList;
import java.util.Iterator;

public class GameObject {

    private static ArrayList<GameObject> gameObjects = new ArrayList<>();
    private ArrayList<Component> components = new ArrayList<>();

    public Transform transform;

    public GameObject() {
        transform = this.addComponent(new Transform());
    }

    //TODO clone()
    //TODO ::
    public void update() {
        components.forEach(component -> component.update());
    }
    public void start() { components.forEach(component -> component.start()); }
    public void onCollisionDetected(Collider2d collider) {}

    public static void callUpdate() {
        gameObjects.forEach(GameObject::update);
    }

    public static void instantiate(GameObject gameObject, Transform parent) {
        gameObjects.add(gameObject);
        gameObject.transform.setParent(parent);
        gameObject.start();
    }
    public static void instantiate(GameObject gameObject) {
        gameObjects.add(gameObject);
        gameObject.start();
    }

    public static void destroy(GameObject obj) {
        obj.components.forEach(Component::destroy);
        gameObjects.remove(obj);
    }

    public <T extends Component> T addComponent(T component) {
        component.gameObject = this;
        component.transform = transform;
        components.add(component);
        return component;
    }

    public <T extends Component> T getComponent(Class<T> cls) {
        for (Component component : components)
        {
            if (component.getClass().equals(cls)) {
                return (T) component;
            }
        }
        return null;
    }
    public <T extends Component> T getComponentInParent(Class<T> cls) {
        Component component;
        if ((component = getComponent(cls)) != null) {
            return (T) component;
        }
        if (transform.getParent() != null) {
            return transform.getParent().getComponentInParent(cls);
        }
        return null;
    }
    public <T extends Component> T getComponentInChildren(Class<T> cls) {
        Component component;
        if ((component = getComponent(cls)) != null) {
            return (T) component;
        }
        for (Transform child : transform) {
            if ((component = child.getComponent(cls)) != null) {
                return (T)component;
            }
        }
        return null;
    }
}
