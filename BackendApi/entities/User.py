

class User:
    def __init__(self, id, name, email, password, accountType, suscriptionType, location, profileUrl, phone, statusAccount, age):
        self.id = id
        self.name = name
        self.email = email
        self.password = password
        self.accountType = accountType
        self.suscriptionType = suscriptionType
        self.location = location
        self.profileUrl = profileUrl
        self.phone = phone
        self.statusAccount = statusAccount
        self.age = age


    def to_dict(self):
        return {
            "id": self.id,
            "name": self.name,
            "email": self.email,
            "password": self.password,
            "accountType": self.accountType,
            "suscriptionType": self.suscriptionType,
            "location": self.location,
            "profileUrl": self.profileUrl,
            "phone": self.phone,
            "statusAccount": self.statusAccount,
            "age": self.age
        }
    


