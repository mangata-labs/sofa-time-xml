package com.mangata.core_ui.util

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class SpacingItemDecoration(
    space: Int,
    context: Context,
    private val addStartSpacing: Boolean = false,
    private val addEndSpacing: Boolean = false
) :
    RecyclerView.ItemDecoration() {

    private val spaceInDp = dpToPx(context, space)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (spaceInDp <= 0) {
            return
        }
        if (addStartSpacing &&
            parent.getChildLayoutPosition(view) < 1 ||
            parent.getChildLayoutPosition(view) >= 1
        ) {
            if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
                outRect.top = spaceInDp
            } else {
                outRect.left = spaceInDp
            }
        }
        if (addEndSpacing && parent.getChildAdapterPosition(view) == getTotalItemCount(parent) - 1) {
            if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
                outRect.bottom = spaceInDp
            } else {
                outRect.right = spaceInDp
            }
        }
    }

    private fun getTotalItemCount(parent: RecyclerView): Int {
        return parent.adapter!!.itemCount
    }

    private fun getOrientation(parent: RecyclerView): Int {
        return if (parent.layoutManager is LinearLayoutManager) {
            (parent.layoutManager as LinearLayoutManager?)!!.orientation
        } else {
            throw IllegalStateException("SpacingItemDecoration can only be used with a LinearLayoutManager.")
        }
    }
}


class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val space: Int,
    private val context: Context,
    private val includeEdge: Boolean = false
) : ItemDecoration() {

    private val spaceInDp = dpToPx(context, space)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount
        if (includeEdge) {
            outRect.left = spaceInDp - column * spaceInDp / spanCount
            outRect.right = (column + 1) * spaceInDp / spanCount
            if (position < spanCount) {
                outRect.top = spaceInDp
            }
            outRect.bottom = spaceInDp
        } else {
            outRect.left = column * spaceInDp / spanCount
            outRect.right = spaceInDp - (column + 1) * spaceInDp / spanCount
            if (position >= spanCount) {
                outRect.top = spaceInDp
            }
        }
    }
}