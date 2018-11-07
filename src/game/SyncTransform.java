package game;

import linAlg.Vector2.Vector2;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;

public class SyncTransform extends Component {

    private static List<Method> methods = methodsInit();
    private static HashMap<String, Integer> stringsToCodesMap = initHashMap();
    private ByteBuffer buffer;
    private DataInputStream input;

    @Override
    public void start() {
        buffer = initBuffer();
        input = initInput();
    }

    @Override
    public void update() {
        if (NetworkManager.isServer) {
            readFunctions();
            sendFunction("syncTransform", transform.getLocalPosition(), transform.getLocalRotation());
            NetworkManager.sendBytes(buffer.array(), 0, buffer.position());
            buffer.clear();
            buffer.put((byte)0);
        }
        else {
            try {
                readFunctions();
                if (buffer != null) {
                    NetworkManager.clientOutput.write(buffer.array(), 0, buffer.position());
                    buffer.clear();
                    buffer.put((byte) 0);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public void sendFunction(String name, Object... params) {
        if (buffer == null) {
            System.out.println("not players can't send functions");
            return;
        }
        buffer.array()[0]++;
        int size = buffer.position();
        buffer.put(stringsToCodesMap.get(name).byteValue());
        try {
            for (Object param : params) {
                if (Integer.TYPE.isInstance(param) || param instanceof Integer) {
                    buffer.putInt((Integer)param);
                } else if (Long.TYPE.isInstance(param) || param instanceof Long) {
                    buffer.putLong((Long)param);
                } else if (Double.TYPE.isInstance(param) || param instanceof Double) {
                    buffer.putDouble((Double)param);
                } else if (Float.TYPE.isInstance(param) || param instanceof Float) {
                    buffer.putFloat((Float)param);
                } else if (Short.TYPE.isInstance(param) || param instanceof Short) {
                    buffer.putShort((Short)param);
                } else if (Character.TYPE.isInstance(param) || param instanceof Character) {
                    buffer.putChar((Character)param);
                } else if (Byte.TYPE.isInstance(param) || param instanceof Byte) {
                    buffer.put((Byte)param);
                } else if (param instanceof Vector2) {
                    buffer.putDouble(((Vector2)param).x);
                    buffer.putDouble(((Vector2)param).y);
                }
                // Можно добавить еще обработчиков для других классов
            }
        } catch (BufferOverflowException e) {
            ByteBuffer newBuffer = ByteBuffer.wrap(new byte[size * 2]);
            buffer.get(newBuffer.array(), 0, size);
            buffer = newBuffer;
            sendFunction(name, params);
        }
    }

    private void readFunctions() {
        if (input == null)
            return;
        int numOfFunc;
        int k;
        Method currentMethod;
        Component component;
        Class[] classes;
        Object[] params;
        try {
            int n = input.read();
            for (int i = 0; i < n; ++i) {
                numOfFunc = input.read();
                currentMethod = methods.get(numOfFunc);
                component = getComponent(currentMethod.getDeclaringClass());
                k = currentMethod.getParameterCount();
                classes = currentMethod.getParameterTypes();
                params = new Object[classes.length];
                for (int j = 0; j < k; ++j) {
                    params[j] = getObject(classes[j], input);
                }
                currentMethod.invoke(component, params);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        } catch (IllegalAccessException e) {
            System.out.println("cannot get access to method");
            System.exit(-1);
        } catch (InvocationTargetException e) {
            System.out.println("sended method thrown exception");
            System.out.println(e.getCause().getMessage());
            System.exit(-1);
        }
    }

    private static Object getObject(Class<?> cls, DataInputStream in) {
        try {
            if (cls.equals(Integer.TYPE) || cls.equals(Integer.class)) {
                return in.readInt();
            } else if (cls.equals(Long.TYPE) || cls.equals(Long.class)) {
                return in.readLong();
            } else if (cls.equals(Double.TYPE) || cls.equals(Double.class)) {
                return in.readDouble();
            } else if (cls.equals(Float.TYPE) || cls.equals(Float.class)) {
                return in.readFloat();
            } else if (cls.equals(Short.TYPE) || cls.equals(Short.class)) {
                return in.readShort();
            } else if (cls.equals(Character.TYPE) || cls.equals(Character.class)) {
                return in.readChar();
            } else if (cls.equals(Byte.TYPE) || cls.equals(Byte.class)) {
                return in.readByte();
            } else if (Vector2.class.isAssignableFrom(cls)) {
                return Vector2.getVector2(in.readDouble(), in.readDouble());
            }
            // аналогично с отправкой
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return null;
    }

    public void syncTransform(Vector2 position, double rotation) {
        transform.setLocalPosition(position);
        transform.setLocalRotation(rotation);
    }

    private static List<Method> methodsInit() {
        List<Method> result = null;
        try {
            result = List.of(
                    SyncTransform.class.getMethod("syncTransform", Vector2.class, double.class),
                    PlayerControl.class.getMethod("incSpeed"),
                    PlayerControl.class.getMethod("decSpeed"),
                    PlayerControl.class.getMethod("incHelm"),
                    PlayerControl.class.getMethod("decHelm")
                    // TODO add functions
            );
        } catch (NoSuchMethodException e) {
            System.out.println("method in methodsInit not found");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return result;
    }

    private static HashMap<String, Integer> initHashMap() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < methods.size(); i++) {
            hashMap.put(methods.get(i).getName(), i);
        }
        // TODO add functions,
        return hashMap;
    }


    private ByteBuffer initBuffer() {
        if (!NetworkManager.isServer) {
            if (this.getComponent(PlayerControl.class) == null) {
                return null;
            }
        }
        ByteBuffer bb = ByteBuffer.wrap(new byte[64]);
        bb.put((byte) 0);
        return bb;
    }

    private DataInputStream initInput() {
        PlayerControl playerControl = this.getComponent(PlayerControl.class);
        if (NetworkManager.isServer) {
            if (playerControl.isPlayer)
                return null;
            return NetworkManager.clientsInput.get(playerControl.playerID);
        } else {
            return NetworkManager.clientInput;
        }
    }

    @Override
    public SyncTransform clone() {
        return new SyncTransform();
    }
}
