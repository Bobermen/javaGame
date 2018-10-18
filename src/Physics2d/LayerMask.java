package Physics2d;

import java.util.*;
import java.util.stream.IntStream;

public class LayerMask implements Iterable<Integer>
{
    int numOfLayers;
    private List<Integer> layers;
    private List<Integer> exceptions;
    public LayerMask(int numOfLayers) {
        exceptions = new ArrayList<>();
        layers = new ArrayList<>();
        for (int i = 0; i < numOfLayers; i++) {
            layers.add(i);
        }
    }

    public LayerMask() {
        this(Physics2d.colliders.size());
    }

    public Iterator<Integer> iterator() {
        return layers.iterator();
    }

    public void invert() {
        List<Integer> t;
        t = exceptions;
        exceptions = layers;
        layers = t;
    }

    public void addException(int num) {
        layers.remove(Integer.valueOf(num));
        exceptions.add(num);
    }

    public void removeException(int num) {
        exceptions.remove(Integer.valueOf(num));
        layers.add(num);
    }

    public void setInterval(int begin, int end) {
        int numOfLayers = layers.size() + exceptions.size();
        layers.clear();
        exceptions.clear();
        IntStream.iterate(0, item -> item < begin, item -> item + 1).forEach(item -> exceptions.add(item));
        IntStream.iterate(begin, item -> item < end, item -> item + 1).forEach(item -> layers.add(item));
        IntStream.iterate(end, item -> item < numOfLayers, item -> item + 1).forEach(item -> exceptions.add(item));
    }

    public void update() {
        int numOfLayers = layers.size() + exceptions.size();
        IntStream.iterate(numOfLayers,
                item -> item < Physics2d.colliders.size(),
                item -> item + 1).forEach(item -> layers.add(item));
    }
}
