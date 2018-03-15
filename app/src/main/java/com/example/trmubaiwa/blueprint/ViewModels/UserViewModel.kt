package com.example.trmubaiwa.blueprint.ViewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.trmubaiwa.blueprint.Models.UserModel
import com.example.trmubaiwa.blueprint.Repositories.UserRepository
import com.example.trmubaiwa.blueprint.Rooms.UserEntity


class UserViewModel constructor(private val userRepository: UserRepository) : ViewModel() {

    fun getUsers(): LiveData<List<UserModel>> = userRepository.getUsers()

    fun setUsers(usersList: List<UserEntity>?) = userRepository.insertUsers(usersList)
}