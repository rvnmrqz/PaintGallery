package com.arvinmarquez.paintgallery.presentation.single_view

import com.arvinmarquez.paintgallery.domain.model.Painting

data class SingleItemViewState(
    val isLoading: Boolean = false,
    val data: Painting? = null,
    val hasError: Boolean = false,
    val message: String? = null
)