package battleship;

import java.util.Scanner;

public enum User {
    USER1("Player 1"),
    USER2("Player 2");

    private String name;

    User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    Scanner scanner = new Scanner(System.in);

    private Ship[] listOfShips = new Ship[5];

    public Ship[] getListOfShips() {
        return listOfShips;
    }

    public void setListOfShips(Ship[] listOfShips) {
        this.listOfShips = listOfShips;
    }


    //////////////////// User take a shot ////////////////////
    private String getUserInputForShot() {
        System.out.print("> ");
        return scanner.next().toUpperCase();
    }

    /// user1.takeAShot(user2, user2PersonalField, user1RivalField);
    void takeAShot(User user1, User user2, Battlefield user2PersonalField, Battlefield user1PersonalField, Battlefield user1RivalField) { // user1.takeAshot; originalRivalField = user2PersonalField; ownRivalField = user1RivalField;
        user1RivalField.print();
        System.out.println("---------------------");
        user1PersonalField.print();
        System.out.println("\n" + user1.getName() + ", it's your turn:\n");
        String coordinate = getUserInputForShot();
        try {
            if (isShotInsideField(coordinate)) {
                drawCell(user2, user2PersonalField, user1RivalField, coordinate);
            } else {
                System.out.println("\nError! You entered the wrong coordinates!");
                user1.passMove();
            }
        } catch (NumberFormatException e) {
            System.out.println("\nError! You entered the wrong coordinates! Try again");
            takeAShot(user1, user2, user2PersonalField, user1PersonalField, user1RivalField);
        }

    }

    ///user1.takeAShot
    private void drawCell(User user2, Battlefield user2PersonalField, Battlefield user1RivalField, String coordinate) {
        int x = Integer.parseInt(replaceLetterCoordinates(coordinate)[0]);
        int y = Integer.parseInt(coordinate.substring(1));
        if (isHit(user2PersonalField, coordinate)) {
            user2PersonalField.getBattlefield()[x][y] = "X ";
            user1RivalField.getBattlefield()[x][y] = "X ";
            replaceCoordinateInDamagedShip(user2, coordinate);
            if (isSunkenShip(user2.getListOfShips())) {
                if (isEveryShipSunken(user2.getListOfShips())) {
                    //user1RivalField.print();
                    System.out.println("\nYou sank the last ship. You won. Congratulations!");
                } else {
                    //user1RivalField.print();
                    System.out.println("\nYou sank a ship!");
                    user2.passMove();
                    //takeAShot(user2, user2PersonalField, user1RivalField);
                }
            } else {
                //user1RivalField.print();
                System.out.println("\nYou hit a ship!");
                user2.passMove();
                //takeAShot(user2, user2PersonalField, user1RivalField);
            }
        } else {
            user2PersonalField.getBattlefield()[x][y] = "M ";
            user1RivalField.getBattlefield()[x][y] = "M ";
            //user1RivalField.print();
            System.out.println("\nYou missed!");
            user2.passMove();
            //takeAShot(user2, user2PersonalField, user1RivalField);
        }
    }

    private void replaceCoordinateInDamagedShip(User user, String coordinate) {
        for (Ship ship : user.getListOfShips()) {
            for (int i = 0; i < ship.getLength(); i++) {
                if (coordinate.equals(ship.getLocationForUsers()[i])) {
                    ship.getLocationForUsers()[i] = "X";
                }
            }
        }
    }

    private void replaceCoordinateInSunkenShip(Ship ship) {
        for (int i = 0; i < ship.getLength(); i++) {
            ship.getLocationForUsers()[i] = "N";
        }
    }

    private boolean isSunkenShip(Ship[] listOfShips) {
        for (Ship ship : listOfShips) {
            int numberOfElements = ship.getLength();
            int numberOfRepetitions = 0;
            for (int i = 0; i < ship.getLength(); i++) {
                if (ship.getLocationForUsers()[i].equals("X")) {
                    numberOfRepetitions++;
                } else {
                    break;
                }
            }
            if (numberOfElements == numberOfRepetitions) {
                replaceCoordinateInSunkenShip(ship);
                return true;
            }
        }
        return false;
    }

