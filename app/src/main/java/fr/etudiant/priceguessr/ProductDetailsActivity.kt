package fr.etudiant.priceguessr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class ProductDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)


        try {
            /* get product from the history page (from product adapter) */
            val data = intent.extras?.getParcelable<Product>("product")
        } catch (e : Exception) {
            /* invalid product passed to activity */
            Toast.makeText(this, getString(R.string.toast_decode_invalid), Toast.LENGTH_SHORT).show()
            finish()
        }


    }
}