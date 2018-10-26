package mapeditor.gui;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;

import static javax.swing.GroupLayout.Alignment.LEADING;
import static javax.swing.LayoutStyle.ComponentPlacement.RELATED;

public class BasicProperties extends JFrame {

	public BasicProperties() {

		initUI();
	}

	private void initUI() {

		Container pane = getContentPane();
		GroupLayout gl = new GroupLayout(pane);
		pane.setLayout(gl);

		JLabel lbl = new JLabel("Author:");
		JTextField field = new JTextField(15);

		GroupLayout.SequentialGroup sg = gl.createSequentialGroup();

		sg.addComponent(lbl).addPreferredGap(RELATED).addComponent(field, GroupLayout.DEFAULT_SIZE,
				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);

		gl.setHorizontalGroup(sg);

		GroupLayout.ParallelGroup pg = gl.createParallelGroup(LEADING, false);

		pg.addComponent(lbl).addComponent(field);
		gl.setVerticalGroup(pg);

		gl.setAutoCreateContainerGaps(true);

		pack();

		setTitle("Simple");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);	

	}
}