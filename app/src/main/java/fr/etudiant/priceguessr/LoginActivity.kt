package fr.etudiant.priceguessr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        var loginInput = findViewById<EditText>(R.id.login_page_input)
        var passwordInput = findViewById<EditText>(R.id.login_page_input_password)
        var btnPasswordVisibility = findViewById<ImageButton>(R.id.login_page_btn_password_visibility)
        var btnConnexion = findViewById<Button>(R.id.login_page_btn_login)
        var btnRedirectRegister = findViewById<Button>(R.id.login_page_btn_register)




        btnConnexion.setOnClickListener {
            val login = loginInput.text.toString()
            val password =  passwordInput.text.toString()
            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, getString(R.string.toast_input_page_invalid), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val queue = Volley.newRequestQueue(this)
            var loginRequest = object : StringRequest(
                Method.POST,
                Constants.API_BASE_URl + Constants.API_USER_AUTH,
                {response ->
                    Log.e("TAG", "login success response : " + JSONObject(response).getString("token"))
                    val token = JSONObject(response).getString("token")
                    Token().setToken(this,token)
                    finish()
                },
                {error ->
                    try {
                        val responseMessage = JSONObject(error.networkResponse.data.decodeToString()).getString("message")
                        val code = error.networkResponse.statusCode
                        when(code) {
                            400 -> Toast.makeText(this, responseMessage, Toast.LENGTH_SHORT).show()
                            else -> Toast.makeText(this, getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
                        }
                    } catch (e : Error) {
                        Toast.makeText(this, getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
                    }
                }
                ) {
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