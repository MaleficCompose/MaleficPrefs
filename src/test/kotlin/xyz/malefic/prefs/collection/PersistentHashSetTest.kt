package xyz.malefic.prefs.collection

import java.util.prefs.Preferences
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import xyz.malefic.prefs.Common

class PersistentHashSetTest {
  private lateinit var prefs: Preferences
  private lateinit var set: PersistentHashSet<String>

  @BeforeEach
  fun setUp() {
    prefs = mock(Preferences::class.java)
    Common.prefs = prefs
    set = PersistentHashSet("testSet")
  }

  @Test
  fun testAdd() {
    set.add("item1")
    verify(prefs).putByteArray(eq("testSet"), any())
  }

  @Test
  fun testRemove() {
    set.add("item1")
    set.remove("item1")
    verify(prefs, times(2)).putByteArray(eq("testSet"), any())
  }

  @Test
  fun testClear() {
    set.add("item1")
    set.clear()
    verify(prefs, times(2)).remove("testSet")
  }
}
