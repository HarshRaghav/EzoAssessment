package com.example.ezoassessment.data

import com.example.ezoassessment.data.models.UserListResponse
import com.example.ezoassessment.data.network.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface Repository {
    suspend fun getUsers(
    ):Result<UserListResponse>
}

class UserRepository @Inject constructor(private val service: APIService): Repository{
    override suspend fun getUsers(): Result<UserListResponse> = withContext(Dispatchers.IO){
        try{
            val result = service.getUsers()
            Result.Success(result)
        }catch (exception:Exception){
            Result.Error(exception)
        }
    }
}