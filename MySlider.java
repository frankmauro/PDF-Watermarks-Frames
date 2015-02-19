import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class MySlider extends JSlider {
	
	public MySlider() {
		super();
	}
	
	// Creo la slider per selezionare la scala dell'immagine
	public void setScale(MyFrame frame) {
		// Al trascinamento dello slider, ne salvo il nuovo valore
		this.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				MyApplication.source = (JSlider)arg0.getSource();
			}
		});
		this.setMinorTickSpacing(1);
		this.setPaintLabels(true);
		this.setValue(5);
		this.setMaximum(10);
		this.setPaintTicks(true);
		
		// Creo una Hashtable che contiene i valori e i testi corrispondenti della slider
		Hashtable<Integer, JLabel> sliderLabel = new Hashtable<Integer, JLabel>();
		sliderLabel.put(new Integer(0), new JLabel("Invisible"));
		sliderLabel.put(new Integer(1), new JLabel(""));
		sliderLabel.put(new Integer(2), new JLabel(""));
		sliderLabel.put(new Integer(3), new JLabel(""));
		sliderLabel.put(new Integer(4), new JLabel(""));
		sliderLabel.put(new Integer(5), new JLabel("Half"));
		sliderLabel.put(new Integer(6), new JLabel(""));
		sliderLabel.put(new Integer(7), new JLabel(""));
		sliderLabel.put(new Integer(8), new JLabel(""));
		sliderLabel.put(new Integer(9), new JLabel(""));
		sliderLabel.put(new Integer(10), new JLabel("Full"));
		
		// Applico la Hashtable alla slider
		this.setLabelTable(sliderLabel);
		
		GridBagConstraints gbc_sliderScale = new GridBagConstraints();
		frame.setConstraints(gbc_sliderScale, 1, 0, 5, 0.3f, 0.15f, new Insets(5, 5, 5, 5), GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		frame.add(this, gbc_sliderScale);
	}
	
	// Creo la slider per selezionare l'opacità dell'immagine
	public void setOpacity(MyFrame frame) {
		// Al trascinamento dello slider, ne salvo il nuovo valore
		this.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				MyApplication.sourceOp = (JSlider)arg0.getSource();
			}
		});
		this.setMinorTickSpacing(1);
		this.setPaintLabels(true);
		this.setValue(5);
		this.setMaximum(10);
		this.setPaintTicks(true);
		
		// Creo una Hashtable che contiene i valori e i testi corrispondenti della slider
		Hashtable<Integer, JLabel> sliderOpLabel = new Hashtable<Integer, JLabel>();
		sliderOpLabel.put(new Integer(0), new JLabel("Invisible"));
		sliderOpLabel.put(new Integer(1), new JLabel(""));
		sliderOpLabel.put(new Integer(2), new JLabel(""));
		sliderOpLabel.put(new Integer(3), new JLabel(""));
		sliderOpLabel.put(new Integer(4), new JLabel(""));
		sliderOpLabel.put(new Integer(5), new JLabel("Half"));
		sliderOpLabel.put(new Integer(6), new JLabel(""));
		sliderOpLabel.put(new Integer(7), new JLabel(""));
		sliderOpLabel.put(new Integer(8), new JLabel(""));
		sliderOpLabel.put(new Integer(9), new JLabel(""));
		sliderOpLabel.put(new Integer(10), new JLabel("Full"));
		
		// Applico la Hashtable alla slider
		this.setLabelTable(sliderOpLabel);
		
		GridBagConstraints gbc_sliderOpacity = new GridBagConstraints();
		frame.setConstraints(gbc_sliderOpacity, 1, 1, 5, 0.3f, 0.15f, new Insets(5, 5, 5, 5), GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		frame.add(this, gbc_sliderOpacity);
	}
}
