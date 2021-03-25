package sun.misc;

import java.security.*;
import java.lang.reflect.*;

import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

public final class Unsafe {

    private static native void registerNatives();
    static {
        registerNatives();
        sun.reflect.Reflection.registerMethodsToFilter(Unsafe.class, "getUnsafe");
    }

    private Unsafe() {}

    private static final Unsafe theUnsafe = new Unsafe();

    @CallerSensitive
    public static Unsafe getUnsafe() {
        Class<?> caller = Reflection.getCallerClass();
        if (!VM.isSystemDomainLoader(caller.getClassLoader()))
            throw new SecurityException("Unsafe");
        return theUnsafe;
    }

    /// peek and poke operations
    /// (compilers should optimize these to memory ops)

    // These work on object fields in the Java heap.
    // They will not work on elements of packed arrays.

    public native int getInt(Object o, long offset);

    public native void putInt(Object o, long offset, int x);

    public native Object getObject(Object o, long offset);

    public native void putObject(Object o, long offset, Object x);

    /** @see #getInt(Object, long) */
    public native boolean getBoolean(Object o, long offset);
    /** @see #putInt(Object, int, int) */
    public native void    putBoolean(Object o, long offset, boolean x);
    /** @see #getInt(Object, long) */
    public native byte    getByte(Object o, long offset);
    /** @see #putInt(Object, int, int) */
    public native void    putByte(Object o, long offset, byte x);
    /** @see #getInt(Object, long) */
    public native short   getShort(Object o, long offset);
    /** @see #putInt(Object, int, int) */
    public native void    putShort(Object o, long offset, short x);
    /** @see #getInt(Object, long) */
    public native char    getChar(Object o, long offset);
    /** @see #putInt(Object, int, int) */
    public native void    putChar(Object o, long offset, char x);
    /** @see #getInt(Object, long) */
    public native long    getLong(Object o, long offset);
    /** @see #putInt(Object, int, int) */
    public native void    putLong(Object o, long offset, long x);
    /** @see #getInt(Object, long) */
    public native float   getFloat(Object o, long offset);
    /** @see #putInt(Object, int, int) */
    public native void    putFloat(Object o, long offset, float x);
    /** @see #getInt(Object, long) */
    public native double  getDouble(Object o, long offset);
    /** @see #putInt(Object, int, int) */
    public native void    putDouble(Object o, long offset, double x);

    @Deprecated
    public int getInt(Object o, int offset) {
        return getInt(o, (long)offset);
    }

    @Deprecated
    public void putInt(Object o, int offset, int x) {
        putInt(o, (long)offset, x);
    }

    @Deprecated
    public Object getObject(Object o, int offset) {
        return getObject(o, (long)offset);
    }

    @Deprecated
    public void putObject(Object o, int offset, Object x) {
        putObject(o, (long)offset, x);
    }

    @Deprecated
    public boolean getBoolean(Object o, int offset) {
        return getBoolean(o, (long)offset);
    }

    @Deprecated
    public void putBoolean(Object o, int offset, boolean x) {
        putBoolean(o, (long)offset, x);
    }

    @Deprecated
    public byte getByte(Object o, int offset) {
        return getByte(o, (long)offset);
    }

    @Deprecated
    public void putByte(Object o, int offset, byte x) {
        putByte(o, (long)offset, x);
    }

    @Deprecated
    public short getShort(Object o, int offset) {
        return getShort(o, (long)offset);
    }

    @Deprecated
    public void putShort(Object o, int offset, short x) {
        putShort(o, (long)offset, x);
    }

    @Deprecated
    public char getChar(Object o, int offset) {
        return getChar(o, (long)offset);
    }

    @Deprecated
    public void putChar(Object o, int offset, char x) {
        putChar(o, (long)offset, x);
    }

    @Deprecated
    public long getLong(Object o, int offset) {
        return getLong(o, (long)offset);
    }

    @Deprecated
    public void putLong(Object o, int offset, long x) {
        putLong(o, (long)offset, x);
    }

