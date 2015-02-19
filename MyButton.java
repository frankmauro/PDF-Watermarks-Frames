import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.pdfbox.Overlay;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.image.WebImage;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;

@SuppressWarnings("serial")
public class MyButton extends WebButton {
	
	public MyButton(String string) {
		super(string);
	}
	
	public MyButton(String string, ImageIcon icon) {
		super(string, icon);
	}
	
	// Implementa pulsante per selezionare il PDF su cui lavorare
	public void selectPDF(MyFrame frame, JLabel label) {
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Creo un JFileChooser limitato alla scelta di soli file con estensione PDF
				JFileChooser fc = new JFileChooser(System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Select a PDF Document", "pdf");
				fc.setFileFilter(filter);
				fc.setAcceptAllFileFilterUsed(false);
				
				// Eseguo un'azione nel caso in cui ho selezionato un file
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					MyApplication.selectedPDF = fc.getSelectedFile();
					MyApplication.selectedPDFName = MyApplication.selectedPDF.getName();
					label.setText("You selected: \"" + MyApplication.selectedPDFName + "\"");
				}
			}
		});
		GridBagConstraints gbc_selectPDF = new GridBagConstraints();
		frame.setConstraints(gbc_selectPDF, 1, 1, 1, 0.3f, 0.15f, new Insets(5, 5, 5, 5), GridBagConstraints.CENTER, GridBagConstraints.NONE);
		frame.add(this, gbc_selectPDF);
	}
	
	// Implementa pulsante per selezionare l'immagine watermark da applicare al PDF
	public void selectImage (MyFrame frame, JLabel label) {
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Creo un JFileChooser limitato alla scelta di soli file con estensione JPG e PNG
				JFileChooser fc = new JFileChooser(System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Select a JPG or PNG image", "jpg", "png");
				fc.setFileFilter(filter);
				fc.setAcceptAllFileFilterUsed(false);
				
				// Eseguo un'azione nel caso in cui ho selezionato un file
	            int returnVal = fc.showOpenDialog(null);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	            	MyApplication.selectedImg = fc.getSelectedFile();
	            	MyApplication.selectedImgName = MyApplication.selectedImg.getName();
	                label.setText("You selected: \"" + MyApplication.selectedImgName + "\"");
	                try {
	                	// Prendo l'immagine selezionata e la converto in Image Buffer per lavorarci successivamente
						MyApplication.selectedImgBuffer = ImageIO.read(MyApplication.selectedImg);
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
			}
		});
		GridBagConstraints gbc_selectImg = new GridBagConstraints();
		frame.setConstraints(gbc_selectImg, 1, 1, 2, 0.3f, 0.15f, new Insets(5, 5, 5, 5), GridBagConstraints.CENTER, GridBagConstraints.NONE);
		frame.add(this, gbc_selectImg);
	}
	
	// Implementa pulsante per selezionare l'eventuale cornice da aggiungere ai bordi del PDF
	public void selectPictureFrame (MyFrame frame, JLabel label) {
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Creo e rendo visibile il frame contenente le cornici
				MyFrame picturesFrame = new MyFrame("Picture Frames Chooser");
				
				ImageIcon frameLogo = new ImageIcon("img" + System.getProperty("file.separator") + "selectaframe.png");
				WebImage imgFrameLogo = new WebImage(frameLogo);
				picturesFrame.add(imgFrameLogo);
				
				// Eseguo un loop tra un numero definito di file PNG che rappresentano le cornici
				for (int i = 0; i <= 7; i++) {
					final int iconIndex = i;
					ImageIcon icon = new ImageIcon("img" + System.getProperty("file.separator") + "elaborateFrame" + iconIndex + "-icon.png");
					WebDecoratedImage img = new WebDecoratedImage(icon);
					
					// Assegno l'immagine e il pulsante per aggiungerla ad un JPanel dedicato, per aggirare i problemi di layout
					JPanel panel = new JPanel();
					panel.add(img);
					MyButton button = new MyButton("Select Frame", new ImageIcon("img" + System.getProperty("file.separator") + "selected.png"));
					button.setAlignmentX(Component.CENTER_ALIGNMENT);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg1) {
							if (iconIndex == 0) {
								MyApplication.selectedFrame = null;
								picturesFrame.setVisible(false);
								picturesFrame.dispose();
								MyApplication.lblSelectedPictureFrame.setText("Select a Frame");
							} else {
								MyApplication.selectedFrame = new File("img" + System.getProperty("file.separator") + "elaborateFrame" + iconIndex + ".png");
								picturesFrame.setVisible(false);
								picturesFrame.dispose();
								MyApplication.lblSelectedPictureFrame.setText("You selected: elaborateFrame" + iconIndex + ".png");
							}
						}
					});
					panel.add(button);
					panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
					picturesFrame.add(panel);
				}
				MyButton btnImportFrame = new MyButton("Import Frame", new ImageIcon("img" + System.getProperty("file.separator") + "plus.png"));
				btnImportFrame.importFrame(picturesFrame);
				picturesFrame.setVisible(true);
			}
		});
		GridBagConstraints gbc_selectPictureFrame = new GridBagConstraints();
		frame.setConstraints(gbc_selectPictureFrame, 1, 1, 3, 0.3f, 0.15f, new Insets(5, 5, 5, 5), GridBagConstraints.CENTER, GridBagConstraints.NONE);
		frame.add(this, gbc_selectPictureFrame);
	}
	
	// Aggiunge il pulsante di importazione di cornici ed un separatore
	public void importFrame(MyFrame frame) {
		setPreferredSize(new Dimension(200, 40));
		
		// Aggiungo un separatore
		JSeparator separator = new JSeparator();
		separator.setPreferredSize(new Dimension(frame.getWidth(), 10));
		frame.add(separator);
		
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Creo un JFileChooser limitato alla scelta di soli file con estensione PNG
				JFileChooser fc = new JFileChooser(System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Select a PNG file", "png");
				fc.setFileFilter(filter);
				fc.setAcceptAllFileFilterUsed(false);
				
				// Eseguo un'azione nel caso in cui ho selezionato un file
	            int returnVal = fc.showOpenDialog(null);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	            	MyApplication.selectedFrame = fc.getSelectedFile();
	            	frame.setVisible(false);
	            	frame.dispose();
	            	MyApplication.lblSelectedPictureFrame.setText("You selected: " + MyApplication.selectedFrame.getName());
	            }
			}
		});
		
		frame.add(this);
	}
	
	// Implementa pulsante per applicare Watermark e eventuale cornice selezionati al PDF
	public void applyThumbnail (MyFrame frame) {
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (MyApplication.selectedPDF != null && MyApplication.selectedImg != null) {
					try {
						MyApplication.selectedPDFDocument = PDDocument.load(MyApplication.selectedPDF);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					// Creo il documento temporaneo watermarkDoc e una lista contenente tutte le pagine del mio PDF
					PDDocument watermarkDoc = new PDDocument();
					List paginePDF = MyApplication.selectedPDFDocument.getDocumentCatalog().getAllPages();
					Iterator iter = paginePDF.iterator();
										
					// Ottengo valori di scala e opacità
					float scale = MyApplication.source.getValue();
					float opacity = MyApplication.sourceOp.getValue();
					opacity = opacity/10;
					scale = scale/10;
					
					// Creo una BufferedImage temporanea con le stesse dimensioni dell'immagine selezionata ma di tipo ARGB, ossia che comprende informazioni anche sul canale Alpha per gestirne l'opacità
					BufferedImage tempImage = new BufferedImage(MyApplication.selectedImgBuffer.getWidth(), MyApplication.selectedImgBuffer.getHeight(), BufferedImage.TYPE_INT_ARGB);
					
					// Creo un oggetto di tipo Graphics2D per ridisegnare l'immagine originale
					Graphics2D g2d = tempImage.createGraphics();
					
					// Applico l'opacità selezionata al mio oggetto g2d
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
					
					// Ridisegno l'immagine originale
					g2d.drawImage(MyApplication.selectedImgBuffer, 0, 0, null);
					
					// Libera le risorse utilizzate da g2d
					g2d.dispose();
					
					// Creo un popOver informativo
					WebPopOver popOver = new WebPopOver();
					popOver.setLayout(new VerticalFlowLayout());
					popOver.setMargin(10);
					ImageIcon iconWait = new ImageIcon("img" + System.getProperty("file.separator") + "wait.png");
					WebImage imgWait = new WebImage(iconWait);
					popOver.add(imgWait);
					popOver.add(new WebLabel("Generating output PDF file..."));
					
					// Mostra il popOver nel momento in cui si verifica l'evento
					popOver.show((WebButton)e.getSource());
					
					// Eseguo un ciclo per ogni singola pagina del PDF originale per applicare watermark e cornici che si adatterrano in base alle dimensioni di ciascuna pagina
					while (iter.hasNext()) {
						// Salvo la pagina attuale e porto avanti l'Iterator
						PDPage pagina = (PDPage)iter.next();
						
						// Ottengo le dimensioni del mediabox e ne ricavo l'oggetto dimPagina
						PDRectangle mediabox = pagina.getMediaBox();
						PDRectangle dimPagina = new PDRectangle(mediabox.getWidth(), mediabox.getHeight());
						
						// Creo la pagina in cui sarà posizionato il watermark, da unire successivamente alla pagina attuale "pagina"
						PDPage paginaWatermark = new PDPage(dimPagina);
						watermarkDoc.addPage(paginaWatermark);
						
						// Apro uno stream per disegnare all'interno di paginaWatermark
						PDPageContentStream stream;						
						try {
							// Inizializzo lo stream sulla paginaWatermark del documento watermarkDoc
							stream = new PDPageContentStream(watermarkDoc, paginaWatermark);
							
							// Creo un oggetto di tipo PDXObjectImage e lo utilizzo per disegnare e posizionare la mia tempImage sulla paginaWatermark
							PDXObjectImage ximage = new PDPixelMap(watermarkDoc, tempImage);
							stream.drawXObject(ximage, ((mediabox.getWidth())/2) - (ximage.getWidth()*scale/2), ((mediabox.getHeight())/2) - (ximage.getHeight()*scale/2), ximage.getWidth()*scale, ximage.getHeight()*scale);
							
							// Se ho selezionato una cornice, eseguo la stessa operazione
							if (MyApplication.selectedFrame != null) {
								// Converto il frame da File a BufferedImage
								BufferedImage tempFrame = ImageIO.read(MyApplication.selectedFrame);
								
								PDXObjectImage ximage2 = new PDPixelMap(watermarkDoc, tempFrame);
								stream.drawXObject(ximage2, 0, 0, mediabox.getWidth(), mediabox.getHeight());
							}
							// Chiudo lo stream
							stream.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					
					// Libero le risorse e quindi chiudo il popOver informativo
					popOver.dispose();
					
					try {
						// Overlay e salvataggio finale
						Overlay overlay = new Overlay();
						PDDocument result = MyApplication.selectedPDFDocument;
						overlay.overlay(watermarkDoc, result);
						watermarkDoc.close();
						
						// Selezione directory di salvataggio
						JFileChooser fc = new JFileChooser(System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop");
						fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
						FileNameExtensionFilter filter = new FileNameExtensionFilter("Save your Thumbnailed PDF Document", "pdf");
						fc.setFileFilter(filter);
						fc.setAcceptAllFileFilterUsed(false);
						int returnVal = fc.showSaveDialog(null);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							File selectedFile = fc.getSelectedFile();
							if (selectedFile != null) {
								String name = selectedFile.getName();
								if (!name.contains(".pdf")) {
									// Se il nome scritto non contiene l'estensione, aggiungi .pdf
									selectedFile = new File(selectedFile.getParentFile(), name + ".pdf");
								}
							}
							
							if (selectedFile.exists()) {
								// Se il file esiste chiedi di sovrascrivere
								int overwrite = JOptionPane.showConfirmDialog(fc, "The file already exists. Overwrite?", "Existing file", JOptionPane.YES_NO_OPTION);
								if (overwrite == JOptionPane.YES_OPTION) {
									result.save(selectedFile);
									JOptionPane.showMessageDialog(frame, "The PDF has been successfully created.", "Successfully exported", JOptionPane.INFORMATION_MESSAGE);
								}
							} else {
								// Se il file non esiste lo salva semplicemente;
								result.save(selectedFile);
								JOptionPane.showMessageDialog(frame, "The PDF has been successfully created.", "Successfully exported", JOptionPane.INFORMATION_MESSAGE);
							}
						}						
						// Chiudo il documento
						result.close();
					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(frame, "Unable to create the PDF file or file already in use.", "Unable to create the PDF", JOptionPane.ERROR_MESSAGE);
					} catch (COSVisitorException | IOException e2) {
						e2.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Please choose both a PDF file and an image to merge.", "Select files to merge", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		GridBagConstraints gbc_applyThumbnail = new GridBagConstraints();
		frame.setConstraints(gbc_applyThumbnail, 1, 2, 5, 0.4f, 0.15f, new Insets(5, 5, 5, 5), GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		frame.add(this, gbc_applyThumbnail);
	}
}
