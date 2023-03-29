package fr.etudiant.priceguessr.fragments

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
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
            Token().deleteToken(requireActivity())
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

        }

        return view
    }

}