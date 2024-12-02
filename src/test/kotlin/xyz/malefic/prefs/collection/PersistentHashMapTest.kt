package xyz.malefic.prefs.collection

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import xyz.malefic.prefs.Common.Companion.prefs

class PersistentHashMapTest {
  private lateinit var map: PersistentHashMap<String, String>

  @BeforeEach
  fun setUp() {
    prefs.clear()
    map = PersistentHashMap("testMap")
  }

  @Test
  fun putAddsEntryToMapAndSavesToPreferences() {
    map["key1"] = "value1"
    assertEquals("value1", map["key1"])
  }

  @Test
  fun removeDeletesEntryFromMapAndSavesToPreferences() {
    map["key1"] = "value1"
    map.remove("key1")
    assertNull(map["key1"])
  }

  @Test
  fun resetClearsMapAndRemovesFromPreferences() {
    map["key1"] = "value1"
    map.reset()
    assertTrue(map.isEmpty())
  }

  @Test
  fun putAllAddsAllEntriesToMapAndSavesToPreferences() {
    val entries = mapOf("key1" to "value1", "key2" to "value2")
    map.putAll(entries)
    assertEquals("value1", map["key1"])
    assertEquals("value2", map["key2"])
  }
}
