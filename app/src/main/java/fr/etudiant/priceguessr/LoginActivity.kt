package fr.etudiant.priceguessr

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.android.volley.NoConnectionError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val loginInput = findViewById<EditText>(R.id.login_page_input)
        val passwordInput = findViewById<EditText>(R.id.login_page_input_password)
        var btnPasswordVisibility = findViewById<ImageButton>(R.id.login_page_btn_password_visibility)
        val btnConnexion = findViewById<Button>(R.id.login_page_btn_login)
        val btnRedirectRegister = findViewById<Button>(R.id.login_page_btn_register)

        btnConnexion.setOnClickListener {
            val login = loginInput.text.toString()
            val password =  passwordInput.text.toString()
            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, getString(R.string.toast_input_page_invalid), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val queue = Volley.newRequestQueue(this)
            val loginRequest = object : StringRequest(
                Method.POST,
                Constants.API_BASE_URl + Constants.API_USER_AUTH,
                {response ->
                    try {
                        /* save login */
                        val sharedPref = this.getSharedPreferences("tokenPreference", Context.MODE_PRIVATE)
                        with (sharedPref.edit()) {
                            putString("userName", loginInput.text.toString())
                            apply()
                        }
                        val token = JSONObject(response).getString("token").toString()
                        Token().setToken(this,token)
                        val intent = Intent(this, MainActivity::class.java)
                        /* kill previous activities and only start MainActivity */
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    } catch (e : Exception) {
                        Toast.makeText(this,getString(R.string.toast_unknown_error), Toast.LENGTH_SHORT).show()
                    }

                },
                {error ->
                    /* handle connection error from API */
                    if (error is NoConnectionError ||  error == null || error.networkResponse == null) {
                        Toast.makeText(this, getString(R.string.api_connection_error), Toast.LENGTH_SHORT).show()
                    } else {
                            /* handle API response */
                            when(error.networkResponse.statusCode) {
                                400 -> {
                                    try {
                                        val responseMessage = JSONObject(error.networkResponse.data.decodeToString()).getString("message")
                                        Toast.makeText(this, responseMessage, Toast.LENGTH_SHORT).show()
                                    } catch (e : Exception) {
                                        Toast.makeText(this, getString(R.string.toast_unknown_error), Toast.LENGTH_SHORT).show()
                                    }
                                }
                                else -> Toast.makeText(this, getString(R.string.toast_unknown_error), Toast.LENGTH_SHORT).show()
                            }
                    }
                }) {
                /* Put credential in request body */
                override fun getParams(): MutableMap<String, String>? {
                    val params = hashMapOf<String, String>()
                    params["login"] = login
                    params["password"] = password
                    return params
                }
            }
            queue.add(loginRequest)
        }


        btnRedirectRegister.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }


    }

    override fun onBackPressed() {
        //can not return to the previous task
        moveTaskToBack(false)
    }

}