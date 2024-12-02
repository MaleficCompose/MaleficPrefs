package xyz.malefic.prefs.collection

import java.io.Serializable
import xyz.malefic.prefs.Common.Companion.prefs
import xyz.malefic.serialize.SerializationUtil.deserialize
import xyz.malefic.serialize.SerializationUtil.serialize

/**
 * A persistent HashMap that saves its state to preferences.
 *
 * @param K the type of keys maintained by this map, which must be serializable.
 * @param V the type of mapped values, which must be serializable.
 * @property key the prefix used to store the map entries in preferences.
 */
class PersistentHashMap<K : Serializable, V : Serializable>(private val key: String) :
  HashMap<K, V>() {
  init {
    loadFromPreferences()
  }

  /** Loads the map from preferences. */
  @Suppress("UNCHECKED_CAST")
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
   * Associates the specified value with the specified key in this map.
   *
   * @param key key with which the specified value is to be associated.
   * @param value value to be associated with the specified key.
   * @return the previous value associated with key, or null if there was no mapping for key.
   */
  override fun put(key: K, value: V): V? {
    val result = super.put(key, value)
    saveToPreferences()
    return result
  }

  /**
   * Removes the mapping for a key from this map if it is present.
   *
   * @param key key whose mapping is to be removed from the map.
   * @return the previous value associated with key, or null if there was no mapping for key.
   */
  override fun remove(key: K): V? {
    val result = super.remove(key)
    saveToPreferences()
    return result
  }

  /**
   * Only kept for compatibility with the [HashMap] class and automatic cleanup. Use the reset
   * function if you want to clear the preferences.
   */
  override fun clear() {
    super.clear()
  }

  /** Removes all the mappings from this map as well as from Preferences. */
  fun reset() {
    clear()
    prefs.remove(key)
  }

  /**
   * Copies all the mappings from the specified map to this map.
   *
   * @param from mappings to be stored in this map.
   */
  override fun putAll(from: Map<out K, V>) {
    super.putAll(from)
    saveToPreferences()
  }
}
