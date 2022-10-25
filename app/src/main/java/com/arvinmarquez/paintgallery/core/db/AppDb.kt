package com.arvinmarquez.paintgallery.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arvinmarquez.paintgallery.data.data_source.local.PaintingDao
import com.arvinmarquez.paintgallery.data.entity.local.PaintingEntity

@Database(
    entities = [PaintingEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDb : RoomDatabase(){

    companion object{
        const val DB_NAME = "paint_gallery.db"
    }

    abstract fun paintingDao(): PaintingDao
}