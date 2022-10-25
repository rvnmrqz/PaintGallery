package com.arvinmarquez.paintgallery.data.entity.remote

import com.google.gson.annotations.SerializedName

data class PaintingListResponse(

    @SerializedName("page")
    val page: Int,

    @SerializedName("max_page")
    val maxPage: Int,

    @SerializedName("item_count")
    val itemCount: Int,

    @SerializedName("list")
    val list: List<PaintingResponse>
)