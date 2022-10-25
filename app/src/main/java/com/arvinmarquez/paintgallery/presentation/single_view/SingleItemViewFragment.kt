package com.arvinmarquez.paintgallery.presentation.single_view

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arvinmarquez.paintgallery.core.utils.Constants.PAINTING_TITLE
import com.arvinmarquez.paintgallery.databinding.FragmentSingleItemViewBinding
import com.arvinmarquez.paintgallery.domain.model.Painting
import com.arvinmarquez.paintgallery.presentation.MainActivity
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SingleItemViewFragment : Fragment() {

    private lateinit var binder: FragmentSingleItemViewBinding
    private val viewModel: SingleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder = FragmentSingleItemViewBinding.inflate(inflater, container, false)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = arguments?.getString(PAINTING_TITLE)
        subscribeObserver()
    }

    private fun subscribeObserver() {
        lifecycleScope.launch {
            viewModel.flowState.collectLatest {
                if(!it.hasError){
                    showLoading(it.isLoading)
                    it.data?.let { painting -> displayPainting(painting) }
                }else{
                    showError(it.message ?: "Unexpected error occurred")
                }
            }
        }
    }

    private fun displayPainting(painting: Painting) {
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

        Glide.with(requireActivity())
            .load(painting.image)
            .skipMemoryCache(false)
            .placeholder(shimmerDrawable)
            .into(binder.backgroundImg)

        Glide.with(requireActivity())
            .load(painting.image)
            .skipMemoryCache(false)
            .placeholder(shimmerDrawable)
            .dontAnimate()
            .into(binder.img)


        binder.tvTitle.text = painting.title
        binder.tvArtist.text = painting.artist
        binder.tvYear.text = painting.year

        if (painting.details != null) {
            binder.tvDetails.movementMethod = ScrollingMovementMethod()
            binder.tvDetails.text = painting.details
        } else
            binder.tvDetails.visibility = View.GONE

        showLoading(false)
    }

    private fun showError(error: String) {
        showLoading(false)
        Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(show: Boolean) {
        binder.shimmerLayout.visibility = if (show) View.VISIBLE else View.GONE
        binder.dataLayout.visibility = if (show) View.GONE else View.VISIBLE
    }
}