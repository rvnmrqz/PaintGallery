package com.arvinmarquez.paintgallery.data.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arvinmarquez.paintgallery.domain.model.Painting

@Entity(tableName = "paintings")
data class PaintingEntity(

    @PrimaryKey(autoGenerate = false)
    val id: Long,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "artist")
    val artist: String,

    @ColumnInfo(name = "year")
    val year: String,

    @ColumnInfo(name = "details", defaultValue = "")
    val details: String?,

    @ColumnInfo(name = "image")
    val image: String
)

fun PaintingEntity.toDomain(): Painting {
    return Painting(
        id = id,
        title = title,
        artist = artist,
        year = year,
        details = details,
        image = image
    )
}
