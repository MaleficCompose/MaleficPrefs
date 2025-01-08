package xyz.malefic.compose.prefs

import java.util.prefs.Preferences

/**
 * Singleton object for managing preferences.
 *
 * This object holds shared resources or utilities for managing user-specific preference data.
 * It provides a centralized place for static-like members, allowing them to be accessed globally
 * without needing an instance.
 *
 * The `Common` object is particularly useful for defining constants, utility functions, or
 * shared instances like `prefs`, which can be accessed directly via `Common.prefs`.
 */
object Common {
    /**
     * A `Preferences` instance that is initialized to the user root node with the class name as the
     * node name.
     */
    var prefs: Preferences = Preferences.userRoot().node(this::class.java.name)
}
