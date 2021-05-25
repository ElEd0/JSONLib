/**
 * Created by Ed0 in 9 feb. 2019
 */
package es.ed0.tinyjson.serialization;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import es.ed0.tinyjson.JSONArray;
import es.ed0.tinyjson.JSONException;
import es.ed0.tinyjson.JSONObject;

/**
 * Class for serializing plain java objects into JSONObjects, arrays into JSONArrays and viceversa.
 */
public class JSONSerializer {
	
	
	/**
	 * Serialize an Object into a JSONObject. The field name will be used as mapping key unless other value
	 * was specified using the {@link Key} annotation, for the serialization to happen the field must be visible (explicitly be public).
	 * Alternatively the class can contain a getter for the field using the standard naming rules: 
	 * getFieldName() or isFieldName() for boolean types for the field named fieldName.
	 * Non-primitive field types like objects will also be serialized into jsons inside the resulting json, same with array types.
	 * The annotation {@link Ignore} can be used to discard fields during serialization and/or deserialization.
	 * @param obj Object to serialize
	 * @return Serialized json
	 * @throws JSONException If an error occurred during serialization.
	 * This could be caused by an inaccessible constructor or field.
	 */
	public JSONObject serializeToJSONObject(Object obj) throws JSONException {
		if (obj == null)
			return null;
		if (obj instanceof JSONObject)
			return reserializeJSONObject((JSONObject) obj);
		else if (isPrimitive(obj.getClass()))
			throw new JSONException("Object is a primitive type (" + obj.getClass().getSimpleName() + ") and "
					+ "cannot be serialized into a Json. Use #serialize(Object) to serialize all object types.");
		else if (obj instanceof JSONArray) 
			throw new JSONException("Cannot serialize the json type " + obj.getClass().getSimpleName() + " into a JSONObject");
		else if (obj.getClass().isArray())
			throw new JSONException("Cannot serialize type " + obj.getClass().getSimpleName() + ". "
					+ "Use #serializeToJSONArray to serialize array types.");
		else
			return objectToJson(obj);
	}
	

	/**
	 * Serialize an array into a JSONArray, note this will only work with array types (i.e int[]) but not with collections.
	 * Use collection.toArray() to return a usable array with the collection contents. A JSONArray may be passed for re-serialization.
	 * @param obj Array to serialize
	 * @return Serialized JSONArray
	 * @throws JSONException If an error occurred during serialization.
	 */
	public JSONArray serializeToJSONArray(Object obj) throws JSONException {
		if (obj == null)
			return null;
		if (obj instanceof JSONArray)
			return reserializeJSONArray((JSONArray) obj);
		else if (isPrimitive(obj.getClass()))
			throw new JSONException("Object is a primitive type (" + obj.getClass().getSimpleName() + ") and "
					+ "cannot be serialized into a Json. Use #serialize(Object) to serialize all object types.");
		else if (obj instanceof JSONObject) 
			throw new JSONException("Cannot serialize the json type " + obj.getClass().getSimpleName() + " into a JSONArray.");
		else if (!obj.getClass().isArray())
			throw new JSONException("Cannot serialize type " + obj.getClass().getSimpleName() + ". "
					+ "Object must be an array. Use #serializeToJSONObject to serialize objects");
		else
			return arrayToJsonArray(obj);
	}
	
	/**
	 * Re-serialize a JSONObject so any object or array contained in it will be serialized into its json counter part
	 * @param obj JSONObject to re-serialize
	 * @return An identical json fully serialized
	 * @throws JSONException If any of the contents failed to serialize
	 */
	public JSONObject reserializeJSONObject(JSONObject obj) throws JSONException {
		final JSONObject result = new JSONObject();
		for (String key : obj.keySet())
			result.put(key, serialize(obj.opt(key)));
		return result;
	}

