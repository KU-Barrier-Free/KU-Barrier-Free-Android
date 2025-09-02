package com.ganaljigi.kubf.data.di

import com.ganaljigi.kubf.data.remote.repository.BuildingRepository
import com.ganaljigi.kubf.data.remote.repository.HomeRepository
import com.ganaljigi.kubf.data.remote.repository.RouteRepository
import com.ganaljigi.kubf.data.remote.repositoryimpl.BuildingRepositoryImpl
import com.ganaljigi.kubf.data.remote.repositoryimpl.HomeRepositoryImpl
import com.ganaljigi.kubf.data.remote.repositoryimpl.RouteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    @Singleton
    abstract fun bindBuildingRepository(buildingRepositoryImpl: BuildingRepositoryImpl): BuildingRepository
    
    @Binds
    @Singleton
    abstract fun bindRouteRepository(routeRepositoryImpl: RouteRepositoryImpl): RouteRepository
}