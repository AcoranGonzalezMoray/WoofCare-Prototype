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
            'age': fields.Integer(required=True, description='Edad del usuario'),
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
    
    @staticmethod
    def getDogModel():
        return {
            'breed': fields.String(required=True, description='Raza del perro'),
            'size': fields.String(required=True, description='Tamaño del perro'),
            'weight': fields.Float(required=True, description='Peso del perro'),
            'age': fields.Integer(required=True, description='Edad del perro en meses')
        }
    
    @staticmethod
    def getAdvertisementModel():
        return {
            'objectId': fields.String(required=True, description='Id del producto al que hace referencia') ,
            'type': fields.String(required=True, description='Tipo de promocion (0:Producto, 1:Usuario)') ,
            'name': fields.String(required=True, description='Nombre de la promocion') ,
            'status': fields.String(required=True, description='Estado de la promocion (0: Disponible, 1: No disponible)') ,
            'description': fields.String(required=True, description='Descripcion de la promocion') ,
            'publicationDate': fields.String(required=True, description='Fecha de la publicacion de la promocion') ,
            'expirationDate': fields.String(required=True, description='Fecha de vencimiento de la promocion') ,
        }
    
    @staticmethod
    def getMessageModel():
        return {
            'receiverid': fields.String(required=True, description='Id del usuario receptor del mensaje'),
            'senderid': fields.String(required=True, description='Id del usuario que envio el mensaje'),
            'type': fields.String(required=True, description='Tipo del mensaje (0: texto, 1: imagen)'),
            'content': fields.String(required=True, description='Contenido del mensaje'),
            'datetime': fields.String(required=True, description='Fechora en la que se envió el mensaje'),
            'serviceid': fields.String(required=True, description='Id del servicio por el que se contacta')
        }
    
    @staticmethod
    def getReviewModel():
        return {
            'objectId': fields.String(required=True, description='Id del objeto (usuario o servicio o producto) sobre el que se hizo la review'),
            'type': fields.String(required=True, description='Tipo de objeto al que hace referencia (0: usuario, 1: servicio, 2: producto)'),
            'publicationDate': fields.String(required=True, description='Fecha de subida la review'),
            'content': fields.String(required=True, description='Contenido de la review, el mensaje'),
            'rating': fields.String(required=True, description='Numero del 1 al 5 indicando la calidad'),
            'uidPublisher': fields.String(required=True, description='Id del usuario que lo publico')
        }
