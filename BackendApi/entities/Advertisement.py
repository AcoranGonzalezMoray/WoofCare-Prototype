class Advertisement:
    def __init__(self, id, objectId, type, name, status, description, publicationDate, expirationDate):
        self.id = id
        self.objectId = objectId
        self.type = type
        self.name = name
        self.status = status
        self.description = description
        self.publicationDate = publicationDate
        self.expirationDate = expirationDate

    def to_dict(self):
        return {
            "id": self.id,
            "objectId": self.objectId,
            "type": self.type,
            "name": self.name,
            "status": self.status,
            "description": self.description,
            "publicationDate": self.publicationDate,
            "expirationDate": self.expirationDate
        }
