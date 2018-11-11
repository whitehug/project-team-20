var isSetup = true;
var placedShips = 0;
var game;
var shipType;
var vertical;
var numSonar = 2
var sonarEnable = false;
var sonarOn = false;
var sonarReveal = false;

function addToLog(toAdd){
    let element = document.createElement('h2');
    element.innerHTML = toAdd;
    let scroll = document.getElementById("scrollbar");
    scroll.appendChild(element);
    scrollBottom();
}

function scrollBottom(){
    let scroll = document.getElementById("scrollbar");
    scroll.scrollTo(0,scroll.scrollHeight);
}

function sonarButtonSetup() {
    let container = document.getElementById("sonarContainer");
    while(container.firstChild) {
        container.removeChild(container.firstChild);
    }
    for(i=0; i<3; i++) {
        let sonar = document.createElement('button');
        sonar.classList.add('sonarButton');
        sonar.classList.add('sonarDisabled');
        container.appendChild(sonar);
    }
    if(numSonar > 0) {
        document.getElementsByClassName('sonarButton')[1].classList.add('chosen');
        document.getElementsByClassName('sonarButton')[1].classList.remove('sonarDisabled');
        document.getElementsByClassName('sonarButton')[1].innerHTML = "<i class='material-icons'>adjust</i>";
        document.getElementsByClassName('sonarButton')[1].id = "cancelSonarButton";
    }

    for(i=0; i<(2*numSonar); i++) {
        let sonar = document.getElementsByClassName('sonarButton')[i];
        sonar.classList.remove('sonarDisabled');
        sonar.addEventListener("click", function(e) {

                for(j=0; j<3; j++) {
                    document.getElementsByClassName('sonarButton')[j].classList.remove('chosen');
                }

                this.classList.add('chosen');
                sonarOn = true;
                let container = document.getElementById("sonarContainer");
                let cancelButton = container.lastChild.previousSibling;
                cancelButton.classList.remove('sonarDisabled');
                cancelButton.addEventListener("click", function(e) {
                    for(j=0; j<3; j++)
                        document.getElementsByClassName('sonarButton')[j].classList.remove('chosen');
                    this.classList.add('chosen');
                    sonarOn = false;
                });
            });
        sonar.innerHTML = "<i class='material-icons'>track_changes</i>";
        i++;
    }
}

function makeGrid(table, isPlayer) {
    for (i=0; i<11; i++) {
        let row = document.createElement('tr');
        if(i != 10) {
            for (j=-1; j<10; j++) {
                if(j != -1){
                    let column = document.createElement('td');
                    let content = document.createElement('div');
                    column.addEventListener("click", cellClick);
                    column.appendChild(content);
                    row.appendChild(column);
                }
                else{
                    let column = document.createElement('td');
                    let content = document.createElement('div');
                    column.classList.add("identifier");
                    content.classList.add("identifier");
                    content.innerHTML = i;
                    column.appendChild(content);
                    row.appendChild(column);
                }
            }
            table.appendChild(row);
        }
        else {
            for (j=-1; j<10; j++) {
                let column = document.createElement('td');
                let content = document.createElement('div');
                column.classList.add("identifier");
                content.classList.add("identifier");
                if(j != -1){
                    content.innerHTML = String.fromCharCode(j + 'A'.charCodeAt(0));
                }
                column.appendChild(content);
                row.appendChild(column);
            }
            table.appendChild(row);
        }
    }
}

function markHits(board, elementId, surrenderText) {
    board.attacks.forEach((attack, idx, array) => {
        let className;
        if (attack.result === "MISS")
            className = "miss";
        else if (attack.result === "HIT")
            className = "hit";
        else if (attack.result === "SUNK")
            className = "sink";
        else if (attack.result === "SURRENDER"){
            className = "surrender";
            addToLog(surrenderText);
            addToLog("<a href=\".\"><h2>Play Again?</h2></a>");
        }

        if(attack.result === "SUNK" && surrenderText === "You won the game")
            sonarEnable = true;
        if(sonarEnable)
            sonarButtonSetup();

        document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - '@'.charCodeAt(0)].classList.add(className);
        if(!sonarOn) {
            if(idx === array.length - 1 && elementId == "player"){
                addToLog("Opponent Attacked: " + (attack.location.row-1) + ", " + (attack.location.column));
                if(document.getElementsByClassName("lastPlaced").length != 0){
                    document.getElementsByClassName("lastPlaced")[0].classList.remove("lastPlaced");
                }
                document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - '@'.charCodeAt(0)].classList.add("lastPlaced");
            }
            if(idx === array.length - 1 && elementId == "opponent"){
                addToLog("You Attacked: " + (attack.location.row-1) + ", " + (attack.location.column));
                if(document.getElementsByClassName("lastLastPlaced").length != 0){
                    document.getElementsByClassName("lastLastPlaced")[0].classList.remove("lastLastPlaced");
                }
                document.getElementById("opponent").rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - '@'.charCodeAt(0)].classList.add("lastLastPlaced");
            }
        }
    });
}

