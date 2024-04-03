import hashlib
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
# =====================SERVICE CONTROLLER==============================
# =====================REQUEST CONTROLLER==============================
# =====================DOG CONTROLLER==============================
# =====================MESSAGE CONTROLLER==============================
# =====================ADVERTISEMENT CONTROLLER==============================
if __name__ == "__main__":
    app.run(debug=True)
