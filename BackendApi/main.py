import hashlib

from context.sqlServer.DBHandler import *
from entities.Message import Message
from entities.Dog import Dog
from entities.Advertisement import Advertisement
from entities.Product import Product
from entities.Request import Request
from entities.Service import Service
from entities.Review import Review
from model import  modelDoc
from flask import Flask, request, jsonify
from flask_restx import Api, Resource, fields
# Importa tu lógica de negocio
from context.sqlServer.userT import *


#=======================CONFIG=======================================
app = Flask(__name__)
version = "v1"
api = Api(app, version='1.0', title='WoofCare API', description='API para la gestión de usuarios en WoofCare.')

#=======================MODEL=======================================
user_model = api.model('User', modelDoc.Model.getUserModel())
product_model = api.model('Product', modelDoc.Model.getProductModel())
request_model = api.model('Request', modelDoc.Model.getRequestModel())
service_model = api.model('Service', modelDoc.Model.getServiceModel())
dog_model = api.model('Dog', modelDoc.Model.getDogModel())
advertisement_model = api.model('Advertisement', modelDoc.Model.getAdvertisementModel())
message_model = api.model('Message', modelDoc.Model.getMessageModel())
review_model = api.model('Review', modelDoc.Model.getReviewModel())


# =====================USER CONTROLLER==============================

@api.route(f"/api/{version}/user")
class UserList(Resource):
    @api.doc('get_users')
    def get(self):
        """
        Retorna todos los usuarios registrados en el sistema.
        """
        users = get_entity_data("Users")
        serialized_users = []
        if(users):serialized_users = [user.to_dict() for user in users]

        return jsonify(serialized_users)

    @api.doc('create_user')
    @api.expect(user_model)
    def post(self):
        """
        Crea un nuevo usuario.
        """
        data = request.json
        new_user = User(
            id=0,  # El ID se generará automáticamente en la base de datos
            name=data["name"],
            email=data["email"],
            password=data["password"],
            accountType=data["accountType"],
            suscriptionType=data["suscriptionType"],
            location=data["location"],
            profileUrl=data["profileUrl"],
            phone=data["phone"],
            statusAccount=data["statusAccount"]        
            )
        if save_entity_to_database(new_user):
            return jsonify({"message": "Usuario creado correctamente"}), 201
        else:
            return jsonify({"message": "Error al crear el usuario"}), 500

@api.route(f"/api/{version}/user/<int:id>")
class UserSpecificUser(Resource):
    @api.doc('get_user')
    def get(self, id):
        """
        Retorna un usuario por su ID.
        """
        users = get_entity_data("Users")
        user = next((user for user in users if user.id == id), None)

        if user:
            serialized_user = user.to_dict()
            return jsonify(serialized_user)
        else:
            return {"message": "Usuario no encontrado"}, 404

    @api.doc('update_user')
    @api.expect(user_model)
    def put(self, id):
        """
        Actualiza un usuario existente.
        """
        data = request.json
        users = get_entity_data("Users")
        user: User = next((user for user in users if user.id == id), None)
        if user:
            user.name = data["name"]
            user.email = data["email"]
            user.password = data["password"]
            user.accountType = data["accountType"]
            user.suscriptionType = data["suscriptionType"]
            user.location = data["location"]
            user.profileUrl = data["profileUrl"]
            user.phone = data["phone"]
            user.statusAccount=data["statusAccount"]    
            if update_entity_in_database(user):
                return {"message": "Usuario actualizado correctamente"}, 200
            else:
                return {"message": "Error al actualizar el usuario"}, 500
        else:
            return {"message": "Usuario no encontrado"}, 404

    @api.doc('delete_user')
    def delete(self, id):
        """
        Elimina un usuario por su ID.
        """
        if delete_entity_from_database(id, "Users"):
            return {"message": "Usuario eliminado correctamente"}, 200
        else:
            return {"message": "Error al eliminar el usuario"}, 500


# =====================SIGNUP AND SIGNIN CONTROLLERS==============================
@api.route(f"/api/{version}/signup")
class SignUp(Resource):
    @api.doc('signup')
    @api.expect(user_model)
    def post(self):
        """
        Registra un nuevo usuario en el sistema.
        """
        data = request.json
        email = data.get("email")
        
        password = data.get("password")

        if email_exists(email):
            return {"message": "El correo electrónico ya está registrado"}, 400
        if password is None:
            return {"message": "La contraseña no puede estar vacía"}, 400
        
        hashed_password = hashlib.sha256(password.encode()).hexdigest()

        # Crear un nuevo usuario
        new_user = User(
            id=0,
            name=data.get("name"),
            email=email,
            password=hashed_password,
            accountType=data.get("accountType"),
            suscriptionType=data.get("suscriptionType"),
            location=data.get("location"),
            profileUrl=data.get("profileUrl"),
            phone=data.get("phone"),
            statusAccount = data.get("statusAccount")
        )

        if save_user_to_database(new_user):
            return {"message": "Usuario creado correctamente"}, 201
        else:
            return {"message": "Error al crear el usuario"}, 500

