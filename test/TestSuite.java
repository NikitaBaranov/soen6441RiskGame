import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite
 */
@RunWith(Suite.class)

@Suite.SuiteClasses({
        MainMenuTest.class,
        LauncherTest.class,
        mapeditor.TestMapEditor.class,
        game.utils.WarningWindowTest.class,
        game.utils.MapLoaderTest.class,
        game.GameTest.class,
        game.model.PlayerTest.class
})
/**
 * Test suite empty class. According to the official doc of jUnit
 */
public class TestSuite {
}