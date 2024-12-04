package xyz.malefic.prefs.delegate

import java.util.prefs.Preferences
import kotlin.reflect.KProperty
import xyz.malefic.prefs.Common

/**
 * A class that provides a delegate for storing and retrieving boolean preferences.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 * @property prefs The Preferences instance used to store the preference.
 */
class BooleanPreference(
  private val key: String,
  private val defaultValue: Boolean = false,
  private val prefs: Preferences = Common.prefs,
) : PreferenceDelegate<Boolean> {
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
