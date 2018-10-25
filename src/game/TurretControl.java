package game;

public class TurretControl extends Component {

    @Override
    public void update() {
        transform.setLocalRotation(transform.getLocalRotation() + 10 * Time.detlaTime);
    }

    @Override
    public Component clone() {
        return new TurretControl();
    }
}
