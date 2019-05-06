import json
import socket
from threading import Thread

from flask import Flask, send_from_directory, request
from flask_socketio import SocketIO

import eventlet

eventlet.monkey_patch()

app = Flask(__name__)
socket_server = SocketIO(app)

scala_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
scala_socket.connect(('localhost', 8000))

delimiter = "~"


def listen_to_scala(the_socket):
    buffer = ""
    while True:
        buffer += the_socket.recv(1024).decode()
        while delimiter in buffer:
            message = buffer[:buffer.find(delimiter)]
            buffer = buffer[buffer.find(delimiter) + 1:]
            get_from_scala(message)


def get_from_scala(data):
    socket_server.emit('gameState', data, broadcast=True)


def send_to_scala(data):
    scala_socket.sendall((json.dumps(data) + delimiter).encode())


Thread(target=listen_to_scala, args=(scala_socket,)).start()


@socket_server.on('connect')
def got_message():
    print(request.sid + " connected")
    message = {"username": request.sid, "action": "connected"}
    socket_server.emit('connect', request.sid)
    send_to_scala(message)


@socket_server.on('disconnect')
def disconnect():
    print(request.sid + " disconnected")
    message = {"username": request.sid, "action": "disconnected"}
    send_to_scala(message)


@socket_server.on('keystates')
def key_state(GUIkeyState):
    print(GUIkeyState)
    x = 0.0
    if GUIkeyState == "LEFT" or GUIkeyState == "A":
        x = -1
    elif GUIkeyState == "RIGHT" or GUIkeyState == "D":
        x = 1
    y = 0.0
    if GUIkeyState == "UP" or GUIkeyState == "W":
        y = -1
    elif GUIkeyState == "DOWN" or GUIkeyState == "D":
        y = 1
    print(x)
    print(y)
    message = {"username": request.sid, "action": "move", "x": x, "y": y}
    send_to_scala(message)


@socket_server.on("click")
def click(jsonClickPos):
    print(request.sid + " attacked")
    clickPos = json.loads(jsonClickPos)
    print(clickPos)
    message = {"username": request.sid, "action": "click", "x": clickPos["x"], "y": clickPos["y"]}
    print(message)
    send_to_scala(message)


@app.route('/')
def index():
    return send_from_directory('static', 'index.html')


@app.route('/<path:filename>')
def static_files(filename):
    return send_from_directory('static', filename)


print("Listening on port 8080")
socket_server.run(app, port=8080)
