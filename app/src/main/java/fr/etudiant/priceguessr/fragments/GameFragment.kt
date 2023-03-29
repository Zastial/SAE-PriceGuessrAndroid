package fr.etudiant.priceguessr.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.etudiant.priceguessr.Product
import fr.etudiant.priceguessr.R
import fr.etudiant.priceguessr.gameLogic.GameLogic


class GameFragment : Fragment() {

    private lateinit var data : List<Product>
    private lateinit var gl: GameLogic
    private lateinit var productImage : ImageView
    private lateinit var productName : EditText


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        try {
            /* get product from the bundle */
            data = arguments?.get("products") as List<Product>
        } catch (e : Exception) {
            /* invalid list of products passed to fragment */
            Toast.makeText(requireActivity(), getString(R.string.toast_decode_invalid), Toast.LENGTH_SHORT).show()
            return null
        }

        val gl = GameLogic(data.toList())
        val view = inflater.inflate(R.layout.fragment_game, container, false)




        return view
    }
}