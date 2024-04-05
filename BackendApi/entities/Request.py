class Request:
    def __init__(self, id, uidReceiver, uidSender, serviceId, status, creationDate):
        self.id = id
        self.uidReceiver = uidReceiver
        self.uidSender = uidSender
        self.serviceId = serviceId
        self.status = status
        self.creationDate = creationDate

    def to_dict(self):
        return {
            "id": self.id,
            "uidReceiver": self.uidReceiver,
            "uidSender": self.uidSender,
            "serviceId": self.serviceId,
            "status": self.status,
            "creationDate": self.creationDate
        }
