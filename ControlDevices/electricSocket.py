from PyP100 import PyP100
import sys
import time

class electricSocket:
    state = None
    
    # Discover the elctric socket
    def __init__(self, email, password):
        self.p100 = PyP100.P100("192.168.0.102", email, password) #Creating a P100 plug object
        self.p100.handshake() #Creates the cookies required for further methods
        self.p100.login() #Sends credentials to the plug and creates AES Key and IV for further methods
        return

    # Turn on the electric socket
    def TurnOn(self):
        self.p100.turnOn()
        self.state = True

    # Turn off the elctric socket
    def TurnOff(self):
        self.p100.turnOff()
        self.state = False

    # Detec the state of the electric socket
    def State(self):
        return self.state

def main(cla):
    email = cla[0]
    password = cla[1]
    socket = electricSocket(email, password)

    while(True):
        if socket.State():
            socket.TurnOff()
        else:
            socket.TurnOn()
        
        time.sleep(1)


if __name__ == "__main__":
    main(sys.argv[1:])