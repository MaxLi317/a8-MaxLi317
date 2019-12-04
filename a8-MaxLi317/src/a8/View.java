package a8;


import JSpotBoard.JSpotBoard;
import JSpotBoard.Spot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class View extends JPanel {

	JPanel resetMessagePanel;
    private JSpotBoard _board;        /* SpotBoard playing area. */

    JLabel size = new JLabel("Size:");
    JTextField sizeField = new JTextField(Model.width + "*" + Model.height);
    JLabel warning = new JLabel("Invalid Input!");
    JButton reset = new JButton("Restart");
    JButton resize = new JButton("Resize");

    JLabel position = new JLabel("position");
    String posText = "";
    JTextField positionField = new JTextField();
    JLabel state = new JLabel("State");
    JTextField stateField = new JTextField();
    String stateText = "";
    JButton setPosAndSta = new JButton("Update");
    
    JButton randomize = new JButton("Randomize");
    JButton allTrue = new JButton("Fill all");
    JButton allFalse = new JButton("Deplete all");

    JLabel low = new JLabel("Low Bound:");
    JTextField lowField = new JTextField(Model.low + "");
    JLabel high = new JLabel("High Bound:");
    JTextField highField = new JTextField(Model.high + "");
    JLabel delay = new JLabel("Delay");
    JTextField delayField = new JTextField(Controller.delay + "");
    JLabel torus = new JLabel("Torus");
    JTextField torusField = new JTextField((Model.torus + "").substring(0, 1));
    JButton setOther = new JButton("SetOther");
    JButton Continue = new JButton("Continue");
    JButton Suspend = new JButton("Suspend");
    JButton NextStep = new JButton("NextStep");

    public View() {
    	Dimension dim = new Dimension(100, 100);
        /* Create SpotBoard and message label. */

        _board = new JSpotBoard(Model.width, Model.height);

        /* Set layout and place SpotBoard at center. */

        setLayout(new BorderLayout());
        add(_board, BorderLayout.CENTER);

        /* Create subpanel for message area and reset button. */

        resetMessagePanel = new JPanel();
        resetMessagePanel.setLayout(new BoxLayout(resetMessagePanel, BoxLayout.Y_AXIS));
        
        JPanel header = new JPanel();
        JLabel headerText = new JLabel("Control Panel");
        header.add(headerText);

        
        JPanel sizePanel = new JPanel();
        sizePanel.setLayout(new BoxLayout(sizePanel, BoxLayout.X_AXIS));
        sizePanel.add(size);
        sizePanel.add(sizeField);
        sizeField.setMaximumSize(new Dimension(100, 100));
        
        JPanel sizeController = new JPanel();
        sizeController.setLayout(new BoxLayout(sizeController, BoxLayout.Y_AXIS));
        sizeController.add(sizePanel);
        sizeController.add(warning);
        sizeController.add(resize);
        warning.setForeground(new Color(255, 0, 0));
        warning.setVisible(false);
        
        resetMessagePanel.add(header);

        resetMessagePanel.add(sizeController);
        div();
        
        JPanel positionPanel = new JPanel();
        positionPanel.setLayout(new BoxLayout(positionPanel, BoxLayout.Y_AXIS));
        
        JPanel positionInput = new JPanel();
        positionInput.setLayout(new BoxLayout(positionInput, BoxLayout.Y_AXIS));
        positionPanel.add(positionInput);
        
        JPanel widthWidget = new JPanel();
        widthWidget.setLayout(new BoxLayout(widthWidget, BoxLayout.X_AXIS));
        widthWidget.add(new JLabel("X:"));
        JTextField widthIn = new JTextField();
        widthIn.setMaximumSize(new Dimension(100, 100));
        widthWidget.add(widthIn);
        positionInput.add(widthWidget);
        
        JPanel heightWidget = new JPanel();
        heightWidget.setLayout(new BoxLayout(heightWidget, BoxLayout.X_AXIS));
        heightWidget.add(new JLabel("Y:"));
        JTextField heightIn = new JTextField();
        heightIn.setMaximumSize(new Dimension(100, 100));
        heightWidget.add(heightIn);
        positionInput.add(heightWidget);
        
        JPanel statusWidget = new JPanel();
        statusWidget.setLayout(new BoxLayout(statusWidget, BoxLayout.X_AXIS));
        statusWidget.add(new JLabel("Alive?"));
        JTextField statusIn = new JTextField();
        statusIn.setMaximumSize(new Dimension(100, 100));
        statusWidget.add(statusIn);
        positionPanel.add(statusWidget);
        
        
        resetMessagePanel.add(positionPanel);
        resetMessagePanel.add(setPosAndSta);
        div();
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        buttons.add(new JLabel("Magic Buttons:)"));
        buttons.add(randomize);
        buttons.add(allTrue);
        buttons.add(allFalse);
        resetMessagePanel.add(buttons);
        div();
        
        JPanel boundPanel = new JPanel();
        boundPanel.setLayout(new BoxLayout(boundPanel, BoxLayout.Y_AXIS));
        JPanel lowBound = new JPanel();
        lowBound.setLayout(new BoxLayout(lowBound, BoxLayout.X_AXIS));
        lowBound.add(low);
        lowBound.add(lowField);
        lowField.setMaximumSize(new Dimension(100, 100));
        boundPanel.add(lowBound);
        
        JPanel highBound = new JPanel();
        highBound.setLayout(new BoxLayout(highBound, BoxLayout.X_AXIS));
        highBound.add(high);
        highBound.add(highField);
        highField.setMaximumSize(dim);
        boundPanel.add(highBound);
        
        resetMessagePanel.add(boundPanel);
        
        JPanel miscPanel = new JPanel();
        miscPanel.setLayout(new BoxLayout(miscPanel, BoxLayout.Y_AXIS));
        JPanel delayPanel = new JPanel();
        delayPanel.setLayout(new BoxLayout(delayPanel, BoxLayout.X_AXIS));
        delayPanel.add(delay);
        delayPanel.add(delayField);
        delayField.setMaximumSize(dim);
        miscPanel.add(delayPanel);
        
        JPanel torusPanel = new JPanel();
        torusPanel.setLayout(new BoxLayout(torusPanel, BoxLayout.X_AXIS));
        torusPanel.add(torus);
        torusPanel.add(torusField);
        torusField.setMaximumSize(dim);
        miscPanel.add(torusPanel);
        resetMessagePanel.add(miscPanel);
        
        resetMessagePanel.add(setOther);
        div();
        resetMessagePanel.add(reset);
        resetMessagePanel.add(Suspend);
        resetMessagePanel.add(Continue);
        resetMessagePanel.add(NextStep);

        sizeField.setActionCommand("dddd");
        
        setPosAndSta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String widthText = widthIn.getText();
				String heightText = heightIn.getText();
				if (widthText.equals("") || heightText.equals("")) {
					View.this.posText = "no input";
				} else {
					View.this.posText = widthText + "," + heightText;
				}
				String statusText = statusIn.getText().toLowerCase();
				if (!(statusText.equals("true") || statusText.equals("false"))) {
					View.this.stateText = "empty";
				}
				if (statusText.equals("true")) {
					View.this.stateText = "t";
				} else if (statusText.equals("false")) {
					View.this.stateText = "f";
				}
				Controller.setPosAndSta(e);
			} 
        	
        });
        
        randomize.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				View.this.posText = "";
				View.this.stateText = "randomize";
				Controller.setPosAndSta(e);
			}
        	
        });
        
        allTrue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				View.this.posText = "";
				View.this.stateText = "t";
				Controller.setPosAndSta(e);
			}
        	
        });
        
        allFalse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				View.this.posText = "";
				View.this.stateText = "f";
				Controller.setPosAndSta(e);
			}
        	
        });

        reset.addActionListener(Controller::reset);
        resize.addActionListener(Controller::reset);        
        setOther.addActionListener(Controller::setOther);
        NextStep.addActionListener(Controller::nextStep);
        Continue.addActionListener(Controller::_continue);
        Suspend.addActionListener(Controller::suspend);
        /* Add subpanel in south area of layout. */

        add(resetMessagePanel, BorderLayout.EAST);

        _board.addSpotListener(new Controller());

    }
    
    void div() {
    	this.resetMessagePanel.add(new JLabel("-----------------"));
    }

    void update() {
        Model.allPositions.get()
                .forEach(position -> {
                    Spot s = _board.getSpotAt(position.x, position.y);
                    if (Model.getCellAlive(position))
                        s.setSpot();
                    else
                        s.clearSpot();
                });
    }

}