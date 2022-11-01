package com.arvinmarquez.paintgallery.data.repository

import com.arvinmarquez.paintgallery.core.utils.Constants
import com.arvinmarquez.paintgallery.core.utils.Resource
import com.arvinmarquez.paintgallery.core.utils.networkBoundResource
import com.arvinmarquez.paintgallery.data.data_source.local.PaintingDao
import com.arvinmarquez.paintgallery.data.data_source.remote.PaintGalleryApi
import com.arvinmarquez.paintgallery.data.entity.local.toDomain
import com.arvinmarquez.paintgallery.data.entity.remote.toEntity
import com.arvinmarquez.paintgallery.domain.model.Painting
import com.arvinmarquez.paintgallery.domain.repository.PaintingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkBoundRepositoryImpl @Inject constructor(
    private val api: PaintGalleryApi,
    private val dao: PaintingDao
) : PaintingRepository {

    override suspend fun getList(page: Int): Flow<Resource<List<Painting>>> {
        return networkBoundResource(
            query = {
                flow {
                    val limit = Constants.ITEMS_PER_PAGE_LIMIT
                    val offset = limit * (page - 1)
                    emit(dao.getPaintings(limit = limit, offset = offset).map { it.toDomain() })
                }
            },
            fetch = {
                api.list(page)
            },
            saveFetchResult = { response ->
                response.body()?.let {
                    it.list.forEach { painting ->
                        dao.insert(painting.toEntity())
                    }
                }
            }
        )
    }

    override suspend fun getPainting(id: Long): Flow<Resource<Painting>> {
        return networkBoundResource(
            query = {
                flow {
                    dao.getPainting(id)?.let { localEntity ->
                        emit(localEntity.toDomain())
                    }
                }
            },
            fetch = {
                api.getPainting(id)
            },
            saveFetchResult = { response ->
                response.body()?.let { painting ->
                    dao.insert(painting.toEntity())
                }
            }
        )
    }
}