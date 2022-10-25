package com.arvinmarquez.paintgallery.presentation.list.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.arvinmarquez.paintgallery.R
import com.arvinmarquez.paintgallery.databinding.ItemPaintingLayoutBinding
import com.arvinmarquez.paintgallery.domain.model.Painting
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

private const val TAG = "PaintingListAdapter"
class PaintingListAdapter : RecyclerView.Adapter<PaintingListAdapter.PaintingViewHolder>() {

    private val adapterItems: MutableList<Painting> = ArrayList()
    var onItemClicked: ((index: Int, painting: Painting) -> Unit)? = null

    class PaintingViewHolder(private val binder: ItemPaintingLayoutBinding) :
        RecyclerView.ViewHolder(binder.root) {

        fun bind(painting: Painting) {
            try {
                val colorWhite = ContextCompat.getColor(
                    binder.root.context,
                    R.color.white
                )

                binder.tvTitle.setBackgroundColor(colorWhite)
                binder.tvYear.setBackgroundColor(colorWhite)
                binder.tvTitle.text = painting.title
                binder.tvYear.text = painting.year

                val shimmer = Shimmer.AlphaHighlightBuilder()
                    .setDuration(1800)
                    .setBaseAlpha(0.7f)
                    .setHighlightAlpha(0.6f)
                    .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                    .setAutoStart(true)
                    .build()

                val shimmerDrawable = ShimmerDrawable().apply {
                    setShimmer(shimmer)
                }

                Glide.with(binder.root.context)
                    .load(painting.image)
                    .skipMemoryCache(false)
                    .placeholder(shimmerDrawable)
                    .into(binder.ivPainting)
            } catch (e: Exception) {
                Log.e(TAG, "bind: ${e.localizedMessage}")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaintingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_painting_layout, parent, false)
        val binder = ItemPaintingLayoutBinding.bind(view)
        return PaintingViewHolder(binder)
    }

    override fun onBindViewHolder(holder: PaintingViewHolder, position: Int) {
        val item = adapterItems[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(position, item)
        }
    }

    override fun getItemCount(): Int {
        return adapterItems.size
    }

    fun setItems(items: List<Painting>) {
        adapterItems.clear()
        adapterItems.addAll(items)
        notifyDataSetChanged()
    }
}