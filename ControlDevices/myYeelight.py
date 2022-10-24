from yeelight import Bulb, discover_bulbs
import time

class myYeelight:
    bulb = None
    on = None

    def __init__(self):
        # self.bulb = Bulb(self.getIP())
        self.bulb = Bulb("192.168.0.103")

    # Discovers the IP of the bulb
    # Probably fails if there are several bulbs on the network
    def getIP(self):
        result = discover_bulbs()
        print(result)
        return result[0]['ip']

    def TurnOn(self):
        self.bulb.turn_on()
        self.on = True

    def TurnOff(self):
        self.bulb.turn_off()
        self.on = False

    # TODO detect state -> E.g. is light is already on or off
    def State(self):
        return self.on

# Simple function to test if I have control of the light
def main():
    yeelight = myYeelight()

    # bulb = Bulb(getIP())
    # on = True

    while True:
        if yeelight.State():
            yeelight.TurnOff()
        else:
            yeelight.TurnOn()
        time.sleep(1)

if __name__ == "__main__":
    main()