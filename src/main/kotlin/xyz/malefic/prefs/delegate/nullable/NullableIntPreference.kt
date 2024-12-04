package xyz.malefic.prefs.delegate.nullable

import java.util.prefs.Preferences
import kotlin.reflect.KProperty
import xyz.malefic.prefs.Common
import xyz.malefic.prefs.delegate.PreferenceDelegate

/**
 * A class that provides a delegate for storing and retrieving nullable int preferences.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 * @property prefs The Preferences instance used to store the preference.
 */
class NullableIntPreference(
  private val key: String,
  private val defaultValue: Int? = null,
  private val prefs: Preferences = Common.prefs,
) : PreferenceDelegate<Int?> {
  /**
   * Gets the nullable int value of the preference.
   *
   * @param thisRef The reference to the object.
   * @param property The property being accessed.
   * @return The nullable int value of the preference.
   */
  override operator fun getValue(thisRef: Any?, property: KProperty<*>): Int? {
    return defaultValue?.let { prefs.getInt(key, it) }
  }

  /**
   * Sets the nullable int value of the preference.
   *
   * @param thisRef The reference to the object.
   * @param property The property being accessed.
   * @param value The new nullable int value to set.
   */
  override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Int?) {
    if (value == null) {
      prefs.remove(key)
    } else {
      prefs.putInt(key, value)
    }
  }
}
