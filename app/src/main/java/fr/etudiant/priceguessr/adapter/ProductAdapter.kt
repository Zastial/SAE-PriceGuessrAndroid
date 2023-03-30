package fr.etudiant.priceguessr.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.etudiant.priceguessr.Product
import fr.etudiant.priceguessr.R

class ProductAdapter(val listProduct : List<Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

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
        holder.productName.text = listProduct[position].title
         //holder.productImage
        holder.productDesc.text = listProduct[position].desc
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }
}