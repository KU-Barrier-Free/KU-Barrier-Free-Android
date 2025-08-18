package com.ganaljigi.kubf.ui.helper.di

import com.ganaljigi.kubf.data.remote.service.HelperService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HelperApiModule {
    @Provides
    @Singleton
    fun provideHelperService(retrofit: Retrofit): HelperService = retrofit.create()
}