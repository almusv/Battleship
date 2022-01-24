package battleship;

public enum Ship {
    AIRCRAFT_CARRIER(5, "Aircraft Carrier"),
    BATTLESHIP(4, "Battleship"),
    SUBMARINE(3, "Submarine"),
    CRUISER(3, "Cruiser"),
    DESTROYER(2, "Destroyer");

    private final int length;

    private final String type;

    boolean hasFirstAttemptToPlace = true;

    private final String[] locationForUsers; // [F3, F4, F5, F6, F7]

    private final String[] locationFor2DimArray; // [6, 3, 6, 4, 6, 5, 6, 6, 6, 7]

    Ship(int length, String type) {
        this.length = length;
        this.type = type;
        this.locationForUsers = new String[length];
        this.locationFor2DimArray = new String[length * 2];
    }


    public int getLength() {
        return length;
    }

    public String getType() {
        return type;
    }

    public String[] getLocationFor2DimArray() {
        return locationFor2DimArray;
    }

    public void setLocationFor2DimArray(String[] locationFor2DimArray) {
        System.arraycopy(locationFor2DimArray, 0, this.locationFor2DimArray, 0, this.locationFor2DimArray.length);
    }

    public String[] getLocationForUsers() {
        return locationForUsers;
    }

    public void setLocationForUsers(String[] shipLocation) {
        for (int i = 0; i < length; i++) {
            if (shipLocation[0].equals(shipLocation[2])) {
                // checking whether the user entered the numbers backwards
                int firstDigit = Integer.parseInt(shipLocation[1]);
                int secondDigit = Integer.parseInt(shipLocation[3]);
                if (firstDigit > secondDigit) {
                    String temp = shipLocation[1];
                    shipLocation[1] = shipLocation[3];
                    shipLocation[3] = temp;
                }
                int temp = Integer.parseInt(shipLocation[1]) + i;
                locationForUsers[i] = shipLocation[0] + temp;
            } else {
                // checking whether the user entered the letters backwards
                char firstLetter = shipLocation[0].charAt(0);
                char secondLetter = shipLocation[2].charAt(0);
                if (firstLetter > secondLetter) {
                    String temp = shipLocation[0];
                    shipLocation[0] = shipLocation[2];
                    shipLocation[2] = temp;
                }

                char temp = (char) (shipLocation[0].charAt(0) + i);
                this.locationForUsers[i] = temp + shipLocation[1];
            }
        }
    }
}
