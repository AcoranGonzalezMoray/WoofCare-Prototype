import pyodbc

from context.sqlServer.connection import getConnection
from entities.Product import Product


connection_string = getConnection()



def execute_query(query):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute(query)
            rows = cursor.fetchall()
            data = [Product(*row) for row in rows]
            return data
    except pyodbc.Error as e:
        print(f"Error executing query: {e}")
        return []
 
def get_product_data():
    query = "SELECT * FROM Products;"
    data = execute_query(query)
    return data



def save_product_to_database(product):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            id = get_last_product_id()
            cursor.execute(
                """
                INSERT INTO Products (id, name, description, price, location, companyName, status, bannerUrls)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """,
                (
                    id + 1,
                    product.name,
                    product.description,
                    product.price,
                    product.location,
                    product.companyName,
                    product.status,
                    product.bannerUrls,
                ),
            )
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error saving product to database: {e}")
        return False

def update_product_in_database(product):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute(
                """
                UPDATE Products
                SET name = ?, description = ?, price = ?, location = ?, companyName = ?, status = ?, bannerUrls = ?
                WHERE id = ?
                """,
                (
                    product.name,
                    product.description,
                    product.price,
                    product.location,
                    product.companyName,
                    product.status,
                    product.bannerUrls,
                    product.id,
                ),
            )
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error updating product in database: {e}")
        return False
    
def get_last_product_id():
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute("SELECT MAX(id) FROM Products")
            result = cursor.fetchone()
            last_id = result[0]
            if last_id is None:
                return 0  # Devolver 0 si no hay usuarios en la base de datos
            else:
                return last_id
    except pyodbc.Error as e:
        print(f"Error getting last user ID from database: {e}")
        return None   

def delete_product_from_database(product_id):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute("DELETE FROM Products WHERE id = ?", (product_id,))
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error deleting product from database: {e}")
        return False
