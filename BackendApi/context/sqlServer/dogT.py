import pyodbc

from context.sqlServer.connection import getConnection
from entities.Dog import Dog


connection_string = getConnection()

def execute_query(query):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute(query)
            rows = cursor.fetchall()
            data = [Dog(*row) for row in rows]
            return data
    except pyodbc.Error as e:
        print(f"Error executing query: {e}")
        return []

def get_dog_data():
    query = "SELECT * FROM DOGS;"
    data = execute_query(query)
    return data

def save_dog_to_database(dog: Dog):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            id = get_last_dog_id()
            cursor.execute(
                """
                INSERT INTO Dogs (id, breed, size, weight, age)
                VALUES (?, ?, ?, ?, ?)
                """,
                (
                    id + 1,
                    dog.breed,
                    dog.size,
                    dog.weight,
                    dog.age,
                ),
            )
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error saving dog to database: {e}")
        return False

def update_dog_in_database(dog: Dog):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute(
                """
                UPDATE Dogs
                SET breed = ?, size = ?, weight = ?, age = ?
                WHERE id = ?
                """,
                (
                    dog.breed,
                    dog.size,
                    dog.weight,
                    dog.age,
                    dog.id,
                ),
            )
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error updating dog in database: {e}")
        return False

def get_last_dog_id():
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute("SELECT MAX(id) FROM Dogs")
            result = cursor.fetchone()
            last_id = result[0]
            if last_id is None:
                return 0  # Devolver 0 si no hay usuarios en la base de datos
            else:
                return last_id
    except pyodbc.Error as e:
        print(f"Error getting last dog ID from database: {e}")
        return None 
    

def delete_dog_from_database(dog_id: int):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute("DELETE FROM Dogs WHERE id = ?", (dog_id,))
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error deleting dog from database: {e}")
        return False