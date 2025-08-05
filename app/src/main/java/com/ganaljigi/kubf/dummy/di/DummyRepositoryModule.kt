package com.ganaljigi.kubf.dummy.di

import com.ganaljigi.kubf.dummy.data.repository.DummyRepository
import com.ganaljigi.kubf.dummy.data.repositoryimpl.DummyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DummyRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindDummyRepository(dummyRepositoryImpl: DummyRepositoryImpl): DummyRepository

}