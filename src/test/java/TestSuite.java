import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite
 */
@RunWith(Suite.class)

@Suite.SuiteClasses({
        mapeditor.TestMapEditor.class,
        game.GameTest.class,
        MainMenuTest.class,
        LauncherTest.class,
        game.utils.WarningWindowTest.class,
        game.utils.MapLoaderTest.class,
        game.model.PlayerTest.class
})
/**
 * Test suite empty class. According to the official doc of jUnit
 */
public class TestSuite {
}