package fr.etudiant.priceguessr.gameLogic

import androidx.lifecycle.ViewModel
import fr.etudiant.priceguessr.Product


/**
 * GameLogic implements the game logic
 * @param list is list of Product
 */
class GameLogic(list: List<Product>){

    private var listProduct : MutableList<Pair<Product, Guess>>
    private var index : Int

    init {
        this.listProduct = mutableListOf()
        for (prod in list) {
            val guess = Guess()
            listProduct.add(Pair(prod, guess))
        }
        this.index = 0
    }


    fun getProduct() : Product?{
        return if (listProduct.isEmpty()) null else listProduct[index].first
    }

    fun nextProduct() {
        if (listProduct.isNotEmpty()) {
            index = (index + 1) % listProduct.size
        }

    }

    fun previousProduct() {
        if (listProduct.isNotEmpty()) {
            index = (index - 1) % listProduct.size
        }
    }


    fun getGuess(): Guess? {
        return if (listProduct.isEmpty()) null else listProduct[index].second
    }

}