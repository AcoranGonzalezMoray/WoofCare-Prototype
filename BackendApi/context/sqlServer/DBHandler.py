import pyodbc

from context.sqlServer.connection import getConnection

from entities.User import User
from entities.Product import Product
from entities.Dog import Dog
from entities.Message import Message
from entities.Advertisement import Advertisement
from entities.Request import Request
from entities.Service import Service
from entities.Review import Review


connection_string = getConnection()
dict = {
    "Users": User,
    "Products": Product,
    "Dogs": Dog,
    "Messages": Message,
    "Advertisements": Advertisement,
    "Requests": Request,
    "Services": Service,
    "Reviews": Review
}        

def execute_query(query, table_name):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute(query)
            rows = cursor.fetchall()
            data = [dict[table_name](*row) for row in rows]
            return data
    except pyodbc.Error as e:
        print(f"Error executing query: {e}")
        return []
    
def get_entity_data(table_name):
    query = f"SELECT * FROM {table_name};"
    data = execute_query(query, table_name)
    return data

def save_entity_to_database(object):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            id = get_last_entity_id(object)

            #obtener el nombre de los atributos
            attributes = [attr for attr in dir(object) if not callable(getattr(object, attr)) and not attr.startswith("__")]
            
            #obtener el valor de los atributos
            attribute_values = [getattr(object, attr) for attr in attributes]

            #Sustituir el id proporcionado por el nuevo
            for i in range(len(attribute_values)):
                if attributes[i] == "id":
                    attribute_values[i] = id + 1
                
            #String de los ?, ?, ?,...
            placeholders = ", ".join(["?"] * len(attributes))
            #String de los atributos
            attributes_string = ", ".join(attributes)
            table_name = get_table_name(object)
            
            cursor.execute(f"INSERT INTO {table_name} ({attributes_string}) VALUES ({placeholders})",
                ( 
                    attribute_values
                ),
            )
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error saving {table_name} to database: {e}")
        return False
    
def update_entity_in_database(object):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            table_name = get_table_name(object)
            attributes = [attr for attr in dir(object) if not callable(getattr(object, attr)) and not attr.startswith("__")]

            updated_fields = ""
            for i in range(len(attributes)):
                if str(attributes[i]) != "id" and i == (len(attributes) -1 ):
                    updated_fields += str(attributes[i]) + " = ?"
                elif str(attributes[i]) != "id":
                    updated_fields += str(attributes[i]) + " = ?, "

            print(id)
            attribute_values = [getattr(object, attr) for attr in attributes]
            for i in range(len(attribute_values)):
                if attributes[i] == "id":
                    attribute_values.pop(i)
            print(f"UPDATE {table_name} SET {updated_fields} WHERE id = ?")
            print(str(attribute_values) + str(id))
            cursor.execute(f"UPDATE {table_name} SET {updated_fields} WHERE id = ?",
                (
                    *attribute_values, object.id
                ),
            )
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error updating {table_name} in database: {e}")
        return False

def get_last_entity_id(object):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            table_name = get_table_name(object)
            cursor.execute(f"SELECT MAX(id) FROM {table_name}")
            result = cursor.fetchone()
            last_id = result[0]
            if last_id is None:
                return 0  # Devolver 0 si no hay anuncios en la base de datos
            else:
                return last_id
    except pyodbc.Error as e:
        print(f"Error getting last {table_name} ID from database: {e}")
        return None 
    
def delete_entity_from_database(id, table_name):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            
            cursor.execute(f"DELETE FROM {table_name} WHERE id = ?", (id))
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error deleting {table_name} from database: {e}")
        return False
    
def get_table_name(object):
    return type(object).__name__ + "s"

def get_conversations(uid):
    print("Antes")
    query = f"SELECT DISTINCT uidSender FROM Messages WHERE uidReceiver = {uid} UNION SELECT DISTINCT uidReceiver FROM Messages WHERE uidSender = {uid}"
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute(query)
            data = cursor.fetchall()
            return data
    except pyodbc.Error as e:
        print(f"Error executing query: {e}")
        return []
    
def get_conversation(uid1, uid2):
    query = f"SELECT * FROM (SELECT * FROM Messages WHERE uidReceiver = {uid1} AND uidSender = {uid2} UNION SELECT * FROM Messages WHERE uidReceiver = {uid2} AND uidSender = {uid1}) AS combined_messages ORDER BY sentDate DESC;"
    data = execute_query(query, "Messages")
    return data