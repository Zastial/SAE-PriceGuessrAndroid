package fr.etudiant.priceguessr.fragments

import android.app.Dialog
import android.content.Context
import android.media.MediaRouter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import fr.etudiant.priceguessr.Constants
import fr.etudiant.priceguessr.Product
import fr.etudiant.priceguessr.R
import fr.etudiant.priceguessr.Token
import fr.etudiant.priceguessr.gameLogic.GameLogic
import org.json.JSONObject


class GameFragment : Fragment() {

    private lateinit var data: Array<Product>
    private lateinit var gl: GameLogic
    private lateinit var productImage: ImageView
    private lateinit var productName: TextView
    private lateinit var productPriceInput: EditText
    private lateinit var btnValidate: Button
    private lateinit var btnPreviousProduct: Button
    private lateinit var btnNextProduct: Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        try {
            /* get product from the bundle */
            data = arguments?.getParcelableArray("products") as Array<Product>
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
        productPriceInput = view.findViewById(R.id.game_page_product_price_input)
        btnValidate = view.findViewById(R.id.game_page_btn_validate)
        btnPreviousProduct = view.findViewById(R.id.game_page_btn_previous_product)
        btnNextProduct = view.findViewById(R.id.game_page_btn_next_product)


        /* .. revoir l'implémentation ..
        *  probleme a cause du getProduct qui peut retourner null
        * */

        val currentProduct = gl.getProduct()
        if (currentProduct == null) {
            Toast.makeText(context, "aucun produit n'a été récupéré", Toast.LENGTH_SHORT).show()
            /* don't load view */
            return null
        } else {
            showProduct(currentProduct)
        }

        btnPreviousProduct.setOnClickListener {
            gl.previousProduct()
            showProduct(gl.getProduct())
        }

        btnNextProduct.setOnClickListener {
            gl.nextProduct()
            showProduct(gl.getProduct())
        }

        /* check if product null  (!! in uri)  */
        btnValidate.setOnClickListener {
            val priceOfUser = productPriceInput.text.toString()
            if (priceOfUser.isEmpty()) {
                Toast.makeText(context, getString(R.string.toast_game_page_empty_price), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val product = gl.getProduct()
            val queue = Volley.newRequestQueue(context)
            val priceRequest = object : StringRequest(
                Request.Method.GET,
                Constants.API_BASE_URl + Constants.API_PRODUCT_GET_ONE + product!!.id + "/" + priceOfUser,
                {response ->
                    Log.e("PROD", response)
                    /* update guess of profuct */

                    val dialog = Dialog(requireContext())
                    dialog.setContentView(R.layout.layout_dialog_custom)

                    // if button is clicked, close the custom dialog
                    dialog.findViewById<Button>(R.id.game_page_dialog_btn_ok).setOnClickListener {
                        dialog.dismiss()
                    }
                    dialog.show()
                },
                { error ->
                    /* Prevent if API is not running  */
                    if (error is VolleyError || error == null || error.networkResponse != null) {
                        Toast.makeText(context,getString(R.string.toast_unknown_error),Toast.LENGTH_SHORT).show()
                    } else {
                        try {
                            val errorCode = error.networkResponse.statusCode
                            val errorBody =
                                JSONObject(error.networkResponse.data.decodeToString()).getString("message")
                            when (errorCode) {
                                401 -> {Toast.makeText(context, getString(R.string.toast_unknown_error), Toast.LENGTH_SHORT).show()}
                                else -> Toast.makeText(context, errorBody, Toast.LENGTH_SHORT).show()
                            }

                        } catch (e: Exception) {
                            Toast.makeText(context,getString(R.string.toast_unknown_error),Toast.LENGTH_SHORT).show()
                        }
                    }
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val header = mutableMapOf<String, String>()
                    header["Authorization"] = Token().getToken(requireActivity())
                    return header
                }
            }
            queue.add(priceRequest)
        }



        return view
    }

    private fun showProduct(currentProduct : Product?) {
        if (currentProduct != null) {
            //TODO check the "guess" of product to see if there is any attempt left or, if the correct price is already guessed
            // in order to load the correct Dialog content

            Picasso.get().load(currentProduct.imgSrc).into(productImage)
            productName.setText(currentProduct.title)
        }
    }


}