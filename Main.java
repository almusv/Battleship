
package battleship;

public class Main {

        static boolean isSomeoneWin = false;

    public static void main(String[] args) {

        Battlefield user1PersonalField = new Battlefield();
        Battlefield user1RivalField = new Battlefield();
        Battlefield user2PersonalField = new Battlefield();
        Battlefield user2RivalField = new Battlefield();
        Battlefield[] setOfField = {user1PersonalField, user1RivalField, user2PersonalField, user2RivalField};

        for (Battlefield field : setOfField) {
            field.fillFogOfWar();
        }

        User user1 = User.USER1;
        User user2 = User.USER2;

        Ship aircraftCarrier1 = Ship.AIRCRAFT_CARRIER;
        Ship battleship1 = Ship.BATTLESHIP;
        Ship submarine1 = Ship.SUBMARINE;
        Ship cruiser1 = Ship.CRUISER;
        Ship destroyer1 = Ship.DESTROYER;
        user1.setListOfShips(new Ship[]{aircraftCarrier1, battleship1, submarine1, cruiser1, destroyer1});

        Ship aircraftCarrier2 = Ship.AIRCRAFT_CARRIER;
        Ship battleship2 = Ship.BATTLESHIP;
        Ship submarine2 = Ship.SUBMARINE;
        Ship cruiser2 = Ship.CRUISER;
        Ship destroyer2 = Ship.DESTROYER;
        user2.setListOfShips(new Ship[]{aircraftCarrier2, battleship2, submarine2, cruiser2, destroyer2});


        /// The game starts. User 1 places the ships
        System.out.println(user1.getName() +", place your ships on the game field\n");
        user1PersonalField.print();

        for (Ship ship : user1.getListOfShips()) {
            user1.placeShip(ship, user1PersonalField);
            user1PersonalField.drawCells(ship);
            user1PersonalField.print();
        }
        user1.passMove();

        /// User 2 places the ships
        System.out.println(user2.getName() +", place your ships on the game field\n");
        user2PersonalField.print();

        for (Ship ship : user2.getListOfShips()) {
            user2.placeShip(ship, user2PersonalField);
            user2PersonalField.drawCells(ship);
            user2PersonalField.print();
        }
        user2.passMove();

        while (!isSomeoneWin) {
            user1.takeAShot(user1, user2, user2PersonalField, user1PersonalField, user1RivalField);
            if (user2.isEveryShipSunken(user2.getListOfShips())) {
                isSomeoneWin = true;
                break;
            }
            user2.takeAShot(user2, user1, user1PersonalField, user2PersonalField, user2RivalField);
            if (user1.isEveryShipSunken(user1.getListOfShips())) {
                isSomeoneWin = true;
                break;
            }
        }
    }
}

