package xyz.malefic.prefs.collection

import java.util.prefs.Preferences
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import xyz.malefic.prefs.Common

class PersistentArrayListTest {
  private lateinit var prefs: Preferences
  private lateinit var list: PersistentArrayList<String>

  @BeforeEach
  fun setUp() {
    prefs = mock(Preferences::class.java)
    Common.prefs = prefs
    list = PersistentArrayList("testList")
  }

  @Test
  fun testAdd() {
    list.add("item1")
    verify(prefs).putByteArray(eq("testList"), any())
  }

  @Test
  fun testRemove() {
    list.add("item1")
    list.remove("item1")
    verify(prefs, times(2)).putByteArray(eq("testList"), any())
  }

  @Test
  fun testClear() {
    list.add("item1")
    list.clear()
    verify(prefs, times(2)).remove("testList")
  }
}
