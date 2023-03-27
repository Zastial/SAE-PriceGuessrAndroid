package fr.etudiant.priceguessr

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import fr.etudiant.priceguessr.fragments.GameFragment
import fr.etudiant.priceguessr.fragments.HistoryFragment
import fr.etudiant.priceguessr.fragments.ProfileFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Auth system, lunch login activity if user is not logged


        //first load GameFragment()
        loadFragment(GameFragment())
        // navigation View Logic
        val navigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navigationView.setOnItemSelectedListener {

            when(it.itemId)
            {
                R.id.home -> {
                    loadFragment(GameFragment())
                    true
                }
                R.id.history -> {
                    loadFragment(HistoryFragment())
                    true
                }
                R.id.profile-> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> {
                    Log.e("TAG", "false")
                    false
                }
            }
        }
    }

    // verify is the user hav
    private fun isLoggedIn(): Boolean {
        // get sharedPreferences of the app
        val sharedPreferences = getSharedPreferences("PriceGuessr", MODE_PRIVATE)

        sharedPreferences.getString("token","")



        var isLogged = true

        return isLogged
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}