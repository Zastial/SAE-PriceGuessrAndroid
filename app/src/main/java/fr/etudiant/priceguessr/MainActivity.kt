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
import fr.etudiant.priceguessr.fragments.ProfilFragment
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        /*  if token is empty we don't need to call API */
        if (Token().getToken(this@MainActivity).isEmpty()) {
            startLoginActivity()
            finish()
        }

        /* Request when starting the app */
        val queue = Volley.newRequestQueue(this)
        val loadProductRequest = object : StringRequest(
            Request.Method.GET,
            Constants.API_BASE_URl + Constants.API_PRODUCT_GET_DAILY,
            {response ->
                try {
                    /* Get product from response */
                    val productList = Json.decodeFromString<List<Product>>(response)

                    /* Give array of products to fragment with bundle*/
                    val bundle = Bundle()
                    bundle.putParcelableArray("products", productList.toTypedArray())

                    /* Load fragment with bundle */
                    loadFragment(GameFragment(), bundle)

                } catch (e : Exception) {
                    Toast.makeText(this, getString(R.string.toast_decode_invalid) , Toast.LENGTH_LONG).show()
                }
            },
            {error ->
                /* Prevent if API is not running  */
                if (error is VolleyError ||  error == null || error.networkResponse != null) {
                    startLoginActivity()
                    finish()
                } else {
                    try {
                        val errorCode = error.networkResponse.statusCode
                        val errorBody = JSONObject(error.networkResponse.data.decodeToString()).getString("message")
                        when (errorCode) {
                            401 ->  {startLoginActivity() }
                            else -> Toast.makeText(this, errorBody, Toast.LENGTH_SHORT).show()
                        }

                    } catch (e : Exception) {
                        Toast.makeText(this, getString(R.string.toast_unknown_error), Toast.LENGTH_SHORT).show()
                    }
                }
            }) {
            /* set token into header of request */
            override fun getHeaders(): MutableMap<String, String> {
                val headers = mutableMapOf<String, String>()
                val token = Token().getToken(this@MainActivity)
                headers[Constants.HEADER_TOKEN_AUTHORIZATION] = token
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
                    loadFragment(ProfilFragment())
                    true
                }
                else -> {
                    false
                }
            }
        }
    }


    private fun loadFragment(fragment: Fragment, data : Bundle? = null) {
        if (data != null) {
            fragment.arguments = data
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }


    private fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }




}