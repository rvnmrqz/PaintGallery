package com.arvinmarquez.paintgallery.presentation.list.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arvinmarquez.paintgallery.R
import com.arvinmarquez.paintgallery.domain.model.Painting

private const val TAG = "LoadingAdapter"
class LoadingAdapter : RecyclerView.Adapter<LoadingAdapter.LoadingViewHolder>() {

    private val adapterItems: MutableList<Painting> = ArrayList()
    var onItemClicked: ((index: Int, painting: Painting) -> Unit)? = null

    class LoadingViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_painting_shimmer_layout, parent, false)
        return LoadingViewHolder(view)
    }

    override fun onBindViewHolder(holder: LoadingViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 1
    }
}