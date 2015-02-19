import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JSlider;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.alee.extended.label.WebHotkeyLabel;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.label.WebLabel;

public class MyApplication {
	static File selectedPDF, selectedImg, selectedFrame;
	static String selectedPDFName, selectedImgName;
	static BufferedImage selectedImgBuffer;
	static JSlider source, sourceOp;
	static PDDocument selectedPDFDocument;
	static WebHotkeyLabel lblSelectedPictureFrame;
	
	public static void main(String[] args) {
		// Installo Look and Feel "WebLookAndFeel"
		WebLookAndFeel.install();
		WebLookAndFeel.setDecorateDialogs(true);
		
		// Creo e rendo visibile la finestra principale dell'applicazione		
		MyFrame mainFrame = new MyFrame("PDF Watermarks & Frames", new int[] {1, 1, 1}, new int[] {200, 1, 1, 1, 1, 1, 1});
		mainFrame.setMinimumSize(new Dimension(900, 650));
		mainFrame.setVisible(true);
		
		// Inserisco il logo
		mainFrame.setLogo();
		
		// Inserisco label, bottoni, e sliders
		WebHotkeyLabel lblSelectPDF = new WebHotkeyLabel("Select a PDF File");
		mainFrame.setLabel(lblSelectPDF, 0, 1, 0.3f, 0.15f, GridBagConstraints.CENTER, GridBagConstraints.NONE);
		
		WebHotkeyLabel lblSelectedPDF = new WebHotkeyLabel("No PDF selected");
		mainFrame.setLabel(lblSelectedPDF, 2, 1, 0.4f, 0.15f, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		
		WebHotkeyLabel lblSelectImage = new WebHotkeyLabel("Select an Image");
		mainFrame.setLabel(lblSelectImage, 0, 2, 0.3f, 0.15f, GridBagConstraints.CENTER, GridBagConstraints.NONE);
		
		WebHotkeyLabel lblSelectedImage = new WebHotkeyLabel("No image selected");
		mainFrame.setLabel(lblSelectedImage, 2, 2, 0.4f, 0.15f, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		
		WebHotkeyLabel lblSelectPictureFrame = new WebHotkeyLabel("Select a Picture Frame");
		mainFrame.setLabel(lblSelectPictureFrame, 0, 3, 0.3f, 0.15f, GridBagConstraints.CENTER, GridBagConstraints.NONE);
		
		lblSelectedPictureFrame = new WebHotkeyLabel("No Frame selected");
		mainFrame.setLabel(lblSelectedPictureFrame, 2, 3, 0.4f, 0.15f, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		
		WebLabel lblScaleRatio = new WebLabel("Watermark Scale Ratio");
		mainFrame.setLabel(lblScaleRatio, 0, 4, 0.3f, 0.15f, GridBagConstraints.SOUTH, GridBagConstraints.NONE);
		
		WebLabel lblOpacityRatio = new WebLabel("Watermark Opacity Ratio");
		mainFrame.setLabel(lblOpacityRatio, 1, 4, 0.3f, 0.15f, GridBagConstraints.SOUTH, GridBagConstraints.NONE);
		
		MyButton btnSelectPDF = new MyButton("Select PDF File", new ImageIcon("img" + System.getProperty("file.separator") + "pdf.png"));
		btnSelectPDF.selectPDF(mainFrame, lblSelectedPDF);
		
		MyButton btnSelectImage = new MyButton("Select an image", new ImageIcon("img" + System.getProperty("file.separator") + "image.png"));
		btnSelectImage.selectImage(mainFrame, lblSelectedImage);
		
		MyButton btnSelectPictureFrame = new MyButton("Select a Frame", new ImageIcon("img" + System.getProperty("file.separator") + "frame.png"));
		btnSelectPictureFrame.selectPictureFrame(mainFrame, lblSelectedPictureFrame);
		
		MyButton btnApply = new MyButton("Apply Watermark & Frame", new ImageIcon("img" + System.getProperty("file.separator") + "save.png"));
		btnApply.applyThumbnail(mainFrame);
		
		MySlider sliderScale = new MySlider();
		sliderScale.setScale(mainFrame);
		
		MySlider sliderOpacity = new MySlider();
		sliderOpacity.setOpacity(mainFrame);
		
		WebLabel lblCopyright = new WebLabel("(C) 2015 - Marcello Silvestri and Francesco Mauro");
		mainFrame.setLabel(lblCopyright, 1, 6, 0.3f, 0.15f, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL);
		
		// Utilizzo la funzione pack per adattare i componenti della finestra in base al layout
		mainFrame.pack();
	}

}
