package xyz.malefic.prefs.nullable

import kotlin.reflect.KProperty
import xyz.malefic.prefs.PreferenceDelegate
import xyz.malefic.prefs.PrefsCommon.Companion.prefs

/**
 * A class that represents a nullable float preference.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 */
class NullableFloatPreference(private val key: String, private val defaultValue: Float? = null) :
  PreferenceDelegate<Float?> {
  /**
   * Gets the nullable float value of the preference.
   *
   * @param thisRef The reference to the object.
   * @param property The property being accessed.
   * @return The nullable float value of the preference.
   */
  override operator fun getValue(thisRef: Any?, property: KProperty<*>): Float? {
    return defaultValue?.let { prefs.getFloat(key, it) }
  }

  /**
   * Sets the nullable float value of the preference.
   *
   * @param thisRef The reference to the object.
   * @param property The property being accessed.
   * @param value The new nullable float value to set.
   */
  override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Float?) {
    if (value == null) {
      prefs.remove(key)
    } else {
      prefs.putFloat(key, value)
    }
  }
}
