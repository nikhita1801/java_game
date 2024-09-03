package project.tictactoe;

import project.tictactoe.User_interface.JButtons;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe {

    public static final String PLAYER_ONE_SYMBOL = "x";
    public static final String PLAYER_TWO_SYMBOL = "o";

    private GUI gui;
    private boolean ki;
    private boolean playerOneNow;
    private boolean gameInProgress;
    private String[][] gamefield;
    private JButtons[][] gamefieldButtons;
    private int round;

    public static void main(String[] args) {
        new TicTacToe();
    }

    public TicTacToe() {
        makeGuiReady();
        resetGame();

        gamefieldButtons = new JButtons[3][3];
        gamefieldButtons[0][0] = GUI.JButtons.GAME_TOP_LEFT;
        gamefieldButtons[0][1] = GUI.JButtons.GAME_TOP_CENTER;
        gamefieldButtons[0][2] = GUI.JButtons.GAME_TOP_RIGHT;
        gamefieldButtons[1][0] = GUI.JButtons.GAME_MIDDLE_LEFT;
        gamefieldButtons[1][1] = GUI.JButtons.GAME_MIDDLE_CENTER;
        gamefieldButtons[1][2] = GUI.JButtons.GAME_MIDDLE_RIGHT;
        gamefieldButtons[2][0] = GUI.JButtons.GAME_BOTTOM_LEFT;
        gamefieldButtons[2][1] = GUI.JButtons.GAME_BOTTOM_CENTER;
        gamefieldButtons[2][2] = GUI.JButtons.GAME_BOTTOM_RIGHT;
    }

    private void makeGuiReady() {
        gui = new GUI("Tic Tac Toe - Philipp Schuster (@phip1611 | 2015)");
        gui.buildGUI();
        gui.show();
        setActionListeners();
    }

    private void startNewGame() {
        if (gameInProgress) {
            return;
        } else {
            gameInProgress = true;
        }
        int playerCount;
        playerCount = gui.getNumberOfPlayers();

        if (playerCount == 0) {
            System.err.println("Es wurde noch nicht die Spieleranzahl ausgewählt!");
            return;
        } else if (playerCount == 1) {
            this.ki = true;
        } else {
            this.ki = false;
        }
    }

    private void resetGame() {
        gameInProgress = false;
        playerOneNow = true;
        round = 0;
        gamefield = new String[3][3];
    }

    private void nextTurn(int x, int y, JButtons button) {
        if (!gameInProgress) {
            return;
        }

        if (gui.canUseThisButton(button)) {
            round++;
            if (playerOneNow) {
                gui.setGamefieldButtonValue(button, PLAYER_ONE_SYMBOL);
                gamefield[x][y] = PLAYER_ONE_SYMBOL;
                checkForWinner();
                if (ki) {
                    nextKITurn();
                }
            } else {
                gui.setGamefieldButtonValue(button, PLAYER_TWO_SYMBOL);
                gamefield[x][y] = PLAYER_TWO_SYMBOL;
            }
            checkForWinner();
            nextPlayer();
        }
    }

    private void nextKITurn() {
        boolean stop = false;
        int a, b;

        if (!gameInProgress) {
            return;
        }

        if (round < 9) {
            round++;
            do {
                a = (int) Math.round((Math.random() * 2));
                b = (int) Math.round((Math.random() * 2));
                if (gamefield[a][b] == null) {
                    stop = true;
                    gamefield[a][b] = PLAYER_TWO_SYMBOL;
                    gui.setGamefieldButtonValue(gamefieldButtons[a][b], PLAYER_TWO_SYMBOL);
                }
            } while (!stop);
        }
        checkForWinner();
        nextPlayer();
    }

    private void setActionListeners() {
        gui.addActionListener(GUI.JButtons.RESET, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
                gui.resetGameField();
                gui.setUserinformationText("Bitte Spieleranzahl wählen!");
                gui.setGamefieldEnabled(false);
            }
        });
        gui.addActionListener(GUI.JButtons.START, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });

        gui.addActionListener(GUI.JButtons.GAME_TOP_LEFT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextTurn(0, 0, GUI.JButtons.GAME_TOP_LEFT);
            }
        });
        gui.addActionListener(GUI.JButtons.GAME_TOP_CENTER, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextTurn(0, 1, GUI.JButtons.GAME_TOP_CENTER);
            }
        });
        gui.addActionListener(GUI.JButtons.GAME_TOP_RIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextTurn(0, 2, GUI.JButtons.GAME_TOP_RIGHT);
            }
        });

        gui.addActionListener(GUI.JButtons.GAME_MIDDLE_LEFT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextTurn(1, 0, GUI.JButtons.GAME_MIDDLE_LEFT);
            }
        });
        gui.addActionListener(GUI.JButtons.GAME_MIDDLE_CENTER, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextTurn(1, 1, GUI.JButtons.GAME_MIDDLE_CENTER);
            }
        });
        gui.addActionListener(GUI.JButtons.GAME_MIDDLE_RIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextTurn(1, 2, GUI.JButtons.GAME_MIDDLE_RIGHT);
            }
        });

        gui.addActionListener(GUI.JButtons.GAME_BOTTOM_LEFT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextTurn(2, 0, GUI.JButtons.GAME_BOTTOM_LEFT);
            }
        });
        gui.addActionListener(GUI.JButtons.GAME_BOTTOM_CENTER, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextTurn(2, 1, GUI.JButtons.GAME_BOTTOM_CENTER);
            }
        });
        gui.addActionListener(GUI.JButtons.GAME_BOTTOM_RIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextTurn(2, 2, GUI.JButtons.GAME_BOTTOM_RIGHT);
            }
        });
    }

    private void nextPlayer() {
        this.playerOneNow = !this.playerOneNow;
    }

    private void checkForWinner() {
        if (round < 5) {
            return;
        } else if (gamefield[0][0] == PLAYER_ONE_SYMBOL
                && gamefield[0][1] == PLAYER_ONE_SYMBOL
                && gamefield[0][2] == PLAYER_ONE_SYMBOL
                || gamefield[1][0] == PLAYER_ONE_SYMBOL
                && gamefield[1][1] == PLAYER_ONE_SYMBOL
                && gamefield[1][2] == PLAYER_ONE_SYMBOL
                || gamefield[2][0] == PLAYER_ONE_SYMBOL
                && gamefield[2][1] == PLAYER_ONE_SYMBOL
                && gamefield[2][2] == PLAYER_ONE_SYMBOL

                || gamefield[0][0] == PLAYER_ONE_SYMBOL
                && gamefield[1][0] == PLAYER_ONE_SYMBOL
                && gamefield[2][0] == PLAYER_ONE_SYMBOL
                || gamefield[0][1] == PLAYER_ONE_SYMBOL
                && gamefield[1][1] == PLAYER_ONE_SYMBOL
                && gamefield[2][1] == PLAYER_ONE_SYMBOL
                || gamefield[0][2] == PLAYER_ONE_SYMBOL
                && gamefield[1][2] == PLAYER_ONE_SYMBOL
                && gamefield[2][2] == PLAYER_ONE_SYMBOL

                || gamefield[0][0] == PLAYER_ONE_SYMBOL
                && gamefield[1][1] == PLAYER_ONE_SYMBOL
                && gamefield[2][2] == PLAYER_ONE_SYMBOL
                || gamefield[0][2] == PLAYER_ONE_SYMBOL
                && gamefield[1][1] == PLAYER_ONE_SYMBOL
                && gamefield[2][0] == PLAYER_ONE_SYMBOL) {

            gameInProgress = false;
            gui.setGamefieldEnabled(false);
            gui.setUserinformationText("Spieler 1 hat gewonnen!");
        } else if (gamefield[0][0] == PLAYER_TWO_SYMBOL
                && gamefield[0][1] == PLAYER_TWO_SYMBOL
                && gamefield[0][2] == PLAYER_TWO_SYMBOL
                || gamefield[1][0] == PLAYER_TWO_SYMBOL
                && gamefield[1][1] == PLAYER_TWO_SYMBOL
                && gamefield[1][2] == PLAYER_TWO_SYMBOL
                || gamefield[2][0] == PLAYER_TWO_SYMBOL
                && gamefield[2][1] == PLAYER_TWO_SYMBOL
                && gamefield[2][2] == PLAYER_TWO_SYMBOL

                || gamefield[0][0] == PLAYER_TWO_SYMBOL
                && gamefield[1][0] == PLAYER_TWO_SYMBOL
                && gamefield[2][0] == PLAYER_TWO_SYMBOL
                || gamefield[0][1] == PLAYER_TWO_SYMBOL
                && gamefield[1][1] == PLAYER_TWO_SYMBOL
                && gamefield[2][1] == PLAYER_TWO_SYMBOL
                || gamefield[0][2] == PLAYER_TWO_SYMBOL
                && gamefield[1][2] == PLAYER_TWO_SYMBOL
                && gamefield[2][2] == PLAYER_TWO_SYMBOL

                || gamefield[0][0] == PLAYER_TWO_SYMBOL
                && gamefield[1][1] == PLAYER_TWO_SYMBOL
                && gamefield[2][2] == PLAYER_TWO_SYMBOL
                || gamefield[0][2] == PLAYER_TWO_SYMBOL
                && gamefield[1][1] == PLAYER_TWO_SYMBOL
                && gamefield[2][0] == PLAYER_TWO_SYMBOL) {

            gameInProgress = false;
            gui.setGamefieldEnabled(false);
            gui.setUserinformationText("Spieler 2 hat gewonnen!");

        } else if (round == 9) {
            gameInProgress = false;
            gui.setGamefieldEnabled(false);
            gui.setUserinformationText("Unentschieden.");
        }
    }
}
