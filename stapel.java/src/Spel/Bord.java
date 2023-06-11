package Spel;

import java.util.Scanner;
import java.util.Random;

public class Bord {
    Scanner scanner = new Scanner(System.in);

    final int GROOTTE = 7;

    boolean bot = false;

    Object[][] spelbord = new Object[GROOTTE][GROOTTE];
    boolean isSpeler = true;
    String team;
    int[] score = { 0, 0 };
    int x;
    int x2;
    int y;
    int y2;
    Stapel<Object> zetten = new Stapel<>();
    Stapel<Object> geheugen = new Stapel<>();
    Random random = new Random();
    boolean computer = false;
    int rondes;
    int tellerC;
    int tellerMogelijk;
    boolean checkNodig = true;

    public String speelSpel() {
        rondes = 0;
        tellerC = 0;
        // tellerMogelijk = 0;
        while (true) {
            checkScore();
            printSpel();
            if (spelAfgelopen()) {
                break;
            }
            checkScore();
            // printSpel();

            if (isSpeler) {
                team = " H ";
                vraagZet();
                // printSpel();
            } else {
                team = " B ";
                vraagComputerZet();
                // printSpel();
            }
            if (!bot) {
                System.out.println(team + " is aan zet");
            }

            saveBord();
            if (isSpeler) {
                isSpeler = false;
            } else {
                isSpeler = true;
                rondes++;
                if (!bot) {
                    terugDraaien();
                }
            }
        }
        // printSpel();
        if (score[0] > score[1]) {
            System.out.println(App.teller + " Speler 1 is de winnaar!!!");
            return "Speler 1";
        }
        if (score[0] < score[1]) {
            System.out.println(App.teller + " Speler 2 is de winnaar...");
            return "Speler 2";
        } else {
            System.out.println(App.teller + " Gelijk spel...");
            return "Gelijk spel";
        }
    }

    private void saveBord() {
        Object[][] kopi = new Object[GROOTTE][GROOTTE];
        for (int i = 0; i < GROOTTE; i++) {
            for (int j = 0; j < GROOTTE; j++) {
                kopi[i][j] = spelbord[i][j];
            }
        }
        zetten.duw(kopi);
    }

    private void terugDraaien() {
        printSpel();
        System.out.println("Wil je je zet terug draaien?");
        System.out.print("y/n : ");
        String antwoord = scanner.nextLine();
        if (antwoord.equals("y")) {
            zetten.pak();
            zetten.pak();
            spelbord = (Object[][]) zetten.pak();
            printSpel();
            isSpeler = true;
            rondes--;
        }
    }

    public void printSpel() {
        if (!bot) {
            checkScore();
            System.out.print(" ");
            for (int i = 1; i <= GROOTTE; i++) {
                System.out.print("  " + i);
            }
            System.out.println();
            for (int i = 0; i < GROOTTE; i++) {
                System.out.print(i + 1 + " ");
                for (int j = 0; j < GROOTTE; j++) {
                    System.out.print(spelbord[i][j]);
                }
                System.out.println("");
            }
            System.out.println("Ronde: " + rondes + " Stand: " + score[0] + " - " + score[1]);
            // System.out.println(team);
        }
    }

    public boolean magHeen() {
        computerBordKopi();
        int tellerM = 0;
        for (int i = x - 2; i < x + 3; i++) {
            for (int j = y - 2; j < y + 3; j++) {
                if (i == x && j == y) {
                    spelbord[i][j] = "[O]";
                }
                if ((i >= 0 && i < GROOTTE && j >= 0 && j < GROOTTE)) {
                    if (spelbord[i][j] == "[ ]" || spelbord[i][j] == "000") {
                        spelbord[i][j] = "[x]";
                        tellerM++;
                    }
                }
            }
        }
        // printSpel();
        if (tellerM > 0) {
            return true;
        }
        spelbord = (Object[][]) geheugen.pak();
        // printSpel();
        return false;
    }

