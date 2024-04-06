import pyodbc

from context.sqlServer.connection import getConnection
from entities.Advertisement import Advertisement

connection_string = getConnection()

def execute_query(query):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute(query)
            rows = cursor.fetchall()
            data = [Advertisement(*row) for row in rows]
            return data
    except pyodbc.Error as e:
        print(f"Error executing query: {e}")
        return []
    
def get_advertisement_data():
    query = "SELECT * FROM Advertisements;"
    data = execute_query(query)
    return data

def save_advertisement_to_database(advertisement: Advertisement):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            id = get_last_advertisement_id()
            cursor.execute(
                """
                INSERT INTO Advertisements (id, objectId, type, name, status, description, publicationDate, expirationDate)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """,
                (
                    id + 1,
                    advertisement.objectId,
                    advertisement.type,
                    advertisement.name,
                    advertisement.status,
                    advertisement.description,
                    advertisement.publicationDate,
                    advertisement.expirationDate,
                ),
            )
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error saving advertisement to database: {e}")
        return False
    
def update_advertisement_in_database(advertisement: Advertisement):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute(
                """
                UPDATE Advertisements
                SET objectId = ?, type = ?, name = ?, status = ?, description = ?, publicationDate = ?, expirationDate = ?
                WHERE id = ?
                """,
                (
                    advertisement.objectId,
                    advertisement.type,
                    advertisement.name,
                    advertisement.status,
                    advertisement.description,
                    advertisement.publicationDate,
                    advertisement.expirationDate,
                    advertisement.id,
                ),
            )
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error updating advertisement in database: {e}")
        return False

def get_last_advertisement_id():
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute("SELECT MAX(id) FROM Advertisements")
            result = cursor.fetchone()
            last_id = result[0]
            if last_id is None:
                return 0  # Devolver 0 si no hay anuncios en la base de datos
            else:
                return last_id
    except pyodbc.Error as e:
        print(f"Error getting last advertisement ID from database: {e}")
        return None 
    
def delete_advertisement_from_database(advertisement_id: int):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute("DELETE FROM Advertisements WHERE id = ?", (advertisement_id,))
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error deleting advertisement from database: {e}")
        return False

