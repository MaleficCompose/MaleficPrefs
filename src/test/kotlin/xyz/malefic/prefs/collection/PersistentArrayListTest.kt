package xyz.malefic.prefs.collection

import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import xyz.malefic.prefs.Common.Companion.prefs

class PersistentArrayListTest {
  @BeforeEach
  fun setUp() {
    prefs.clear()
  }

  @Test
  fun `should load from preferences`() {
    val list = PersistentArrayList<String>("testKey")
    list.add("item1")
    list.add("item2")

    val newList = PersistentArrayList<String>("testKey")
    assertEquals(list, newList, "Expected list to be loaded from preferences")
  }

  @Test
  fun `should save to preferences on add`() {
    val list = PersistentArrayList<String>("testKey")
    list.add("item1")

    val newList = PersistentArrayList<String>("testKey")
    assertTrue(newList.contains("item1"), "Expected item to be saved to preferences")
  }

  @Test
  fun `should save to preferences on remove`() {
    val list = PersistentArrayList<String>("testKey")
    list.add("item1")
    list.remove("item1")

    val newList = PersistentArrayList<String>("testKey")
    assertTrue(!newList.contains("item1"), "Expected item to be removed from preferences")
  }

  @Test
  fun `should reset preferences`() {
    val list = PersistentArrayList<String>("testKey")
    list.add("item1")
    list.reset()

    val newList = PersistentArrayList<String>("testKey")
    assertTrue(newList.isEmpty(), "Expected list to be reset in preferences")
  }
}
