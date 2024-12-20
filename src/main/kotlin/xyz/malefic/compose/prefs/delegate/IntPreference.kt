package xyz.malefic.compose.prefs.delegate

import xyz.malefic.compose.prefs.Common
import java.util.prefs.Preferences
import kotlin.reflect.KProperty

/**
 * A class that provides a delegate for storing and retrieving int preferences.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 * @property prefs The Preferences instance used to store the preference.
 */
class IntPreference(
    private val key: String,
    private val defaultValue: Int = 0,
    private val prefs: Preferences = Common.prefs,
) : PreferenceDelegate<Int> {
    /**
     * Gets the value of the preference.
     *
     * @param thisRef The reference to the object that contains the property.
     * @param property The metadata for the property.
     * @return The value of the preference.
     */
    override operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): Int = prefs.getInt(key, defaultValue)

    /**
     * Sets the value of the preference.
     *
     * @param thisRef The reference to the object that contains the property.
     * @param property The metadata for the property.
     * @param value The new value for the preference.
     */
    override operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: Int,
    ) {
        prefs.putInt(key, value)
    }
}
