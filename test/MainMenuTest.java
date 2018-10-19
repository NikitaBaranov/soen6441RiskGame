import org.junit.Before;
import org.junit.Test;
import org.junit.BeforeClass;

import static org.junit.Assert.*;

public class MainMenuTest {

    @BeforeClass
    public void setUp() throws Exception {
        new MainMenu("RiskGame by Team 3", 450, 250);
    }
}
