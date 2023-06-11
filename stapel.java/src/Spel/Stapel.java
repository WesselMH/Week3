package Spel;

public class Stapel <T>{
    public Kaart onderste;
    public Kaart bovenste;
     
    
    

    public void duw(T a){
        Kaart kaart;
        if (onderste == null){
            kaart = new Kaart(a);
            onderste = kaart;
        }else{
            kaart = new Kaart(a, bovenste);
        }
        bovenste = kaart;
    }

    public T pak(){
        if(bovenste != null){
            Kaart huidig = bovenste;
            if(onderste == huidig){
                bovenste = null;
            }else{           
                bovenste = huidig.vorige;        
            }
            return huidig.inhoud;

        } return null;
        
    }
    /**
     * Kaart
     */
    public class Kaart {
        public Kaart vorige;
        public T inhoud;
        
        public Kaart(T inhoud){
            this.inhoud = inhoud;
        }

        public Kaart(T inhoud, Kaart vorige){
            this.inhoud = inhoud;
            this.vorige = vorige;
        }
    }        
}
