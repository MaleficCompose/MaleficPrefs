package xyz.malefic.prefs

import kotlin.reflect.KProperty
import xyz.malefic.prefs.PrefsCommon.Companion.prefs

/**
 * Delegate for string preferences.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference if not set.
 */
class StringPreference(private val key: String, private val defaultValue: String = "") :
  PreferenceDelegate<String> {
  /**
   * Gets the value of the preference.
   *
   * @param thisRef The reference to the object that contains the property.
   * @param property The metadata for the property.
   * @return The value of the preference or the default value if not set.
   */
  override operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
    return prefs[key, defaultValue]
  }

  /**
   * Sets the value of the preference.
   *
   * @param thisRef The reference to the object that contains the property.
   * @param property The metadata for the property.
   * @param value The new value for the preference.
   */
  override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
    prefs.put(key, value)
  }
}
