class Review:
    def __init__(self, id, objectId, type, publicationDate, content, rating, uidPublisher):
        self.id = id
        self.objectId = objectId
        self.type = type
        self.publicationDate = publicationDate
        self.content = content
        self.rating = rating
        self.uidPublisher = uidPublisher

    def to_dict(self):
        return {
            "id": self.id,
            "objectId": self.objectId,
            "type": self.type,
            "publicationDate": self.publicationDate,
            "content": self.content,
            "rating": self.rating,
            "uidPublisher": self.uidPublisher
        }