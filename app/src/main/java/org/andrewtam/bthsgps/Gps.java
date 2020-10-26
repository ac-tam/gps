package org.andrewtam.bthsgps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.time.LocalTime;
import java.util.Currency;

public class Gps extends AppCompatActivity {

    private LocalTime[] starts;
    private Room startRoom, currentRoom, nextRoom, turnOne, turnTwo;
    private String[] schedule;
    public SharedPreferences data;
    TextView period;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        final Handler handler = new Handler();

        data = PreferenceManager.getDefaultSharedPreferences(this);


                final Button clickButton = findViewById(R.id.button);
                clickButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        changeActivity();
                    }
                });


                final Button otherButton = findViewById(R.id.andrew);
                otherButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        me();
                    }
                });
                 Runnable it = new Runnable() {
            @Override
            public void run() {

                TextView first, stairs, second, even, third, straight;
                ImageView arrow1, imgStairs, arrow2;
                boolean facingCenter = true;


                first = findViewById(R.id.firstmove);

                second = findViewById(R.id.secondmove);
                third = findViewById(R.id.thirdmove);

                stairs = findViewById(R.id.stairs);


                //try {


                starts = new LocalTime[]{
                        LocalTime.of(0, 0), // 0 to 1
                        LocalTime.of(7, 30), // 0 to 1
                        LocalTime.of(8, 20), // 1 to 2
                        LocalTime.of(9, 10), // 2 to 3
                        LocalTime.of(10, 10), //3 to 4
                        LocalTime.of(10, 50), // 4 to 5
                        LocalTime.of(11, 30), // 5 to 6
                        LocalTime.of(12, 10), //6 to 7
                        LocalTime.of(13, 0), // 7 to 8
                        LocalTime.of(13, 50), //8 to 9
                        LocalTime.of(14, 30), //9 to 10
                };


                for (int i = 10; i > 0; i--)
                    if (LocalTime.now().isAfter(starts[i])) {
                            startRoom = new Room(data.getString((i - 1) + "", "1W13"));
                            currentRoom = new Room(data.getString((i - 1) + "", "1W13"));
                            nextRoom = new Room(data.getString((i) + "", "1W13"));
                            TextView a = findViewById(R.id.period2);
                            a.setText("Period " + (i - 1) + " - " + (i));
                            break;
                    }




                String[] text = new String[]{"", "", "", ""};
                period = findViewById(R.id.period);
                period.setText(currentRoom.getRoom() + " to " + nextRoom.getRoom());


                boolean move = true;

                if (nextRoom.getDirect() == Room.Direction.C && currentRoom.getDirect() != Room.Direction.C && currentRoom.getFloor() == nextRoom.getFloor())
                    if (currentRoom.getDirect() == Room.Direction.E)
                        nextRoom = new Room(nextRoom.getFloor() + "E13");
                    else
                        nextRoom = new Room(nextRoom.getFloor() + "W13");


                // determine floor
                if (currentRoom.getFloor() < nextRoom.getFloor())
                    text[1] = ("Go up the stairs to the " + addSuffix(nextRoom.getFloor()) + " floor");
                else if (currentRoom.getFloor() > nextRoom.getFloor())
                    text[1] = ("Go down the stairs to the " + addSuffix(nextRoom.getFloor()) + " floor");
                else
                    move = false;


                // go to the staircase
                if (move) { //assign staircase
                    if (currentRoom.between(new Room("1E20"), new Room("1E28")) || //NE  E 21 - 27
                            currentRoom.between(new Room("1N0"), new Room("1N5"))) {   // 1 - 4
                        currentRoom = new Room(nextRoom.getFloor() + "NE");
                    } else if (currentRoom.between(new Room("1W0"), new Room("1W7")) || // NW // W 1 -  6
                            currentRoom.between(new Room("1N4"), new Room("1N9"))) {   // 5 - 8
                        currentRoom = new Room(nextRoom.getFloor() + "NW");
                    } else if (currentRoom.between(new Room("1E0"), new Room("1E7")) || //SE
                            currentRoom.between(new Room("1S7"), new Room("1S15"))) {
                        currentRoom = new Room(nextRoom.getFloor() + "SE");
                    } else if (currentRoom.between(new Room("1W20"), new Room("1W28")) || //SW
                            currentRoom.between(new Room("1S0"), new Room("1S8"))) {
                        currentRoom = new Room(nextRoom.getFloor() + "SW");
                    } else if (currentRoom.between(new Room("1W6"), new Room("1W21")) || // CW
                            currentRoom.between(new Room("1C5"), new Room("1C9"))) {
                        currentRoom = new Room(nextRoom.getFloor() + "CW");
                    } else if (currentRoom.between(new Room("1E5"), new Room("1E21")) || // CE
                            currentRoom.between(new Room("1C0"), new Room("1C6"))) {
                        currentRoom = new Room(nextRoom.getFloor() + "CE");
                    }

                    if (startRoom.before(currentRoom)) //go to stairs
                        text[0] = ("Turn right and go straight until you see the stairs and enter the " + currentRoom.getRoom().substring(1) + " staircase.");
                    else
                        text[0] = ("Turn left and go straight until you see the stairs and enter the " + currentRoom.getRoom().substring(1) + " staircase.");

                }

                if (currentRoom.getRoom().equals(nextRoom.getRoom()))
                    text[0] = ("You're already at Room " + nextRoom.getRoom());


                    //same direction
                else if (currentRoom.sameDirect(nextRoom)) {
                    if (currentRoom.isStairs()) {
                        switch (currentRoom.getRoom().substring(1)) {
                            case "NW":
                            case "SE":
                                if (currentRoom.before(nextRoom))
                                    text[2] = ("Turn right and walk straight. Look for Room " + nextRoom.getRoom());
                                else
                                    text[2] = ("Exit the staircase and go straight. Look for " + nextRoom.getRoom());
                                break;
                            case "SW":
                            case "NE":
                                if (currentRoom.before(nextRoom))
                                    text[2] = ("Exit the staircase and go straight. Look for " + nextRoom.getRoom());
                                else
                                    text[2] = ("Turn left and walk straight. Look for Room " + nextRoom.getRoom());
                                break;
                            case "CW":
                            case "CE":
                                if (currentRoom.before(nextRoom))
                                    text[2] = ("Turn right and walk straight. Look for Room " + nextRoom.getRoom());
                                else
                                    text[2] = ("Turn left and walk straight. Look for Room " + nextRoom.getRoom());
                                break;
                        }
                    } else if (currentRoom.before(nextRoom))
                        text[2] = ("Turn right and walk straight. Look for Room " + nextRoom.getRoom());
                    else
                        text[2] = ("Turn left and walk straight. Look for Room " + nextRoom.getRoom());
                } else if (currentRoom.lShape(nextRoom)) {
                    switch (currentRoom.getCoords()[0] + "" + nextRoom.getCoords()[1]) {
                        case ("00"):
                            turnOne = new Room(currentRoom.getFloor() + "NW");
                            break;
                        case ("270"):
                        case ("027"):
                            turnOne = new Room(currentRoom.getFloor() + "SW");
                            break;
                        case ("2714"):
                        case ("1427"):
                            turnOne = new Room(currentRoom.getFloor() + "SE");
                            break;
                        case ("014"):
                        case ("140"):
                            turnOne = new Room(currentRoom.getFloor() + "NE");
                            break;
                    }

                    switch (currentRoom.getCoords()[1] + "" + nextRoom.getCoords()[0]) {
                        case ("00"):
                            turnOne = new Room(currentRoom.getFloor() + "NW");
                            break;
                        case ("270"):
                        case ("027"):
                            turnOne = new Room(currentRoom.getFloor() + "SW");
                            break;
                        case ("2714"):
                        case ("1427"):
                            turnOne = new Room(currentRoom.getFloor() + "SE");
                            break;
                        case ("014"):
                        case ("140"):
                            turnOne = new Room(currentRoom.getFloor() + "NE");
                            break;
                    }


                    if (currentRoom.before(turnOne))
                        switch (currentRoom.getRoom().substring(1)) {
                            case "SW":
                            case "NE":

                                text[2] = ("Walk straight until the end of the building");
                                break;
                            default:
                                text[2] = ("Turn right and walk straight until the end of the building");
                        }
                    else
                        switch (currentRoom.getRoom().substring(1)) {
                            case "NW":
                            case "SE":
                                text[2] = ("Walk straight until the end of the building");
                                break;
                            default:
                                text[2] = ("Turn left and walk straight until the end of the building");
                        }


                    if (nextRoom.getDirect() == Room.Direction.W || nextRoom.getDirect() == Room.Direction.S)
                        if (turnOne.beforeNonRelative(nextRoom))
                            text[3] = ("Turn left and walk straight and look for Room " + nextRoom.getRoom());
                        else
                            text[3] = ("Turn right and walk straight and look for Room " + nextRoom.getRoom());

                    else if (turnOne.beforeNonRelative(nextRoom))
                        text[3] = ("Turn right and walk straight and look for Room " + nextRoom.getRoom());
                    else
                        text[3] = ("Turn left and walk straight and look for Room " + nextRoom.getRoom());

                }
                // U or Snake Shape
                else if (currentRoom.opposite(nextRoom)) {
                    System.out.println("h");
                    if (Math.abs(nextRoom.getCoords()[0] - currentRoom.getCoords()[0]) != 27) //ew
                        if (nextRoom.getCoords()[1] - currentRoom.getCoords()[1] < 0) ///W to e
                            if (currentRoom.getCoords()[0] < 6 && nextRoom.getCoords()[0] < 6) {
                                turnOne = new Room("1NE");
                                turnTwo = new Room("1NW");
                            } else if (currentRoom.getCoords()[0] > 20 && nextRoom.getCoords()[0] > 20) {
                                turnOne = new Room("1SE");
                                turnTwo = new Room("1SW");
                            } else {
                                turnOne = new Room("1CE");
                                turnTwo = new Room("1CW");
                            }
                        else if (currentRoom.getCoords()[0] < 6 && nextRoom.getCoords()[0] < 6) {
                            turnTwo = new Room("1NE");
                            turnOne = new Room("1NW");
                        } else if (currentRoom.getCoords()[0] > 20 && nextRoom.getCoords()[0] > 20) {
                            turnTwo = new Room("1SE");
                            turnOne = new Room("1SW");
                        } else {
                            turnTwo = new Room("1CE");
                            turnOne = new Room("1CW");
                        }


                    else if (nextRoom.getCoords()[0] - currentRoom.getCoords()[0] < 0)
                        if (currentRoom.getCoords()[1] < 5) {
                            turnOne = new Room("1SW");
                            turnTwo = new Room("1NW");
                        } else {
                            turnOne = new Room("1SE");
                            turnTwo = new Room("1NE");
                        }


                    else if (currentRoom.getCoords()[1] < 5) {
                        turnOne = new Room("1NW");
                        turnTwo = new Room("1SW");
                    } else {
                        turnOne = new Room("1NE");
                        turnTwo = new Room("1SE");
                    }

                    if (startRoom.before(currentRoom))
                        text[0] = ("Turn right and go straight until you see the stairs and enter the " + currentRoom.getRoom().substring(1) + " staircase.");
                    else
                        text[0] = ("Turn left and go straight until you see the stairs and enter the " + currentRoom.getRoom().substring(1) + " staircase.");


                    if (currentRoom.getFloor() != nextRoom.getFloor()) {
                        if (currentRoom.getCoords()[0] == turnOne.getCoords()[0])
                            text[2] = ("From the staircase, walk all the way down to the other side of the building");
                        else if (turnOne.getRoom().substring(1).contains(("N")) || turnOne.getRoom().substring(1).contains(("W"))) //!
                            if (currentRoom.before(turnOne))
                                text[2] = ("Turn right and walk straight and look the center stairs. Turn at the stairs and cross the center.");
                            else
                                text[2] = ("Turn left and walk straight and look for the center stairs. Turn at the stairs and cross the center.");
                        else if (currentRoom.beforeNonRelative(turnOne))
                            text[2] = ("Turn left and walk straight and look for the center stairs. Turn at the stairs and cross the center.");
                        else
                            text[2] = ("Turn right and walk straight and look for the center stairs. Turn at the stairs and cross the center.");
                    } else
                        text[2] = ("Walk all the way down to the other side of the building");


                    if (nextRoom.getDirect() == Room.Direction.W || nextRoom.getDirect() == Room.Direction.S) //!
                        if (turnTwo.before(nextRoom))
                            text[3] = ("Turn left and walk straight and look for Room " + nextRoom.getRoom());
                        else
                            text[3] = ("Turn right and walk straight and look for Room " + nextRoom.getRoom());
                    else if (turnTwo.beforeNonRelative(nextRoom))
                        text[3] = ("Turn right and walk straight and look for Room " + nextRoom.getRoom());
                    else
                        text[3] = ("Turn left and walk straight and look for Room " + nextRoom.getRoom());


                    if (nextRoom.getFloor() == 5 && turnOne.getRoom().contains("C")) {
                        String temp = text[1];
                        text[1] = text[2];
                        text[2] = (temp);

                        if (text[3].contains("left"))
                            text[3] = ("Turn right and walk straight and look for Room " + nextRoom.getRoom());
                        else
                            text[3] = ("Turn left and walk straight and look for Room " + nextRoom.getRoom());

                    }

                }


                for (int k = 0; k < 4; k++)
                    for (int i = 0; i < 3; i++)
                        if (text[i] == "") {
                            text[i] = text[i + 1];
                            text[i + 1] = "";
                        }

                first.setText(text[0]);
                stairs.setText(text[1]);
                second.setText(text[2]);
                third.setText(text[3]);
                handler.postDelayed(this, 1000);

            }

        };
        handler.postDelayed(it,0);

    }


    private String addSuffix(int floor) {
        if (floor == 1)
            return floor + "st";
        if (floor == 2)
            return floor + "nd";
        if (floor == 3)
            return floor + "rd";
        return floor + "th";
    }


    public void changeActivity() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }

    public void me() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("Developed by Andrew Tam");
        // add a button
        builder.setPositiveButton("Close", null);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void printArray(String[] arr) {
        String str = "";
        for (String s : arr)
            str += s + ". ";

        System.out.println(str);
    }


}


