package fr.etudiant.priceguessr.models

import kotlinx.serialization.Serializable

@Serializable
data class Shop(
    val buCode: String? = null,
    val stock: Int = 0,
    val name: String?= "Shop",
    val longitude: Float,
    val latitude: Float
    ) {

    override fun toString(): String {
        return "${name}"
    }
}
