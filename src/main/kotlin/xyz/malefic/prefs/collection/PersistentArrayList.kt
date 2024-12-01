package xyz.malefic.prefs.collection

import java.io.Serializable
import xyz.malefic.prefs.Common.Companion.prefs
import xyz.malefic.serialize.SerializationUtil.deserialize
import xyz.malefic.serialize.SerializationUtil.serialize

/**
 * A persistent ArrayList that saves its state to preferences.
 *
 * @param T the type of elements held in this list, which must be serializable.
 * @property key the key used to store the list in preferences.
 */
class PersistentArrayList<T : Serializable>(private val key: String) : ArrayList<T>() {
  init {
    loadFromPreferences()
  }

  /** Loads the list from preferences. */
  private fun loadFromPreferences() {
    clear()
    val serializedList = prefs.getByteArray(key, null) ?: return
    val deserializedList = deserialize(serializedList, ArrayList::class.java)
    if (deserializedList != null) {
      @Suppress("UNCHECKED_CAST") addAll(deserializedList as ArrayList<T>)
    }
  }

  /** Saves the list to preferences. */
  private fun saveToPreferences() {
    prefs.putByteArray(key, serialize(ArrayList(this)))
  }

  /**
   * Adds the specified element to this list.
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
   * Removes the specified element from this list.
   *
   * @param element the element to be removed.
   * @return `true` if the element was removed successfully.
   */
  override fun remove(element: T): Boolean {
    val result = super.remove(element)
    saveToPreferences()
    return result
  }

  /** Removes all elements from this list. */
  override fun clear() {
    super.clear()
    prefs.remove(key)
  }
}
