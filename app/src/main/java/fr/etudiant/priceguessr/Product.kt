package fr.etudiant.priceguessr

import java.util.*

data class Product(
    var id : String,
    var date : Date,
    var title : String,
    var price : Float,
    var imgSrc : String) {


    override fun toString(): String {
        return "{'id': '${id}', 'date':'${date}', 'title':'${title}','price':'${price}', 'imgSrc':'${imgSrc}'}"
    }
}
