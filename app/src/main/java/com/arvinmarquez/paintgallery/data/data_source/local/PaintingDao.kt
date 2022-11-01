package com.arvinmarquez.paintgallery.data.data_source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.arvinmarquez.paintgallery.data.entity.local.PaintingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PaintingDao {

    @Query("SELECT * FROM paintings")
    fun getLivePaintings(): Flow<List<PaintingEntity>>

    @Query("SELECT * FROM paintings")
    suspend fun getPaintings(): List<PaintingEntity>

    @Query("SELECT * FROM paintings LIMIT :limit OFFSET :offset")
    suspend fun getPaintings(limit: Int, offset: Int? = 0): List<PaintingEntity>

    @Query("SELECT * FROM paintings WHERE id = :id")
    fun getLivePainting(id: Long): Flow<PaintingEntity?>

    @Query("SELECT * FROM paintings WHERE id = :id")
    suspend fun getPainting(id: Long): PaintingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PaintingEntity): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(entity: PaintingEntity): Int

    @Query("DELETE FROM paintings")
    suspend fun deleteAll()

}