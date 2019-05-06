function handleKeys(event) {
    var message = "";

    if(event.key === "w" || event.key === "ArrowUp"){
        message = "UP";
    }else if(event.key === "a" || event.key === "ArrowLeft"){
        message = "LEFT";
    }else if(event.key === "s" || event.key === "ArrowDown"){
        message = "DOWN";
    }else if(event.key === "d" || event.key === "ArrowRight"){
        message = "RIGHT";
    }

    socket.emit("keystates", JSON.stringify(message));
}

function handleClick(event) {
    var clickPos = {
        "x" : event.pageX,
        "y" : event.pageY
    };
    socket.emit("click", JSON.stringify(clickPos));
}

document.addEventListener("keydown", function (event) {
    handleKeys(event, true);
});

document.addEventListener("click", function (event) {
    handleClick(event);
});