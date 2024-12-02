package xyz.malefic.prefs.collection

import java.io.Serializable
import xyz.malefic.prefs.Common.Companion.prefs
import xyz.malefic.serialize.SerializationUtil.deserialize
import xyz.malefic.serialize.SerializationUtil.serialize

/**
 * A persistent HashSet that saves its state to preferences.
 *
 * @param T the type of elements held in this set, which must be serializable.
 * @property key the key used to store the set in preferences.
 */
class PersistentHashSet<T : Serializable>(private val key: String) : HashSet<T>() {
  init {
    loadFromPreferences()
  }

  /** Loads the set from preferences. */
  private fun loadFromPreferences() {
    clear()
    val serializedSet = prefs.getByteArray(key, null) ?: return
    val deserializedSet = deserialize(serializedSet, HashSet::class.java)
    if (deserializedSet != null) {
      @Suppress("UNCHECKED_CAST") addAll(deserializedSet as HashSet<T>)
    }
  }

  /** Saves the set to preferences. */
  private fun saveToPreferences() {
    prefs.putByteArray(key, serialize(HashSet(this)))
  }

  /**
   * Adds the specified element to this set.
   *
   * @param element the element to be added.
   * @return `true` if the element was added successfully.
   */
  override fun add(element: T): Boolean {
    val result = super.add(element)
    saveToPreferences()
    return result
  }

  /**
   * Removes the specified element from this set.
   *
   * @param element the element to be removed.
   * @return `true` if the element was removed successfully.
   */
  override fun remove(element: T): Boolean {
    val result = super.remove(element)
    saveToPreferences()
    return result
  }

  /**
   * Only kept for compatibility with the [HashSet] class and automatic cleanup. Use the reset
   * function if you want to clear the preferences.
   */
  override fun clear() {
    super.clear()
  }

  /** Removes all the elemenst from this set as well as from Preferences. */
  fun reset() {
    clear()
    prefs.remove(key)
  }
}
