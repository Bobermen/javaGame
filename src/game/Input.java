package game;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Input implements KeyListener, MouseListener,
        MouseMotionListener, MouseWheelListener {
    private static Input current;

    private static final short NUM_KEYS = 256;
    private static final short NUM_BUTTONS = 5;

    private static boolean[] keys = new boolean[NUM_KEYS];
    private static boolean[] keysLast = new boolean[NUM_KEYS];

    private static boolean[] buttons = new boolean[NUM_BUTTONS];
    private static boolean[] buttonsLast = new boolean[NUM_BUTTONS];

    private static int mouseX, mouseY;
    private static int scroll;

    private static int scale = 1;

    private static boolean isCtrlPressed = false;
    private static boolean isShiftPressed = false;
    private static boolean isAltPressed = false;
    private static boolean isEscapePressed = false;
    private static boolean isWheelClicked = false;
    private static boolean isRightClicked = false;
    private static boolean isLeftClicked = false;

    public Input(int scale) {
        current = this;

        mouseX = 0;
        mouseY = 0;
        scroll = 0;

        this.scale = scale;
        if(scale < 0) scale = 1;
    }

    public static void addListeners(Canvas c) {
        c.addKeyListener(current);
        c.addMouseMotionListener(current);
        c.addMouseListener(current);
        c.addMouseWheelListener(current);
    }

    //update in each game loop
    public static void update() {
        scroll = 0;
        System.arraycopy(keys, 0, keysLast, 0, NUM_KEYS);
        System.arraycopy(buttons, 0, buttonsLast, 0, NUM_BUTTONS);
    }

    public static boolean isKey(int keycode) {
        return keys[keycode];
    }

    public static boolean isKeyPressed(int keycode) {
        return keys[keycode] && !keysLast[keycode];
    }

    public static boolean isKeyReleased(int keycode) {
        return !keys[keycode] && keysLast[keycode];
    }

    public static boolean isButton(int button) {
        return buttons[button];
    }

    public static boolean isButtonPressed(int button) {
        return buttons[button] && !buttonsLast[button];
    }

    public static boolean isButtonReleased(int button) {
        return !buttons[button] && buttonsLast[button];
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll = e.getWheelRotation();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX() / scale;
        mouseY = e.getY() / scale;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX() / scale;
        mouseY = e.getY() / scale;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()] = true;
        if(e.getButton() == MouseEvent.BUTTON1) isLeftClicked = true;
        if(e.getButton() == MouseEvent.BUTTON2) isWheelClicked = true;
        if(e.getButton() == MouseEvent.BUTTON3) isRightClicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttons[e.getButton()] = false;
        if(e.getButton() == MouseEvent.BUTTON1) isLeftClicked = false;
        if(e.getButton() == MouseEvent.BUTTON2) isWheelClicked = false;
        if(e.getButton() == MouseEvent.BUTTON3) isRightClicked = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;

        if(e.getKeyCode() == KeyEvent.VK_SHIFT) isShiftPressed = true;
        if(e.getKeyCode() == KeyEvent.VK_CONTROL) isCtrlPressed = true;
        if(e.getKeyCode() == KeyEvent.VK_ALT) isAltPressed = true;
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) isEscapePressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {

        keys[e.getKeyCode()] = false;

        if(e.getKeyCode() == KeyEvent.VK_SHIFT) isShiftPressed = false;
        if(e.getKeyCode() == KeyEvent.VK_CONTROL) isCtrlPressed = false;
        if(e.getKeyCode() == KeyEvent.VK_ALT) isAltPressed = false;
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) isEscapePressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public static int getMouseX() {
        return mouseX;
    }

    public static int getMouseY() {
        return mouseY;
    }

    public static int getScroll() {
        return scroll;
    }

    public static boolean isCtrlPressed() {
        return isCtrlPressed;
    }

    public static boolean isShiftPressed() {
        return isShiftPressed;
    }

    public static boolean isAltPressed() {
        return isAltPressed;
    }

    public static boolean isEscapePressed() {
        return isEscapePressed;
    }

    public static boolean isWheelClicked() {
        return isWheelClicked;
    }

    public static boolean isRightClicked() {
        return isRightClicked;
    }

    public static boolean isLeftClicked() {
        return isLeftClicked;
    }
}