import pyodbc
from entities.User import User
from context.sqlServer.connection import getConnection

connection_string = getConnection()


def execute_query(query):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute(query)
            rows = cursor.fetchall()
            data = [User(*row) for row in rows]
            return data
    except pyodbc.Error as e:
        print(f"Error executing query: {e}")
        return []


def get_user_data():
    query = "SELECT * FROM Users;"
    data = execute_query(query)
    return data


def save_user_to_database(user):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            id = get_last_user_id()
            cursor.execute(
                """
                INSERT INTO Users (id, name, email, password, accountType, suscriptionType, location, profileUrl, phone, statusAccount)
                VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?)
                """,
                (
                    id + 1,
                    user.name,
                    user.email,
                    user.password,
                    user.accountType,
                    user.suscriptionType,
                    user.location,
                    user.profileUrl,
                    user.phone,
                    user.statusAccount,
                ),
            )
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error saving user to database: {e}")
        return False


def update_user_in_database(user):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute(
                """
                UPDATE Users
                SET name = ?, email = ?, password = ?, accountType = ?, suscriptionType = ?, location = ?, profileUrl = ?, phone = ? , statusAccount = ?
                WHERE id = ?
                """,
                (
                    user.name,
                    user.email,
                    user.password,
                    user.accountType,
                    user.suscriptionType,
                    user.location,
                    user.profileUrl,
                    user.phone,
                    user.statusAccount,
                    user.id,
                ),
            )
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error updating user in database: {e}")
        return False


def get_last_user_id():
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute("SELECT MAX(id) FROM Users")
            result = cursor.fetchone()
            last_id = result[0]
            if last_id is None:
                return 0  # Devolver 0 si no hay usuarios en la base de datos
            else:
                return last_id
    except pyodbc.Error as e:
        print(f"Error getting last user ID from database: {e}")
        return None


def get_user_by_email(email):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute("SELECT * FROM Users WHERE email = ?", (email,))
            row = cursor.fetchone()
            if row:
                user = User(
                    id=row[0],
                    name=row[1],
                    email=row[2],
                    password=row[3],
                    accountType=row[4],
                    suscriptionType=row[5],
                    location=row[6],
                    profileUrl=row[7],
                    phone=row[8],
                    statusAccount=row[9]
                )
                return user
            else:
                return None
    except pyodbc.Error as e:
        print(f"Error getting user by email: {e}")
        return None


def email_exists(email):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute("SELECT COUNT(*) FROM Users WHERE email = ?", (email,))
            count = cursor.fetchone()[0]
            if count > 0:
                return True
            else:
                return False
    except pyodbc.Error as e:
        print(f"Error checking if email exists: {e}")
        return False


def delete_user_from_database(user_id):
    try:
        with pyodbc.connect(connection_string) as connection:
            cursor = connection.cursor()
            cursor.execute("DELETE FROM Users WHERE id = ?", (user_id,))
            connection.commit()
        return True
    except pyodbc.Error as e:
        print(f"Error deleting user from database: {e}")
        return False
