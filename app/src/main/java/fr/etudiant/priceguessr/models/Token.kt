package fr.etudiant.priceguessr.models

import android.app.Activity
import android.content.Context
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
        val sharedPref = activity.getSharedPreferences("tokenPreference", Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("token", jwt)
            apply()
        }
    }

    fun deleteToken(activity: Activity) {
        setToken(activity, "")
    }
}