    @Deprecated
    public float getFloat(Object o, int offset) {
        return getFloat(o, (long)offset);
    }

    @Deprecated
    public void putFloat(Object o, int offset, float x) {
        putFloat(o, (long)offset, x);
    }

    @Deprecated
    public double getDouble(Object o, int offset) {
        return getDouble(o, (long)offset);
    }

    @Deprecated
    public void putDouble(Object o, int offset, double x) {
        putDouble(o, (long)offset, x);
    }

    // These work on values in the C heap.

    public native byte    getByte(long address);

    public native void    putByte(long address, byte x);

    /** @see #getByte(long) */
    public native short   getShort(long address);
    /** @see #putByte(long, byte) */
    public native void    putShort(long address, short x);
    /** @see #getByte(long) */
    public native char    getChar(long address);
    /** @see #putByte(long, byte) */
    public native void    putChar(long address, char x);
    /** @see #getByte(long) */
    public native int     getInt(long address);
    /** @see #putByte(long, byte) */
    public native void    putInt(long address, int x);
    /** @see #getByte(long) */
    public native long    getLong(long address);
    /** @see #putByte(long, byte) */
    public native void    putLong(long address, long x);
    /** @see #getByte(long) */
    public native float   getFloat(long address);
    /** @see #putByte(long, byte) */
    public native void    putFloat(long address, float x);
    /** @see #getByte(long) */
    public native double  getDouble(long address);
    /** @see #putByte(long, byte) */
    public native void    putDouble(long address, double x);

    public native long getAddress(long address);

    public native void putAddress(long address, long x);

    /// wrappers for malloc, realloc, free:

    public native long allocateMemory(long bytes);

    public native long reallocateMemory(long address, long bytes);

    public native void setMemory(Object o, long offset, long bytes, byte value);

    public void setMemory(long address, long bytes, byte value) {
        setMemory(null, address, bytes, value);
    }

    public native void copyMemory(Object srcBase, long srcOffset,
                                  Object destBase, long destOffset,
                                  long bytes);
    public void copyMemory(long srcAddress, long destAddress, long bytes) {
        copyMemory(null, srcAddress, null, destAddress, bytes);
    }

    public native void freeMemory(long address);

    /// random queries

    public static final int INVALID_FIELD_OFFSET   = -1;

    @Deprecated
    public int fieldOffset(Field f) {
        if (Modifier.isStatic(f.getModifiers()))
            return (int) staticFieldOffset(f);
        else
            return (int) objectFieldOffset(f);
    }