class Room {

    public int[] coords;

    public boolean isStairs() {
        return isStairs;
    }

    private boolean isStairs;
    private int floor, roomNumber;
    private Direction direct;

    enum Direction {
        N, S, E, W, C
    }

    String room;

    Room(String s) {
        room = s;
        isStairs = true;
        if (s.contains("SW"))
            coords = new int[]{27, 0};
        else if (s.contains("SE"))
            coords = new int[]{27, 14};
        else if (s.contains("NE"))
            coords = new int[]{0, 14};
        else if (s.contains("NW"))
            coords = new int[]{0, 0};
        else if (s.contains("CE")) {
            coords = new int[]{13, 14};
            direct = Direction.E;
        } else if (s.contains("CW")) {
            coords = new int[]{13, 0};
            direct = Direction.W;
        } else {
            isStairs = false;

            if (s.equals(""))
                s = "1W13";
            if (s.equals("LOCK3")) {
                floor = 3;
                roomNumber = 12;
                direct = Direction.S;
                coords = new int[]{27, 6};
            } else if (s.equals("LIB")) {
                floor = 5;
                room = "5W13";
                roomNumber = 13;
                direct = Direction.W;
                coords = new int[]{13, 0};
            } else {
                floor = Integer.parseInt(s.substring(0, 1));

                roomNumber = Integer.parseInt(s.substring(2));

                try {
                    direct = Direction.valueOf(Direction.class, s.substring(1, 2));
                } catch (Exception e) {
                }
            }

            if (direct == Direction.E)
                coords = new int[]{27 - roomNumber, 14};
            if (direct == Direction.W)
                coords = new int[]{roomNumber, 0};
            if (direct == Direction.N)
                coords = new int[]{0, 9 - roomNumber};
            if (direct == Direction.S)
                coords = new int[]{27, roomNumber};

            if (direct == Direction.C)
                coords = new int[]{13, 8 - roomNumber}; //!!
        }
    }

