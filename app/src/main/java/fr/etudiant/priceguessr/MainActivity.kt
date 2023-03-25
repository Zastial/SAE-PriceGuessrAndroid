package fr.etudiant.priceguessr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import fr.etudiant.priceguessr.fragments.GameFragment
import fr.etudiant.priceguessr.fragments.HistoryFragment
import fr.etudiant.priceguessr.fragments.ProfileFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Auth system, lunch login activity if user is not logged

        if (isLoggedIn()){

        } else {

        }


        // import navigation view
        /*
        val navigationView = findViewById<NavigationView>(R.id.bottomNavigationView)
        navigationView.setNavigationItemSelectedListener {

            when(it.itemId)
            {
                R.id.home -> {
                    // il faut charger le fragment
                    loadFrangment(GameFragment())
                    return@setNavigationItemSelectedListener true
                }
                R.id.history -> {
                    // il faut charger le fragment
                    loadFrangment(HistoryFragment())
                    return@setNavigationItemSelectedListener true
                }
                R.id.profile-> {
                    // il faut charger le fragment
                    loadFrangment(ProfileFragment())
                    return@setNavigationItemSelectedListener true
                }
                else -> {
                    Log.e("TAG", "false")
                    false
                }
            }
        }*/


        //injecter le fragment dans la boite (fragment container)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, ProfileFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    // verify ..
    private fun isLoggedIn(): Boolean {
        var isLogged = true

        return isLogged
    }

    private fun loadFrangment(fragment: Fragment) {
        //val transaction = supportFragmentManager.beginTransaction()
        //transaction.replace(R.id.fragment_container, GameFragment)
    }
}