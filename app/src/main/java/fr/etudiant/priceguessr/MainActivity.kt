package fr.etudiant.priceguessr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //injecter le fragment dans la boite (fragment container)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, GameFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun loadFrangment(fragment: Fragment) {
        //val transaction = supportFragmentManager.beginTransaction()
        //transaction.replace(R.id.fragment_container, GameFragment)
    }
}