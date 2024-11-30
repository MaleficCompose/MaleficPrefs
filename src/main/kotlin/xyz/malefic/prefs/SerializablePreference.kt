package xyz.malefic.prefs

import java.io.Serializable
import kotlin.reflect.KProperty
import xyz.malefic.prefs.PrefsCommon.Companion.prefs
import xyz.malefic.serialize.SerializationUtil.deserialize
import xyz.malefic.serialize.SerializationUtil.serialize

/**
 * A class that provides a delegate for storing and retrieving serializable preferences.
 *
 * @param T The type of the serializable object.
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 */
class SerializablePreference<T : Serializable>(
  private val key: String,
  private val defaultValue: T,
) : PreferenceDelegate<T> {
  /**
   * Retrieves the serialized value from preferences.
   *
   * @param thisRef The reference to the object that contains the property.
   * @param property The metadata for the property.
   * @return The deserialized value stored in preferences, or the default value if not found.
   */
  override operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
    val serializedValue = prefs.getByteArray(key, serialize(defaultValue))
    val deserializedValue = deserialize(serializedValue, defaultValue::class.java)
    return deserializedValue ?: defaultValue
  }

  /**
   * Stores the serialized value in preferences.
   *
   * @param thisRef The reference to the object that contains the property.
   * @param property The metadata for the property.
   * @param value The value to store in preferences.
   */
  override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    prefs.putByteArray(key, serialize(value))
  }
}
