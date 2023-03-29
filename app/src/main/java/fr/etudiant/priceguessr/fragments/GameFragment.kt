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
import fr.etudiant.priceguessr.gameLogic.GameLogic
import kotlinx.serialization.json.Json
import org.json.JSONObject


class GameFragment : Fragment() {

    private lateinit var data: List<Product>



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        try {
            data = arguments?.get("products") as List<Product>
        } catch (e : Exception) {
            /* invalid list of products passed to fragment */
            Toast.makeText(requireActivity(), getString(R.string.toast_decode_invalid), Toast.LENGTH_SHORT).show()
            return null
        }

        val gl = GameLogic(data.toList())
        Log.e("GAME", data.toString())
        data.forEach {
            Log.e("GAME", it.toString())
        }


        /* if data not fetched -> don't load fragment */
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        /* request to load product of the day */
        return view
    }
}