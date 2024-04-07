package com.example.woofcareapp.navigation.repository

import com.example.woofcareapp.api.models.User


object DataRepository {
    private var user: User? = null
    private var users: List<User>? = null
    private var userPlus: User? = null

    fun setUserPlus(newUser: User){
        userPlus = newUser
    }

    fun getUserPlus():User?{
        return userPlus
    }

    fun setUsers(newEmployees: List<User>) {
        users= newEmployees
    }

    fun getUsers(): List<User>?{
        return users
    }

    fun setUser(newUser: User) {
        user = newUser
    }

    fun getUser(): User? {
        return user
    }
}
