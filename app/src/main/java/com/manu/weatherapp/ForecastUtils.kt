package com.manu.weatherapp

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

/*
fun formatTempForDisplay(temp : Float) : String {
  return String.format("%.2f", temp)
}
*/

fun Float.formatTempForDisplay(tempDisplaySetting: TempDisplaySetting): String {
    return when (tempDisplaySetting) {
        TempDisplaySetting.Fahrenheit -> String.format("%.2f "+ "\u2109", this)
        TempDisplaySetting.Celsius -> {
            val temp = (this - 32f) * (5f/9f)
            String.format("%.2f "+ "\u2103", temp)
        }
    }
}

fun showTempDisplaySettingDialog(context: Context, tempDisplaySettingManager: TempDisplaySettingManager) {
    val dialogBuilder = AlertDialog.Builder(context)
        .setTitle("Choose Display Units")
        .setMessage("Choose which temperature unit to use for temperature display")
        .setPositiveButton("F") { _, _ ->
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Fahrenheit)
        }
        .setNeutralButton("C") {_, _ ->
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Celsius)
        }
        .setOnDismissListener(){
            Toast.makeText(context, "Settings will take effect on app restart", Toast.LENGTH_SHORT).show()
        }
    dialogBuilder.show()
}