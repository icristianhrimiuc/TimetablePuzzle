package timetablepuzzle.swing.windows.cards;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class HomeCard extends JPanel{
	private static final long serialVersionUID = 1L;
	private static final Color foregroundColor = Color.BLACK;
	private static final Font foregroundFont = new Font("Courier New",Font.BOLD, 14);
	private static final Insets  textAreaMarginDimensions = new Insets(50,100,50,100);
	
	private String backgroundImageFilePath;
	private String textToDisplayFilePath;
	private Image backgroundImage;
	private String welcomeText;
	
	public HomeCard(String backgroundImageFilePath, String textToDisplayFilePath)
	{
		this.backgroundImageFilePath = backgroundImageFilePath;
		this.textToDisplayFilePath = textToDisplayFilePath;
		LoadImageFromFile();
		LoadTextFromFile();		
		SetHomeCardComponents();		
	}
	
	private void LoadImageFromFile()
	{        
        try {
        	TryToLoadImageFromFile();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void TryToLoadImageFromFile() throws InterruptedException
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		MediaTracker mediaTracker = new MediaTracker(this);
		backgroundImage = toolkit.getImage(backgroundImageFilePath);
        mediaTracker.addImage(backgroundImage, 0);
		mediaTracker.waitForAll();
	}
	
	private void LoadTextFromFile()
	{
		try {
			welcomeText = new String(Files.readAllBytes(Paths.get(textToDisplayFilePath)),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void SetHomeCardComponents()
	{
		this.setLayout(new BorderLayout());	
		this.add(CreateWelcomeScrollPane(CreateWelcomeTextArea()));
	}
	
	private JTextArea CreateWelcomeTextArea()
	{
		JTextArea jtaWelcome = new JTextArea();
		jtaWelcome.setText(welcomeText);		
		jtaWelcome.setOpaque(false);
		jtaWelcome.setForeground(foregroundColor);
		jtaWelcome.setFont(foregroundFont);
		jtaWelcome.setMargin(textAreaMarginDimensions);
		jtaWelcome.setLineWrap(true);
		jtaWelcome.setWrapStyleWord(true);
		jtaWelcome.setEditable(false);
		jtaWelcome.setHighlighter(null);
	
		return jtaWelcome;
	}
	
	private JScrollPane CreateWelcomeScrollPane(JTextArea jtaWelcome)
	{
		JScrollPane welcomeScrollPane = new JScrollPane(jtaWelcome);
		welcomeScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		welcomeScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		welcomeScrollPane.getViewport().setOpaque(false);
		welcomeScrollPane.setOpaque(false);
		
		return welcomeScrollPane;
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);       

        if (BackgroundImageIsSet()) {
            g.drawImage(backgroundImage, GetX(), GetY(), this);
        }
    }
	
	private boolean BackgroundImageIsSet()
	{
		return backgroundImage != null;
	}
	
	private int GetX(){
		return (this.getWidth() - backgroundImage.getWidth(null)) / 2;
	}
	
	private int GetY(){
		return (this.getHeight() - backgroundImage.getHeight(null)) / 2;
	}
}
