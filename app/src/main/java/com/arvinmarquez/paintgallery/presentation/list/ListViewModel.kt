package com.arvinmarquez.paintgallery.presentation.list


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class ListViewModel @Inject constructor(
    private val repository: PaintingRepository
) : ViewModel() {

    private var page = 1
    private var loading = false

    private var list = arrayListOf<Painting>()

    private var _stateFlow = MutableStateFlow(
        ListViewState(
            isLoading = true,
            list = emptyList()
        )
    )
    val stateFlow = _stateFlow.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        if (loading) return
        page = 1
        viewModelScope.launch {
            repository.getList(1).collectLatest {
                when (it.status) {
                    Status.LOADING -> {
                        loading = true
                        _stateFlow.value = ListViewState(isLoading = true)
                    }
                    Status.SUCCESS -> {
                        loading = false
                        val tempList = it.data ?: emptyList()
                        list = tempList as ArrayList<Painting>

                        _stateFlow.value = ListViewState(list = list)
                    }
                    Status.ERROR -> {
                        loading = false
                        _stateFlow.value = ListViewState( list = it.data ?: emptyList(), hasError = true, message = it.message)
                    }
                }
            }
        }
    }

    fun loadNextData() {
        if (loading) return
        loading = true

        viewModelScope.launch {
            val tempPage = page + 1

            repository.getList(tempPage).collectLatest {
                when (it.status) {
                    Status.LOADING -> {
                        loading = true
                        _stateFlow.value = ListViewState(list = list, maxPageLoaded = false)
                    }
                    Status.SUCCESS -> {
                        loading = false
                        page = tempPage

                        val newItems = it.data ?: emptyList()
                        list.addAll(newItems)

                        if (newItems.isNotEmpty()) {
                            _stateFlow.value = ListViewState(list = list, message = "new data")
                        } else {
                            _stateFlow.value = ListViewState(list = list, maxPageLoaded = true)
                        }
                    }
                    Status.ERROR -> {
                        loading = false
                        _stateFlow.value = ListViewState(
                            list = it.data ?: emptyList(),
                            hasError = true,
                            message = it.message
                        )
                    }
                }
            }
        }
    }

    fun reloadData(){

    }


}