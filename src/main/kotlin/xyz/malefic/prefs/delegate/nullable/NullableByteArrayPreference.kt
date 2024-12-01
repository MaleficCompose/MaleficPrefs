package xyz.malefic.prefs.delegate.nullable

import kotlin.reflect.KProperty
import xyz.malefic.prefs.Common.Companion.prefs
import xyz.malefic.prefs.delegate.PreferenceDelegate

/**
 * A class that represents a nullable byte array preference.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 */
class NullableByteArrayPreference(
  private val key: String,
  private val defaultValue: ByteArray? = null,
) : PreferenceDelegate<ByteArray?> {
  /**
   * Gets the nullable byte array value of the preference.
   *
   * @param thisRef The reference to the object.
   * @param property The property being accessed.
   * @return The nullable byte array value of the preference.
   */
  override operator fun getValue(thisRef: Any?, property: KProperty<*>): ByteArray? {
    return prefs.getByteArray(key, defaultValue)
  }

  /**
   * Sets the nullable byte array value of the preference.
   *
   * @param thisRef The reference to the object.
   * @param property The property being accessed.
   * @param value The new nullable byte array value to set.
   */
  override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: ByteArray?) {
    if (value == null) {
      prefs.remove(key)
    } else {
      prefs.putByteArray(key, value)
    }
  }
}
