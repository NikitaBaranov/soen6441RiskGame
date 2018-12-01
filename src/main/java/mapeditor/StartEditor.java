package mapeditor;

/**
 * The main class to initialize everything.
 * @author Nikitha Papani, Dinesh Pattapu, Rodolfo Mota Miranda
 *
 */
public class StartEditor {

	/**
	 * The main function called by operating system
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		Editor editorObj = new Editor();
		editorObj.initEditor();
	}

}
