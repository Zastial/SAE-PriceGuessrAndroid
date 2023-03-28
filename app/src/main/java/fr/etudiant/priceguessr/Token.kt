package fr.etudiant.priceguessr

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.ViewModel

class Token() : ViewModel() {

    private var jwt : String

    init {
        jwt = ""
    }

    fun getToken(activity : Activity) : String {
        if (jwt.isEmpty()) {
            val token = activity.getSharedPreferences("token", Context.MODE_PRIVATE)
            jwt = token.toString()
        }
        return jwt
    }

    fun setToken(activity: Activity, newToken : String) {
        jwt = newToken
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("token", jwt)
            apply()
        }
    }

    fun deleteToken() {
        jwt = ""
    }
}