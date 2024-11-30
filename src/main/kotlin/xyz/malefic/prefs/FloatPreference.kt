package xyz.malefic.prefs

import kotlin.reflect.KProperty
import xyz.malefic.prefs.PrefsCommon.Companion.prefs

/**
 * A class that provides a delegate for storing and retrieving float preferences.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 */
class FloatPreference(private val key: String, private val defaultValue: Float = 0f) :
  PreferenceDelegate<Float> {
  /**
   * Retrieves the float value from preferences.
   *
   * @param thisRef The reference to the object that contains the property.
   * @param property The metadata for the property.
   * @return The float value stored in preferences, or the default value if not found.
   */
  override operator fun getValue(thisRef: Any?, property: KProperty<*>): Float {
    return prefs.getFloat(key, defaultValue)
  }

  /**
   * Stores the float value in preferences.
   *
   * @param thisRef The reference to the object that contains the property.
   * @param property The metadata for the property.
   * @param value The float value to store in preferences.
   */
  override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Float) {
    prefs.putFloat(key, value)
  }
}
