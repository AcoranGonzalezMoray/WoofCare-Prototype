from flask_restx import fields

class Model:
    @staticmethod
    def getUserModel():
        return  {
            'name': fields.String(required=True, description='Nombre del usuario'),
            'email': fields.String(required=True, description='Correo electrónico del usuario'),
            'password': fields.String(required=True, description='Contraseña del usuario'),
            'accountType': fields.Integer(required=True, description='Tipo de cuenta del usuario 0: Busca Servicios 1:Oferta Servicios Cuidados 2:Oferta Servicios Entrenamiento'),
            'subscriptionType': fields.Integer(required=True, description='Tipo de suscripción del usuario A: B: C: D:'),
            'location': fields.String(required=True, description='Ubicación del usuario'),
            'profileUrl': fields.String(required=True, description='URL del perfil del usuario'),
            'phone': fields.String(required=True, description='Número de teléfono del usuario'),
            'service': fields.String(required=True, description='Servicio del usuario')     
        }
    