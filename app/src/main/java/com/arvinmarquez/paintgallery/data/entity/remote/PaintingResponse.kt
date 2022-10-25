package com.arvinmarquez.paintgallery.data.entity.remote

import com.arvinmarquez.paintgallery.data.entity.local.PaintingEntity
import com.arvinmarquez.paintgallery.domain.model.Painting
import com.google.gson.annotations.SerializedName

data class PaintingResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("title")
    val title: String,

    @SerializedName("artist")
    val artist: String,

    @SerializedName("year")
    val year: String,

    @SerializedName("details")
    val details: String? = null,

    @SerializedName("image")
    val image: String
)

fun PaintingResponse.toDomain(): Painting {
    return Painting(
        id = id,
        title = title,
        artist = artist,
        year = year,
        details = details,
        image = image
    )
}

fun PaintingResponse.toEntity(): PaintingEntity {
    return PaintingEntity(
        id = id,
        title = title,
        artist = artist,
        year = year,
        details = details,
        image = image
    )
}