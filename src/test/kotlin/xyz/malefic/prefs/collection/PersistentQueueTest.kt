package xyz.malefic.prefs.collection

import java.util.prefs.Preferences
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import xyz.malefic.prefs.Common

class PersistentQueueTest {
  private lateinit var prefs: Preferences
  private lateinit var queue: PersistentQueue<String>

  @BeforeEach
  fun setUp() {
    prefs = mock(Preferences::class.java)
    Common.prefs = prefs
    queue = PersistentQueue("testQueue")
  }

  @Test
  fun testOffer() {
    queue.offer("item1")
    verify(prefs).putByteArray(eq("testQueue"), any())
  }

  @Test
  fun testPoll() {
    queue.offer("item1")
    queue.poll()
    verify(prefs, times(2)).putByteArray(eq("testQueue"), any())
  }

  @Test
  fun testClear() {
    queue.offer("item1")
    queue.clear()
    verify(prefs, times(2)).remove("testQueue")
  }
}
