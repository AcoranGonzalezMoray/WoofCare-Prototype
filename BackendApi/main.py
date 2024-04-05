import hashlib
from context.sqlServer.productT import delete_product_from_database, get_product_data, save_product_to_database, update_product_in_database
from context.sqlServer.requestT import delete_request_from_database, get_request_data, save_request_to_database, update_request_in_database
from context.sqlServer.serviceT import delete_service_from_database, get_service_data, save_service_to_database, update_service_in_database
from entities.Product import Product
from entities.Request import Request
from entities.Service import Service
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

# =====================USER CONTROLLER==============================

@api.route(f"/api/{version}/user")
class UserList(Resource):
    @api.doc('get_users')
    def get(self):
        """
        Retorna todos los usuarios registrados en el sistema.
        """
        users = get_user_data()
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
        if save_user_to_database(new_user):
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
        users = get_user_data()
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
        users = get_user_data()
        user = next((user for user in users if user.id == id), None)
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
            if update_user_in_database(user):
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
        if delete_user_from_database(id):
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
                return {"message": "Inicio de sesión exitoso"}, 200
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
        products = get_product_data()
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
        if save_product_to_database(new_product):
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
        products = get_product_data()
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
        products = get_product_data()
        product = next((product for product in products if product.id == id), None)
        if product:
            product.name = data["name"]
            product.description = data["description"]
            product.price = data["price"]
            product.location = data["location"]
            product.companyName = data["companyName"]
            product.status = data["status"]
            product.bannerUrls = data["bannerUrls"]
            if update_product_in_database(product):
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
        if delete_product_from_database(id):
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
        requests = get_request_data()
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
        if save_request_to_database(new_request):
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
        requests = get_request_data()
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
        requests = get_request_data()
        requestN = next((request for request in requests if request.id == id), None)
        if requestN:
            requestN.uidReceiver = data["uidReceiver"]
            requestN.uidSender = data["uidSender"]
            requestN.serviceId = data["serviceId"]
            requestN.status = data["status"]
            requestN.creationDate = data["creationDate"]
            if update_request_in_database(requestN):
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
        if delete_request_from_database(id):
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
        services = get_service_data()
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
        if save_service_to_database(new_service):
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
        services = get_service_data()
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
        services = get_service_data()
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
            if update_service_in_database(service):
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
        if delete_service_from_database(id):
            return {"message": "Servicio eliminado correctamente"}, 200
        else:
            return {"message": "Error al eliminar el servicio"}, 500


if __name__ == "__main__":
    app.run(debug=True)
