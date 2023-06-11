package Spel;

import java.util.ArrayList;

public class App {
    public static Object[] spelbord;
    public static Bord bord = new Bord();
    public static int teller = 0;
    static int speler1 = 0;
    static int speler2 = 0;
    // static int gelijk = 0;

	public static void main(String[] args) throws Exception {
        // System.out.println("Hello, World!");
        Stapel<Object> stapel = new Stapel<>();
        ArrayList<String> winnaar = new ArrayList<>();
       
        while (teller < 10) {
            bord.setUp();
            winnaar.add(bord.speelSpel());
            teller++;
            bord.printSpel();
        }
        for (int i = 0; i < winnaar.size(); i++) {
            if (winnaar.get(i).equals("Speler 1")) {
                speler1++;
            }else{
                speler2++;
            }
        }
        System.out.println("Speler 1: " + speler1 + " - Speler 2: " + speler2);
    }
}
