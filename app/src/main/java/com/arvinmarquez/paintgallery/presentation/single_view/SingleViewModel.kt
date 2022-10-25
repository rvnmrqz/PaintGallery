package com.arvinmarquez.paintgallery.presentation.single_view

import androidx.lifecycle.*
import com.arvinmarquez.paintgallery.core.utils.Constants
import com.arvinmarquez.paintgallery.core.utils.Resource
import com.arvinmarquez.paintgallery.core.utils.Status
import com.arvinmarquez.paintgallery.domain.model.Painting
import com.arvinmarquez.paintgallery.domain.repository.PaintingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleViewModel @Inject constructor(
    private val repository: PaintingRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    private var paintingId: Long = state.get<Long>(Constants.PAINTING_ID) ?: 0L
    private var isLoading = false

    private var _flowState = MutableStateFlow(SingleItemViewState(isLoading = true))
    val flowState = _flowState.asStateFlow()

    init {
        loadPainting()
    }

    private fun loadPainting() {
        if (isLoading) return

        viewModelScope.launch {
            repository.getPainting(paintingId).collectLatest { resource ->
                isLoading = resource.status == Status.LOADING

                when (resource.status) {
                    Status.LOADING -> {
                        _flowState.value = SingleItemViewState(isLoading = true)
                    }
                    Status.SUCCESS -> {
                        _flowState.value = SingleItemViewState(data = resource.data)
                    }
                    Status.ERROR -> {
                        _flowState.value =
                            SingleItemViewState(hasError = true, message = resource.message)
                    }
                }
            }
        }
    }

}