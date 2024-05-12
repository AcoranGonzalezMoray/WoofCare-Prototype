package com.example.woofcareapp.navigation.repository

import androidx.compose.runtime.remember
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.woofcareapp.api.models.Message
import com.example.woofcareapp.api.models.Product
import com.example.woofcareapp.api.models.Service
import com.example.woofcareapp.api.models.User


object DataRepository {
    private var user: User? = null
    private var userPlus: User? = null
    private var productPlus: Product? = null
    private var servicePlus: Service? = null
    private var sizeOfText: TextUnit = 18.sp
    private var messagesPlus: List<Message>? = null
    private var UID: Int = 0

    fun setSizeOfText(size: TextUnit){
        sizeOfText = size
    }

    fun getSizeOfText(): TextUnit {
        return sizeOfText
    }

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


    private var users: List<User>? = listOf(
        User(
            id = 146,
            name = "John Doe",
            email = "john.doe@example.com",
            password = "password123",
            accountType = 0,
            suscriptionType = 1,
            location = "New York",
            profileUrl = "https://www.lavanguardia.com/files/image_480_496/files/fp/uploads/2021/09/30/6154f27965814.r_d.528-377.jpeg",
            phone = 1234567890,
            age = 18,
            statusAccount = 1
        ),
        User(
            id = 2456,
            name = "Jane Smith",
            email = "jane.smith@example.com",
            password = "password456",
            accountType = 1,
            suscriptionType = 2,
            location = "Los Angeles",
            profileUrl = "https://s1.elespanol.com/2023/06/08/vivir/salud-mental/769933690_233804290_1706x960.jpg",
            phone = 9876543210,
            age = 18,
            statusAccount = 1
        ),
        User(
            id = 3456,
            name = "Alice Johnson",
            email = "alice.johnson@example.com",
            password = "password789",
            accountType = 2,
            suscriptionType = 0,
            location = "Chicago",
            profileUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIeOvKydWQ3J5PJv3jQV4gdQzmqtjFi1FDZ4Zjxh5yAA&s",
            phone = 5555555555,
            age = 18,
            statusAccount = 1
        ),
        User(
            id = 4456,
            name = "Eve Johnson",
            email = "eve.johnson@example.com",
            password = "password789",
            accountType = 2,
            suscriptionType = 0,
            location = "Chicago",
            profileUrl = "https://pymstatic.com/5844/conversions/personas-emocionales-wide_webp.webp",
            phone = 5555555555,
            age = 18,
            statusAccount = 1
        ),
        User(
            id = 5456,
            name = "Mary Johnson",
            email = "mary.johnson@example.com",
            password = "password789",
            accountType = 2,
            suscriptionType = 0,
            location = "Chicago",
            profileUrl = "https://secrecyjewels.es/blog/wp-content/uploads/2022/10/esencia-de-una-persona.jpg",
            phone = 5555555555,
            age = 18,
            statusAccount = 1
        ),
        User(
            id = 6456,
            name = "Adam Johnson",
            email = "adam.johnson@example.com",
            password = "password789",
            accountType = 2,
            suscriptionType = 0,
            location = "Chicago",
            profileUrl = "https://images.ecestaticos.com/vU8sC8tLdkx-2YYh1fkOGL8vfeY=/0x0:990x557/1200x900/filters:fill(white):format(jpg)/f.elconfidencial.com%2Foriginal%2F62c%2Fe5d%2F314%2F62ce5d3141c0b670144a692b7f6a21fa.jpg",
            phone = 5555555555,
            age = 18,
            statusAccount = 1
        )
    )
    private var services: List<Service>? =  listOf(
        Service(
            id = 1456,
            name = "Paseos Diarios",
            type = 0,
            status = 0,
            publicationDate = "2024-04-21",
            description = "Paseos diarios para perros de todas las edades y razas. Incluye ejercicio moderado y socialización.",
            price = 25.0,
            uid = 3456,
            bannerUrl =
            "https://imagenes.20minutos.es/files/image_1920_1080/uploads/imagenes/2020/04/02/imagen-de-una-perro-de-paseo.jpeg"
                    +";"+"https://entrenosotros.consum.es/public/Image/2020/12/paseo-perros.jpg"+";"

        ),
        Service(
            id = 2456,
            name = "Entrenamiento Básico",
            type = 0,
            status = 0,
            publicationDate = "2024-04-20",
            description = "Entrenamiento básico para cachorros y perros adultos. Enseñanza de órdenes básicas y comportamiento adecuado.",
            price = 50.0,
            uid = 2456,
            bannerUrl =
            "https://entrenosotros.consum.es/public/Image/2020/12/paseo-perros.jpg"
                    +";"+"https://imagenes.20minutos.es/files/image_1920_1080/uploads/imagenes/2020/04/02/imagen-de-una-perro-de-paseo.jpeg"+";"

        ),
        Service(
            id = 3456,
            name = "Cuidado de Día",
            type = 0,
            status = 0,
            publicationDate = "2024-04-19",
            description = "Cuidado diurno para perros mientras los propietarios están fuera. Incluye tiempo de juego y supervisión.",
            price = 35.0,
            uid = 146,
            bannerUrl = "https://fotografias.lasexta.com/clipping/cmsimages01/2017/02/07/364CAAAC-A60E-43BB-8FED-05AA0B8F3AF9/98.jpg?crop=1200,675,x0,y0&width=1900&height=1069&optimize=low&format=webply"
                    +";"+"https://entrenosotros.consum.es/public/Image/2020/12/paseo-perros.jpg"+";"
        ),
        Service(
            id = 4456,
            name = "Adiestramiento Avanzado",
            type = 0,
            status = 0,
            publicationDate = "2024-04-18",
            description = "Adiestramiento avanzado para perros con necesidades especiales. Enseñanza de habilidades avanzadas y obediencia.",
            price = 70.0,
            uid = 3456,
            bannerUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRh_kYZsNXRIfCbKxZ1Fi2fxgSVw6d5-2QMvw&usqp=CAU"
                    +";"+"https://imagenes.20minutos.es/files/image_1920_1080/uploads/imagenes/2020/04/02/imagen-de-una-perro-de-paseo.jpeg"+";"
        ),
        Service(
            id = 5456,
            name = "Guardería Nocturna",
            type = 0,
            status = 0,
            publicationDate = "2024-04-17",
            description = "Guardería nocturna para perros que necesitan alojamiento durante la noche. Ambiente seguro y cómodo para descansar.",
            price = 40.0,
            uid = 146,
            bannerUrl = "https://fordogtrainers.es/images/stories/Arneses/h1/arnes-cuero-perros-de-servicios-h1.jpg"
                    +";"+"https://entrenosotros.consum.es/public/Image/2020/12/paseo-perros.jpg"+";"
        ),
        Service(
            id = 6456,
            name = "Terapia Canina",
            type = 0,
            status = 0,
            publicationDate = "2024-04-16",
            description = "Terapia emocional para perros que sufren de ansiedad o estrés. Sesiones individuales y grupales disponibles.",
            price = 60.0,
            uid = 2456,
            bannerUrl =  "https://www.auservigroup.com/wp-content/uploads/2024/04/Imagen-articulo-25-400x250.jpg"
                    +";"+"https://imagenes.20minutos.es/files/image_1920_1080/uploads/imagenes/2020/04/02/imagen-de-una-perro-de-paseo.jpeg"+";"
        )
    )
    private var products: List<Product>? =  listOf(
        Product(
            id = 1456,
            name = "Dog Harness - Small",
            description = "Adjustable harness for small breed dogs",
            price = 29.99,
            location = "Pet Supplies",
            companyName = "Petco",
            status = 1,
            reviewId = 1,
            webUrl = "https://www.carrefour.es/edipets-arnes-para-perro-antitirones-ajustable-y-personalizable/8436599036953/p",
            bannerUrls =
            "https://d30r1d8hifg350.cloudfront.net/wp-content/uploads/2021/08/8564-yiou05-1.webp" + ";" +
                    "https://storage.googleapis.com/catalog-pictures-carrefour-es/catalog/pictures/hd_510x_/8436599036953_1.jpg"
        ),
        Product(
            id = 2456,
            name = "Dog Food Bowl Set",
            description = "Set of stainless steel bowls for food and water",
            price = 19.99,
            location = "Pet Supplies",
            companyName = "AmazonBasics",
            status = 1,
            reviewId = 1,
            webUrl = "https://www.amazon.com/Pecute-Stainless-Non-Spill-Non-Skid-Silicone/dp/B072MGFCL7",
            bannerUrls =
            "https://m.media-amazon.com/images/I/61MdGHbyjvS.jpg" + ";" +
                    "https://m.media-amazon.com/images/I/617OzEz+TtL._AC_UF1000,1000_QL80_.jpg"
        ),
        Product(
            id = 3456,
            name = "Dog Chew Toys - Variety Pack",
            description = "Assorted chew toys for dogs of all sizes",
            price = 24.99,
            location = "Pet Supplies",
            companyName = "KONG",
            status = 1,
            reviewId = 1,
            webUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQrF5haLsLQaHiXBxqsFQsn6siu7JU6_Mu6-2wPv4qiBw&s",
            bannerUrls =
            "https://m.media-amazon.com/images/I/913CvabhHRL._AC_UF1000,1000_QL80_.jpg" + ";" +
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQrF5haLsLQaHiXBxqsFQsn6siu7JU6_Mu6-2wPv4qiBw&s"
        ),
        Product(
            id = 446,
            name = "Dog Bed - Large",
            description = "Comfortable bed for large breed dogs",
            price = 59.99,
            location = "Pet Supplies",
            companyName = "PetSmart",
            status = 1,
            reviewId = 1,
            webUrl = "https://ansaleeov.xyz/product_details/55323921.html",
            bannerUrls =
            "https://www.onmolecule.com/cdn/shop/products/Molecule_PetBed_Showroom3_Large1.jpg?v=1666390614" + ";" +
                    "https://m.media-amazon.com/images/I/81ZQfvR0mLL._AC_UF1000,1000_QL80_.jpg"
        ),
        Product(
            id = 546,
            name = "Dog Leash - Reflective",
            description = "Durable leash with reflective stitching for added safety",
            price = 15.99,
            location = "Pet Supplies",
            companyName = "Rabbitgoo",
            status = 1,
            reviewId = 1,
            webUrl = "https://www.amazon.com/Pawtitas-Padded-Reflective-Handle-Training/dp/B0164ZG7XS",
            bannerUrls =
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS-tSVH8YvqLMOwzmZiRXrLjuuSFZdk5PMx8V3U_9m4tQ&s" + ";" +
                    "https://m.media-amazon.com/images/I/71spgkeEeEL._AC_UF1000,1000_QL80_.jpg"
        )
    )
}
