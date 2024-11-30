package xyz.malefic.prefs

import kotlin.reflect.KProperty
import xyz.malefic.prefs.PrefsCommon.Companion.prefs

/**
 * A class that represents a boolean preference.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 */
class BooleanPreference(private val key: String, private val defaultValue: Boolean = false) :
  PreferenceDelegate<Boolean> {
  /**
   * Gets the boolean value of the preference.
   *
   * @param thisRef The reference to the object.
   * @param property The property being accessed.
   * @return The boolean value of the preference.
   */
  override operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
    return prefs.getBoolean(key, defaultValue)
  }

  /**
   * Sets the boolean value of the preference.
   *
   * @param thisRef The reference to the object.
   * @param property The property being accessed.
   * @param value The new boolean value to set.
   */
  override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
    prefs.putBoolean(key, value)
  }
}
