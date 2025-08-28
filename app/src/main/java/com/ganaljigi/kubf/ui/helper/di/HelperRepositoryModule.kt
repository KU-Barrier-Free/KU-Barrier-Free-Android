package com.ganaljigi.kubf.ui.helper.di

import com.ganaljigi.kubf.ui.helper.repository.HelperRepository
import com.ganaljigi.kubf.ui.helper.repositoryimpl.HelperRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HelperRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindHelperRepository(
        impl: HelperRepositoryImpl
    ): HelperRepository
}