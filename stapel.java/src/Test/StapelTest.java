package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;


import Spel.Stapel;

public class StapelTest {
    Stapel<Object> test = new Stapel<>();

    

    @Test
    public void duwTestBoolean() {
        test.duw(true);
        Object voorspelling = test.bovenste.inhoud;
        assertEquals(true, voorspelling);
        test.pak();
    }

    @Test
    public void duwTestString() {
        test.duw("hallo");
        Object voorspelling = test.bovenste.inhoud;
        assertEquals("hallo", voorspelling);
        test.pak();
    }

    @Test
    public void duwTestInt() {
        test.duw(1234);
        Object voorspelling = test.bovenste.inhoud;
        assertEquals(1234, voorspelling);
        test.pak();
    }

    @Test
    public void duwTestDouble() {
        test.duw(3.4);
        Object voorspelling = test.bovenste.inhoud;
        assertEquals(3.4, voorspelling);
        test.pak();
    }

    @Test
    public void duwTestNull() {
        test.duw(2);
        Object voorspelling = test.onderste.inhoud;
        assertEquals(2, voorspelling);
        test.pak();
    }

    @Test
    public void pakTest() {
        test.duw(7);
        Object voorspelling = test.pak();
        assertEquals(7, voorspelling);
    }

    @Test
    public void pakTestNull() {
        Object voorspelling = test.pak();
        assertEquals(voorspelling, null);
    }

    @Test
    public void pakTestNietNull() {
        test.duw("hallo");
        Object voorspelling = test.pak();        
        assertNotEquals(voorspelling, null);
    }
}