	/**
	 * Re-serialize a JSONArray so any object or array contained in it will be serialized into its json counter part
	 * @param obj JSONArray to re-serialize
	 * @return An identical json fully serialized
	 * @throws JSONException If any of the contents failed to serialize
	 */
	public JSONArray reserializeJSONArray(JSONArray obj) throws JSONException {
		final JSONArray result = new JSONArray();
		for (Object value : obj)
			result.add(serialize(value));
		return result;
	}
	
	/**
	 * Serializes any objects into its serialized counter part.
	 * Primitive types will return themselves. JSONObjects and JSONArrays will be re-serialized,
	 * So any object contained inside them will be serialized into a resulting json containing only jsons and safe to parse. 
	 * Arrays will be serialized into a JSONArray and other objects will be serialized into JSONObjects. null will return null.
	 * @param obj Objects to serialize or re-serialize
	 * @return serialized object, this can be a primitive type a JSON or null
	 * @throws JSONException If the serialization could not be done
	 */
	public Object serialize(Object obj) throws JSONException {
		if (obj == null)
			return null;
		if (isPrimitive(obj.getClass()))
			return obj;	
		else if (obj instanceof JSONObject)
			return reserializeJSONObject((JSONObject) obj);
		else if (obj instanceof JSONArray)
			return reserializeJSONArray((JSONArray) obj);
		else if (obj.getClass().isArray())
			return arrayToJsonArray(obj);
		else
			return objectToJson(obj);
	}
	
	/**
	 * Deserializes a JSON into an object of the specified type. Like so 
	 * <code>Pojo pojo = deserialize(json, Pojo.class)</code>. This also works with JSONArrays, 
	 * which can only be deserialized into array types, <code>Pojo[] pojo = deserialize(jsonArray, Pojo[].class)</code>.<br>
	 * A class must contain an empty constructor for it to be deserializable, the field name will be used as key when searching in the json
	 * unless other value was specified using the {@link Key} annotation. For the deserialization to happen the field must be visible (explicitly be public).
	 * Alternatively the class can contain a setter for the field using the standard naming rules: 
	 * setFieldName() for the field named fieldName. The annotation {@link Ignore} can be used to discard fields during serialization and/or deserialization.
	 * @param obj Object to deserialize, commonly a json
	 * @param clazz Class type to deserialize to
	 * @param <T> Class type
	 * @return Deserialized object of type clazz
	 * @throws JSONException If the Object could not be deserialized for any reason. 
	 */
	public <T> T deserialize(Object obj, Class<T> clazz) throws JSONException {
		if (obj == null)
			return null;
		if (isPrimitive(clazz))
			return getPrimitiveAs(obj, clazz);
		else if (obj instanceof JSONObject)
			return jsonToObject((JSONObject) obj, clazz);
		else if (obj instanceof JSONArray) {
			if (clazz.isArray())
				return jsonArrayToArray((JSONArray) obj, clazz);
			else
				throw new JSONException("The type " + clazz.getSimpleName() + " is an array. A JSONArray was expected");
		}
		throw new JSONException("Object could not be deserialized. Object type: "
				+ obj.getClass().getSimpleName() + " | target Class type: " + clazz.getSimpleName());
	}
	
	
	private JSONObject objectToJson(Object o) throws JSONException {
		JSONObject result = new JSONObject();
		Class<?> clazz = o.getClass();		

		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(Ignore.class))
				continue;
			
			String key = (field.isAnnotationPresent(Key.class) ?
					field.getAnnotation(Key.class).value() : field.getName());
			Object value = null;
			boolean accessible = true;
			
