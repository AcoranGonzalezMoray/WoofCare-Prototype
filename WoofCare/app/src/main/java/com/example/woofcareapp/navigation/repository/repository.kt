package com.example.woofcareapp.navigation.repository

import com.example.woofcareapp.api.models.User


object DataRepository {
    private var user: User? = null
    private var employees: List<User>? = null
    private var token: String = ""
    private var userPlus: User? = null

    fun setUserPlus(newUser: User){
        userPlus = newUser
    }

    fun getUserPlus():User?{
        return userPlus
    }

    fun setEmployees(newEmployees: List<User>) {
        employees= newEmployees
    }

    fun getEmployees(): List<User>?{
        return employees
    }

    fun setToken(newToken: String) {
        token = newToken
    }

    fun getToken(): String {
        return token
    }

    fun setUser(newUser: User) {
        user = newUser
    }

    fun getUser(): User? {
        return user
    }
}
