package org.util;

import java.util.ArrayList;
import java.util.Collections;

public class CollectionFactory {
    public static <T> ArrayList<T> ArrayListOf(T... elements) {
        ArrayList<T> result = new ArrayList<>();
        Collections.addAll(result, elements);
        return result;
    }
}
