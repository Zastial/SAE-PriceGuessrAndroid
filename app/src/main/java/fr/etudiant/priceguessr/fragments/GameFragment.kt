package fr.etudiant.priceguessr.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import fr.etudiant.priceguessr.Constants
import fr.etudiant.priceguessr.MainActivity
import fr.etudiant.priceguessr.models.Product
import fr.etudiant.priceguessr.R
import fr.etudiant.priceguessr.models.Token
import fr.etudiant.priceguessr.gameLogic.GameLogic
import org.json.JSONObject


class GameFragment : Fragment() {

    private lateinit var data: Array<Product>
    private lateinit var productImage: ImageView
    private lateinit var productName: TextView
    private lateinit var productPriceInput: EditText
    private lateinit var btnValidate: Button
    private lateinit var btnPreviousProduct: Button
    private lateinit var btnNextProduct: Button
    private lateinit var gl: GameLogic

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        productImage = view.findViewById(R.id.game_page_imageView)
        productName = view.findViewById(R.id.game_page_product_name_input)
        productPriceInput = view.findViewById(R.id.game_page_product_price_input)
        btnValidate = view.findViewById(R.id.game_page_btn_validate)
        btnPreviousProduct = view.findViewById(R.id.game_page_btn_previous_product)
        btnNextProduct = view.findViewById(R.id.game_page_btn_next_product)

        val queue = Volley.newRequestQueue(context)
        gl = ViewModelProvider(requireActivity()).get(GameLogic::class.java)

        if (gl.isEmpty()) {
            Toast.makeText(context, "aucun produit n'a été récupéré", Toast.LENGTH_SHORT).show()
            /* don't load view */
            return null
        } else {
            showCurrentProduct()
        }

        btnPreviousProduct.setOnClickListener {
            gl.previousProduct()
            showCurrentProduct()
        }

        btnNextProduct.setOnClickListener {
            gl.nextProduct()
            showCurrentProduct()
        }

        /* check if product null  (!! in uri)  */
        btnValidate.setOnClickListener {
            val priceOfUser = productPriceInput.text.toString()
            if (priceOfUser.isEmpty()) {
                Toast.makeText(context, getString(R.string.toast_game_page_empty_price), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val product = gl.getProduct()

            val priceRequest = object : StringRequest(
                Request.Method.GET,
                Constants.API_BASE_URl + Constants.API_PRODUCT_GET_ONE + product!!.id + "/" + priceOfUser,
                {response ->
                    Log.e("PROD response in price request", response)
                    try {
                        val actualGuessProduct = gl.getGuess()
                        Log.e("PROD BEFORE", actualGuessProduct.toString())
                        /* get response */
                        val responseBody = JSONObject(response)
                        val guessRemaining =  responseBody.get("guessRemaining").toString().toInt()
                        val guessIsCorrect = responseBody.get("correct").toString().toBoolean()
                        val correctPriceIsLess = responseBody.get("correctPriceIsLess").toString().toBoolean()
                        /* update product */
                        actualGuessProduct?.guessRemaining = guessRemaining
                        actualGuessProduct?.correct = guessIsCorrect
                        actualGuessProduct?.correctPriceIsLess = correctPriceIsLess
                        Log.e("PROD AFTER", gl.getGuess().toString())

                        /* modify dialog content */
                        val dialog = Dialog(requireContext())
                        dialog.setContentView(R.layout.dialog_custom_game_results)


                        if (guessIsCorrect) {
                            dialog.findViewById<TextView>(R.id.game_page_dialog_text_answer_is_correct).setText(R.string.game_page_dialog_title_good_answer)
                            dialog.findViewById<TextView>(R.id.game_page_dialog_text_price_answer).setText(productPriceInput.text.toString() + getText(R.string.default_product_currency))
                        } else if (correctPriceIsLess) {
                            dialog.findViewById<TextView>(R.id.game_page_dialog_text_price_answer).setText(R.string.game_page_dialog_desc_price_is_lower)
                        }
                        dialog.findViewById<TextView>(R.id.game_page_dialog_guess_remaining).text = getString(R.string.game_page_dialog_desc_guess_remaining).replace("{X}", guessRemaining.toString())

                        /* update btn validate */
                        setValidationButton()

                        dialog.findViewById<Button>(R.id.game_page_dialog_btn_ok).setOnClickListener {
                            dialog.dismiss()
                        }
                        dialog.show()

                    } catch (e: Exception) {
                        Toast.makeText(context, getString(R.string.toast_decode_invalid), Toast.LENGTH_SHORT).show()
                    }
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
                    header[Constants.HEADER_TOKEN_AUTHORIZATION] = Token().getToken(requireActivity())
                    return header
                }
            }
            queue.add(priceRequest)
        }

        return view
    }

    private fun showCurrentProduct() {
        val currentProduct = gl.getProduct()
        if (currentProduct != null) {
            Picasso.get().load(currentProduct.imgSrc).into(productImage)
            productName.setText(currentProduct.title)
            setValidationButton()
        }
    }

    /** setValidationButton
     *  disable the button if user already guessed the price of product or
     *  the maximum number of guesses has been reached
     */
    private fun setValidationButton() {
        val guess = gl.getGuess()
        if (guess !=null) {
            btnValidate.isEnabled = !guess.correct && guess.guessRemaining > 0
            Log.e("PROD is available", btnValidate.isEnabled.toString())
        }
    }

}