from yeelight import Bulb, discover_bulbs
import time

class yeelight:
    bulb = None

    def __init__(self):
        self.bulb = Bulb(getIP())

    # Discovers the IP of the bulb
    # Probably fails if there are several bulbs on the network
    def getIP():
        result = discover_bulbs()
        return result[0]['ip']

    def TurnOn(self):
        self.bulb.turn_on()

    def TurnOff(self):
        self.bulb.turn_off()

    # TODO detect state -> E.g. is light is already on or off
    def State():
        return

# # Simple function to test if I have control of the light
# def main():
#     bulb = Bulb(getIP())
#     on = True

#     while True:
#         if on:
#             bulb.turn_off()
#         else:
#             bulb.turn_on()
#         on = not on
#         time.sleep(1)

# if __name__ == "__main__":
#     main()