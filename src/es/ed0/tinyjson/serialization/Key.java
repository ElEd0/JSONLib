/**
 * Created by Ed0 in 9 feb. 2019
 */
package es.ed0.tinyjson.serialization;

/**
 * This annotation may be used to specify the key name under which a field value will be mapped when serializing into a json. 
 * This is also the key name which will be used to search th value inside the respective json when deserializing.<br>
 * Ie <br>
 * @Key("key1")<br>
 * public String field = "value";<br>
 * Will result in the json data {"key1":"value"}, which can later be correctly deserialized 
 * if the key annotation is present in the deserializing class type
 */
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(java.lang.annotation.ElementType.FIELD)
public @interface Key {
	public String value();
}
