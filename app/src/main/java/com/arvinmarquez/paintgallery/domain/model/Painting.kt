package com.arvinmarquez.paintgallery.domain.model


import com.arvinmarquez.paintgallery.data.entity.local.PaintingEntity

data class Painting(
    val id: Long,
    val title: String,
    val artist: String,
    val year: String,
    val details: String?,
    val image: String
)

fun Painting.toEntity(): PaintingEntity {
    return PaintingEntity(
        id = id,
        title = title,
        artist = artist,
        year = year,
        details = details,
        image = image
    )
}