@api.route(f"/api/{version}/signin")
class SignIn(Resource):
    @api.doc('signin')
    def post(self):
        """
        Inicia sesión en el sistema.
        """
        data = request.json
        email = data.get("email")
        password = data.get("password")
        # Obtener el usuario por correo electrónico
        user = get_user_by_email(email)
        if user:
            # Verificar la contraseña
            hashed_password = hashlib.sha256(password.encode()).hexdigest()
            if hashed_password == user.password:
                return jsonify(user.to_dict())
            else:
                return {"message": "Contraseña incorrecta"}, 401
        else:
            return {"message": "Usuario no encontrado"}, 404

# =====================PRODUCT CONTROLLER==============================
@api.route(f"/api/{version}/product")
class ProductList(Resource):
    @api.doc('get_products')
    def get(self):
        """
        Retorna todos los productos registrados en el sistema.
        """
        products = get_entity_data("Products")
        serialized_products = []
        if(products):serialized_products = [product.to_dict() for product in products]

        return jsonify(serialized_products)

    @api.doc('create_product')
    @api.expect(product_model)
    def post(self):
        """
        Crea un nuevo producto.
        """
        data = request.json
        new_product = Product(
            id=0,  # El ID se generará automáticamente en la base de datos
            name=data["name"],
            description=data["description"],
            price=data["price"],
            location=data["location"],
            companyName=data["companyName"],
            status=data["status"],
            bannerUrls=data["bannerUrls"]        
            )
        if save_entity_to_database(new_product):
            return {"message": "Producto creado correctamente"}, 201
        else:
            return {"message": "Error al crear el producto"}, 500

@api.route(f"/api/{version}/product/<int:id>")
class SpecificProduct(Resource):
    @api.doc('get_product')
    def get(self, id):
        """
        Retorna un producto por su ID.
        """
        products = get_entity_data("Products")
        product = next((product for product in products if product.id == id), None)

        if product:
            serialized_product = product.to_dict()
            return jsonify(serialized_product)
        else:
            return {"message": "Producto no encontrado"}, 404

    @api.doc('update_product')
    @api.expect(product_model)
    def put(self, id):
        """
        Actualiza un producto existente.
        """
        data = request.json
        products = get_entity_data("Products")
        product = next((product for product in products if product.id == id), None)
        if product:
            product.name = data["name"]
            product.description = data["description"]
            product.price = data["price"]
            product.location = data["location"]
            product.companyName = data["companyName"]
            product.status = data["status"]
            product.bannerUrls = data["bannerUrls"]
            if update_entity_in_database(product):
                return {"message": "Producto actualizado correctamente"}, 200
            else:
                return {"message": "Error al actualizar el producto"}, 500
        else:
            return {"message": "Producto no encontrado"}, 404

    @api.doc('delete_product')
    def delete(self, id):
        """
        Elimina un producto por su ID.
        """
        if delete_entity_from_database(id, "Products"):
            return {"message": "Producto eliminado correctamente"}, 200
        else:
            return {"message": "Error al eliminar el producto"}, 500

# =====================REQUEST CONTROLLER==============================
@api.route(f"/api/{version}/request")
class RequestList(Resource):
    @api.doc('get_requests')
    def get(self):
        """
        Retorna todas las solicitudes registradas en el sistema.
        """
        requests = get_entity_data("Requests")
        serialized_requests = []
        if requests:
            serialized_requests = [request.to_dict() for request in requests]

        return jsonify(serialized_requests)

    @api.doc('create_request')
    @api.expect(request_model)
    def post(self):
        """
        Crea una nueva solicitud.
        """
        data = request.json
        new_request = Request(
            id=0,  # El ID se generará automáticamente en la base de datos
            uidReceiver=data["uidReceiver"],
            uidSender=data["uidSender"],
            serviceId=data["serviceId"],
            status=data["status"],
            creationDate=data["creationDate"]
        )
        if save_entity_to_database(new_request):
            return {"message": "Solicitud creada correctamente"}, 201
        else:
            return {"message": "Error al crear la solicitud"}, 500

