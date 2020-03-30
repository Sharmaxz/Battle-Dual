package com.sharmaxz.battle_dual.shared

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.Display


open class DisplayManager(val context: Context) {

    val metrics : DisplayMetrics = context.resources.displayMetrics

    val dpi = getDeviceDensity()
    val navigationHeight = getNavigationBarHeight()


    private fun getDeviceDensity() : String {
         return when (metrics.densityDpi) {
             in DisplayMetrics.DENSITY_LOW .. DisplayMetrics.DENSITY_MEDIUM -> "LDPI"
             in DisplayMetrics.DENSITY_MEDIUM .. DisplayMetrics.DENSITY_HIGH  -> "MDPI"
             in DisplayMetrics.DENSITY_HIGH .. DisplayMetrics.DENSITY_XHIGH -> "HDPI"
             in DisplayMetrics.DENSITY_XHIGH .. DisplayMetrics.DENSITY_XXHIGH -> "XHDPI"
             in DisplayMetrics.DENSITY_XXHIGH .. DisplayMetrics.DENSITY_XXXHIGH -> "XXHDPI"
             in DisplayMetrics.DENSITY_XXXHIGH .. 800 -> "XXXHDPI"
             in 800 .. 960 -> "XXXXHDPI"
            else -> "UNKNOWN"
        }
    }

    private fun getNavigationBarHeight(): Int {
        val resources: Resources = context.resources
        val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }



}
