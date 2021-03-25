package java.util.concurrent.atomic;
import java.io.Serializable;

/**
 * LongAdder 引入了分段累加的概念，内部一共有两个参数参与计数：第一个叫作 base，它是一个变量，第二个是 Cell[] ，是一个数组。
 * 其中的 base 是用在竞争不激烈的情况下的，可以直接把累加结果改到 base 变量上。
 * 那么，当竞争激烈的时候，就用到Cell[] 数组了。一旦竞争激烈，各个线程会分散累加到自己所对应的那个 Cell[] 数组的某一个对象中，而不会大家共用同一个
 */
public class LongAdder extends Striped64 implements Serializable {
    private static final long serialVersionUID = 7249069246863182397L;

    /**
     * Creates a new adder with initial sum of zero.
     */
    public LongAdder() {
    }

    /**
     * Adds the given value.
     *
     * @param x the value to add
     */
    public void add(long x) {
        Cell[] as; long b, v; int m; Cell a;
        if ((as = cells) != null || !casBase(b = base, b + x)) {
            boolean uncontended = true;
            if (as == null || (m = as.length - 1) < 0 ||
                (a = as[getProbe() & m]) == null ||
                !(uncontended = a.cas(v = a.value, v + x)))
                longAccumulate(x, null, uncontended);
        }
    }

    /**
     * Equivalent to {@code add(1)}.
     */
    public void increment() {
        add(1L);
    }

    /**
     * Equivalent to {@code add(-1)}.
     */
    public void decrement() {
        add(-1L);
    }

    public long sum() {
        Cell[] as = cells; Cell a;
        long sum = base;
        if (as != null) {
            for (int i = 0; i < as.length; ++i) {
                if ((a = as[i]) != null)
                    sum += a.value;
            }
        }
        return sum;
    }

    public void reset() {
        Cell[] as = cells; Cell a;
        base = 0L;
        if (as != null) {
            for (int i = 0; i < as.length; ++i) {
                if ((a = as[i]) != null)
                    a.value = 0L;
            }
        }
    }

    public long sumThenReset() {
        Cell[] as = cells; Cell a;
        long sum = base;
        base = 0L;
        if (as != null) {
            for (int i = 0; i < as.length; ++i) {
                if ((a = as[i]) != null) {
                    sum += a.value;
                    a.value = 0L;
                }
            }
        }
        return sum;
    }

    public String toString() {
        return Long.toString(sum());
    }

    public long longValue() {
        return sum();
    }

    public int intValue() {
        return (int)sum();
    }

    public float floatValue() {
        return (float)sum();
    }

    public double doubleValue() {
        return (double)sum();
    }

    private static class SerializationProxy implements Serializable {
        private static final long serialVersionUID = 7249069246863182397L;

        private final long value;

        SerializationProxy(LongAdder a) {
            value = a.sum();
        }

        private Object readResolve() {
            LongAdder a = new LongAdder();
            a.base = value;
            return a;
        }
    }

    private Object writeReplace() {
        return new SerializationProxy(this);
    }

    private void readObject(java.io.ObjectInputStream s)
        throws java.io.InvalidObjectException {
        throw new java.io.InvalidObjectException("Proxy required");
    }

}
