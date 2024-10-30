package com.example.domain.repository

import com.example.domain.model.UserDomainModel
import com.example.domain.network.ResultWrapper

interface UserRepository {
    suspend fun register(name: String, email: String, password: String):ResultWrapper<UserDomainModel>
    suspend fun login(email: String, password: String):ResultWrapper<UserDomainModel>
}