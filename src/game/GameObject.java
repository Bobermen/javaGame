package game;

import Physics2d.Colliders2d.Collider2d;

import java.util.ArrayList;

public class GameObject {

    //private static ArrayList<GameObject> gameObjects = new ArrayList<>();
    private ArrayList<Component> components = new ArrayList<>();

    public Transform transform;
    public boolean enabled;
    public Scene scene;

    public GameObject() {
        transform = new Transform();
        transform.gameObject = this;
    }

    public GameObject clone() {
        return null;//TODO
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
        gameObject.scene = Game.getActiveScene();
        gameObject.scene.root.add(gameObject.transform);
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
        gameObject.scene = Game.getActiveScene();
        gameObject.scene.root.add(gameObject.transform);
        gameObject.start();
        return gameObject;
    }

    public static void destroy(GameObject obj) {
        obj.components.forEach(Component::destroy);

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