    private void vraagZet() {
        while (true) {
            if (bot) {
                /* random computer */
                y = random.nextInt(GROOTTE);
                x = random.nextInt(GROOTTE);

            } else {
                System.out.print("Kies op de X-as:");
                y = scanner.nextInt() - 1;
                System.out.print("Kies op de Y-as:");
                x = scanner.nextInt() - 1;
                scanner.nextLine();
            }
            if (spelbord[(x)][(y)] == team && (beschikbaar() )) {
                printSpel();
                if (magHeen()) {
                    vraagBevestiging();
                    break;

                }
                // printSpel();
                
            } else {
                if (tellerMogelijk > 2000) {
                    break;
                }
                tellerMogelijk++;
                if (isSpeler && !bot) {
                    System.out.println("Dit is niet jouw vakje!");
                }
            }
        }
    }

    private void vraagComputerZet() {
        vraagZet: while (true) {
            y = random.nextInt(GROOTTE);
            x = random.nextInt(GROOTTE);
            if (spelbord[(x)][(y)] == team && (beschikbaar()/* || tellerMogelijk > 2000 */)) {
                // printSpel();
                if (magHeen()) {
                    inner: while (true) {

                        y2 = random.nextInt(GROOTTE);
                        x2 = random.nextInt(GROOTTE);
                        if (computerKeuze()) {
                            xTerug();
                            teamTerug();
                            // printSpel();
                            uitspreiding();
                            break vraagZet;

                        }
                        spelbord = (Object[][]) geheugen.pak();
                        tellerC++;
                        if (tellerC > 5000) {
                            if (spelbord[x2][y2] == "[x]") {
                                spelbord[x2][y2] = team;
                                // printSpel();
                                xTerug();
                                teamTerug();
                                // printSpel();
                                uitspreiding();
                                tellerC = 0;
                                break vraagZet;
                            }
                        }
                        // break inner;
                    }
                }
                // printSpel();
                // tellerC = 0;
                
                // break vraagZet;
            }
            if (tellerMogelijk > 2000) {
                break;
            }
            tellerMogelijk++;
        }
    }

    private boolean beschikbaar() {
        for (int i = x - 2; i < x + 3; i++) {
            for (int j = y - 2; j < y + 3; j++) {
                if ((i >= 0 && i < GROOTTE && j >= 0 && j < GROOTTE)) {
                    if (spelbord[i][j] == "[ ]") {
                        // spelbord[i][j] = "000";
                        return true;
                    }
                }
            }
        }
        // printSpel();
        // tellerMogelijk++;
        return false;
    }

    private void vraagBevestiging() {
        while (true) {
            if (bot) {
                /* random computer */
                y2 = random.nextInt(GROOTTE);
                x2 = random.nextInt(GROOTTE);

            } else {
                printSpel();
                System.out.print("Bevestig op de X-as:");
                y2 = scanner.nextInt() - 1;
                System.out.print("Bevestig op de Y-as:");
                x2 = scanner.nextInt() - 1;
                scanner.nextLine();
            }
            if (spelbord[x2][y2] == "[x]") {
                spelbord[x2][y2] = team;
                // printSpel();
                xTerug();
                teamTerug();
                // printSpel();
                uitspreiding();
                break;
            } else {
                if (isSpeler && !bot) {
                    System.out.println("Dit vakje kan je niet kiezen...");
                }
            }
        }
    }

    private boolean computerKeuze() {
        computerBordKopi();

        if (spelbord[x2][y2] == "[x]") {
            spelbord[x2][y2] = team;

            int[] tempScore = new int[2];
            int tempB = 0;

            for (int i = 0; i < score.length; i++) {
                tempScore[i] = score[i];
            }
            uitspreiding();
            for (int i = 0; i < GROOTTE; i++) {
                for (int j = 0; j < GROOTTE; j++) {
                    if (spelbord[i][j] == "[B]") {
                        tempB++;
                    }
                }
            }

            if (tempB > tempScore[1] + 2) {
                tempScore[1] = tempB;
                Object[][] tempKopi = new Object[GROOTTE][GROOTTE];
                for (int i = 0; i < GROOTTE; i++) {
                    for (int j = 0; j < GROOTTE; j++) {
                        tempKopi[i][j] = spelbord[i][j];
                    }
                }
                // printSpel();
                return true;
            }
        }
        return false;
    }

