package xyz.malefic.prefs.collection

import java.util.prefs.Preferences
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import xyz.malefic.prefs.Common

class PersistentHashMapTest {
  private lateinit var prefs: Preferences
  private lateinit var map: PersistentHashMap<String, String>

  @BeforeEach
  fun setUp() {
    prefs = mock(Preferences::class.java)
    Common.prefs = prefs
    map = PersistentHashMap("testMap")
  }

  @Test
  fun testPut() {
    map["key1"] = "value1"
    verify(prefs).putByteArray(eq("testMap-key1"), any())
  }

  @Test
  fun testRemove() {
    map["key1"] = "value1"
    map.remove("key1")
    verify(prefs).putByteArray(eq("testMap-key1"), any())
  }

  @Test
  fun testClear() {
    map["key1"] = "value1"
    map.clear()
    verify(prefs, times(2)).remove("testMap-keys")
  }
}