    public int getFloor() {
        return floor;
    }


    public int getRoomNumber() {
        return roomNumber;
    }


    public Direction getDirect() {
        return direct;
    }


    public String getRoom() {
        return room;
    }


    public int[] getCoords() {
        return coords;
    }


    public boolean opposite(Room other) {
        if ((room.substring(1).equals("NW") && other.getRoom().substring(1).equals("SW")) ||
                (room.substring(1).equals("NE") && other.getRoom().substring(1).equals("SE")) ||
                (room.substring(1).equals("SW") && other.getRoom().substring(1).equals("NW")) ||
                (room.substring(1).equals("SE") && other.getRoom().substring(1).equals("NE")))
            return false;
        return (Math.abs(coords[0] - other.getCoords()[0]) == 27 || Math.abs(coords[1] - other.getCoords()[1]) == 14);
    }

    public boolean before(Room other) {
        if (isStairs || other.isStairs()) {
            if ((other.getCoords()[0] == 27 || other.getCoords()[1] == 0) && (this.getCoords()[0] == 27 || this.getCoords()[1] == 0))
                return (this.getCoords()[0] < other.getCoords()[0] || this.getCoords()[1] < other.getCoords()[1]);
            else
                return (this.getCoords()[0] > other.getCoords()[0] || this.getCoords()[1] > other.getCoords()[1]);

        } else
            return (this.getDirect() == other.getDirect()) && this.getRoomNumber() < other.getRoomNumber();
    }

    public boolean beforeNonRelative(Room other) {
        return this.getCoords()[0] < other.getCoords()[0] || this.getCoords()[1] < other.getCoords()[1];
    }


    public boolean between(Room first, Room after) {
        if (after.before(first))
            return after.before(this) && this.before(first);
        return first.before(this) && this.before(after);
    }

    public boolean sameDirect(Room other) {
        if (this.isStairs())
            return this.getRoom().contains(other.getDirect().toString());
        else if (other.isStairs())
            return other.getRoom().contains(this.getDirect().toString());
        else
            return this.getDirect() == other.getDirect();
    }

    public boolean lShape(Room other) {
        switch (this.getCoords()[1] + "" + other.getCoords()[0]) {
            case ("00"):
            case ("270"):
            case ("027"):
            case ("2714"):
            case ("1427"):
            case ("014"):
            case ("140"):
                return true;
            default:
                return false;
        }
    }
}



