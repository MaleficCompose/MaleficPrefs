package xyz.malefic.prefs.collection

import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import xyz.malefic.prefs.Common.Companion.prefs

class PersistentQueueTest {
  @BeforeEach
  fun setUp() {
    prefs.clear()
  }

  @Test
  fun `should load from preferences`() {
    val queue = PersistentQueue<String>("testKey")
    queue.offer("item1")
    queue.offer("item2")

    val newQueue = PersistentQueue<String>("testKey")
    assertEquals(queue, newQueue, "Expected queue to be loaded from preferences")
  }

  @Test
  fun `should save to preferences on offer`() {
    val queue = PersistentQueue<String>("testKey")
    queue.offer("item1")

    val newQueue = PersistentQueue<String>("testKey")
    assertTrue(newQueue.contains("item1"), "Expected item to be saved to preferences")
  }

  @Test
  fun `should save to preferences on poll`() {
    val queue = PersistentQueue<String>("testKey")
    queue.offer("item1")
    queue.poll()

    val newQueue = PersistentQueue<String>("testKey")
    assertTrue(!newQueue.contains("item1"), "Expected item to be removed from preferences")
  }

  @Test
  fun `should reset preferences`() {
    val queue = PersistentQueue<String>("testKey")
    queue.offer("item1")
    queue.reset()

    val newQueue = PersistentQueue<String>("testKey")
    assertTrue(newQueue.isEmpty(), "Expected queue to be reset in preferences")
  }
}