@api.route(f"/api/{version}/request/<int:id>")
class SpecificRequest(Resource):
    @api.doc('get_request')
    def get(self, id):
        """
        Retorna una solicitud por su ID.
        """
        requests = get_entity_data("Requests")
        request = next((request for request in requests if request.id == id), None)

        if request:
            serialized_request = request.to_dict()
            return jsonify(serialized_request)
        else:
            return {"message": "Solicitud no encontrada"}, 404

    @api.doc('update_request')
    @api.expect(request_model)
    def put(self, id):
        """
        Actualiza una solicitud existente.
        """
        data = request.json
        requests = get_entity_data("Requests")
        requestN = next((request for request in requests if request.id == id), None)
        if requestN:
            requestN.uidReceiver = data["uidReceiver"]
            requestN.uidSender = data["uidSender"]
            requestN.serviceId = data["serviceId"]
            requestN.status = data["status"]
            requestN.creationDate = data["creationDate"]
            if update_entity_in_database(requestN):
                return {"message": "Solicitud actualizada correctamente"}, 200
            else:
                return {"message": "Error al actualizar la solicitud"}, 500
        else:
            return {"message": "Solicitud no encontrada"}, 404

    @api.doc('delete_request')
    def delete(self, id):
        """
        Elimina una solicitud por su ID.
        """
        if delete_entity_from_database(id, "Requests"):
            return {"message": "Solicitud eliminada correctamente"}, 200
        else:
            return {"message": "Error al eliminar la solicitud"}, 500
        
# =====================SERVICE CONTROLLER==============================
@api.route(f"/api/{version}/service")
class ServiceList(Resource):
    @api.doc('get_services')
    def get(self):
        """
        Retorna todos los servicios registrados en el sistema.
        """
        services = get_entity_data("Services")
        serialized_services = []
        if services:
            serialized_services = [service.to_dict() for service in services]

        return jsonify(serialized_services)

    @api.doc('create_service')
    @api.expect(service_model)
    def post(self):
        """
        Crea un nuevo servicio.
        """
        data = request.json
        new_service = Service(
            id=0,  # El ID se generará automáticamente en la base de datos
            name=data["name"],
            type=data["type"],
            status=data["status"],
            publicationDate=data["publicationDate"],
            description=data["description"],
            price=data["price"],
            uid=data["uid"],
            bannerUrl=data["bannerUrl"]
        )
        if save_entity_to_database(new_service):
            return {"message": "Servicio creado correctamente"}, 201
        else:
            return {"message": "Error al crear el servicio"}, 500

@api.route(f"/api/{version}/service/<int:id>")
class SpecificService(Resource):
    @api.doc('get_service')
    def get(self, id):
        """
        Retorna un servicio por su ID.
        """
        services = get_entity_data("Services")
        service = next((service for service in services if service.id == id), None)

        if service:
            serialized_service = service.to_dict()
            return jsonify(serialized_service)
        else:
            return {"message": "Servicio no encontrado"}, 404

    @api.doc('update_service')
    @api.expect(service_model)
    def put(self, id):
        """
        Actualiza un servicio existente.
        """
        data = request.json
        services = get_entity_data("Services")
        service = next((service for service in services if service.id == id), None)
        if service:
            service.name = data["name"]
            service.type = data["type"]
            service.status = data["status"]
            service.publicationDate = data["publicationDate"]
            service.description = data["description"]
            service.price = data["price"]
            service.uid = data["uid"]
            service.bannerUrl = data["bannerUrl"]
            if update_entity_in_database(service):
                return {"message": "Servicio actualizado correctamente"}, 200
            else:
                return {"message": "Error al actualizar el servicio"}, 500
        else:
            return {"message": "Servicio no encontrado"}, 404

    @api.doc('delete_service')
    def delete(self, id):
        """
        Elimina un servicio por su ID.
        """
        if delete_entity_from_database(id):
            return {"message": "Servicio eliminado correctamente"}, 200
        else:
            return {"message": "Error al eliminar el servicio"}, 500

#========================DOG CONTROLLER================================

@api.route(f"/api/{version}/dog")
class DogList(Resource):
    @api.doc('get_dogs')
    def get(self):
        """
        Retorna todos los perros registrados en el sistema.
        """
        dogs = get_entity_data("Dogs")
        serialized_dogs = []
        if dogs:
            serialized_dogs = [dog.to_dict() for dog in dogs]
        return jsonify(serialized_dogs)

    @api.doc('create_dog')
    @api.expect(dog_model)
    def post(self):
        """
        Crea un nuevo perro.
        """
        data = request.json
        new_dog = Dog(
            id=0,  # El ID se generará automáticamente en la base de datos
            breed=data["breed"],
            size=data["size"],
            weight=data["weight"],
            age=data["age"]
        )
        if save_entity_to_database(new_dog):
            return {"message": "Perro creado correctamente"}, 201
        else:
            return {"message": "Error al crear el perro"}, 500

