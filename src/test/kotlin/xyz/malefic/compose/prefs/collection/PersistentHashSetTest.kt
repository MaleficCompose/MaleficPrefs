package xyz.malefic.compose.prefs.collection

import xyz.malefic.compose.prefs.Common.Companion.prefs
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PersistentHashSetTest {
    @BeforeTest
    fun setUp() {
        prefs.clear()
    }

    @Test
    fun `should load from preferences`() {
        val set = PersistentHashSet<String>("testKey")
        set.add("item1")
        set.add("item2")

        val newSet = PersistentHashSet<String>("testKey")
        assertEquals(set, newSet, "Expected set to be loaded from preferences")
    }

    @Test
    fun `should save to preferences on add`() {
        val set = PersistentHashSet<String>("testKey")
        set.add("item1")

        val newSet = PersistentHashSet<String>("testKey")
        assertTrue(newSet.contains("item1"), "Expected item to be saved to preferences")
    }

    @Test
    fun `should save to preferences on remove`() {
        val set = PersistentHashSet<String>("testKey")
        set.add("item1")
        set.remove("item1")

        val newSet = PersistentHashSet<String>("testKey")
        assertTrue(!newSet.contains("item1"), "Expected item to be removed from preferences")
    }

    @Test
    fun `should reset preferences`() {
        val set = PersistentHashSet<String>("testKey")
        set.add("item1")
        set.reset()

        val newSet = PersistentHashSet<String>("testKey")
        assertTrue(newSet.isEmpty(), "Expected set to be reset in preferences")
    }
}
