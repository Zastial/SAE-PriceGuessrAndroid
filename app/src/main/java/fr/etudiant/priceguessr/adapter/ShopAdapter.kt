package fr.etudiant.priceguessr.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import fr.etudiant.priceguessr.R
import fr.etudiant.priceguessr.models.Shop

class ShopAdapter(var listShop : List<Shop>, val context: Context) : RecyclerView.Adapter<ShopAdapter.ViewHolder>() {




    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val shopName = view.findViewById<TextView>(R.id.item_shop_name)
        val shopStock = view.findViewById<TextView>(R.id.item_shop_stock)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_vertical_shop, parent, false)

        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shop = listShop[position]
        holder.shopName.text = shop.name
        holder.shopStock.text = context.getString(R.string.item_shop_stock).replace("{X}", "${shop.stock}")

        holder.itemView.setOnClickListener {
            // Uri for map intent
            val intentUri = Uri.parse("geo:0,0?q=${shop.latitude},${shop.longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, intentUri)
            // Google Maps package optionnal
            mapIntent.setPackage("com.google.android.apps.maps")


            startActivity(context, mapIntent, null)

        }


    }

    override fun getItemCount(): Int {
        return listShop.size
    }




}