package fr.etudiant.priceguessr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import com.squareup.picasso.Picasso

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var image: ImageView
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var price: TextView
    private lateinit var btnBack: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        image = findViewById(R.id.detail_product_page_product_image)
        title = findViewById(R.id.detail_product_page_product_title)
        description = findViewById(R.id.detail_product_page_product_desc)
        price = findViewById(R.id.detail_product_page_product_price)
        btnBack = findViewById(R.id.detail_product_page_btn_back)

        try {
            /* get product from the history page (from product adapter) */
            val data = intent.extras?.getParcelable<Product>("product")
            if (data != null) {
                Picasso.get().load(data.imgSrc).into(image)
                title.text = data.title
                description.text = data.desc
                price.text = data.price.toString()
            }

        } catch (e : Exception) {
            /* invalid product passed to activity */
            Toast.makeText(this, getString(R.string.toast_decode_invalid), Toast.LENGTH_SHORT).show()
            finish()
        }


        btnBack.setOnClickListener {
            finish()
        }


    }
}