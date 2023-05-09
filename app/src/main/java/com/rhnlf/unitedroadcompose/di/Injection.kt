package com.rhnlf.unitedroadcompose.di

import com.rhnlf.unitedroadcompose.data.PlayerRepository

object Injection {
    fun provideRepository(): PlayerRepository {
        return PlayerRepository.getInstance()
    }
}