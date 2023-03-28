package fr.etudiant.priceguessr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.android.volley.toolbox.Volley

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        var emailInput = findViewById<EditText>(R.id.login_page_input_email)
        var passwordInput = findViewById<EditText>(R.id.login_page_input_password)
        var btnPasswordVisibility = findViewById<ImageButton>(R.id.login_page_btn_password_visibility)
        var btnConnexion = findViewById<Button>(R.id.login_page_btn_login)
        var btnRedirectRegister = findViewById<Button>(R.id.login_page_btn_register)




        btnConnexion.setOnClickListener {
            val queue = Volley.newRequestQueue(context)
        }
    }

    override fun onBackPressed() {
        //can not return to the previous task
        moveTaskToBack(false)
    }

}