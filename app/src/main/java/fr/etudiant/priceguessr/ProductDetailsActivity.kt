package fr.etudiant.priceguessr

import android.app.Dialog
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import fr.etudiant.priceguessr.adapter.ShopAdapter
import fr.etudiant.priceguessr.models.Product
import fr.etudiant.priceguessr.models.Shop
import fr.etudiant.priceguessr.models.Token
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import java.time.Instant
import java.util.*

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var productId: String
    private lateinit var image: ImageView
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var price: TextView
    private lateinit var btnBack: Button
    private lateinit var btnAvailability: Button
    private lateinit var dialogBtnClose: ImageButton
    private lateinit var dialogRecyclerView: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        image = findViewById(R.id.detail_product_page_product_image)
        title = findViewById(R.id.detail_product_page_product_title)
        description = findViewById(R.id.detail_product_page_product_desc)
        price = findViewById(R.id.detail_product_page_product_price)
        btnBack = findViewById(R.id.detail_product_page_btn_back)
        btnAvailability = findViewById(R.id.detail_product_page_btn_availabilitty)
        productId = ""



        try {
            /* get product from the history page (from product adapter) */
            val data = intent.extras?.getParcelable<Product>("product")
            Log.e("DATA PROD", data.toString())
            if (data != null) {
                Picasso.get().load(data.imgSrc).into(image)
                title.text = data.title
                description.text = data.desc
                productId = data.id

                val date = Date.from(Instant.parse(data.date))
                if (DateUtils.isToday(date.time)) {
                    price.text = data.price.toString()
                } else {
                    price.text = "???"
                }
            }

        } catch (e : Exception) {
            /* invalid product passed to activity */
            Toast.makeText(this, getString(R.string.toast_decode_invalid)+e.message, Toast.LENGTH_SHORT).show()
            finish()
        }


        btnBack.setOnClickListener {
            finish()
        }

        /* open activity to see all shops where the product is available */
        btnAvailability.setOnClickListener {
            if (productId.isEmpty()) {
                Toast.makeText(this, getString(R.string.toast_no_data_available), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            /* setup dialog view with recycler */
            val dialogShop = Dialog(this)
            dialogShop.setContentView(R.layout.dialog_custom_shops)
            dialogBtnClose = dialogShop.findViewById(R.id.dialog_shops_btn_close)
            dialogRecyclerView = dialogShop.findViewById(R.id.dialog_shops_recycler_view)
            dialogRecyclerView.addItemDecoration(DividerItemDecoration(this@ProductDetailsActivity,  DividerItemDecoration.VERTICAL))

            

            dialogBtnClose.setOnClickListener {
                dialogShop.dismiss()
            }


            val queue = Volley.newRequestQueue(this)
            /* request to get list of shops where the product is available */
            val shopRequest = object : StringRequest(
                Request.Method.GET,
                Constants.API_BASE_URl + Constants.API_PRODUCT_SHOP_AVAILABILITY + productId,
                {response ->
                    try {
                        val shopList = Json.decodeFromString<List<Shop>>(response)
                        /* load view*/
                        val shopAdapter = ShopAdapter(shopList, this@ProductDetailsActivity)

                        dialogRecyclerView.adapter = shopAdapter
                        dialogRecyclerView.layoutManager = LinearLayoutManager(this)

                        
                        dialogShop.show()

                    } catch (e : Exception) {
                        Toast.makeText(this, getString(R.string.toast_decode_invalid) , Toast.LENGTH_LONG).show()
                    }

                },
                {error ->
                    /* Prevent if API is not running  */
                    if (error is VolleyError ||  error == null || error.networkResponse != null) {
                        // TODO

                    } else {
                        try {
                            val errorCode = error.networkResponse.statusCode
                            val errorBody = JSONObject(error.networkResponse.data.decodeToString()).getString("message")
                            when (errorCode) {
                                400 ->  Toast.makeText(this, errorBody, Toast.LENGTH_SHORT).show()
                                else -> Toast.makeText(this,  getString(R.string.toast_unknown_error), Toast.LENGTH_SHORT).show()
                            }

                        } catch (e : Exception) {
                            Toast.makeText(this, getString(R.string.toast_unknown_error), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = mutableMapOf<String,String>()
                    headers[Constants.HEADER_TOKEN_AUTHORIZATION] = Token().getToken(this@ProductDetailsActivity)
                    return headers
                }
            }

            queue.add(shopRequest)
        }
    }
}