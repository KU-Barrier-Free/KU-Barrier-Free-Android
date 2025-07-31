package com.ganaljigi.kubf.data.di

import com.ganaljigi.kubf.data.remote.service.KUBFService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideKUBFService(retrofit: Retrofit): KUBFService = retrofit.create()
}