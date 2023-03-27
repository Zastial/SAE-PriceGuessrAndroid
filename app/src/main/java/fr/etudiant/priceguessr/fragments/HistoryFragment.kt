package fr.etudiant.priceguessr.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.etudiant.priceguessr.R
import fr.etudiant.priceguessr.adapter.ProductAdapter
import fr.etudiant.priceguessr.adapter.ProductDecoration


class HistoryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)


        verticalRecyclerView.adapter = ProductAdapter()
        verticalRecyclerView.addItemDecoration(ProductDecoration())

        return view
    }
}