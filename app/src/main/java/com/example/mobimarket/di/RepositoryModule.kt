package com.example.mobimarket.di

import com.example.mobimarket.api.Api
import com.example.mobimarket.repo.AuthRepository
import com.example.mobimarket.repo.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun getAuthRepository(api: Api) = AuthRepository(api)

    @Provides
    @Singleton
    fun getProductRepository(api: Api) = ProductRepository(api)
}