@api.route(f"/api/{version}/dog/<int:id>")
class SpecificDog(Resource):
    @api.doc('get_dog')
    def get(self, id):
        """
        Retorna un perro por su ID.
        """
        dogs = get_entity_data("Dogs")
        dog = next((dog for dog in dogs if dog.id == id), None)

        if dog:
            serialized_dog = dog.to_dict()
            return jsonify(serialized_dog)
        else:
            return {"message": "Perro no encontrado"}, 404

    @api.doc('update_dog')
    @api.expect(dog_model)
    def put(self, id):
        """
        Actualiza un perro existente.
        """
        data = request.json
        dogs = get_entity_data("Dogs")
        dog = next((dog for dog in dogs if dog.id == id), None)
        if dog:
            dog.breed = data["breed"]
            dog.size = data["size"]
            dog.weight = data["weight"]
            dog.age = data["age"]
            if update_entity_in_database(dog):
                return {"message": "Perro actualizado correctamente"}, 200
            else:
                return {"message": "Error al actualizar el perro"}, 500
        else:
            return {"message": "Perro no encontrado"}, 404

    @api.doc('delete_dog')
    def delete(self, id):
        """
        Elimina un perro por su ID.
        """
        if delete_entity_from_database(id, "Dogs"):
            return {"message": "Perro eliminado correctamente"}, 200
        else:
            return {"message": "Error al eliminar el perro"}, 500


#========================================ADVERTISEMENT CONTROLLER===================================

@api.route(f"/api/{version}/advertisement")
class AdvertisementList(Resource):
    @api.doc('get_advertisements')
    def get(self):
        """
        Retorna todos los anuncios registrados en el sistema.
        """
        advertisements = get_entity_data("Advertisements")
        serialized_advertisements = []
        if advertisements:
            serialized_advertisements = [advertisement.to_dict() for advertisement in advertisements]

        return jsonify(serialized_advertisements)

    @api.doc('create_advertisement')
    @api.expect(advertisement_model)
    def post(self):
        """
        Crea un nuevo anuncio.
        """
        data = request.json
        new_advertisement = Advertisement(
            id=0,  # El ID se generará automáticamente en la base de datos
            objectId=data["objectId"],
            type=data["type"],
            name=data["name"],
            status=data["status"],
            description=data["description"],
            publicationDate=data["publicationDate"],
            expirationDate=data["expirationDate"]
        )
        if save_entity_to_database(new_advertisement):
            return {"message": "Anuncio creado correctamente"}, 201
        else:
            return {"message": "Error al crear el anuncio"}, 500

@api.route(f"/api/{version}/advertisement/<int:id>")
class SpecificAdvertisement(Resource):
    @api.doc('get_advertisement')
    def get(self, id):
        """
        Retorna un anuncio por su ID.
        """
        advertisements = get_entity_data("Advertisements")
        advertisement = next((advertisement for advertisement in advertisements if advertisement.id == id), None)

        if advertisement:
            serialized_advertisement = advertisement.to_dict()
            return jsonify(serialized_advertisement)
        else:
            return {"message": "Anuncio no encontrado"}, 404

    @api.doc('update_advertisement')
    @api.expect(advertisement_model)
    def put(self, id):
        """
        Actualiza un anuncio existente.
        """
        data = request.json
        advertisements = get_entity_data("Advertisements")
        advertisement: Advertisement = next((advertisement for advertisement in advertisements if advertisement.id == id), None)
        if advertisement:
            advertisement.objectId = data["objectId"]
            advertisement.type = data["type"]
            advertisement.name = data["name"]
            advertisement.status = data["status"]
            advertisement.description = data["description"]
            advertisement.publicationDate = data["publicationDate"]
            advertisement.expirationDate = data["expirationDate"]
            if update_entity_in_database(advertisement):
                return {"message": "Anuncio actualizado correctamente"}, 200
            else:
                return {"message": "Error al actualizar el anuncio"}, 500
        else:
            return {"message": "Anuncio no encontrado"}, 404

    @api.doc('delete_advertisement')
    def delete(self, id):
        """
        Elimina un anuncio por su ID.
        """
        if delete_entity_from_database(id, "Advertisements"):
            return {"message": "Anuncio eliminado correctamente"}, 200
        else:
            return {"message": "Error al eliminar el anuncio"}, 500

