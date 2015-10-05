package ualberta15.reflex;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Ben on 01/10/2015.
 */
public class TestBuzzer extends ActivityInstrumentationTestCase2 {

    public TestBuzzer() {
        super(MenuActivity.class);
    }

    public void testRandomBetween(){
        Buzzer buzzer = new Buzzer("TestPlayer");
        int testRandom = buzzer.randomBetween(10, 100);
        assertTrue((testRandom > 9) && (testRandom < 101));
    }
}