			try {
				if (!Modifier.isPrivate(field.getModifiers()) && !Modifier.isProtected(field.getModifiers())) {
					value = field.get(o);
				} else {
					Method getter = clazz.getDeclaredMethod(getGetterName(field));
					if (Modifier.isPublic(getter.getModifiers())) {
						value = getter.invoke(o);
					} else 
						accessible = false;
				}
				
				if (accessible)
					result.put(key, serialize(value));
				else {
					// TODO getter not found or field private
				}
				
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new JSONException("The field " + key + " in object " + clazz.getSimpleName() + " is inaccessible", e);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new JSONException("The field " + key + " in object " + clazz.getSimpleName() + " is inaccessible. "
						+ "If the field is private create a getter for it: "
						+ getGetterName(field) + "()", e);
			} catch (InvocationTargetException e) {
				throw new JSONException("The getter " + getGetterName(field) + " for field " + field.getName()
					+ " is accessible but throws an exception", e);
			}
		}
		return result;
	}

	private <T> T jsonToObject(JSONObject json, Class<T> clazz) throws JSONException {
		T result = null;
		try {
			result = clazz.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| SecurityException e1) {
			throw new JSONException("Cannot instantiate " + clazz.getSimpleName(), e1);
		} catch (NoSuchMethodException e2) {
			throw new JSONException("Cannot find empty constructor in class " + clazz.getSimpleName());
		}

		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(Ignore.class))
				continue;
			
			String key = (field.isAnnotationPresent(Key.class) ?
					field.getAnnotation(Key.class).value() : field.getName());
			
			Object value = null;
			try { value = json.get(key); } catch (JSONException e) { continue; /* key not present */ }

			try {				
				if (!Modifier.isPrivate(field.getModifiers()) && !Modifier.isProtected(field.getModifiers())) {
					field.set(result, deserialize(value, field.getType()));
				} else {
					Method setter = clazz.getDeclaredMethod(getSetterName(field), field.getType());
					if (Modifier.isPublic(setter.getModifiers())) {
						setter.invoke(result, deserialize(value, field.getType()));
						
					} // TODO setter is private
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new JSONException("The field " + key + " in object " + clazz.getSimpleName() + " is inaccessible", e);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new JSONException("The field " + key + " in object " + clazz.getSimpleName() + " is inaccessible. "
						+ "If the field is private create a setter for it: "
						+ getSetterName(field) + "(" + field.getType().getSimpleName() + " "
						+ field.getName() + ")");
			} catch (InvocationTargetException e) {
				throw new JSONException("The setter " + getSetterName(field) + " for field " + field.getName()
					+ " is accessible but throws an exception", e);
			}
		}
		return result;
	}
	
	/**
	 * Receives a simple array and serializes it into an ordered JSONArray. 
	 * See {@link #serialize(Object)} for more information on how the array items will be serialized.
	 * @param obj array as an object
	 * @return JSONArray containing contents
	 * @throws JSONException if serialization failed.
	 */
	private JSONArray arrayToJsonArray(Object obj) throws JSONException {
		final JSONArray result = new JSONArray();
		for (int i = 0; i < Array.getLength(obj); i++)
			result.add(serialize(Array.get(obj, i)));
		return result;
	}
	
	/**
	 * Receives a JSONArray and a simple array type and returns a deserialized version of the json
	 * using the array component type. See {@link #deserialize(Object, Class)} for more information on how 
	 * the contents will be deserialized.
	 * @param array JSONArray to deserialize
	 * @param clazz array class to return
	 * @return deserialized array
	 * @throws JSONException If deserialization failed.
	 */
	private <T> T jsonArrayToArray(JSONArray array, Class<T> clazz) throws JSONException {
		final Object objectArray = Array.newInstance(clazz.getComponentType(), array.size());
		for (int i = 0; i < array.size(); i++)
			Array.set(objectArray, i, deserialize(array.get(i), clazz.getComponentType()));
		return clazz.cast(objectArray);
	}

	/**
	 * Casts the primitive object to the given primitive type. Valid primitive objects and types
	 * follow: <code>boolean</code>, <code>byte</code>, <code>short</code>, <code>int</code>, 
	 * <code>float</code>, <code>double</code>, <code>long</code>, <code>char</code>, 
	 * <code>Boolean</code>, <code>Byte</code>, <code>Short</code>, <code>Integer</code>, 
	 * <code>Float</code>, <code>Double</code>, <code>Long</code>, <code>Character</code> and <code>String</code>
	 * @param obj primitive to cast
	 * @param clazz target primitive type
	 * @param <T> Class type
	 * @return the new primitive
	 * @throws JSONException if the given object is not a primitive or the casting failed
	 */
	@SuppressWarnings("unchecked")
	public <T> T getPrimitiveAs(Object obj, Class<T> clazz) throws JSONException {
		Object result = null;
		T ret = null;
		try {
			if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) result = Boolean.valueOf(obj.toString());
			else if (clazz.equals(Byte.class) || clazz.equals(byte.class)) result = Byte.valueOf(obj.toString());
			else if (clazz.equals(Short.class) || clazz.equals(short.class)) result = Short.valueOf(obj.toString());
			else if (clazz.equals(Integer.class) || clazz.equals(int.class)) result = Integer.valueOf(obj.toString());
			else if (clazz.equals(Float.class) || clazz.equals(float.class)) result = Float.valueOf(obj.toString());
			else if (clazz.equals(Double.class) || clazz.equals(double.class)) result = Double.valueOf(obj.toString());
			else if (clazz.equals(Long.class) || clazz.equals(long.class)) result = Long.valueOf(obj.toString());
			else if (clazz.equals(Character.class) || clazz.equals(char.class)) result = (char) obj;
			else if (clazz.equals(String.class)) result = obj.toString();
			else
				throw new JSONException("The class " + clazz.getSimpleName() + " could not be identified to a primitive type");
			
			ret = (T) result;
			
		} catch (NumberFormatException | ClassCastException e) {
			throw new JSONException("The object type " + obj.getClass().getSimpleName()
					+ " is not castable to " + clazz.getSimpleName());
		}
		return ret;
	}
	
	/**
	 * Returns whether the given class is a primitive type class. It will output true if the class equals 
	 * any of the following: <code>boolean</code>, <code>byte</code>, <code>short</code>, <code>int</code>, 
	 * <code>float</code>, <code>double</code>, <code>long</code>, <code>char</code>, 
	 * <code>Boolean</code>, <code>Byte</code>, <code>Short</code>, <code>Integer</code>, 
	 * <code>Float</code>, <code>Double</code>, <code>Long</code>, <code>Character</code> and <code>String</code>.<br>
	 * @param clazz Class type
	 * @return true if the class is a valid primitive, false otherwise
	 */
	public boolean isPrimitive(Class<?> clazz) {
		if (clazz == null)
			return false;
		if (clazz.isPrimitive())
			return true;
		else if (clazz.equals(Boolean.class) || clazz.equals(Byte.class)
				|| clazz.equals(Short.class) || clazz.equals(Integer.class)
				|| clazz.equals(Float.class) || clazz.equals(Double.class)
				|| clazz.equals(Long.class)  || clazz.equals(Character.class)
				|| clazz.equals(String.class))
			return true;
		else
			return false;
	}

	/**
	 * Returns the standard getter method name for the given field name.<br>
	 * Eg: field with name userId will result in the getter name getUserId. 
	 * The boolean field alive will result in isAlive.
	 * @param f field
	 * @return getter name
	 */
	public static String getGetterName(Field f) {
		final StringBuilder sb = new StringBuilder();
		if (f.getType().equals(Boolean.class) || f.getType().equals(boolean.class))
			sb.append("is");
		else
			sb.append("get");
		sb.append(f.getName().substring(0, 1).toUpperCase());
		sb.append(f.getName().substring(1));
		return sb.toString();
	}

	/**
	 * Returns the standard setter method name for the given field name.<br>
	 * Eg: field with name userId will result in the setter name setUserId 
	 * @param f field
	 * @return setter name
	 */
	public static String getSetterName(Field f) {
		final StringBuilder sb = new StringBuilder();
		sb.append("set");
		sb.append(f.getName().substring(0, 1).toUpperCase());
		sb.append(f.getName().substring(1));
		return sb.toString();
	}
	
}
