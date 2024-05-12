class Product:
    def __init__(self, id, name, description, price, location, companyName, status, bannerUrls, webUrl):
        self.id = id
        self.name = name
        self.description = description
        self.price = price
        self.location = location
        self.companyName = companyName
        self.status = status
        self.bannerUrls = bannerUrls
        self.webUrl = webUrl

    def to_dict(self):
        return {
            "id": self.id,
            "name": self.name,
            "description": self.description,
            "price": self.price,
            "location": self.location,
            "companyName": self.companyName,
            "status": self.status,
            "bannerUrls": self.bannerUrls,
            "webUrl": self.webUrl
        }
