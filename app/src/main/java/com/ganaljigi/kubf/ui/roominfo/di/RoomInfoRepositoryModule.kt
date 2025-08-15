package com.ganaljigi.kubf.ui.roominfo.di

import com.ganaljigi.kubf.ui.roominfo.repository.RoomInfoRepository
import com.ganaljigi.kubf.ui.roominfo.repositoryimpl.RoomInfoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RoomInfoRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRoomInfoRepository(
        impl: RoomInfoRepositoryImpl
    ): RoomInfoRepository
}