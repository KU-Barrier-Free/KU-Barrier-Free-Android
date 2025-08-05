package com.ganaljigi.kubf.dummy.di

import com.ganaljigi.kubf.dummy.data.service.DummyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DummyApiModule {
    @Provides
    @Singleton
    fun provideDummyService(retrofit: Retrofit): DummyService {
        return retrofit.create()
    }
}