package concurrency.stm;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author mishadoff
 */
public final class GlobalContext extends Context {
    private static GlobalContext instance = new GlobalContext();

    private Map<Ref, Object> refMap = Collections.synchronizedMap(new WeakHashMap<Ref, Object>());

    private GlobalContext() { }

    public static GlobalContext get() {
        return instance;
    }

    @Override
    <T> T get(Ref<T> ref) {
        if (!refMap.containsKey(ref)) {
            RefTuple<T, Long> tuple = ref.content;
            refMap.put(ref, tuple.value);
        }
        return (T) refMap.get(ref);
    }
}
