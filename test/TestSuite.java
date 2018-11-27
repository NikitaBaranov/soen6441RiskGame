import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite
 */
@RunWith(Suite.class)

@Suite.SuiteClasses({
//        mapeditor.TestMapEditor.class, // Done
//        MainMenuTest.class, // Done
//        LauncherTest.class, // Done
//        game.GameTest.class, // TODO
//        game.utils.WarningWindowTest.class, // Done
        game.utils.MapLoaderTest.class, // TODO
//        game.model.PlayerTest.class // TODO
})
/**
 * Test suite empty class. According to the official doc of jUnit
 */
public class TestSuite {
}