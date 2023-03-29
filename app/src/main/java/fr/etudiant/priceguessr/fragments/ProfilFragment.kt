package fr.etudiant.priceguessr.fragments

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import fr.etudiant.priceguessr.LoginActivity
import fr.etudiant.priceguessr.MainActivity
import fr.etudiant.priceguessr.R
import fr.etudiant.priceguessr.Token

class ProfilFragment() : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profil, container, false)


        val btnLogout = view.findViewById<Button>(R.id.parameter_page_btn_logout)

        btnLogout.setOnClickListener {
            activity?.applicationContext?.let { it1 -> Token().deleteToken(it1) }
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)

        }

        return view
    }

}