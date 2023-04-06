package fr.etudiant.priceguessr.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class Guess allows to stock the response of the API when
 * getting updated information of products
 * @param guessRemaining the number of remaining trials
 * @param correct true if the user guess the correct price of the product
 * @param correctPriceIsLess true if the correct price of product is less than the guessed price
 */

@Serializable
data class Guess(var guessRemaining : Int = 5,
                 var correct : Boolean = false,
                 var correctPriceIsLess : Boolean = false,
                 @SerialName("productId") val productId: String? = null
)


