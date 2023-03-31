package fr.etudiant.priceguessr.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.etudiant.priceguessr.Constants
import fr.etudiant.priceguessr.Product
import fr.etudiant.priceguessr.ProductDetailsActivity
import fr.etudiant.priceguessr.R

class ProductAdapter(var listProduct : List<Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage = view.findViewById<ImageView>(R.id.item_image)
        val productName = view.findViewById<TextView>(R.id.item_name)
        val productDesc = view.findViewById<TextView>(R.id.item_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_vertical_product, parent, false)

        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = listProduct[position]
        holder.productName.text = product.title
        Picasso.get().load(product.imgSrc).into(holder.productImage)
        holder.productDesc.text = product.desc

        holder.itemView.setOnClickListener {
            val detailIntent = Intent(holder.itemView.context, ProductDetailsActivity::class.java)
            detailIntent.putExtra("product", product)
            holder.itemView.context.startActivity(detailIntent)
        }
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }


    fun setData(listProduct: List<Product>) {
        this.listProduct = listProduct
        notifyDataSetChanged()
    }
}