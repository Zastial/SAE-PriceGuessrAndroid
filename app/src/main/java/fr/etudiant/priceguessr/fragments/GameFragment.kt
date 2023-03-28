package fr.etudiant.priceguessr.fragments

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
import fr.etudiant.priceguessr.Constants
import fr.etudiant.priceguessr.LoginActivity
import fr.etudiant.priceguessr.MainActivity
import fr.etudiant.priceguessr.R


class GameFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_game, container, false)

        val queue = Volley.newRequestQueue(context)
        var stringRequest = object : StringRequest(
            Request.Method.GET,
            Constants.API_BASE_URl + Constants.API_PRODUCT_GET_DAILY,
            {response ->
                // get JsonObject ?
                Log.e("TAG", "resp " + response)

                try {
                    //var dailyProducts = Json.decodeFromString<MutableList<Product>>(response)
                } catch (e : Exception) {
                    Toast.makeText(activity, "Erreur lors de la récupération des données." , Toast.LENGTH_LONG).show()
                }
                // render products and implement game logic



            },
            {error ->
                /* if error == 401 (!authorization) start login activity */
                Log.e("TAG", "error "+ error)
                Log.e("TAG", "Starting Login activity ... ")
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)

            }) {

        }

        queue.add(stringRequest)

        return view
    }
}