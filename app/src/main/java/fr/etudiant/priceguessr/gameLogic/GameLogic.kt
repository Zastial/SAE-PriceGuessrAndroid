package fr.etudiant.priceguessr.gameLogic

import androidx.lifecycle.ViewModel
import fr.etudiant.priceguessr.models.Product


/**
 * GameLogic implements the game logic
 * @param list is list of Product
 */
class GameLogic(): ViewModel() {

    private var listProduct : MutableList<Pair<Product, Guess>>
    private var index : Int

    init {
        this.listProduct = mutableListOf()
        this.index = 0
    }


    fun getProduct() : Product? {
        return if (listProduct.isEmpty()) null else listProduct[index].first
    }

    fun getGuess(): Guess? {
        return if (listProduct.isEmpty()) null else listProduct[index].second
    }

    fun nextProduct() {
        if (listProduct.isNotEmpty()) {
            index = (index + 1) % listProduct.size
        }

    }

    fun previousProduct() {
        if (listProduct.isNotEmpty()) {
            // modulo doesn't work well with negative numbers in Kotlin
            index--
            if (index < 0) {
                index = listProduct.lastIndex
            }
        }
    }



    fun isEmpty(): Boolean = listProduct.isEmpty()

    fun setProducts(list: Array<Product>) {
        this.listProduct = mutableListOf()
        for (prod in list) {
            /* set product with guess by default */
            val guess = Guess()
            listProduct.add(Pair(prod, guess))
        }
        this.index = 0
    }

    fun setGuess(guessProductList: Array<Guess>) {
        for (guess in guessProductList) {
            if (guess.productId.isNullOrBlank()) {continue}
            val index = listProduct.indexOfFirst {it.first.id == guess.productId }
            if (index != -1) {
                listProduct[index] = Pair(listProduct[index].first, guess)
            }
        }
    }


}