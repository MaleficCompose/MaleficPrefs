package xyz.malefic.prefs.collection

import java.io.Serializable
import java.util.LinkedList
import java.util.Queue
import xyz.malefic.prefs.Common.Companion.prefs
import xyz.malefic.serialize.SerializationUtil.deserialize
import xyz.malefic.serialize.SerializationUtil.serialize

/**
 * A persistent queue that saves its state to preferences.
 *
 * @param T the type of elements held in this queue, which must be serializable.
 * @property key the key used to store the queue in preferences.
 */
class PersistentQueue<T : Serializable>(private val key: String) : LinkedList<T>(), Queue<T> {
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
   * Inserts the specified element into this queue.
   *
   * @param e the element to add.
   * @return `true` if the element was added to this queue, else `false`.
   */
  override fun offer(e: T): Boolean {
    val result = super.offer(e)
    saveToPreferences()
    return result
  }

  /**
   * Retrieves and removes the head of this queue, or returns `null` if this queue is empty.
   *
   * @return the head of this queue, or `null` if this queue is empty.
   */
  override fun poll(): T? {
    val result = super.poll()
    saveToPreferences()
    return result
  }

  /**
   * Only kept for compatibility with the [Queue] class and automatic cleanup. Use the reset
   * function if you want to clear the preferences.
   */
  override fun clear() {
    super.clear()
  }

  /** Removes all the elements from this queue as well as from Preferences. */
  fun reset() {
    clear()
    prefs.remove(key)
  }
}
