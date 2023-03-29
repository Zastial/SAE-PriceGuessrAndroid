package fr.etudiant.priceguessr

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import androidx.lifecycle.ViewModel

class Token() : ViewModel() {

    private var jwt : String

    init {
        jwt = ""
    }

    fun getToken(activity : Activity) : String {
        if (jwt.isEmpty()) {
            val token = activity.getSharedPreferences("tokenPreference", Context.MODE_PRIVATE)
                .getString("token", "")
            jwt = token.toString()
        }
        return jwt
    }

    fun setToken(activity: Activity, newToken : String) {
        jwt = newToken
        val sharedPref = activity?.getSharedPreferences("tokenPreference", Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("token", jwt)
            apply()
        }
    }

    fun deleteToken(activity: Activity) {
        setToken(activity, "")
    }
}