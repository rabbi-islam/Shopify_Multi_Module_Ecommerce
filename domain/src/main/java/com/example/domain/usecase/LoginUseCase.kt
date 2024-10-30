package com.example.domain.usecase

import com.example.domain.repository.UserRepository

class LoginUseCase(
    private val userRepository: UserRepository

) {
    suspend fun execute(email: String, password: String) = userRepository.login(email, password)

}