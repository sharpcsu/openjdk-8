package java.util.function;

@FunctionalInterface
public interface LongSupplier {

    /**
     * Gets a result.
     *
     * @return a result
     */
    long getAsLong();
}
