package fr.etudiant.priceguessr.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.etudiant.priceguessr.Product
import fr.etudiant.priceguessr.R
import fr.etudiant.priceguessr.gameLogic.GameLogic


class GameFragment : Fragment() {

    private lateinit var data: Array<Product>
    private lateinit var gl: GameLogic
    private lateinit var productImage: ImageView
    private lateinit var productName: TextView
    private lateinit var productPrice: EditText
    private lateinit var btnValidate : Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        try {
            /* get product from the bundle */
            Log.e("GAME","arguments products : " +arguments?.get("products"))
            data = arguments?.get("products") as Array<Product>
        } catch (e : Exception) {
            /* invalid list of products passed to fragment */
            Toast.makeText(requireActivity(), getString(R.string.toast_decode_invalid), Toast.LENGTH_SHORT).show()
            return null
        }
        /* initialise gamelogic */
        gl = GameLogic(data.toList())

        val view = inflater.inflate(R.layout.fragment_game, container, false)
        productImage = view.findViewById(R.id.game_page_imageView)
        productName = view.findViewById(R.id.game_page_product_name_input)
        productPrice = view.findViewById(R.id.game_page_product_price_input)
        btnValidate = view.findViewById(R.id.game_page_btn_validate)


        btnValidate.setOnClickListener {

        }



        return view
    }
}