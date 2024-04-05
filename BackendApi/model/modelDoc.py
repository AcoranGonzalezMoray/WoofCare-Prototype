from flask_restx import fields

class Model:
    @staticmethod
    def getUserModel():
        return  {
            'name': fields.String(required=True, description='Nombre del usuario'),
            'email': fields.String(required=True, description='Correo electrónico del usuario'),
            'password': fields.String(required=True, description='Contraseña del usuario'),
            'accountType': fields.Integer(required=True, description='Tipo de cuenta del usuario 0: Busca Servicios 1:Oferta Servicios Cuidados 2:Oferta Servicios Entrenamiento'),
            'suscriptionType': fields.Integer(required=True, description='Tipo de suscripción del usuario A: B: C: D:'),
            'location': fields.String(required=True, description='Ubicación del usuario'),
            'profileUrl': fields.String(required=True, description='URL del perfil del usuario'),
            'phone': fields.String(required=True, description='Número de teléfono del usuario'),
            'statusAccount': fields.String(required=True, description='0:Activa, 1: Indica que la cuenta esta suspendida temporalmente, 2: Indica que la cuenta esta bloqueada'),
        }
    
    @staticmethod
    def getProductModel():
        return {
            'name': fields.String(required=True, description='Nombre del producto'),
            'description': fields.String(required=True, description='Descripción del producto'),
            'price': fields.Float(required=True, description='Precio del producto'),
            'location': fields.String(required=True, description='Ubicación del producto'),
            'companyName': fields.String(required=True, description='Nombre de la empresa del producto'),
            'status': fields.String(required=True, description='Estado del producto 0: Activo 1: Descatalogado '),
            'bannerUrls': fields.String(required=True, description='URLs del banner del producto'),
        }
    
    @staticmethod
    def getRequestModel():
        return {
            'uidReceiver': fields.String(required=True, description='ID del receptor de la solicitud'),
            'uidSender': fields.String(required=True, description='ID del remitente de la solicitud'),
            'serviceId': fields.Integer(required=True, description='ID del servicio relacionado con la solicitud'),
            'status': fields.String(required=True, description='Estado de la solicitud 0: Pendiente, 1:Aceptado, 2:Denegarla, 3:Cancelado'),
            'creationDate': fields.DateTime(required=True, description='Fecha de creación de la solicitud en formato ISO8601')
        }
    
    @staticmethod
    def getServiceModel():
        return {
            'name': fields.String(required=True, description='Nombre del servicio'),
            'type': fields.String(required=True, description='Tipo de servicio 1: Cuidador, 2:Entrenador'),
            'status': fields.String(required=True, description='Estado del servicio 0: Disponible, 1:No disponible'),
            'publicationDate': fields.DateTime(required=True, description='Fecha de publicación del servicio en formato ISO8601'),
            'description': fields.String(required=True, description='Descripción del servicio'),
            'price': fields.Float(required=True, description='Precio del servicio'),
            'uid': fields.String(required=True, description='ID del usuario que publicó el servicio'),
            'bannerUrl': fields.String(required=True, description='URL del banner del servicio')
        }