package game;

import Physics2d.Colliders2d.Collider2d;

import java.util.ArrayList;

public class GameObject {

    //private static ArrayList<GameObject> gameObjects = new ArrayList<>();
    private ArrayList<Component> components = new ArrayList<>();

    public Transform transform;
    public boolean enabled;

    public GameObject() {
        transform = new Transform();
        transform.gameObject = this;
    }

    public void update() {
        components.forEach(Component::update);
        for (Transform child : transform) {
            child.gameObject.update();
        }
    }
    public void start() { components.forEach(Component::start); }
    public void onCollisionDetected(Collider2d collider) {}

    public static GameObject instantiate(GameObject gameObject, Transform parent) {
        //GameObject clone = gameObject.clone();
        //clone.scene = Game.getActiveScene();
        //clone.transform.setParent(parent);
        //clone.start();
        //return clone;

        gameObject.transform.setParent(parent);
        gameObject.start();
        return gameObject;
    }
    public static GameObject instantiate(GameObject gameObject) {
        //GameObject clone = gameObject.clone();
        //clone.scene = Game.getActiveScene();
        //clone.scene.root.add(clone.transform);
        //clone.start();
        //return clone;
        Game.root.add(gameObject);
        gameObject.start();
        return gameObject;
    }
    public void destroy() {
        if (transform.getParent() == null) {
            Game.root.remove(this);
        }
        components.forEach(Component::destroy);
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

    public GameObject clone() {
        GameObject res = new GameObject();
        res.enabled = enabled;
        res.transform = (Transform)transform.clone();
        for (int i = 0; i < components.size(); i++) {
            Component t = components.get(i).clone();
            t.gameObject = res;
            t.transform = res.transform;
            res.components.add(t);
        }
        for (Transform child : transform) {
            child.gameObject.clone().transform.setParent(res.transform);
        }
        return res;
    }
}