function redrawGrid() {
    if(isSetup){
        document.getElementById("opContainer").style.display = "none";
        document.getElementById("battleLog").style.display = "none";
        document.getElementById("setupWindow1").style.display = "block";
        document.getElementById("setupWindow2").style.display = "block";
    }
    else{
        document.getElementById("opContainer").style.display = "block";
        document.getElementById("battleLog").style.display = "block";
        document.getElementById("setupWindow1").style.display = "none";
        document.getElementById("setupWindow2").style.display = "none";
    }

    Array.from(document.getElementById("opponent").childNodes).forEach((row) => row.remove());
    Array.from(document.getElementById("player").childNodes).forEach((row) => row.remove());
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);
    if (game === undefined) {
        return;
    }

    game.playersBoard.ships.forEach((ship) => ship.occupiedSquares.forEach((square) => {
        document.getElementById("player").rows[square.row-1].cells[square.column.charCodeAt(0) - '@'.charCodeAt(0)].classList.add("occupied");
    }));

    markHits(game.opponentsBoard, "opponent", "You won the game");
    markHits(game.playersBoard, "player", "You lost the game");
}

var oldListener;
function registerCellListener(f) {
    let el = document.getElementById("player");
    for (i=0; i<10; i++) {
        for (j=0; j<11; j++) {
            let cell = el.rows[i].cells[j];
            cell.removeEventListener("mouseover", oldListener);
            cell.removeEventListener("mouseout", oldListener);
            cell.addEventListener("mouseover", f);
            cell.addEventListener("mouseout", f);
        }
    }
    oldListener = f;
}

function cellClick() {
    let row = this.parentNode.rowIndex + 1;
    let col = String.fromCharCode(this.cellIndex + 64);
    console.log(col);
    if (isSetup) {
        sendXhr("POST", "/place", {game: game, shipType: shipType, x: row, y: col, isVertical: vertical}, function(data) {
            game = data;

            redrawGrid();
            placedShips++;

            if(shipType === "MINESWEEPER") {
                document.getElementById("place_minesweeper").classList.add("disabled");
                document.getElementById("place_minesweeper").disabled = true;
                document.getElementById("place_minesweeper_v").classList.add("disabled");
                document.getElementById("place_minesweeper_v").disabled = true;
                if(document.getElementById("place_destroyer").disabled == false) {
                    document.getElementById("place_destroyer").focus();
                    registerCellListener(place(3));
                    shipType = "DESTROYER";
                }
                else {
                    document.getElementById("place_battleship").focus();
                    registerCellListener(place(4));
                    shipType = "BATTLESHIP";
                }
            }
            else if(shipType === "DESTROYER") {
                document.getElementById("place_destroyer").classList.add("disabled");
                document.getElementById("place_destroyer").disabled = true;
                document.getElementById("place_destroyer_v").classList.add("disabled");
                document.getElementById("place_destroyer_v").disabled = true;

                 if(document.getElementById("place_battleship").disabled == false) {
                    document.getElementById("place_battleship").focus();
                    registerCellListener(place(4));
                    shipType = "BATTLESHIP";
                 }
                 else {
                    document.getElementById("place_minesweeper").focus();
                    registerCellListener(place(2));
                    shipType = "MINESWEEPER";
                 }
             }
            else {
                document.getElementById("place_battleship").classList.add("disabled");
                document.getElementById("place_battleship_v").classList.add("disabled");
                document.getElementById("place_battleship").disabled = true;
                document.getElementById("place_battleship_v").disabled = true;

                 if(document.getElementById("place_minesweeper").disabled == false) {
                    document.getElementById("place_minesweeper").focus();
                    registerCellListener(place(2));
                    shipType = "MINESWEEPER";
                 }
                 else {
                     document.getElementById("place_destroyer").focus();
                     registerCellListener(place(4));
                     shipType = "DESTROYER";
                }
            }
            if (placedShips == 3) {
                isSetup = false;
                redrawGrid();
                registerCellListener((e) => {});
            }
        });

    }

    else if(sonarOn) {
        numSonar--;
        redrawGrid();
        sonarOn = false;
        sonarFunc(row, col);
    }
    else {
        sendXhr("POST", "/attack", {game: game, x: row, y: col}, function(data) {
            game = data;
            redrawGrid();
        })
    }
}

