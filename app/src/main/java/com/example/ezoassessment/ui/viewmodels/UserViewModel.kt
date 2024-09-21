package com.example.ezoassessment.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ezoassessment.data.Result
import com.example.ezoassessment.data.Repository
import com.example.ezoassessment.data.models.User
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    var users: LiveData<List<User>> = _users

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg:LiveData<String> = _errorMsg

    fun getUsers(){
        viewModelScope.launch {
            val result = repository.getUsers()
            when(result) {
                is Result.Success -> _users.value = result.data.data
                is Result.Error -> _errorMsg.value = result.exception.message
            }
        }
    }

}