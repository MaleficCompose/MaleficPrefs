package xyz.malefic.prefs.delegate.nullable

import kotlin.reflect.KProperty
import xyz.malefic.prefs.Common.Companion.prefs
import xyz.malefic.prefs.delegate.PreferenceDelegate

/**
 * A class that represents a nullable double preference.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 */
class NullableDoublePreference(private val key: String, private val defaultValue: Double? = null) :
  PreferenceDelegate<Double?> {
  /**
   * Gets the nullable double value of the preference.
   *
   * @param thisRef The reference to the object.
   * @param property The property being accessed.
   * @return The nullable double value of the preference.
   */
  override operator fun getValue(thisRef: Any?, property: KProperty<*>): Double? {
    return defaultValue?.let { prefs.getDouble(key, it) }
  }

  /**
   * Sets the nullable double value of the preference.
   *
   * @param thisRef The reference to the object.
   * @param property The property being accessed.
   * @param value The new nullable double value to set.
   */
  override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Double?) {
    if (value == null) {
      prefs.remove(key)
    } else {
      prefs.putDouble(key, value)
    }
  }
}
