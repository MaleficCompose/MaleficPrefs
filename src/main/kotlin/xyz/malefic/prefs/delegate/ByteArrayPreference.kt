package xyz.malefic.prefs.delegate

import java.util.prefs.Preferences
import kotlin.reflect.KProperty
import xyz.malefic.prefs.Common

/**
 * A class that provides a delegate for storing and retrieving byte array preferences.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 * @property prefs The Preferences instance used to store the preference.
 */
class ByteArrayPreference(
  private val key: String,
  private val defaultValue: ByteArray = ByteArray(0),
  private val prefs: Preferences = Common.prefs,
) : PreferenceDelegate<ByteArray> {

  /**
   * Retrieves the byte array value from preferences.
   *
   * @param thisRef The reference to the object that contains the property.
   * @param property The metadata for the property.
   * @return The byte array value stored in preferences, or the default value if not found.
   */
  override operator fun getValue(thisRef: Any?, property: KProperty<*>): ByteArray {
    return prefs.getByteArray(key, defaultValue)
  }

  /**
   * Stores the byte array value in preferences.
   *
   * @param thisRef The reference to the object that contains the property.
   * @param property The metadata for the property.
   * @param value The byte array value to store in preferences.
   */
  override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: ByteArray) {
    prefs.putByteArray(key, value)
  }
}
