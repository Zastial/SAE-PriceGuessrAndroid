package fr.etudiant.priceguessr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import fr.etudiant.priceguessr.fragments.GameFragment
import fr.etudiant.priceguessr.fragments.HistoryFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Auth system, lunch login activity if user is not logged

        if (isLoggedIn()){

        } else {

        }





        //injecter le fragment dans la boite (fragment container)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, HistoryFragment())
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