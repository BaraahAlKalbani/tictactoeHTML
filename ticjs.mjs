// Set up initial variables and game state
let board = ['', '', '', '', '', '', '', '', '']; // an array representing the game board with empty strings
let currentPlayer = 'X'; // a variable to keep track of the current player
let gameover = false; // a boolean variable to keep track of whether the game is over
let player2Bot = false;
let selectedBotDifficulty = "easy";
let isAuthenticated = false; // a variable to keep track of whether the user is authenticated
let username = null;
let password = null;
let credentials ;
const playingMode = document.getElementById('playMode');
const vsBot = document.getElementById('bot');
const botDifficulty = document.getElementById("botDifficulty");

vsBot.addEventListener('click',() => {
  setBotAsPlayer2();
  
  if(player2Bot==true)
  {
    botDifficulty.style.display = "block";
  }
  else{
    botDifficulty.style.display = "none";
  }
});

botDifficulty.addEventListener("change",() =>{
  setBotAsPlayer2();
  selectedBotDifficulty = selectedBotDifficulty=== "easy" ? "hard":"easy";
  console.log(selectedBotDifficulty);
  botDifficulty.style.display = "block";
});
// Function to handle cell click events
function handleCellClick(event) {
  
    const cell = event.target; // get the clicked cell
    const index = cell.id; // get the index of the clicked cell
    console.log(currentPlayer+" Move Index:"+index);
    // Check if cell is already occupied or game is over
    if (board[index] !== '' || gameover) { // if the cell is already occupied or the game is over, return without doing anything
      return;
    }
    
    // Update board and cell content
    board[index] = currentPlayer; // set the board array at the clicked index to the current player's symbol ('X' or 'O')
    cell.classList.add(currentPlayer); // add the current player's symbol as a class to the clicked cell
    cell.innerText = currentPlayer; // set the clicked cell's content to the current player's symbol
    
    // Check for winning conditions or draw
    if (checkForWin()) { // check if there is a winning combination on the board
      console.log(currentPlayer+" Won");
      endGame(false); // call the endGame function with false to indicate there was a winner, not a draw
    } else if (checkForDraw()) { // check if the game is a draw
      console.log(" its a DRAW!!!");
      endGame(true); // call the endGame function with true to indicate there was a draw
    } else {
      // Switch players and update message
      currentPlayer = currentPlayer == 'X' ? 'O' : 'X'; // switch the current player
      updateMessage(); // update the message element with the new current player
      
      // Check if the next move is for the bot player
      if (player2Bot && currentPlayer=='O') {   

        setTimeout(() => {
          if(username==null || password== null)
          {
            let username = "admin";
            let password = "WbaraahW";
           credentials = btoa(`${username}:${password}`);
          }
          let url = `http://localhost:8080/api/bot/make-move`;
          if(selectedBotDifficulty=="hard"){
            url = `http://localhost:8080/api/bot/make-move/hard`;
          }
          let reqConfig = {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              "Authorization": `Basic ${credentials}`
            },
            
            body: JSON.stringify({
              board
            })
          };
        
          fetch(url, reqConfig)
            .then(response => response.json())
            .then(data => {
              // The updated board is returned in the response data
              let botIndex = data.botMove;
              // Update the board on the webpage
              console.log("Bot Move Index:"+botIndex);
              board[botIndex]= currentPlayer;
              document.getElementById(botIndex).innerText = currentPlayer;
              // Check for winning conditions or draw after bot makes a move
              if (checkForWin()) { 
                console.log("Bot Won");
                endGame(false); 
              } else if (checkForDraw()) { 
                console.log(" its a DRAW!!!");
                endGame(true); 
              } else {
                currentPlayer = currentPlayer == 'X' ? 'O' : 'X'; 
                updateMessage(); 
              }
            }).catch(error => console.log(error));
          }, 0);  
        }
      }
    }
// Function to check for winning conditions
function checkForWin() {
  const winningCombos = [ // an array of all possible winning combinations on the board
    [0, 1, 2], [3, 4, 5], [6, 7, 8], // rows
    [0, 3, 6], [1, 4, 7], [2, 5, 8], // columns
    [0, 4, 8], [2, 4, 6] // diagonals
  ];
  
  for (let combo of winningCombos) { // iterate through each winning combination
    const [a, b, c] = combo; // destructure the combination into individual variables
    if (board[a] != '' && board[a] == board[b] && board[b] == board[c]) { // if all three cells in the combination have the same symbol (either 'X' or 'O'), then there is a winning combination
      return true; // return true to indicate there is a winning combination
    }
  }
  
  return false; // if no winning combination was found, return false
}
// Function to check for draw
function checkForDraw() {
  return board.every(cell => cell !== ''); // check if every cell on the board is occupied (i.e., not an empty string)
}
// Function to end the game and update message
function endGame(draw) {
  gameover = true; // set the gameover variable to true
  const messageElement = document.getElementById('message'); // get the message element
  messageElement.innerText = draw ? "It's a draw!" : `${currentPlayer} wins!`; // set the message text to  draw/ win 
}

// Function to update the message displayed on the webpage with the current player's turn
function updateMessage() {
    // Get the message element from the webpage
    const messageElement = document.getElementById('message');
    // Update the message with the current player's turn using template literals
    messageElement.innerText = `It's ${currentPlayer}'s turn!`;    
}

    // Function to reset the game by clearing the board, resetting the player to 'X', and setting the gameover flag to false
    function restartGame() {
      botDifficulty.style.display = "none";

    // Reset the board by setting each cell to an empty string
    board = ['', '', '', '', '', '', '', '', ''];
    // Reset the current player to 'X'
    currentPlayer = 'X';
    // Reset the gameover flag to false
    gameover = false;
    //Reset the bot player
    player2Bot = false;
    playingMode.innerText = 'Player vs Player';
    // Get all the cell elements from the webpage and reset each one
    const cells = document.querySelectorAll('.cell');
    cells.forEach(cell => {
    // Remove the 'X' and 'O' classes from each cell
    cell.classList.remove('X', 'O');
    // Clear the text content of each cell
    cell.innerText = '';
    });
    // Update the message to show the current player's turn
    updateMessage();
    }

    function setBotAsPlayer2()
    {
        restartGame();
        playingMode.innerText = 'Player vs Bot';
        player2Bot = true;
    }
    // Add event listeners to each cell element and the restart button
    const cells = document.querySelectorAll('.cell');
    cells.forEach(cell => {
    // Add a click event listener to each cell element that will call the handleCellClick function
    cell.addEventListener('click', handleCellClick);
    });
    const restartButton = document.getElementById('restart');
    // Add a click event listener to the restart button that will call the restartGame function
    restartButton.addEventListener('click', restartGame);
    // Initialize the message displayed on the webpage with the current player's turn
    updateMessage();