    boolean isEveryShipSunken(Ship[] listOfShips) {
        int numberOfElements = 17;
        int numberOfRepetitions = 0;
        outer:
        for (Ship ship : listOfShips) {
            for (String element : ship.getLocationForUsers()) {
                if (element.equals("N")) {
                    numberOfRepetitions++;
                } else {
                    break outer;
                }
            }
        }
        return numberOfElements == numberOfRepetitions;
    }


    //    private boolean isHit(User user, String coordinate) {
//        for (Ship ship : user.getListOfShips()) {
//            for (String element : ship.getLocationForUsers()) {
//                if (coordinate.equals(element)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
    private boolean isHit(Battlefield field, String coordinate) {
        for (String occupiedCells : field.getCellsOccupiedByShips()) {
            if (coordinate.equals(occupiedCells)) {
                return true;
            }
        }
        return false;
    }

    private boolean isShotInsideField (String coordinate) {
        return ((int) coordinate.charAt(0) >= 65 && (int) coordinate.charAt(0) <= 74) &&
                (Integer.parseInt(coordinate.substring(1)) >= 1 && Integer.parseInt(coordinate.substring(1)) <= 10);
    }


    private String[] replaceLetterCoordinates(String coordinate) {
        String[] temp = {coordinate.substring(0, 1), coordinate.substring(1)};
        switch (temp[0]) {
            case "A":
                temp[0] = "1";
                break;
            case "B":
                temp[0] = "2";
                break;
            case "C":
                temp[0] = "3";
                break;
            case "D":
                temp[0] = "4";
                break;
            case "E":
                temp[0] = "5";
                break;
            case "F":
                temp[0] = "6";
                break;
            case "G":
                temp[0] = "7";
                break;
            case "H":
                temp[0] = "8";
                break;
            case "I":
                temp[0] = "9";
                break;
            case "J":
                temp[0] = "10";
                break;
        }
        return temp;
    }


    //////////////////// User place a ship ////////////////////

    public void placeShip(Ship ship, Battlefield battlefield) {
        if (ship.hasFirstAttemptToPlace) {
            System.out.printf("\nEnter the coordinates of the %s (%d cells):\n\n", ship.getType(), ship.getLength());
        }

        String[] coordinateFromUser = getInputFromUser(); // coordinateFromUser [F, 3, F, 7]
        System.out.println();
        if (isInteger(coordinateFromUser[1]) && isInteger(coordinateFromUser[3])) {
            if (isShipInLine(coordinateFromUser)) {
                if (isLengthCorrect(ship, coordinateFromUser)) {
                    if (isInsideField(coordinateFromUser)) {
                        ship.setLocationForUsers(coordinateFromUser);
                        if (battlefield.isNotOccupiedCells(ship.getLocationForUsers())) { // locationForUsers [F3, F4, F5, F6, F7]
                            String[] temp = splitShipCoordinate(ship.getLocationForUsers());
                            ship.setLocationFor2DimArray(replaceLetterCoordinates(temp)); // locationFor2DimArray [6, 3, 6, 4, 6, 5, 6, 6, 6, 7]
                            battlefield.fillOccupiedCells(ship.getLocationForUsers());
                        } else {
                            System.out.println("\nError! You placed it too close to another one. Try again:\n");
                            shipIsPlacedAgain(ship, battlefield);
                        }
                    } else {
                        System.out.println("\nError! Wrong ship location! Try again:\n");
                        shipIsPlacedAgain(ship, battlefield);
                    }
                } else {
                    System.out.printf("\nError! Wrong length of the %s! Try again:\n\n", ship.getType());
                    shipIsPlacedAgain(ship, battlefield);
                }
            } else {
                System.out.println("\nError! Wrong ship location! Try again:\n");
                shipIsPlacedAgain(ship, battlefield);
            }
        } else {
            System.out.println("\nError! You should type coordinates like A1 or E7 only inside the field\n");
            shipIsPlacedAgain(ship, battlefield);
        }
    }

