package ui.swing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import model.*;
import controller.*;
import util.*;

public class MainFrame extends JFrame implements ui.IMainFrame, KeyListener, ComponentListener, ActionListener, IObserver, WindowListener{
		
	private static final int TOP_MARGIN = 20;
	private static final int LEFT_MARGIN = 5;	
	private IEditorController controller;
	private Composition document;
	private JFileChooser jFileChooser;
	private JMenuItem imageMenuItem;
	private JMenuItem aboutMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem scrollMenuItem;
	private List<Row> rows;
	private ICompositor compositor;
	
	public MainFrame(Composition document, IEditorController controller){		
		super();		
		
		this.document = document;
		this.controller = controller;
		this.document.registerObserver(this);
		this.compositor = new SimpleCompositor();
		
		this.setTitle("Lexi - " + this.getNow());		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 450, 300);
		this.setLayout(new BorderLayout());
		
		JMenuBar menuBar = new JMenuBar();		
		this.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		this.imageMenuItem = new JMenuItem("Insert Image");
		this.imageMenuItem.addActionListener(this);
		mnFile.add(imageMenuItem);		
		
		this.scrollMenuItem = new JMenuItem(Constants.ScrollOnText);
		this.scrollMenuItem.addActionListener(this);
		mnFile.add(this.scrollMenuItem);
	
		this.exitMenuItem = new JMenuItem("Exit");
		this.exitMenuItem.addActionListener(this);
		mnFile.add(exitMenuItem);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		this.aboutMenuItem = new JMenuItem("About");
		this.aboutMenuItem.addActionListener(this);
		mnHelp.add(aboutMenuItem);
		
		// create the status bar panel and shove it down the bottom of the frame
		/* JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(this.getWidth(), 16));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		JLabel statusLabel = new JLabel("status");
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel); */
				
		this.addKeyListener(this);
		this.addComponentListener(this);
		this.addWindowListener(this);
		
		this.setVisible(true);		
	}	

	@Override
	public void componentResized(ComponentEvent e) {		
		this.repaint();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		this.repaint();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {		
			KeyPressedEventArgs param = new KeyPressedEventArgs(new SwingGraphics(this.getGraphics()), this.getTop(), this.getLeft(), this.getContentPane().getWidth(),
					this.getContentPane().getHeight(), e, this.getFont());
			this.controller.onKeyPressed(param);
			ViewEventArgs args = new ViewEventArgs(new SwingGraphics(this.getGraphics()), this.getTop(), this.getLeft(), this.getWidth(),
					this.getHeight());
			if (e.getKeyCode() == KeyEvent.VK_PAGE_UP || e.getKeyCode() == KeyEvent.VK_PAGE_DOWN){
				//if (this.controller.getLogicalDocument().needScrolling(args)){
					this.repaint();
				//}
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		ViewEventArgs param = new ViewEventArgs(new SwingGraphics(this.getGraphics()), this.getTop(), this.getLeft(), this.getWidth(),
				this.getHeight());
		List<Row> rows = this.compositor.compose(this.document.getChildren(), param);
		// this.controller.getLogicalDocument().setRows(rows);
		System.out.println("-->");
		this.controller.getLogicalDocument().draw(rows, param);
		//this.controller.onComponentResized(param);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		if (e.getSource().equals(this.imageMenuItem)){
			this.onInsertImageMenuItemClick(e);
		}
		else if (e.getSource().equals(this.scrollMenuItem)){
			this.handleScrolling();
		}
		else if (e.getSource().equals(this.aboutMenuItem)){
			JOptionPane.showMessageDialog(this, "Lext editor implementation\nDeveloper: Amit Dutta" +
					"\nEmail: adutta@cis.uab.edu\nWeb: http://www.amitdutta.net", "Lexi", JOptionPane.INFORMATION_MESSAGE);
		}
		else if (e.getSource().equals(this.exitMenuItem)){
			this.document.removeObserver(this);
			this.dispose();
		}
	}
	
	@Override
	public void update(ModelChangedEventArgs args) {	
		ViewEventArgs param = new ViewEventArgs(new SwingGraphics(this.getGraphics()), this.getTop(), this.getLeft(), this.getContentPane().getWidth(),
				this.getContentPane().getHeight());
		List<Row> rows = this.compositor.compose(args.getGlyphs(), param);
		// this.controller.getLogicalDocument().setRows(rows);
		this.controller.getLogicalDocument().draw(rows, param);		
	}
		
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		this.document.removeObserver(this);		
	}

	@Override
	public void windowClosed(WindowEvent e) {		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void handleScrolling(){
		this.controller.onMenuItemPressed(new MenuPressedEventArgs(this.scrollMenuItem));
		if (this.scrollMenuItem.getText() == Constants.ScrollOffText){
			this.scrollMenuItem.setText(Constants.ScrollOnText);
		}
		else{
			this.scrollMenuItem.setText(Constants.ScrollOffText);
		}
		
		this.repaint();
	}
	
	private String getNow(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	private JFileChooser getJFileChooser(){
		if (this.jFileChooser == null){
			this.jFileChooser = new JFileChooser();			
		}
		
		return this.jFileChooser;
	}
	
	private void onInsertImageMenuItemClick(ActionEvent evt){
		if(this.getJFileChooser().showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
			try {
				BufferedImage image = ImageIO.read(this.getJFileChooser().getSelectedFile());
				InsertImageEventArgs args = new InsertImageEventArgs(new SwingGraphics(this.getGraphics()), this.getTop(), this.getLeft(), this.getContentPane().getWidth(),
						this.getContentPane().getHeight(), image);
				this.controller.onImageInserted(args);
				
			}catch (IOException ex){
				ex.printStackTrace();				
			}
		}
	}
	
	private int getLeft(){
		return this.getInsets().left + LEFT_MARGIN;
	}
	
	private int getTop(){
		return this.getInsets().top + this.getJMenuBar().getHeight() + TOP_MARGIN;
	}		
}
