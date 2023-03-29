package fr.etudiant.priceguessr

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.etudiant.priceguessr.fragments.GameFragment
import fr.etudiant.priceguessr.fragments.HistoryFragment
import fr.etudiant.priceguessr.fragments.ProfileFragment
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        /*  if token is empty we don't need to call API*/
        Log.e("EMPTY TOK", Token().getToken(this@MainActivity))
        if (Token().getToken(this@MainActivity).isEmpty()) {
            startLoginActivity()
            finish()
        }

        /* request when starting the app */
        val queue = Volley.newRequestQueue(this)
        val loadProductRequest = object : StringRequest(
            Request.Method.GET,
            Constants.API_BASE_URl + Constants.API_PRODUCT_GET_DAILY,
            {response ->
                try {
                    /* récupération des données */
                    Log.e("MAIN", "respone success game activity : " + response)
                    //var dailyProducts = Json.decodeFromString<MutableList<Product>>()

                    /* data successfully receive */
                    loadFragment(GameFragment())

                } catch (e : Exception) {
                    Toast.makeText(this, "Erreur lors de la récupération des données." , Toast.LENGTH_LONG).show()
                }
            },
            {error ->
                /* Prevent if API is not running  */
                if (error is VolleyError ||  error == null || error.networkResponse != null) {
                    Log.e("MAIN VOLLEy", "Volley perso error")
                    startLoginActivity()
                    finish()

                } else {
                    try {
                        val errorCode = error.networkResponse.statusCode
                        val errorBody = JSONObject(error.networkResponse.data.decodeToString()).getString("message")
                        when (errorCode) {
                            401 ->  {startLoginActivity() ; Log.e("MAIN", "Starting Login activity ... ")}
                            else -> Toast.makeText(this, errorBody, Toast.LENGTH_SHORT).show()
                        }

                    } catch (e : Exception) {

                        Toast.makeText(this, "aaaaaaaa", Toast.LENGTH_SHORT).show()
                    }
                }



            }) {
            /* set token into header of request */
            override fun getHeaders(): MutableMap<String, String> {
                val headers = mutableMapOf<String, String>()
                val token = Token().getToken(this@MainActivity)
                headers["Authorization"] = token
                return headers
            }
        }
        loadProductRequest.setTag("laodProduct")
        queue.add(loadProductRequest)




        // navigation View Logic
        navigationView.setOnItemSelectedListener {

            when(it.itemId)
            {
                R.id.home -> {
                    loadFragment(GameFragment())
                    true
                }
                R.id.history -> {
                    loadFragment(HistoryFragment())
                    true
                }
                R.id.profile-> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> {
                    Log.e("TAG", "false")
                    false
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    private fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }




}