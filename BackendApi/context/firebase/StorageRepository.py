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

            # Eliminar la imagen
            bucket = storage.bucket()
            blob = bucket.blob("Fotos_Perfil/" + file_name)
            await blob.delete()
        except Exception as e:
            print("Error deleting image:", e)

    async def upload_image(self, archivo, name):
        try:
            # Generar un nuevo nombre para el archivo
            name_new = name + datetime.datetime.now().strftime("%Y-%m-%d_%H-%M-%S")

            # Subir la imagen
            bucket = storage.bucket()
            blob = bucket.blob("Fotos_Perfil/" + name_new+".jpg")
            blob.upload_from_filename(archivo)

            # Obtener la URL de descarga de la imagen
            # Generar una URL pública para acceder al objeto sin firmar
            # Construir la URL manualmente utilizando la información proporcionada
            # Codificar el nombre del archivo
            encoded_name = quote(name_new)
            # https://firebasestorage.googleapis.com/v0/b/woofcare-801b7.appspot.com/o/Fotos_Perfil%2Fimage_name2024-04-05_22-32-31.jpg?alt=media
            # Construir la URL con el nombre del archivo codificado
            download_url = f"https://firebasestorage.googleapis.com/v0/b/woofcare-801b7.appspot.com/o/Fotos_Perfil%2F{encoded_name}.jpg?alt=media"
            return download_url
        except Exception as e:
            print("Error uploading image:", e)



async def main():
    # Instancia de la clase StorageRepository
    storage_repo = StorageRepository()
    # Ruta local de la imagen a subir
    local_image_path = "C:\\Users\\acora\\OneDrive\\Imágenes\\marmol-negro-monterrey-1.jpg"
    # Nombre de la imagen
    image_name = "image_name"

    download_url = await storage_repo.upload_image(local_image_path, image_name)
    print("URL de descarga de la imagen:", download_url)

# Ejecutar el bucle de eventos de asyncio
asyncio.run(main())
