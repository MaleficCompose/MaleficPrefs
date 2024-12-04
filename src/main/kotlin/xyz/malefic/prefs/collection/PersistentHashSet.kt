package xyz.malefic.prefs.collection

import java.io.Serializable
import java.util.prefs.Preferences
import xyz.malefic.prefs.Common
import xyz.malefic.serialize.SerializationUtil.deserialize
import xyz.malefic.serialize.SerializationUtil.serialize

/**
 * A persistent implementation of a HashSet that saves its state to preferences.
 *
 * @param T the type of elements in this set, which must be Serializable
 * @param key the key used to store the set in preferences
 * @param prefs the Preferences instance used to store the set
 */
class PersistentHashSet<T : Serializable>(
  private val key: String,
  private val prefs: Preferences = Common.prefs,
) : HashSet<T>() {
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
   * Adds an element to the set and saves the set to preferences.
   *
   * @param element the element to add
   * @return true if the set changed as a result of the call
   */
  override fun add(element: T): Boolean {
    val result = super.add(element)
    saveToPreferences()
    return result
  }

  /**
   * Adds all elements in the specified collection to the set and saves the set to preferences.
   *
   * @param elements the collection containing elements to be added
   * @return true if the set changed as a result of the call
   */
  override fun addAll(elements: Collection<T>): Boolean {
    val result = super.addAll(elements)
    saveToPreferences()
    return result
  }

  /**
   * Removes the specified element from the set and saves the set to preferences.
   *
   * @param element the element to be removed from the set, if present
   * @return true if the set contained the specified element
   */
  override fun remove(element: T): Boolean {
    val result = super.remove(element)
    saveToPreferences()
    return result
  }

  /**
   * Removes all elements in the specified collection from the set and saves the set to preferences.
   *
   * @param elements the collection containing elements to be removed from the set
   * @return true if the set changed as a result of the call
   */
  override fun removeAll(elements: Collection<T>): Boolean {
    val result = super.removeAll(elements.toSet())
    saveToPreferences()
    return result
  }

  /**
   * Retains only the elements in the set that are contained in the specified collection and saves
   * the set to preferences.
   *
   * @param elements the collection containing elements to be retained in the set
   * @return true if the set changed as a result of the call
   */
  override fun retainAll(elements: Collection<T>): Boolean {
    val result = super.retainAll(elements.toSet())
    saveToPreferences()
    return result
  }

  /**
   * Clears the set. This method is only kept for compatibility with [HashSet] and automatic
   * cleanup. Use [reset] to clear the set and remove it from preferences.
   */
  override fun clear() {
    super.clear()
  }

  /** Clears the set and removes it from preferences. */
  fun reset() {
    clear()
    prefs.remove(key)
  }
}
