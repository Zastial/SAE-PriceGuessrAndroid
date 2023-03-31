package fr.etudiant.priceguessr.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import fr.etudiant.priceguessr.Constants
import fr.etudiant.priceguessr.Product
import fr.etudiant.priceguessr.R
import fr.etudiant.priceguessr.Token
import fr.etudiant.priceguessr.adapter.ProductAdapter
import fr.etudiant.priceguessr.adapter.ProductDecoration
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


class HistoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        recyclerView = view.findViewById(R.id.history_page_vertical_recycler_view)
        val productAdapter = ProductAdapter(listOf<Product>())
        recyclerView.adapter = productAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(ProductDecoration())


        /* get all products */
        val queue = Volley.newRequestQueue(context)
        val productRequest = object : StringRequest(
            Method.GET,
            Constants.API_BASE_URl + Constants.API_PRODUCT_GET_ALL,
            {response ->
                try {
                    /* Get all products from response */
                    val productList = Json.decodeFromString<List<Product>>(response)
                    productAdapter.setData(productList)
                } catch (e : Exception) {
                    Toast.makeText(context, getString(R.string.toast_decode_invalid) , Toast.LENGTH_LONG).show()
                }
            },
            {error ->
                Log.e("HIST", error.toString())
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val header = mutableMapOf<String, String>()
                header["Authorization"] = Token().getToken(requireActivity())
                return header
            }
        }



        queue.add(productRequest)

        return view
    }
}