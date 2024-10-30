package com.example.domain.usecase

import com.example.domain.repository.UserRepository

class RegisterUseCase(
    private val userRepository: UserRepository

) {
    suspend fun execute(name: String, email: String, password: String) = userRepository.register(name, email, password)

}