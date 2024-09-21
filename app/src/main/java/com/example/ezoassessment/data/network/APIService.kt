package com.example.ezoassessment.data.network

import com.example.ezoassessment.data.Constants
import com.example.ezoassessment.data.models.UserListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET(Constants.userEndPoint)
    suspend fun getUsers(): UserListResponse
}