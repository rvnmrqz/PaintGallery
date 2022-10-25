package com.arvinmarquez.paintgallery.domain.repository

import com.arvinmarquez.paintgallery.core.utils.Resource
import com.arvinmarquez.paintgallery.domain.model.Painting
import kotlinx.coroutines.flow.Flow

interface PaintingRepository {
    suspend fun getList(page: Int): Flow<Resource<List<Painting>>>
    suspend fun getPainting(id: Long): Flow<Resource<Painting>>
}