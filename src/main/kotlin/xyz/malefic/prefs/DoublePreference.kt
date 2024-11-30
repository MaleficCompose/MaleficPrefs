package xyz.malefic.prefs

import kotlin.reflect.KProperty
import xyz.malefic.prefs.PrefsCommon.Companion.prefs

/**
 * A class that provides a delegate for storing and retrieving double preferences.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 */
class DoublePreference(private val key: String, private val defaultValue: Double = 0.0) :
  PreferenceDelegate<Double> {
  /**
   * Retrieves the double value from preferences.
   *
   * @param thisRef The reference to the object that contains the property.
   * @param property The metadata for the property.
   * @return The double value stored in preferences, or the default value if not found.
   */
  override operator fun getValue(thisRef: Any?, property: KProperty<*>): Double {
    return prefs.getDouble(key, defaultValue)
  }

  /**
   * Stores the double value in preferences.
   *
   * @param thisRef The reference to the object that contains the property.
   * @param property The metadata for the property.
   * @param value The double value to store in preferences.
   */
  override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Double) {
    prefs.putDouble(key, value)
  }
}
