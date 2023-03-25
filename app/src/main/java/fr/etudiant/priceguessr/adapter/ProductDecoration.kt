package fr.etudiant.priceguessr.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

// decoration of recycler
class ProductDecoration : RecyclerView.ItemDecoration() {

    // add ofset (décalage)
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        // get rectangle of the item and add spacing
        outRect.bottom = 20

    }

}