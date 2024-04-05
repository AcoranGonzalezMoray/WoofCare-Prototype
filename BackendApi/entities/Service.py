class Service:
    def __init__(self, id, name, type, status, publicationDate, description, price, uid, bannerUrl):
        self.id = id
        self.name = name
        self.type = type
        self.status = status
        self.publicationDate = publicationDate
        self.description = description
        self.price = price
        self.uid = uid
        self.bannerUrl = bannerUrl

    def to_dict(self):
        return {
            "id": self.id,
            "name": self.name,
            "type": self.type,
            "status": self.status,
            "publicationDate": self.publicationDate,
            "description": self.description,
            "price": self.price,
            "uid": self.uid,
            "bannerUrl": self.bannerUrl
        }
