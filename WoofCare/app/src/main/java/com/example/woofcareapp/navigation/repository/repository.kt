package com.example.woofcareapp.navigation.repository

import com.example.woofcareapp.api.models.Message
import com.example.woofcareapp.api.models.Product
import com.example.woofcareapp.api.models.Service
import com.example.woofcareapp.api.models.User


object DataRepository {
    private var user: User? = null
    private var users: List<User>? = null
    private var services: List<Service>? = null
    private var products: List<Product>? = null

    private var userPlus: User? = null
    private var productPlus: Product? = null
    private var servicePlus: Service? = null
    private var messagesPlus: List<Message>? = null
    private var UID: Int = 0
    fun setUID(id: Int){
        UID = id
    }

    fun getUID():Int{
        return UID
    }

    fun setMessagePlus(messages: List<Message>){
        messagesPlus = messages
    }

    fun getMessagePlus():List<Message>?{
        return messagesPlus
    }


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

    fun setServices(newServices: List<Service>) {
        services= newServices
    }

    fun getServices(): List<Service>?{
        return services
    }

    fun setProducts(newProducts: List<Product>) {
        products= newProducts
    }

    fun getProducts(): List<Product>?{
        return products
    }

    fun setUser(newUser: User) {
        user = newUser
    }

    fun getUser(): User? {
        return user
    }
}
