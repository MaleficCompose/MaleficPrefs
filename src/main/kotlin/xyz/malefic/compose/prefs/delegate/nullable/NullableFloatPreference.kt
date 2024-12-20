package xyz.malefic.compose.prefs.delegate.nullable

import xyz.malefic.compose.prefs.Common
import xyz.malefic.compose.prefs.delegate.PreferenceDelegate
import java.util.prefs.Preferences
import kotlin.reflect.KProperty

/**
 * A class that provides a delegate for storing and retrieving nullable float preferences.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 * @property prefs The Preferences instance used to store the preference.
 */
class NullableFloatPreference(
    private val key: String,
    private val defaultValue: Float? = null,
    private val prefs: Preferences = Common.prefs,
) : PreferenceDelegate<Float?> {
    /**
     * Gets the nullable float value of the preference.
     *
     * @param thisRef The reference to the object.
     * @param property The property being accessed.
     * @return The nullable float value of the preference.
     */
    override operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): Float? = defaultValue?.let { prefs.getFloat(key, it) }

    /**
     * Sets the nullable float value of the preference.
     *
     * @param thisRef The reference to the object.
     * @param property The property being accessed.
     * @param value The new nullable float value to set.
     */
    override operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: Float?,
    ) {
        if (value == null) {
            prefs.remove(key)
        } else {
            prefs.putFloat(key, value)
        }
    }
}
