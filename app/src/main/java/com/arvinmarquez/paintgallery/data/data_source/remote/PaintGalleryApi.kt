package com.arvinmarquez.paintgallery.data.data_source.remote

import com.arvinmarquez.paintgallery.data.entity.remote.PaintingListResponse
import com.arvinmarquez.paintgallery.data.entity.remote.PaintingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PaintGalleryApi {

    @GET("list")
    suspend fun list(
        @Query("page", encoded = true) page: Int? = 1
    ): Response<PaintingListResponse>

    @GET("painting")
    suspend fun getPainting(
        @Query("id", encoded = true) id: Long
    ): Response<PaintingResponse>
}