import firebase_admin
from firebase_admin import credentials, storage
from urllib.parse import urlparse, quote
import os
import datetime
import asyncio

class StorageRepository:
    def __init__(self):
        # Ruta al archivo de credenciales de Firebase
        cred_path = "context\\firebase\\credential.json"
        # Inicializar la aplicación Firebase
        cred = credentials.Certificate(cred_path)
        firebase_admin.initialize_app(cred, {
            'storageBucket': 'woofcare-801b7.appspot.com'
        })


    async def delete_image(self, name):
        try:
            # Parsear la URL
            uri = urlparse(name)
            # Obtener el nombre del archivo
            file_name = os.path.basename(uri.path)
            file_name = file_name.split('%2F')[1]

            # Eliminar la imagen
            bucket = storage.bucket()
            blob = bucket.blob("Fotos_Perfil/" + file_name)

            await blob.delete()
        except Exception as e:
            print("Error deleting image:", e)

    async def upload_image(self, archivo, name):
        try:
            name_new = name + datetime.datetime.now().strftime("%Y-%m-%d_%H-%M-%S")

            bucket = storage.bucket()
            blob = bucket.blob("Fotos_Perfil/" + name_new+".jpg")
            blob.upload_from_filename(archivo)


            encoded_name = quote(name_new)

            download_url = f"https://firebasestorage.googleapis.com/v0/b/woofcare-801b7.appspot.com/o/Fotos_Perfil%2F{encoded_name}.jpg?alt=media"
            return download_url
        except Exception as e:
            print("Error uploading image:", e)