#=============================================MESSAGE CONTROLLER=========================================

@api.route(f"/api/{version}/message")
class MessageList(Resource):
    @api.doc('get_messages')
    def get(self):
        """
        Retorna todos los mensajes registrados en el sistema.
        """
        messages = get_entity_data("Messages")
        serialized_messages = []
        if messages:
            serialized_messages = [message.to_dict() for message in messages]

        return jsonify(serialized_messages)
    """
    @api.doc('get_conversation')
    def get(self, id1, id2):
        
        #Retorna todos los mensajes entre dos usuarios.
        
        data = request.json
        print(data)
        messages = get_conversation(id1, id2)
        serialized_messages = []
        if messages:
            serialized_messages = [message.to_dict() for message in messages]

        return jsonify(serialized_messages)
    """
    @api.doc('create_message')
    @api.expect(message_model)
    def post(self):
        """
        Crea un nuevo mensaje.
        """
        data = request.json
        new_message = Message(
            id=0,  # El ID se generará automáticamente en la base de datos
            uidReceiver=data["uidReceiver"],
            uidSender=data["uidSender"],
            type=data["type"],
            message=data["message"],
            sentDate=data["sentDate"],
            serviceId=data["serviceId"]
        )
        if save_entity_to_database(new_message):
            return {"message": "Mensaje creado correctamente"}, 201
        else:
            return {"message": "Error al crear el mensaje"}, 500

@api.route(f"/api/{version}/message/<int:id>")
class MessageOp(Resource):
    @api.doc('delete_message')
    def delete(self, id):
        """
        Elimina un mensaje por su ID.
        """
        if delete_entity_from_database(id, "Messages"):
            return {"message": "Mensaje eliminado correctamente"}, 200
        else:
            return {"message": "Error al eliminar el mensaje"}, 500

#======================================REVIEW CONTROLLER=======================================

@api.route(f"/api/{version}/review")
class ReviewList(Resource):
    @api.doc('get_reviews')
    def get(self):
        """
        Retorna todos los comentarios registrados en el sistema.
        """
        reviews = get_entity_data("Reviews")
        serialized_reviews = []
        if reviews:
            serialized_reviews = [review.to_dict() for review in reviews]

        return jsonify(serialized_reviews)

    @api.doc('create_review')
    @api.expect(review_model)
    def post(self):
        """
        Crea un nuevo comentario.
        """
        data = request.json
        new_advertisement = Review(
            id=0,  # El ID se generará automáticamente en la base de datos
            objectId=data["objectId"],
            type=data["type"],
            publicationDate=data["publicationDate"],
            content=data["content"],
            rating=data["rating"],
            uidPublisher=data["uidPublisher"]
        )
        if save_entity_to_database(new_advertisement):
            return {"message": "Comentario creado correctamente"}, 201
        else:
            return {"message": "Error al crear el comentario"}, 500

@api.route(f"/api/{version}/review/<int:id>")
class SpecificAdvertisement(Resource):
    @api.doc('get_review')
    def get(self, id):
        """
        Retorna un anuncio por su ID.
        """
        reviews = get_entity_data("Reviews")
        review = next((review for review in reviews if review.id == id), None)

        if review:
            serialized_reviews = review.to_dict()
            return jsonify(serialized_reviews)
        else:
            return {"message": "Comentario no encontrado"}, 404

    @api.doc('update_review')
    @api.expect(review_model)
    def put(self, id):
        """
        Actualiza un comentario existente.
        """
        data = request.json
        reviews = get_entity_data("Reviews")
        review: Review = next((review for review in reviews if review.id == id), None)
        if review:
            review.objectId = data["objectId"]
            review.type = data["type"]
            review.publicationDate = data["publicationDate"]
            review.content = data["content"]
            review.rating = data["rating"]
            review.uidPublisher = data["uidPublisher"]
            if update_entity_in_database(review):
                return {"message": "Comentario actualizado correctamente"}, 200
            else:
                return {"message": "Error al actualizar el comentario"}, 500
        else:
            return {"message": "Comentario no encontrado"}, 404

    @api.doc('delete_review')
    def delete(self, id):
        """
        Elimina un comentario por su ID.
        """
        if delete_entity_from_database(id, "Reviews"):
            return {"message": "Comentario eliminado correctamente"}, 200
        else:
            return {"message": "Error al eliminar el comentario"}, 500

if __name__ == "__main__":
    app.run(host='0.0.0.0', debug=True)