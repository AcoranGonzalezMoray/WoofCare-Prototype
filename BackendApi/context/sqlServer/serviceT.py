import pyodbc

from context.sqlServer.connection import getConnection
from entities.Service import Service


connection_string = getConnection()

def execute_query(query):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute(query)
            rows = cursor.fetchall()
            data = [Service(*row) for row in rows]
            return data
    except pyodbc.Error as e:
        print(f"Error executing query: {e}")
        return []

def get_service_data():
    query = "SELECT * FROM Services;"
    data = execute_query(query)
    return data

def save_service_to_database(service):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            id = get_last_service_id()
            cursor.execute(
                """
                INSERT INTO Services (id, name, type, status, publicationDate, description, price, uid, bannerUrl)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """,
                (
                    id + 1,
                    service.name,
                    service.type,
                    service.status,
                    service.publicationDate,
                    service.description,
                    service.price,
                    service.uid,
                    service.bannerUrl,
                ),
            )
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error saving service to database: {e}")
        return False

def update_service_in_database(service):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute(
                """
                UPDATE Services
                SET name = ?, type = ?, status = ?, publicationDate = ?, description = ?, price = ?, uid = ?, bannerUrl = ?
                WHERE id = ?
                """,
                (
                    service.name,
                    service.type,
                    service.status,
                    service.publicationDate,
                    service.description,
                    service.price,
                    service.uid,
                    service.bannerUrl,
                    service.id,
                ),
            )
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error updating service in database: {e}")
        return False

def get_last_service_id():
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute("SELECT MAX(id) FROM Services")
            result = cursor.fetchone()
            last_id = result[0]
            if last_id is None:
                return 0  # Devolver 0 si no hay servicios en la base de datos
            else:
                return last_id
    except pyodbc.Error as e:
        print(f"Error getting last service ID from database: {e}")
        return None

def delete_service_from_database(service_id):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute("DELETE FROM Services WHERE id = ?", (service_id,))
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error deleting service from database: {e}")
        return False
