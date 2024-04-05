import pyodbc

from context.sqlServer.connection import getConnection
from entities.Request import  Request


connection_string = getConnection()

def execute_query(query):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute(query)
            rows = cursor.fetchall()
            data = [Request(*row) for row in rows]
            return data
    except pyodbc.Error as e:
        print(f"Error executing query: {e}")
        return []

def get_request_data():
    query = "SELECT * FROM Requests;"
    data = execute_query(query)
    return data

def save_request_to_database(request):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            id = get_last_request_id()
            cursor.execute(
                """
                INSERT INTO Requests (id, uidReceiver, uidSender, serviceId, status, creationDate)
                VALUES (?, ?, ?, ?, ?, ?)
                """,
                (
                    id + 1,
                    request.uidReceiver,
                    request.uidSender,
                    request.serviceId,
                    request.status,
                    request.creationDate,
                ),
            )
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error saving request to database: {e}")
        return False

def update_request_in_database(request):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute(
                """
                UPDATE Requests
                SET uidReceiver = ?, uidSender = ?, serviceId = ?, status = ?, creationDate = ?
                WHERE id = ?
                """,
                (
                    request.uidReceiver,
                    request.uidSender,
                    request.serviceId,
                    request.status,
                    request.creationDate,
                    request.id,
                ),
            )
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error updating request in database: {e}")
        return False

def get_last_request_id():
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute("SELECT MAX(id) FROM Requests")
            result = cursor.fetchone()
            last_id = result[0]
            if last_id is None:
                return 0  # Devolver 0 si no hay solicitudes en la base de datos
            else:
                return last_id
    except pyodbc.Error as e:
        print(f"Error getting last request ID from database: {e}")
        return None

def delete_request_from_database(request_id):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute("DELETE FROM Requests WHERE id = ?", (request_id,))
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error deleting request from database: {e}")
        return False
