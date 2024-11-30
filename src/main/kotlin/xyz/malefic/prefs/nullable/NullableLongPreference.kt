package xyz.malefic.prefs.nullable

import kotlin.reflect.KProperty
import xyz.malefic.prefs.PreferenceDelegate
import xyz.malefic.prefs.PrefsCommon.Companion.prefs

/**
 * A class that represents a nullable long preference.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 */
class NullableLongPreference(private val key: String, private val defaultValue: Long? = null) :
  PreferenceDelegate<Long?> {
  /**
   * Gets the nullable long value of the preference.
   *
   * @param thisRef The reference to the object.
   * @param property The property being accessed.
   * @return The nullable long value of the preference.
   */
  override operator fun getValue(thisRef: Any?, property: KProperty<*>): Long? {
    return defaultValue?.let { prefs.getLong(key, it) }
  }

  /**
   * Sets the nullable long value of the preference.
   *
   * @param thisRef The reference to the object.
   * @param property The property being accessed.
   * @param value The new nullable long value to set.
   */
  override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Long?) {
    if (value == null) {
      prefs.remove(key)
    } else {
      prefs.putLong(key, value)
    }
  }
}
