package com.android.outbaselibrary.utils;

public class ArrayUtils {

    public static final int INDEX_NOT_FOUND = -1;

    public static boolean isEmpty(Object[] array) {
        return (array == null) || (array.length == 0);
    }

    public static boolean isEmpty(long[] array) {
        return (array == null) || (array.length == 0);
    }

    public static boolean isEmpty(int[] array) {
        return (array == null) || (array.length == 0);
    }

    public static boolean isEmpty(short[] array) {
        return (array == null) || (array.length == 0);
    }

    public static boolean isEmpty(char[] array) {
        return (array == null) || (array.length == 0);
    }

    public static boolean isEmpty(byte[] array) {
        return (array == null) || (array.length == 0);
    }

    public static boolean isEmpty(double[] array) {
        return (array == null) || (array.length == 0);
    }

    public static boolean isEmpty(float[] array) {
        return (array == null) || (array.length == 0);
    }

    public static boolean isEmpty(boolean[] array) {
        return (array == null) || (array.length == 0);
    }

    public static boolean contains(int[] array, int valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    public static int indexOf(int[] array, int valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(int[] array, int valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static <T> boolean isIn(T value, T[] array) {
        for (T ele : array) {
            if (value.equals(ele)) {
                return true;
            }
        }
        return false;
    }
}
