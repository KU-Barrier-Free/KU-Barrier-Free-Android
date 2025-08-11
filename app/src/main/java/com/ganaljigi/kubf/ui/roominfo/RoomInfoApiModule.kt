package com.ganaljigi.kubf.ui.roominfo

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomInfoApiModule {
    @Provides
    @Singleton
    fun provideRoomInfoService(retrofit: Retrofit): RoomInfoService = retrofit.create()
}