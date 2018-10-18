package Physics2d.Colliders2d;

public class Pair <T1, T2>{
    public T1 first;
    public T2 second;
    private Pair() {}

    public static <T1, T2> Pair<T1, T2> makePair(T1 first, T2 second){
        Pair<T1, T2> result = new Pair<>();
        result.first = first;
        result.second = second;
        return result;
    }

    @Override
    public String toString() {
        return first.toString() + " " + second.toString();
    }
}