function sendXhr(method, url, data, handler) {
    var req = new XMLHttpRequest();
    req.addEventListener("load", function(event) {
        if (req.status != 200) {
            addToLog("Not a valid square!")
            return;
        }
        handler(JSON.parse(req.responseText));
    });
    req.open(method, url);
    req.setRequestHeader("Content-Type", "application/json");
    req.send(JSON.stringify(data));
}

function sonarFunc(row, col) {
    if(numSonar == 1)
        addToLog("You Used Your First Sonar Pulse");
    else
        addToLog("You Used Your Last Sonar Pulse");
    var colIM = col.charCodeAt(0) - 1;
    var colIP = col.charCodeAt(0) + 1;
    sonarFunc2(row + 1, col, false);
    sonarFunc2(row + 1, String.fromCharCode(colIM), false);
    sonarFunc2(row + 1, String.fromCharCode(colIP), false);
    sonarFunc2(row + 2, col, false);
    sonarFunc2(row - 1, col, false);
    sonarFunc2(row - 1, String.fromCharCode(colIM), false);
    sonarFunc2(row - 1, String.fromCharCode(colIP), false);
    sonarFunc2(row - 2, col, false);
    sonarFunc2(row, String.fromCharCode(colIM), false);
    sonarFunc2(row, String.fromCharCode(colIP), false);
    colIM--;
    colIP++;
    sonarFunc2(row, String.fromCharCode(colIM), false);
    sonarFunc2(row, String.fromCharCode(colIP), false);
    sonarFunc2(row, col, true);


}
function sonarFunc2(x, y, print) {
    sonarReveal = false;
    console.log(x);
    console.log(y);
    if(!(x > 0 && x < 11 && y > '@' && y < 'K'))
        return;
    console.log(x);
    console.log(y);
    let table = document.getElementById("opponent");
    sendXhr("POST", "/sonar", {game: game, x: x, y: y}, function(data) {
        game = data;
        console.log("nuts");
        let className;
        if(game.opponentsBoard.sonarList) {
            sonarReveal = true;
            className = "revealed";
        }
        else
            className = "empty";
        if(print) {
            if(sonarReveal)
                addToLog("Looks Like You Found Something!")
            else
                addToLog("Nothing But Some Fish");
        }
        let yInt = y.charCodeAt(0) -'@'.charCodeAt(0);
        if(!(document.getElementById('opponent').rows[x-1].cells[yInt].classList.contains("miss")) && !(document.getElementById('opponent').rows[x-1].cells[yInt].classList.contains("hit")) && !(document.getElementById('opponent').rows[x-1].cells[yInt].classList.contains("sink")))
            document.getElementById("opponent").rows[x-1].cells[yInt].classList.add(className);
    })
}

function place(size) {
    return function() {
        let row = this.parentNode.rowIndex;
        let col = this.cellIndex;
        let table = document.getElementById("player");
        for (let i=0; i<size && (vertical ? 0 <= i + row && i + row < 10: 0 < i + col && i + col < 11) && i + col != 0; i++) {
            let cell;
            if(vertical) {
                let tableRow = table.rows[row+i];
                if (tableRow === undefined) {
                    // ship is over the edge; let the back end deal with it
                    break;
                }
                cell = tableRow.cells[col];
            } else {
                cell = table.rows[row].cells[col+i];
            }
            if (cell === undefined) {
                // ship is over the edge; let the back end deal with it
                break;
            }
            cell.classList.toggle("placed");
        }
    }
}

function initGame() {
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);
    document.getElementById("place_minesweeper").addEventListener("click", function(e) {
        shipType = "MINESWEEPER";
        registerCellListener(place(2));
        vertical = false;
    });
    document.getElementById("place_destroyer").addEventListener("click", function(e) {
        shipType = "DESTROYER";
       registerCellListener(place(3));
       vertical = false;
    });
    document.getElementById("place_battleship").addEventListener("click", function(e) {
        shipType = "BATTLESHIP";
        registerCellListener(place(4));
        vertical = false;
    });
    document.getElementById("place_minesweeper_v").addEventListener("click", function(e) {
        shipType = "MINESWEEPER";
        registerCellListener(place(2));
        vertical = true;
    });
    document.getElementById("place_destroyer_v").addEventListener("click", function(e) {
        shipType = "DESTROYER";
        registerCellListener(place(3));
        vertical = true;
    });
    document.getElementById("place_battleship_v").addEventListener("click", function(e) {
        shipType = "BATTLESHIP";
        registerCellListener(place(4));
        vertical = true;
    });
    sendXhr("GET", "/game", {}, function(data) {
        game = data;
    });

    redrawGrid();
};