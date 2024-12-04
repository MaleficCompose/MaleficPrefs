package xyz.malefic.prefs.collection

import java.io.Serializable
import java.util.LinkedList
import java.util.Queue
import java.util.prefs.Preferences
import xyz.malefic.prefs.Common
import xyz.malefic.serialize.SerializationUtil.deserialize
import xyz.malefic.serialize.SerializationUtil.serialize

/**
 * A persistent implementation of a Queue that saves its state to preferences.
 *
 * @param T the type of elements in this queue, which must be Serializable
 * @param key the key used to store the queue in preferences
 * @param prefs the Preferences instance used to store the queue
 */
class PersistentQueue<T : Serializable>(
  private val key: String,
  private val prefs: Preferences = Common.prefs,
) : LinkedList<T>(), Queue<T> {
  init {
    loadFromPreferences()
  }

  /** Loads the queue from preferences. */
  private fun loadFromPreferences() {
    clear()
    val serializedQueue = prefs.getByteArray(key, null) ?: return
    val deserializedQueue = deserialize(serializedQueue, LinkedList::class.java)
    if (deserializedQueue != null) {
      @Suppress("UNCHECKED_CAST") addAll(deserializedQueue as LinkedList<T>)
    }
  }

  /** Saves the queue to preferences. */
  private fun saveToPreferences() {
    prefs.putByteArray(key, serialize(LinkedList(this)))
  }

  /**
   * Inserts the specified element into this queue and saves the queue to preferences.
   *
   * @param e the element to add
   * @return true if the element was added to this queue, else false
   */
  override fun offer(e: T): Boolean {
    val result = super.offer(e)
    saveToPreferences()
    return result
  }

  /**
   * Adds the specified element to this queue and saves the queue to preferences.
   *
   * @param element the element to add
   * @return true if the element was added to this queue, else false
   */
  override fun add(element: T): Boolean {
    val result = super.add(element)
    saveToPreferences()
    return result
  }

  /**
   * Adds all elements in the specified collection to this queue and saves the queue to preferences.
   *
   * @param elements the collection containing elements to be added
   * @return true if the queue changed as a result of the call
   */
  override fun addAll(elements: Collection<T>): Boolean {
    val result = super.addAll(elements)
    saveToPreferences()
    return result
  }

  /**
   * Retrieves and removes the head of this queue, or returns null if this queue is empty. Saves the
   * queue to preferences.
   *
   * @return the head of this queue, or null if this queue is empty
   */
  override fun poll(): T? {
    val result = super.poll()
    saveToPreferences()
    return result
  }

  /**
   * Removes a single instance of the specified element from this queue, if it is present. Saves the
   * queue to preferences.
   *
   * @param element the element to be removed from this queue, if present
   * @return true if the queue contained the specified element
   */
  override fun remove(element: T): Boolean {
    val result = super.remove(element)
    saveToPreferences()
    return result
  }

  /**
   * Removes all elements in the specified collection from this queue and saves the queue to
   * preferences.
   *
   * @param elements the collection containing elements to be removed from this queue
   * @return true if the queue changed as a result of the call
   */
  override fun removeAll(elements: Collection<T>): Boolean {
    val result = super.removeAll(elements.toSet())
    saveToPreferences()
    return result
  }

  /**
   * Retains only the elements in this queue that are contained in the specified collection and
   * saves the queue to preferences.
   *
   * @param elements the collection containing elements to be retained in this queue
   * @return true if the queue changed as a result of the call
   */
  override fun retainAll(elements: Collection<T>): Boolean {
    val result = super.retainAll(elements.toSet())
    saveToPreferences()
    return result
  }

  /**
   * Clears the queue. This method is only kept for compatibility with [LinkedList] and automatic
   * cleanup. Use [reset] to clear the queue and remove it from preferences.
   */
  override fun clear() {
    super.clear()
  }

  /** Clears the queue and removes it from preferences. */
  fun reset() {
    clear()
    prefs.remove(key)
  }
}
