package com.arvinmarquez.paintgallery.core.di


import com.arvinmarquez.paintgallery.data.data_source.remote.PaintGalleryApi
import com.arvinmarquez.paintgallery.domain.repository.PaintingRepository
import com.arvinmarquez.paintgallery.data.repository.PaintingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun providesPaintingsRepository(api: PaintGalleryApi): PaintingRepository =
        PaintingRepositoryImpl(api)
}