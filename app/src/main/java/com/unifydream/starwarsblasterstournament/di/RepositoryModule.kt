package com.unifydream.starwarsblasterstournament.di

import com.unifydream.starwarsblasterstournament.data.api.TournamentApi
import com.unifydream.starwarsblasterstournament.data.repository.TournamentRepository
import com.unifydream.starwarsblasterstournament.data.repository.TournamentRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTournamentRepository(apiService: TournamentApi): TournamentRepository {
        return TournamentRepositoryImpl(apiService)
    }
}
