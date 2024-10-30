package com.example.data.repository

import com.example.domain.model.UserDomainModel
import com.example.domain.network.NetworkService
import com.example.domain.network.ResultWrapper
import com.example.domain.repository.UserRepository

class UserRepositoryImpl(
    private val networkService: NetworkService
):UserRepository {
    override suspend fun register(
        name: String,
        email: String,
        password: String,
    ): ResultWrapper<UserDomainModel> {
        return networkService.register(name, email, password)
    }

    override suspend fun login(email: String, password: String): ResultWrapper<UserDomainModel> {
        return networkService.login(email, password)
    }
}