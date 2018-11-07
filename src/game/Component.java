package game;

import Physics2d.Colliders2d.Collider2d;

abstract public class Component {

    public GameObject gameObject; //TODO it's not safety
    public Transform transform; //TODO but what should I do

    public void start() {
    }
    public void update() {

    }
    public void destroy() {}

    public void onCollisionDetected(Collider2d collider) {

    }
    public <T extends Component> T getComponent(Class<?> cls) {
        return gameObject.getComponent(cls);
    }
    public <T extends Component> T getComponentInParent(Class<?> cls) {
        return gameObject.getComponentInParent(cls);
    }
    public <T extends Component> T getComponentInChildren(Class<?> cls) {
        return gameObject.getComponentInChildren(cls);
    }

    @Override
    public abstract Component clone();
}