    private void computerBordKopi() {
        Object[][] compBord = new Object[GROOTTE][GROOTTE];
        for (int i = 0; i < GROOTTE; i++) {
            for (int j = 0; j < GROOTTE; j++) {
                compBord[i][j] = spelbord[i][j];
            }
        }
        geheugen.duw(compBord);
    }

    private void teamTerug() {
        for (int i = x2 - 1; i < x2 + 2; i++) {
            for (int j = y2 - 1; j < y2 + 2; j++) {
                if ((i >= 0 && i < GROOTTE && j >= 0 && j < GROOTTE)) {
                    if (spelbord[i][j] == "[O]") {
                        spelbord[i][j] = team;
                    }
                }
            }
        }
        teamNietTerug();
    }

    private void teamNietTerug() {
        for (int i = 0; i < GROOTTE; i++) {
            for (int j = 0; j < GROOTTE; j++) {
                if (spelbord[i][j] == "[O]") {
                    spelbord[i][j] = "[ ]";
                }
            }
        }
    }

    private void xTerug() {
        for (int i = 0; i < GROOTTE; i++) {
            for (int j = 0; j < GROOTTE; j++) {
                if (spelbord[i][j] == "[x]") {
                    spelbord[i][j] = "[ ]";
                }
            }
        }
    }

    public void setUp() {
        if (!bot) {
            System.out.println("1) 2 spelers \n" +
            "2) easy computer \n" +
            "3) medium computer");
            System.out.print("Tegen wie wil je spelen: ");
            String antwoord = scanner.nextLine();
            if (antwoord.equals("3")) {
            computer = true;
            }
            
        }else{
            computer = true;
        }

        for (int i = 0; i < GROOTTE; i++) {
            for (int j = 0; j < GROOTTE; j++) {
                spelbord[i][j] = "[ ]";
                if (i < 2 && j < 2) {
                    spelbord[i][j] = " H ";
                }
                if (i >= GROOTTE - 2 && j >= GROOTTE - 2) {
                    spelbord[i][j] = " B ";
                }
            }
        }
        saveBord();
    }

    private void checkScore() {
        int tellerH = 0;
        int tellerB = 0;
        for (int i = 0; i < GROOTTE; i++) {
            for (int j = 0; j < GROOTTE; j++) {
                if (spelbord[i][j] == " H ") {
                    tellerH++;
                }
                if (spelbord[i][j] == " B ") {
                    tellerB++;
                }
            }
        }
        score[0] = tellerH;
        score[1] = tellerB;
    }

    private boolean spelAfgelopen() {
        // printSpel();
        // System.out.println(team);
        int gekleurdeVakjes = (GROOTTE * GROOTTE);
        if (score[0] == 0 || score[1] == 0) {
            return true;
        }
        for (int i = 0; i < GROOTTE; i++) {
            for (int j = 0; j < GROOTTE; j++) {
                if (spelbord[i][j] != "[ ]") {
                    gekleurdeVakjes--;
                    if (gekleurdeVakjes == 0) {
                        if (spelbord[i][j] == "[O]") {
                            spelbord[i][j] = team;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void uitspreiding() {
        for (int i = x2 - 1; i < x2 + 2; i++) {
            for (int j = y2 - 1; j < y2 + 2; j++) {
                if ((i >= 0 && i < GROOTTE && j >= 0 && j < GROOTTE)) {

                    if (!isSpeler) {
                        if (spelbord[i][j] == " H ") {
                            spelbord[i][j] = " B ";
                        }
                    } else {
                        if (spelbord[i][j] == " B ") {
                            spelbord[i][j] = " H ";
                        }
                    }
                }
            }
        }
    }
}
