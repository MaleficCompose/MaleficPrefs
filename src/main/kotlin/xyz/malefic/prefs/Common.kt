package xyz.malefic.prefs

import java.util.prefs.Preferences

/** A common class for managing preferences. */
class Common {
  companion object {
    /**
     * A `Preferences` instance that is initialized to the user root node with the class name as the
     * node name.
     */
    var prefs: Preferences = Preferences.userRoot().node(this::class.java.name)
  }
}
