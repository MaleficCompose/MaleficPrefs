package xyz.malefic.compose.prefs.collection

import xyz.malefic.compose.prefs.Common
import xyz.malefic.compose.serialize.SerializationUtil.deserialize
import xyz.malefic.compose.serialize.SerializationUtil.serialize
import java.io.Serializable
import java.util.prefs.Preferences

/**
 * A persistent implementation of a HashMap that saves its state to preferences.
 *
 * @param K the type of keys maintained by this map, which must be Serializable
 * @param V the type of mapped values, which must be Serializable
 * @param key the key used to store the map in preferences
 * @param prefs the preferences instance used to store the map
 */
class PersistentHashMap<K : Serializable, V : Serializable>(
    private val key: String,
    private val prefs: Preferences = Common.prefs,
) : HashMap<K, V>() {
    init {
        loadFromPreferences()
    }

    /** Loads the map from preferences. */
    private fun loadFromPreferences() {
        clear()
        val serializedMap = prefs.getByteArray(key, null)
        if (serializedMap != null) {
            val deserializedMap = deserialize(serializedMap, HashMap::class.java) as? HashMap<K, V>
            if (deserializedMap != null) {
                putAll(deserializedMap)
            }
        }
    }

    /** Saves the map to preferences. */
    private fun saveToPreferences() {
        val serializedMap = serialize(HashMap(this))
        prefs.putByteArray(key, serializedMap)
    }

    /**
     * Associates the specified value with the specified key in this map and saves the map to
     * preferences.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no mapping for key
     */
    override fun put(
        key: K,
        value: V,
    ): V? {
        val result = super.put(key, value)
        saveToPreferences()
        return result
    }

    /**
     * Copies all of the mappings from the specified map to this map and saves the map to preferences.
     *
     * @param from mappings to be stored in this map
     */
    override fun putAll(from: Map<out K, V>) {
        super.putAll(from)
        saveToPreferences()
    }

    /**
     * Removes the mapping for a key from this map if it is present and saves the map to preferences.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no mapping for key
     */
    override fun remove(key: K): V? {
        val result = super.remove(key)
        saveToPreferences()
        return result
    }

    /**
     * Clears the map. This method is only kept for compatibility with [HashMap] and automatic
     * cleanup. Use [reset] to clear the map and remove it from preferences.
     */
    override fun clear() {
        super.clear()
    }

    /** Clears the map and removes it from preferences. */
    fun reset() {
        clear()
        prefs.remove(key)
    }
}
