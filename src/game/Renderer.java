package game;

import linAlg.Vector2.Vector2;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Renderer {

    public static ArrayList<Sprite> sprites = new ArrayList();

    public static void draw (Sprite sprite, Graphics2D g) {

        Vector2 position = sprite.transform.getPosition();
        double angle = sprite.transform.getRotation();
        double scale = sprite.transform.getScale();
        double k = Camera.main.resolutionWidth / Camera.main.width;

        position = position.sub(Camera.main.transform.getPosition()).mul(k);
        scale *= k;

        AffineTransform affine = new AffineTransform();
        int pivotX = (int)(sprite.pivot.x*scale);
        int pivotY = (int)(sprite.pivot.y*scale);

        affine.translate(Camera.main.resolutionWidth/2 + position.x - pivotX,
                Camera.main.resolutionHeight/2 - (position.y + pivotY));
        affine.rotate(-Math.toRadians(angle), pivotX, pivotY);
        affine.scale(scale, scale);

        g.drawImage(sprite.image, affine, null);
        //g.setColor(Color.red);
        //g.drawRect(Camera.main.resolutionWidth/2,
           //     Camera.main.resolutionHeight/2, 10, 10);
        //System.out.println(k);
        //System.out.println("Screen position = " + position.x + "   " + position.y + " Scale = " + scale);
        //System.out.println("AffineTranslate = " + affine.getTranslateX() + "  " + affine.getTranslateY() );
        //System.out.println(pivotX);
    }
}
