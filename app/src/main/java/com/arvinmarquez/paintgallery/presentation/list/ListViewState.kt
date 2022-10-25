package com.arvinmarquez.paintgallery.presentation.list

import com.arvinmarquez.paintgallery.domain.model.Painting

data class ListViewState(
    val isLoading: Boolean = false,
    val list: List<Painting> = emptyList(),
    val maxPageLoaded: Boolean = false,
    val hasError: Boolean = false,
    val message: String? = null
)