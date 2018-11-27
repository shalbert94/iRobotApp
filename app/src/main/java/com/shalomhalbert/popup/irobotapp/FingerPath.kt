package com.shalomhalbert.popup.irobotapp

import android.graphics.Path

/**
 * Represents the path drawn with users finger on the screen
 */

data class FingerPath(var color: Int, var path: Path) {
    companion object {
        const val BRUSH_SIZE = 20f
    }
}