package battleship;


public class Battlefield {
    private final String[][] battlefield = new String[11][11];
    private final String[] allOccupiedCells = new String[83]; // [F3, F4, F5, F6, F7, E2, F2, F8, E3, E4, E5, E6, E7, G3, G4, G5, G6, G7, G2, E8, G8, null, null, null, null, null, null ...]
    private int cntAllOccupiedCells = 0;
    private final String[] cellsOccupiedByShips = new String[17]; // [F3, F4, F5, F6, F7, null, null, null, null, null, null, null, null, null, null, null, null]
    private int cntCellsOccupiedByShips = 0;

    public String[] getCellsOccupiedByShips() {
        return cellsOccupiedByShips;
    }

    public String[][] getBattlefield() {
        return battlefield;
    }

    void fillFogOfWar() {
        char nameOfRow = 64;
        for (int i = 0; i < battlefield.length; i++) {
            for (int j = 0; j < battlefield[i].length; j++) {
                if (i == 0 && j == 0) {
                    battlefield[i][j] = "  ";
                } else if (i != 0 && j != 0) {
                    battlefield[i][j] = "~ ";
                } else if (i != 0) {
                    battlefield[i][j] = (char) (nameOfRow + i) + " ";
                } else {
                    battlefield[i][j] = j + " ";
                }
            }
        }
    }

    void print() {
        for (String[] strings : battlefield) {
            for (String element : strings) {
                System.out.print(element);
            }
            System.out.println();
        }
    }

    void drawCells(Ship ship) {
        for (int i = 0; i < ship.getLocationForUsers().length; i++) {
            int x = Integer.parseInt(ship.getLocationFor2DimArray()[i * 2]);
            int y = Integer.parseInt(ship.getLocationFor2DimArray()[i * 2 + 1]);
            battlefield[x][y] = "O ";
        }
    }

    void fillOccupiedCells(String[] shipLocation) {
        int firstDigit = Integer.parseInt(shipLocation[0].substring(1));
        int lastDigit = Integer.parseInt(shipLocation[shipLocation.length - 1].substring(1));
        char firstCharacter = shipLocation[0].charAt(0);
        char lastCharacter = shipLocation[shipLocation.length - 1].charAt(0);

        for (int i = 0; i < shipLocation.length; i++) {
            allOccupiedCells[i] = shipLocation[i];
            cellsOccupiedByShips[cntCellsOccupiedByShips] = shipLocation[i];
            cntAllOccupiedCells++;
            cntCellsOccupiedByShips++;
        }
        if (firstCharacter != 'A' && firstDigit != 1) {
            allOccupiedCells[cntAllOccupiedCells] = (char) ((int) firstCharacter - 1) + (firstDigit - 1 + "");
            cntAllOccupiedCells++;
        }

        if (shipLocation[0].substring(0, 1).equals(shipLocation[1].substring(0, 1))) {
            if (firstDigit != 1) {
                allOccupiedCells[cntAllOccupiedCells] = shipLocation[0].substring(0, 1) + (firstDigit - 1);
                cntAllOccupiedCells++;
            }
            if (lastDigit != 10) {
                allOccupiedCells[cntAllOccupiedCells] = shipLocation[0].substring(0, 1) + (lastDigit + 1);
                cntAllOccupiedCells++;
            }
            if (firstCharacter != 'A') {
                int ascii = (int) firstCharacter;
                for (String s : shipLocation) {
                    allOccupiedCells[cntAllOccupiedCells] = (char) (ascii - 1) + s.substring(1);
                    cntAllOccupiedCells++;
                }
            }
            if (firstCharacter != 'J') {
                for (String s : shipLocation) {
                    allOccupiedCells[cntAllOccupiedCells] = (char) ((int) firstCharacter + 1) + s.substring(1);
                    cntAllOccupiedCells++;
                }
            }
            if (firstCharacter != 'J' && firstDigit != 1) {
                allOccupiedCells[cntAllOccupiedCells] = (char) ((int) firstCharacter + 1) + (firstDigit - 1 + "");
                cntAllOccupiedCells++;
            }
            if (firstCharacter != 'A' && lastDigit != 10) {
                allOccupiedCells[cntAllOccupiedCells] = (char) ((int) firstCharacter - 1) + (lastDigit + 1 + "");
                cntAllOccupiedCells++;
            }
            if (firstCharacter != 'J' && lastDigit != 10) {
                allOccupiedCells[cntAllOccupiedCells] = (char) ((int) firstCharacter + 1) + (lastDigit + 1 + "");
                cntAllOccupiedCells++;
            }
        } else {
            if (firstCharacter != 'A') {
                allOccupiedCells[cntAllOccupiedCells] = (char) ((int) firstCharacter - 1) + shipLocation[0].substring(1);
                cntAllOccupiedCells++;
            }
            if (lastCharacter != 'J') {
                allOccupiedCells[cntAllOccupiedCells] = (char) ((int) lastCharacter + 1) + shipLocation[0].substring(1);
                cntAllOccupiedCells++;
            }
            if (firstDigit != 1) {
                for (String s : shipLocation) {
                    allOccupiedCells[cntAllOccupiedCells] = s.substring(0, 1) + (firstDigit - 1);
                    cntAllOccupiedCells++;
                }
            }
            if (lastDigit != 10) {
                for (String s : shipLocation) {
                    allOccupiedCells[cntAllOccupiedCells] = s.substring(0, 1) + (firstDigit + 1);
                    cntAllOccupiedCells++;
                }
            }
            if (lastCharacter != 'J' && firstDigit != 1) {
                allOccupiedCells[cntAllOccupiedCells] = (char) ((int) lastCharacter + 1) + (firstDigit - 1 + "");
                cntAllOccupiedCells++;
            }
            if (firstCharacter != 'A' && firstDigit != 10) {
                allOccupiedCells[cntAllOccupiedCells] = (char) ((int) firstCharacter - 1) + (lastDigit + 1 + "");
                cntAllOccupiedCells++;
            }
            if (lastCharacter != 'J' && firstDigit != 10) {
                allOccupiedCells[cntAllOccupiedCells] = (char) ((int) lastCharacter + 1) + (lastDigit + 1 + "");
                cntAllOccupiedCells++;
            }
        }
    }

    boolean isNotOccupiedCells(String[] shipLocation) {
        for (String s : shipLocation) {
            for (String allOccupiedCell : allOccupiedCells) {
                if (s.equals(allOccupiedCell)) {
                    return false;
                } else if (allOccupiedCell == null) {
                    break;
                }
            }
        }
        return true;
    }
}

