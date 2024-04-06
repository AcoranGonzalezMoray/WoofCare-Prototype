class Message:
    def __init__(self, id, uidReceiver, uidSender, type, message, sentDate, serviceId):
        self.id = id
        self.uidReceiver = uidReceiver
        self.uidSender = uidSender
        self.type = type
        self.message = message
        self.sentDate = sentDate
        self.serviceId = serviceId

    def to_dict(self):
        return {
            "id": self.id,
            "uidReceiver": self.uidReceiver,
            "uidSender": self.uidSender,
            "type": self.type,
            "message": self.message,
            "sentDate": self.sentDate,
            "serviceId": self.serviceId
        }