    @Deprecated
    public Object staticFieldBase(Class<?> c) {
        Field[] fields = c.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (Modifier.isStatic(fields[i].getModifiers())) {
                return staticFieldBase(fields[i]);
            }
        }
        return null;
    }

    public native long staticFieldOffset(Field f);

    public native long objectFieldOffset(Field f);

    public native Object staticFieldBase(Field f);

    public native boolean shouldBeInitialized(Class<?> c);

    public native void ensureClassInitialized(Class<?> c);

    public native int arrayBaseOffset(Class<?> arrayClass);

    /** The value of {@code arrayBaseOffset(boolean[].class)} */
    public static final int ARRAY_BOOLEAN_BASE_OFFSET
            = theUnsafe.arrayBaseOffset(boolean[].class);

    /** The value of {@code arrayBaseOffset(byte[].class)} */
    public static final int ARRAY_BYTE_BASE_OFFSET
            = theUnsafe.arrayBaseOffset(byte[].class);

    /** The value of {@code arrayBaseOffset(short[].class)} */
    public static final int ARRAY_SHORT_BASE_OFFSET
            = theUnsafe.arrayBaseOffset(short[].class);

    /** The value of {@code arrayBaseOffset(char[].class)} */
    public static final int ARRAY_CHAR_BASE_OFFSET
            = theUnsafe.arrayBaseOffset(char[].class);

    /** The value of {@code arrayBaseOffset(int[].class)} */
    public static final int ARRAY_INT_BASE_OFFSET
            = theUnsafe.arrayBaseOffset(int[].class);

    /** The value of {@code arrayBaseOffset(long[].class)} */
    public static final int ARRAY_LONG_BASE_OFFSET
            = theUnsafe.arrayBaseOffset(long[].class);

    /** The value of {@code arrayBaseOffset(float[].class)} */
    public static final int ARRAY_FLOAT_BASE_OFFSET
            = theUnsafe.arrayBaseOffset(float[].class);

    /** The value of {@code arrayBaseOffset(double[].class)} */
    public static final int ARRAY_DOUBLE_BASE_OFFSET
            = theUnsafe.arrayBaseOffset(double[].class);

    /** The value of {@code arrayBaseOffset(Object[].class)} */
    public static final int ARRAY_OBJECT_BASE_OFFSET
            = theUnsafe.arrayBaseOffset(Object[].class);

    public native int arrayIndexScale(Class<?> arrayClass);

    /** The value of {@code arrayIndexScale(boolean[].class)} */
    public static final int ARRAY_BOOLEAN_INDEX_SCALE
            = theUnsafe.arrayIndexScale(boolean[].class);

    /** The value of {@code arrayIndexScale(byte[].class)} */
    public static final int ARRAY_BYTE_INDEX_SCALE
            = theUnsafe.arrayIndexScale(byte[].class);

    /** The value of {@code arrayIndexScale(short[].class)} */
    public static final int ARRAY_SHORT_INDEX_SCALE
            = theUnsafe.arrayIndexScale(short[].class);

    /** The value of {@code arrayIndexScale(char[].class)} */
    public static final int ARRAY_CHAR_INDEX_SCALE
            = theUnsafe.arrayIndexScale(char[].class);

    /** The value of {@code arrayIndexScale(int[].class)} */
    public static final int ARRAY_INT_INDEX_SCALE
            = theUnsafe.arrayIndexScale(int[].class);

    /** The value of {@code arrayIndexScale(long[].class)} */
    public static final int ARRAY_LONG_INDEX_SCALE
            = theUnsafe.arrayIndexScale(long[].class);

    /** The value of {@code arrayIndexScale(float[].class)} */
    public static final int ARRAY_FLOAT_INDEX_SCALE
            = theUnsafe.arrayIndexScale(float[].class);

    /** The value of {@code arrayIndexScale(double[].class)} */
    public static final int ARRAY_DOUBLE_INDEX_SCALE
            = theUnsafe.arrayIndexScale(double[].class);

    /** The value of {@code arrayIndexScale(Object[].class)} */
    public static final int ARRAY_OBJECT_INDEX_SCALE
            = theUnsafe.arrayIndexScale(Object[].class);

    public native int addressSize();

    public static final int ADDRESS_SIZE = theUnsafe.addressSize();

     * Report the size in bytes of a native memory page (whatever that is).
     * This value will always be a power of two.
     */
    public native int pageSize();


    /// random trusted operations from JNI:

    public native Class<?> defineClass(String name, byte[] b, int off, int len,
                                       ClassLoader loader,
                                       ProtectionDomain protectionDomain);

    public native Class<?> defineAnonymousClass(Class<?> hostClass, byte[] data, Object[] cpPatches);


    public native Object allocateInstance(Class<?> cls)
        throws InstantiationException;

    public native void monitorEnter(Object o);

    public native void monitorExit(Object o);

    public native boolean tryMonitorEnter(Object o);

    /** Throw the exception without telling the verifier. */
    public native void throwException(Throwable ee);


    public final native boolean compareAndSwapObject(Object o, long offset,
                                                     Object expected,
                                                     Object x);

    /**
     * 比较和交换
     * @param o 要修改的对象
     * @param offset 要修改变量的偏移量
     * @param expected 修改之前的值
     * @param x 修改之后的值
     * @return
     */
    public final native boolean compareAndSwapInt(Object o,
                                                  long offset,
                                                  int expected,
                                                  int x);

    public final native boolean compareAndSwapLong(Object o, long offset,
                                                   long expected,
                                                   long x);

    public native Object getObjectVolatile(Object o, long offset);

    public native void    putObjectVolatile(Object o, long offset, Object x);

    /** Volatile version of {@link #getInt(Object, long)}  */
    public native int     getIntVolatile(Object o, long offset);

    /** Volatile version of {@link #putInt(Object, long, int)}  */
    public native void    putIntVolatile(Object o, long offset, int x);

    /** Volatile version of {@link #getBoolean(Object, long)}  */
    public native boolean getBooleanVolatile(Object o, long offset);

    /** Volatile version of {@link #putBoolean(Object, long, boolean)}  */
    public native void    putBooleanVolatile(Object o, long offset, boolean x);

    /** Volatile version of {@link #getByte(Object, long)}  */
    public native byte    getByteVolatile(Object o, long offset);

    /** Volatile version of {@link #putByte(Object, long, byte)}  */
    public native void    putByteVolatile(Object o, long offset, byte x);

    /** Volatile version of {@link #getShort(Object, long)}  */
    public native short   getShortVolatile(Object o, long offset);

    /** Volatile version of {@link #putShort(Object, long, short)}  */
    public native void    putShortVolatile(Object o, long offset, short x);

    /** Volatile version of {@link #getChar(Object, long)}  */
    public native char    getCharVolatile(Object o, long offset);

    /** Volatile version of {@link #putChar(Object, long, char)}  */
    public native void    putCharVolatile(Object o, long offset, char x);

    /** Volatile version of {@link #getLong(Object, long)}  */
    public native long    getLongVolatile(Object o, long offset);

    /** Volatile version of {@link #putLong(Object, long, long)}  */
    public native void    putLongVolatile(Object o, long offset, long x);

    /** Volatile version of {@link #getFloat(Object, long)}  */
    public native float   getFloatVolatile(Object o, long offset);

    /** Volatile version of {@link #putFloat(Object, long, float)}  */
    public native void    putFloatVolatile(Object o, long offset, float x);

    /** Volatile version of {@link #getDouble(Object, long)}  */
    public native double  getDoubleVolatile(Object o, long offset);

    /** Volatile version of {@link #putDouble(Object, long, double)}  */
    public native void    putDoubleVolatile(Object o, long offset, double x);

    public native void    putOrderedObject(Object o, long offset, Object x);

    /** Ordered/Lazy version of {@link #putIntVolatile(Object, long, int)}  */
    public native void    putOrderedInt(Object o, long offset, int x);

    /** Ordered/Lazy version of {@link #putLongVolatile(Object, long, long)} */
    public native void    putOrderedLong(Object o, long offset, long x);

    public native void unpark(Object thread);

    public native void park(boolean isAbsolute, long time);

    public native int getLoadAverage(double[] loadavg, int nelems);

    // The following contain CAS-based Java implementations used on
    // platforms not supporting native instructions

    public final int getAndAddInt(Object o, long offset, int delta) {
        int v;
        do {
            v = getIntVolatile(o, offset);
        } while (!compareAndSwapInt(o, offset, v, v + delta));
        return v;
    }

    public final long getAndAddLong(Object o, long offset, long delta) {
        long v;
        do {
            v = getLongVolatile(o, offset);
        } while (!compareAndSwapLong(o, offset, v, v + delta));
        return v;
    }

    public final int getAndSetInt(Object o, long offset, int newValue) {
        int v;
        do {
            v = getIntVolatile(o, offset);
        } while (!compareAndSwapInt(o, offset, v, newValue));
        return v;
    }

    public final long getAndSetLong(Object o, long offset, long newValue) {
        long v;
        do {
            v = getLongVolatile(o, offset);
        } while (!compareAndSwapLong(o, offset, v, newValue));
        return v;
    }

    public final Object getAndSetObject(Object o, long offset, Object newValue) {
        Object v;
        do {
            v = getObjectVolatile(o, offset);
        } while (!compareAndSwapObject(o, offset, v, newValue));
        return v;
    }


    public native void loadFence();

    public native void storeFence();

    public native void fullFence();

    private static void throwIllegalAccessError() {
       throw new IllegalAccessError();
    }

}
