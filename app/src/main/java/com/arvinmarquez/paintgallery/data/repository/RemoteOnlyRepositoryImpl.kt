package com.arvinmarquez.paintgallery.data.repository

import com.arvinmarquez.paintgallery.core.utils.Resource
import com.arvinmarquez.paintgallery.data.data_source.remote.PaintGalleryApi
import com.arvinmarquez.paintgallery.data.entity.remote.toDomain
import com.arvinmarquez.paintgallery.domain.model.Painting
import com.arvinmarquez.paintgallery.domain.repository.PaintingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteOnlyRepositoryImpl @Inject constructor(
    private val api: PaintGalleryApi
) : PaintingRepository {

    override suspend fun getList(page: Int): Flow<Resource<List<Painting>>> {
        return flow {
            emit(Resource.loading())

            try {
                val response = api.list(page)

                if (response.isSuccessful) {
                    val list = response.body()?.list?.map { it.toDomain() } ?: emptyList()
                    emit(Resource.success(list))
                } else {
                    val errorMsg = response.errorBody()?.string()
                    response.errorBody()?.close()
                    emit(Resource.error(errorMsg ?: "Connection error"))
                }
            } catch (e: Exception) {
                emit(Resource.error(e.localizedMessage ?: "Unexpected error occurred"))
            }
        }
    }

    override suspend fun getPainting(id: Long): Flow<Resource<Painting>> {
        return flow {
            emit(Resource.loading())

            val response = api.getPainting(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.success(it.toDomain()))
                } ?: emit(Resource.error("Painting not found"))
            } else {
                emit(Resource.error(response.message()))
            }
        }
    }
}