from tkinter import *
import tkinter
from electricSocket import electricSocket
from myYeelight import myYeelight
import sys

def main(cla):
    # declare the window
    window = Tk()
    # set window title
    window.title("Control Devices UI")
    # set window width and height
    window.configure(width=500, height=300)
    # set window background color
    window.configure(bg='lightgray')

    email = sys.argv[1]
    password = sys.argv[2]

    def toggleElectricSocket():
        if socket.State():
            socket.TurnOff()
        else:
            socket.TurnOn()

    socket = electricSocket(email, password)
    toggleElectricSocketButton = Button(window, text = "Toggle Socket", bd = '5', command = toggleElectricSocket)
    toggleElectricSocketButton.pack(side = 'top')

    def toggleYeelight():
        if yeeLight.State():
            yeeLight.TurnOff()
        else:
            yeeLight.TurnOn()

    yeeLight = myYeelight()
    toggleYeelightButton = Button(window, text = "Toggle Bulb", bd= '5', command = toggleYeelight)
    toggleYeelightButton.pack(side = 'top')

    window.mainloop()

if __name__ == "__main__":
    main(sys.argv[1:])