package com.example.miniproyecto01.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("RegistroPrefs", Context.MODE_PRIVATE)

    fun guardarMatricula(matricula: String) {
        prefs.edit().putString("ultima_matricula", matricula).apply()
    }

    fun obtenerMatricula(): String {
        return prefs.getString("ultima_matricula", "") ?: ""
    }
}