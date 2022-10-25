package com.arvinmarquez.paintgallery.core.di

import com.arvinmarquez.paintgallery.data.data_source.remote.PaintGalleryApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        const val BASE_URL = "https://rvnmrqz.io/projects/paint_gallery/"
    }

    @Provides
    @Singleton
    fun providesRetrofitBuilder(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun providesPaintGalleryApi(retrofit: Retrofit): PaintGalleryApi =
        retrofit.create(PaintGalleryApi::class.java)
}