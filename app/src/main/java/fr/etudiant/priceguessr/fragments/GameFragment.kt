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


    private var fetched = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        /* if data not fetched -> don't load fragment */
        val view = if (fetched) inflater.inflate(R.layout.fragment_game, container, false) else null
        /* request to load product of the day */
        return view
    }
}