    private void shipIsPlacedAgain(Ship ship, Battlefield battlefield) {
        ship.hasFirstAttemptToPlace = false;
        placeShip(ship, battlefield);
    }

    private String[] getInputFromUser() {
        System.out.print("> ");
        while (true) {
            String firstCoordinate = scanner.next().toUpperCase();
            String secondCoordinate = scanner.next().toUpperCase();
            if ((firstCoordinate.length() == 2 || firstCoordinate.length() == 3) && ((secondCoordinate.length() == 2 || secondCoordinate.length() == 3))) {
                return new String[]{firstCoordinate.substring(0, 1), firstCoordinate.substring(1), secondCoordinate.substring(0, 1), secondCoordinate.substring(1)};
            } else {
                System.out.println("Error! You should type coordinates only between A-J and 1-10 inclusively");
            }
        }
    }

    private boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    private boolean isShipInLine(String[] shipCoordinate) {
        return shipCoordinate[0].equals(shipCoordinate[2]) || shipCoordinate[1].equals(shipCoordinate[3]);
    }

    private boolean isLengthCorrect(Ship ship, String[] shipCoordinate) {
        return Math.abs(shipCoordinate[0].charAt(0) - shipCoordinate[2].charAt(0)) == ship.getLength() - 1 ||
                Math.abs(Integer.parseInt(shipCoordinate[1]) - Integer.parseInt(shipCoordinate[3])) == ship.getLength() - 1;
    }

    private boolean isInsideField(String[] shipCoordinate) {
        return ((int) shipCoordinate[0].charAt(0) >= 65 && (int) shipCoordinate[0].charAt(0) <= 74) &&
                ((int) shipCoordinate[2].charAt(0) >= 65 && (int) shipCoordinate[2].charAt(0) <= 74) &&
                (Integer.parseInt(shipCoordinate[1]) >= 1 && Integer.parseInt(shipCoordinate[1]) <= 10) &&
                (Integer.parseInt(shipCoordinate[3]) >= 1 && Integer.parseInt(shipCoordinate[3]) <= 10);
    }

    private String[] splitShipCoordinate(String[] shipCoordinate) {
        String[] separatedShipCoordinate = new String[shipCoordinate.length * 2];
        for (int i = 0; i < shipCoordinate.length; i++) {
            separatedShipCoordinate[i * 2] = shipCoordinate[i].substring(0, 1);
            separatedShipCoordinate[i * 2 + 1] = shipCoordinate[i].substring(1);
        }
        return separatedShipCoordinate;
    }

    private String[] replaceLetterCoordinates(String[] separatedShipCoordinate) {
        for (int i = 0; i < separatedShipCoordinate.length; i++) {
            if (i % 2 == 0) {
                switch (separatedShipCoordinate[i]) {
                    case "A":
                        separatedShipCoordinate[i] = "1";
                        break;
                    case "B":
                        separatedShipCoordinate[i] = "2";
                        break;
                    case "C":
                        separatedShipCoordinate[i] = "3";
                        break;
                    case "D":
                        separatedShipCoordinate[i] = "4";
                        break;
                    case "E":
                        separatedShipCoordinate[i] = "5";
                        break;
                    case "F":
                        separatedShipCoordinate[i] = "6";
                        break;
                    case "G":
                        separatedShipCoordinate[i] = "7";
                        break;
                    case "H":
                        separatedShipCoordinate[i] = "8";
                        break;
                    case "I":
                        separatedShipCoordinate[i] = "9";
                        break;
                    case "J":
                        separatedShipCoordinate[i] = "10";
                        break;
                }
            }
        }
        return separatedShipCoordinate;
    }

//////////////////// User pass the move  ////////////////////

    void passMove() {
        System.out.println("\nPress Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
