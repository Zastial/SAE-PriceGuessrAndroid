package fr.etudiant.priceguessr

import android.content.Context
import android.widget.Toast

class Constants {

    companion object {
        val API_BASE_URl = "http://10.0.2.2:3000"

        val HEADER_TOKEN_AUTHORIZATION = "Authorization"

        //product
        val API_PRODUCT_GET_ALL = "/product"
        val API_PRODUCT_GET_ONE = "/product/" //require params (id ..)
        val API_PRODUCT_GET_DAILY= "/product/daily"
        val API_PRODUCT_GET_DAILY_GUESS = "/product/daily/guess"
        val API_PRODUCT_GET_DAILY_BY_DATE = "/product/daily/" // require date
        val API_PRODUCT_SHOP_AVAILABILITY = "/product/availability/" //require product id

        //user
        /* API_USER_POST_REGISTER (post) Register a new user with login and password */
        val API_USER_POST_REGISTER = "/user/register"

        /* API_USER can be used to :
        * - DELETE (delete) user
        * - MODIFY (put) user with body : password?*/
        val API_USER= "/user"

        /* API_AUTH (post)
        * Authentificate user with login and password
        * */
        val API_USER_AUTH = "/user/auth"

    }
}