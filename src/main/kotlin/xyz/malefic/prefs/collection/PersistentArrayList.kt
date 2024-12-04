package xyz.malefic.prefs.collection

import java.io.Serializable
import java.util.prefs.Preferences
import xyz.malefic.prefs.Common
import xyz.malefic.serialize.SerializationUtil.deserialize
import xyz.malefic.serialize.SerializationUtil.serialize

/**
 * A persistent implementation of an ArrayList that saves its state to preferences.
 *
 * @param T the type of elements in this list, which must be Serializable
 * @param key the key used to store the list in preferences
 * @param prefs the preferences instance used to store the list
 */
class PersistentArrayList<T : Serializable>(
  private val key: String,
  private val prefs: Preferences = Common.prefs,
) : ArrayList<T>() {
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
   * Adds an element to the list and saves the list to preferences.
   *
   * @param element the element to add
   * @return true if the list changed as a result of the call
   */
  override fun add(element: T): Boolean {
    val result = super.add(element)
    saveToPreferences()
    return result
  }

  /**
   * Adds an element at the specified position in the list and saves the list to preferences.
   *
   * @param index the index at which the element is to be inserted
   * @param element the element to add
   */
  override fun add(index: Int, element: T) {
    super.add(index, element)
    saveToPreferences()
  }

  /**
   * Adds all elements in the specified collection to the list and saves the list to preferences.
   *
   * @param elements the collection containing elements to be added
   * @return true if the list changed as a result of the call
   */
  override fun addAll(elements: Collection<T>): Boolean {
    val result = super.addAll(elements)
    saveToPreferences()
    return result
  }

  /**
   * Adds all elements in the specified collection at the specified position in the list and saves
   * the list to preferences.
   *
   * @param index the index at which to insert the first element from the specified collection
   * @param elements the collection containing elements to be added
   * @return true if the list changed as a result of the call
   */
  override fun addAll(index: Int, elements: Collection<T>): Boolean {
    val result = super.addAll(index, elements)
    saveToPreferences()
    return result
  }

  /**
   * Replaces the element at the specified position in the list with the specified element and saves
   * the list to preferences.
   *
   * @param index the index of the element to replace
   * @param element the element to be stored at the specified position
   * @return the element previously at the specified position
   */
  override fun set(index: Int, element: T): T {
    val result = super.set(index, element)
    saveToPreferences()
    return result
  }

  /**
   * Removes the first occurrence of the specified element from the list and saves the list to
   * preferences.
   *
   * @param element the element to be removed from the list, if present
   * @return true if the list contained the specified element
   */
  override fun remove(element: T): Boolean {
    val result = super.remove(element)
    saveToPreferences()
    return result
  }

  /**
   * Removes the element at the specified position in the list and saves the list to preferences.
   *
   * @param index the index of the element to be removed
   * @return the element that was removed from the list
   */
  override fun removeAt(index: Int): T {
    val result = super.removeAt(index)
    saveToPreferences()
    return result
  }

  /**
   * Removes all elements in the specified collection from the list and saves the list to
   * preferences.
   *
   * @param elements the collection containing elements to be removed from the list
   * @return true if the list changed as a result of the call
   */
  override fun removeAll(elements: Collection<T>): Boolean {
    val result = super.removeAll(elements.toSet())
    saveToPreferences()
    return result
  }

  /**
   * Retains only the elements in the list that are contained in the specified collection and saves
   * the list to preferences.
   *
   * @param elements the collection containing elements to be retained in the list
   * @return true if the list changed as a result of the call
   */
  override fun retainAll(elements: Collection<T>): Boolean {
    val result = super.retainAll(elements.toSet())
    saveToPreferences()
    return result
  }

  /**
   * Clears the list. This method is only kept for compatibility with [ArrayList] and automatic
   * cleanup. Use [reset] to clear the list and remove it from preferences.
   */
  override fun clear() {
    super.clear()
  }

  /** Clears the list and removes it from preferences. */
  fun reset() {
    clear()
    prefs.remove(key)
  }
}
