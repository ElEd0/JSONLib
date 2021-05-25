/**
 * Created by Ed0 in 9 feb. 2019
 */
package es.ed0.tinyjson.serialization;

/**
 * This annotation may be used in a field to ignore it during serialization and deserialization. 
 * If you don't want a field to have presence in the resulting json, ignore it with this annotation.
 * During deserialization, if a value for an ignored field is found, it will be discarded, as if it was not found.
 */
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(java.lang.annotation.ElementType.FIELD)
public @interface Ignore {}