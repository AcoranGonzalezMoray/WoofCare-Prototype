import pyodbc

from context.sqlServer.connection import getConnection
from entities.Message import Message

connection_string = getConnection()

def execute_query(query):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute(query)
            rows = cursor.fetchall()
            data = [Message(*row) for row in rows]
            return data
    except pyodbc.Error as e:
        print(f"Error executing query: {e}")
        return []
    
def get_message_data():
    query = "SELECT * FROM Messages"
    data = execute_query(query)
    return data

#Todos los usuarios con los que ha intercambiado mensajes
def get_messages(id):
    query = f"SELECT * FROM Messages WHERE (uidReceiver= {id} OR uidSender = {id});"
    data = execute_query(query)
    return data

def get_conversation(id1, id2):
    query = f"SELECT * FROM Messages WHERE (uidReceiver = {id1} AND uidSender = {id2}) OR (uidReceiver = {id2} AND uidSender = {id1}) ORDER BY sentDate;"
    data = execute_query(query)
    return data

def save_message_to_database(message: Message):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            id = get_last_message_id()
            cursor.execute(
                """
                INSERT INTO Messages (id, uidReceiver, uidSender, type, message, sentDate, serviceId)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """,
                (
                    id + 1,
                    message.uidReceiver,
                    message.uidSender,
                    message.type,
                    message.message,
                    message.sentDate,
                    message.serviceId,
                ),
            )
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error saving message to database: {e}")
        return False

def get_last_message_id():
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute("SELECT MAX(id) FROM Messages")
            result = cursor.fetchone()
            last_id = result[0]
            if last_id is None:
                return 0  # Devolver 0 si no hay mensajes en la base de datos
            else:
                return last_id
    except pyodbc.Error as e:
        print(f"Error getting last message ID from database: {e}")
        return None    
    
def delete_message_from_database(message_id):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute("DELETE FROM Messages WHERE id = ?", (message_id,))
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error deleting product from database: {e}")
        return False
