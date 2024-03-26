package com.example.googlemappoltava

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideMyViewModel(): MyViewModel {
        return MyViewModel()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideApiInterface(): ApiInterface {
        return Client.client.create(ApiInterface::class.java)
    }
}