package com.example.woofcareapp.navigation.repository

import com.example.woofcareapp.api.models.Product
import com.example.woofcareapp.api.models.Service
import com.example.woofcareapp.api.models.User


object DataRepository {
    private var user: User? = null
    private var users: List<User>? = null
    private var userPlus: User? = null
    private var productPlus: Product? = null
    private var servicePlus: Service? = null

    fun setServicePlus(newService: Service){
        servicePlus = newService
    }

    fun getServicePlus():Service?{
        return servicePlus
    }


    fun setProductPlus(newProduct: Product){
        productPlus = newProduct
    }

    fun getProductPlus():Product?{
        return productPlus
    }

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
