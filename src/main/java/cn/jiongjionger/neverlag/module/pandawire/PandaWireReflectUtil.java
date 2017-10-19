package cn.jiongjionger.neverlag.module.pandawire;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class PandaWireReflectUtil {

	/*
	 * @author md_5
	 */

	public static <T> T get(final Object obj, final Field field, final Class<T> type) {
		try {
			return type.cast(setAccessible(field).get(obj));
		} catch (ReflectiveOperationException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static <T> T getOfT(final Object obj, final Class<T> type) {
		for (final Field field : obj.getClass().getDeclaredFields()) {
			if (type.equals(field.getType())) {
				return get(obj, field, type);
			}
		}
		return null;
	}

	public static <T extends AccessibleObject> T setAccessible(T object) {
		object.setAccessible(true);
		return object;
	}

	public static void setStatic(final String name, final Class<?> clazz, final Object val) {
		try {
			final Field field = clazz.getDeclaredField(name);
			setAccessible(Field.class.getDeclaredField("modifiers")).setInt(field, field.getModifiers() & ~Modifier.FINAL);
			setAccessible(Field.class.getDeclaredField("root")).set(field, null);
			setAccessible(Field.class.getDeclaredField("overrideFieldAccessor")).set(field, null);
			setAccessible(field).set(null, val);
		} catch (ReflectiveOperationException ex) {
			ex.printStackTrace();
		}
	}
}
