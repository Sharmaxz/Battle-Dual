package com.infnet.battle_dual

import android.content.Context
import android.util.DisplayMetrics


open class DisplayManager(context: Context) {

    val metrics : DisplayMetrics = context.resources.displayMetrics
    var dpi = getDeviceDensity()

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



}