package xyz.malefic.prefs.delegate

import java.util.prefs.Preferences
import kotlin.reflect.KProperty
import xyz.malefic.prefs.Common

/**
 * A class that provides a delegate for storing and retrieving long preferences.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 * @property prefs The Preferences instance used to store the preference.
 */
class LongPreference(
  private val key: String,
  private val defaultValue: Long = 0L,
  private val prefs: Preferences = Common.prefs,
) : PreferenceDelegate<Long> {
  /**
   * Retrieves the long value from preferences.
   *
   * @param thisRef The reference to the object that contains the property.
   * @param property The metadata for the property.
   * @return The long value stored in preferences, or the default value if not found.
   */
  override operator fun getValue(thisRef: Any?, property: KProperty<*>): Long {
    return prefs.getLong(key, defaultValue)
  }

  /**
   * Stores the long value in preferences.
   *
   * @param thisRef The reference to the object that contains the property.
   * @param property The metadata for the property.
   * @param value The long value to store in preferences.
   */
  override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Long) {
    prefs.putLong(key, value)
  }
}
