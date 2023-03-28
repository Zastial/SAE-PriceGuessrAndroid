package fr.etudiant.priceguessr.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import fr.etudiant.priceguessr.*
import kotlinx.serialization.json.Json


class GameFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_game, container, false)


        /* request to load product of the day */
        val queue = Volley.newRequestQueue(context)
        val loadProductRequest = object : StringRequest(
            Request.Method.GET,
            Constants.API_BASE_URl + Constants.API_PRODUCT_GET_DAILY,
            {response ->

                try {
                    /* récupération des données */
                    Log.e("TAG", "respone success game activity" + response.toString())
                    //var dailyProducts = Json.decodeFromString<MutableList<Product>>(response)
                } catch (e : Exception) {
                    Toast.makeText(activity, "Erreur lors de la récupération des données." , Toast.LENGTH_LONG).show()
                }
            },
            {error ->
                /* if error == 401 (!authorization) start login activity */
                Log.e("TAG", "error "+ error)
                Log.e("TAG", "Starting Login activity ... ")

                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)

            }) {
            /* set token into header of request */
            override fun getHeaders(): MutableMap<String, String> {
                val headers = mutableMapOf<String, String>()
                Log.e("TOKEN", Token().getToken(activity as Activity))
                headers["Authorization"] = Token().getToken(activity as Activity)
                return headers
            }
        }

        queue.add(loadProductRequest)

        return view
    }
}