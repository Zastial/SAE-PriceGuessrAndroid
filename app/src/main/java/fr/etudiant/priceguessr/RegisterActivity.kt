package fr.etudiant.priceguessr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.android.volley.NoConnectionError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val login = findViewById<EditText>(R.id.register_page_input_login)
        val password = findViewById<EditText>(R.id.register_page_input_password)
        val confirmPassword = findViewById<EditText>(R.id.register_page_input_confirm_password)
        val btnVisibilityPassword  = findViewById<ImageButton>(R.id.register_page_btn_password_visibility)
        val btnRegister = findViewById<Button>(R.id.register_page_btn_register)
        val btnLogin = findViewById<Button>(R.id.register_page_btn_login)


        btnRegister.setOnClickListener {
            val formLogin = login.text.toString()
            val formPassword = password.text.toString()
            val formConfirmPassword = confirmPassword.text.toString()

            /* form input empty verification */
            if (formLogin.isEmpty() || formPassword.isEmpty() || formConfirmPassword.isEmpty()) {
                Toast.makeText(this, getString(R.string.toast_input_page_invalid), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            /* form input password and confirm password verification */
            if (formPassword != formConfirmPassword) {
                Toast.makeText(this, getString(R.string.toast_password_confirm_invalid), Toast.LENGTH_SHORT).show()
            }

            val queue = Volley.newRequestQueue(this)
            val registerRequest = object : StringRequest(
                Method.POST,
                Constants.API_BASE_URl + Constants.API_USER_POST_REGISTER,
                {response ->
                    Toast.makeText(this, getString(R.string.toast_register_sucess), Toast.LENGTH_SHORT).show()
                    /* redirect to login */
                    finish()
                },
                {error ->
                    if (error is NoConnectionError ||  error == null || error.networkResponse == null) {
                        Toast.makeText(this, getString(R.string.api_connection_error), Toast.LENGTH_SHORT).show()
                    } else {
                        /* decode networkResponse
                        * show user already exists if code is 409, else try to get message from API response */
                        when (error.networkResponse.statusCode) {
                            409 -> Toast.makeText(this, getString(R.string.toast_user_already_exists), Toast.LENGTH_SHORT).show()
                            else -> {
                                try {
                                    val responseMessage = JSONObject(error.networkResponse.data.decodeToString()).getString("message")
                                    Toast.makeText(this, responseMessage, Toast.LENGTH_SHORT).show()
                                } catch (e : Exception) {
                                    Toast.makeText(this, getString(R.string.toast_unknown_error), Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                    }



                }
            ) {
                override fun getParams(): MutableMap<String, String>? {
                    val params = hashMapOf<String, String>()
                    params["login"] = formLogin
                    params["password"] = formPassword
                    return params
                }
            }

            queue.add(registerRequest)
        }

        btnLogin.setOnClickListener {
            finish()
        }
    }
}