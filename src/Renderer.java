import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Renderer {

    public static ArrayList<Sprite> sprites = new ArrayList();

    public static void draw (Sprite sprite, Graphics2D g) {
        Transform obj = sprite.transform.getParent();
        Vector2 position = sprite.transform.position.clone();
        double angle = sprite.transform.rotation;
        double scale = sprite.transform.scale;
        //TODO apply transform functions
        while (obj != null) {
            scale *= obj.scale;
            angle += obj.rotation;
            position.iadd(obj.position);
            obj = obj.getParent();
        }
        double k = Camera.main.resolutionWidth / Camera.main.width;

        position.isub(Camera.main.transform.position).imul(k);
        scale *= k;

        AffineTransform affine = new AffineTransform();
        int pivotX = (int)(sprite.pivot.x*scale);
        int pivotY = (int)(sprite.pivot.y*scale);

        affine.translate(Camera.main.resolutionWidth/2 + position.x - pivotX,
                Camera.main.resolutionHeight/2 - (position.y + pivotY));
        affine.rotate(-Math.toRadians(angle), pivotX, pivotY);
        affine.scale(scale, scale);

        g.drawImage(sprite.image, affine, null);
        g.setColor(Color.red);
        g.drawRect(Camera.main.resolutionWidth/2,
                Camera.main.resolutionHeight/2, 10, 10);

        //System.out.println("Screen position = " + position.x + "   " + position.y + " Scale = " + scale);
        //System.out.println("AffineTranslate = " + affine.getTranslateX() + "  " + affine.getTranslateY() );
        //System.out.println(pivotX);
    }
}
