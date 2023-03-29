package fr.etudiant.priceguessr.gameLogic

/**
 * Data class Guess alows to stock the API
 * @param guessRemaining the number of remmaining trials
 * @param correct true if the user guess the correct price of the product
 * @param correctPriceIsLess true if the correct price of product is less than the guessed price
 */
data class Guess(var guessRemaining : Int = 5, var correct : Boolean = false, var correctPriceIsLess : Boolean = false) {

}
