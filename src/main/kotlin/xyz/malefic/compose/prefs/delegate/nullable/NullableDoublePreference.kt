package xyz.malefic.compose.prefs.delegate.nullable

import xyz.malefic.compose.prefs.Common
import xyz.malefic.compose.prefs.delegate.PreferenceDelegate
import java.util.prefs.Preferences
import kotlin.reflect.KProperty

/**
 * A class that provides a delegate for storing and retrieving nullable double preferences.
 *
 * @property key The key for the preference.
 * @property defaultValue The default value for the preference.
 * @property prefs The Preferences instance used to store the preference.
 */
class NullableDoublePreference(
    private val key: String,
    private val defaultValue: Double? = null,
    private val prefs: Preferences = Common.prefs,
) : PreferenceDelegate<Double?> {
    /**
     * Gets the nullable double value of the preference.
     *
     * @param thisRef The reference to the object.
     * @param property The property being accessed.
     * @return The nullable double value of the preference.
     */
    override operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): Double? = defaultValue?.let { prefs.getDouble(key, it) }

    /**
     * Sets the nullable double value of the preference.
     *
     * @param thisRef The reference to the object.
     * @param property The property being accessed.
     * @param value The new nullable double value to set.
     */
    override operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: Double?,
    ) {
        if (value == null) {
            prefs.remove(key)
        } else {
            prefs.putDouble(key, value)
        }
    }
}
