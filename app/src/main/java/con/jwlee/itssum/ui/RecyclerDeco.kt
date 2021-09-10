package con.jwlee.itssum.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerDeco(divHeight: Int) : RecyclerView.ItemDecoration() {

    var divHeight = divHeight

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) != parent.getAdapter()!!.getItemCount() - 1) {
            outRect.bottom = divHeight;
        }
    }

}