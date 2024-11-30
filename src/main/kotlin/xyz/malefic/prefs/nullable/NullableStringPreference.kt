package xyz.malefic.prefs.nullable

import kotlin.reflect.KProperty
import xyz.malefic.prefs.PreferenceDelegate
import xyz.malefic.prefs.PrefsCommon.Companion.prefs

/**
 * A class that represents a nullable string preference.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 */
class NullableStringPreference(private val key: String, private val defaultValue: String? = null) :
  PreferenceDelegate<String?> {
  /**
   * Gets the nullable string value of the preference.
   *
   * @param thisRef The reference to the object.
   * @param property The property being accessed.
   * @return The nullable string value of the preference.
   */
  override operator fun getValue(thisRef: Any?, property: KProperty<*>): String? {
    return prefs.get(key, defaultValue)
  }

  /**
   * Sets the nullable string value of the preference.
   *
   * @param thisRef The reference to the object.
   * @param property The property being accessed.
   * @param value The new nullable string value to set.
   */
  override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
    if (value == null) {
      prefs.remove(key)
    } else {
      prefs.put(key, value)
    }
  }
}
