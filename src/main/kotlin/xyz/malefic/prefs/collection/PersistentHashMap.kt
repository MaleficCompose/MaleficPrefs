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
 * @property keyPrefix the prefix used to store the map entries in preferences.
 */
class PersistentHashMap<K : Serializable, V : Serializable>(private val keyPrefix: String) :
  HashMap<K, V>() {
  init {
    loadFromPreferences()
  }

  /** Loads the map from preferences. */
  private fun loadFromPreferences() {
    clear()
    val keys = getStringSet("$keyPrefix-keys").orEmpty()
    for (key in keys) {
      val serializedValue = prefs.getByteArray("$keyPrefix-$key", null)
      if (serializedValue != null) {
        val deserializedValue = deserialize(serializedValue, serializedValue::class.java)
        if (deserializedValue != null) {
          @Suppress("UNCHECKED_CAST") put(key as K, deserializedValue as V)
        }
      }
    }
  }

  /** Saves the map to preferences. */
  private fun saveToPreferences() {
    // Remove old keys
    val oldKeys = getStringSet("$keyPrefix-keys").orEmpty()
    for (key in oldKeys) {
      prefs.remove("$keyPrefix-$key")
    }

    // Save new keys and their serialized values
    putStringSet("$keyPrefix-keys", keys.map { it.toString() }.toSet())
    for ((key, value) in this) {
      prefs.putByteArray("$keyPrefix-$key", serialize(value))
    }
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

  /** Removes all of the mappings from this map. */
  override fun clear() {
    super.clear()
    prefs.remove("$keyPrefix-keys")
  }

  /**
   * Copies all of the mappings from the specified map to this map.
   *
   * @param from mappings to be stored in this map.
   */
  override fun putAll(from: Map<out K, V>) {
    super.putAll(from)
    saveToPreferences()
  }

  /**
   * Retrieves a set of strings from preferences.
   *
   * @param key the key used to retrieve the set.
   * @return the set of strings, or null if not found.
   */
  private fun getStringSet(key: String): Set<String>? {
    val csv = prefs[key, null] ?: return null
    return csv.split(",").filter { it.isNotEmpty() }.toSet()
  }

  /**
   * Stores a set of strings in preferences.
   *
   * @param key the key used to store the set.
   * @param set the set of strings to be stored.
   */
  private fun putStringSet(key: String, set: Set<String>) {
    val csv = set.joinToString(",")
    prefs.put(key, csv)
  }
}
