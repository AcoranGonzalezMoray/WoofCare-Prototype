

class User:
    def __init__(self, id, name, email, password, accountType, subscriptionType, location, profileUrl, phone, service):
        self.id = id
        self.name = name
        self.email = email
        self.password = password
        self.accountType = accountType
        self.subscriptionType = subscriptionType
        self.location = location
        self.profileUrl = profileUrl
        self.phone = phone
        self.service = service

    def to_dict(self):
        return {
            "id": self.id,
            "name": self.name,
            "email": self.email,
            "password": self.password,
            "accountType": self.accountType,
            "subscriptionType": self.subscriptionType,
            "location": self.location,
            "profileUrl": self.profileUrl,
            "phone": self.phone,
            "service": self.service
        }
    


