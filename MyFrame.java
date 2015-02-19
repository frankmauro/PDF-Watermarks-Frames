import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.alee.extended.image.WebImage;

@SuppressWarnings("serial")
public class MyFrame extends JFrame {

	// Crea un frame con titolo, numero colonne e numero righe specificato
	public MyFrame(String string, int cWidth[], int rHeight[]) {
		super(string);
		this.setBounds(100, 100, 900, 650);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = cWidth;
		gridBagLayout.rowHeights = rHeight;
		gridBagLayout.columnWeights = new double[]{Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{Double.MIN_VALUE};
		this.setLayout(gridBagLayout);
	}
	
	// Crea un frame secondario (utilizzato per selezione cornici) non ridimensionabile
	public MyFrame(String string) {
		super(string);
		this.setBounds(150, 150, 720, 850);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 10, 20);
		this.setLayout(flowLayout);
		this.setResizable(false);
	}
	
	// Inserisce il logo all'interno del layout
	public void setLogo() {
		ImageIcon icon = new ImageIcon("img" + System.getProperty("file.separator") + "logo.png");
		WebImage img = new WebImage(icon);
		GridBagConstraints gbc_frameTitle = new GridBagConstraints();
		setConstraints(gbc_frameTitle, 3, 0, 0, 1.0f, 0.35f, new Insets(5, 5, 5, 5), GridBagConstraints.CENTER, GridBagConstraints.NONE);
		this.add(img, gbc_frameTitle);
	}
	
	// Inserisce una label all'interno del layout
	public void setLabel (JLabel label, int gridx, int gridy, float weightx, float weighty, int anchor, int fill) {
		GridBagConstraints gbc_labelText = new GridBagConstraints();
		setConstraints(gbc_labelText, 1, gridx, gridy, weightx, weighty, new Insets(5, 5, 5, 5), anchor, fill);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setMinimumSize(new Dimension(150, 30));
		label.setPreferredSize(new Dimension(150, 30));
		label.setMaximumSize(new Dimension(150, 30));
		this.add(label, gbc_labelText);
	}
	
	// Funzione per impostare velocemente i GridBagConstraints per il layout
	public void setConstraints(GridBagConstraints constraint, int width, int x, int y, float w_x, float w_y, Insets insets, int anchor, int fill) {
		constraint.gridwidth = width;
		constraint.gridx = x;
		constraint.gridy = y;
		constraint.insets = insets;
		constraint.weightx = w_x;
		constraint.weighty = w_y;
		constraint.anchor = anchor;
		constraint.fill = fill;
	}
}
