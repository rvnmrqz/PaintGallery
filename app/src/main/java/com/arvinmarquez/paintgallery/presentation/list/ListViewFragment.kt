package com.arvinmarquez.paintgallery.presentation.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arvinmarquez.paintgallery.databinding.FragmentListViewBinding
import com.arvinmarquez.paintgallery.presentation.list.adapter.LoadingAdapter
import com.arvinmarquez.paintgallery.presentation.list.adapter.PaintingListAdapter
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListViewFragment : Fragment() {

    private lateinit var binder: FragmentListViewBinding
    private val viewModel: ListViewModel by viewModels()

    private val loadingAdapter = LoadingAdapter()
    private val listAdapter = PaintingListAdapter()
    private val concatAdapter = ConcatAdapter(listAdapter, loadingAdapter)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder = FragmentListViewBinding.inflate(inflater, container, false)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        subscribeObservers()
    }

    private fun initViews() {
        val rv = binder.rvList
        val sr = binder.srLayout

        rv.adapter = concatAdapter
        listAdapter.onItemClicked = { _, painting ->
            val action = ListViewFragmentDirections.actionListViewFragmentToSingleItemViewFragment(
                painting.id,
                painting.title
            )
            findNavController().navigate(action)
        }

        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!rv.canScrollVertically(1) && listAdapter.itemCount > 0) {
                    viewModel.loadNextData()
                }
            }
        })
        sr.setOnRefreshListener {
            showLoading(true)
            sr.isRefreshing = false
            viewModel.loadData()
        }
    }

    private fun subscribeObservers() {
        lifecycleScope.launch {
            viewModel.stateFlow.collectLatest {
                if (!it.hasError) {
                    showLoading(it.isLoading)
                    showListLoading(!it.maxPageLoaded)
                    listAdapter.setItems(it.list)
                } else {
                    showError(it.message ?: "Unexpected error occurred")
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binder.shimmerLayout.visibility = if (show) View.VISIBLE else View.GONE
        binder.rvList.visibility = if (show) View.GONE else View.VISIBLE
    }

    private fun showListLoading(show: Boolean) {
        if (show && concatAdapter.adapters.find { it == loadingAdapter } != null) concatAdapter.addAdapter(
            loadingAdapter
        )
        else {
            concatAdapter.removeAdapter(loadingAdapter)
        }
    }

    private fun showError(message: String) {
        showLoading(true)
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}