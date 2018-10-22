package game;

public class Camera extends Component {

    public static Camera main = null;
    public double width = 1000, height;
    public int resolutionWidth, resolutionHeight;
    public Camera (int resolutionWidth, int resolutionHeight) {
        this.resolutionWidth = resolutionWidth;
        this.resolutionHeight = resolutionHeight;
        height = width/resolutionWidth * resolutionHeight;
        main = this;
    }

    // TODO posChange, screenToWorld, worldToScreen
    public void update() {
        height = width/resolutionWidth * resolutionHeight;
        width += Input.getScroll() * 100;
        if (width > 40000) {
            width = 40000;
        }
        if (width < 50) {
            width = 50;
        }
    }
}
