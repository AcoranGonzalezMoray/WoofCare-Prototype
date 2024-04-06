class Dog:
    def __init__(self, id, breed, size, age, weight):
        self.id = id
        self.breed = breed
        self.size = size
        self.weight = weight
        self.age = age

    def to_dict(self):
        return {
            "id": self.id,
            "breed": self.breed,
            "size": self.size,
            "weight": self.weight,
            "age": self.age
        }
