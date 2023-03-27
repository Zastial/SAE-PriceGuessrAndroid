package fr.etudiant.priceguessr



import android.util.JsonWriter
import java.util.*


class APIStub {

    private var listProduct : MutableList<Product>
    private val product1 : Product
    private val product2 : Product

    init {
        listProduct = mutableListOf<Product>()
        product1 = Product("p1", Date(31-12-2018), "einstein", 10.0F, "https://www.google.com/imgres?imgurl=https%3A%2F%2Fcdn.futura-sciences.com%2Fsources%2Fimages%2Factu%2FAlbert_Einstein_Langue.jpg&tbnid=rr0hMm7dtc1VnM&vet=12ahUKEwj04bji7Pv9AhWNT6QEHdF1BFwQMygBegUIARDoAQ..i&imgrefurl=https%3A%2F%2Fwww.futura-sciences.com%2Fsante%2Factualites%2Fmedecine-cerveau-einstein-etait-exceptionnel-dixit-nouvelle-etude-42905%2F&docid=2-Jv9RJ9joEp6M&w=430&h=600&q=einstein&ved=2ahUKEwj04bji7Pv9AhWNT6QEHdF1BFwQMygBegUIARDoAQ", "default desc")
        product2 = Product("p2", Date(31-12-2018), "einstein", 10.0F, "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.maisons-pierre.com%2Fwp-content%2Fuploads%2F2020%2F08%2FVisuel5.jpg&tbnid=8C0jJ3lOgLMr9M&vet=12ahUKEwjbt5GN7vv9AhUTrEwKHSrUCjcQMygAegUIARCdAg..i&imgrefurl=https%3A%2F%2Fwww.maisons-pierre.com%2Fnos-maisons%2F&docid=NGjLMDQ4ER3bfM&w=750&h=461&q=maison&ved=2ahUKEwjbt5GN7vv9AhUTrEwKHSrUCjcQMygAegUIARCdAg", "default desc")
        listProduct.add(product1)
        listProduct.add(product2)
    }

    fun getProduit() : String {
        return listProduct.get(0).toString()
    }

    fun getListProduit()  {
        //TODO
        return
    }
}