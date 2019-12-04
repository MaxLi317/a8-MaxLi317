package a8;

import java.awt.BorderLayout;

import javax.swing.*;

public class StartGame {
	public static void main(String[] args) {
        /* Create top level window. */
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Example");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Create panel for content. Uses BorderLayout. */
        
        JPanel top_panel = new JPanel();
        top_panel.setLayout(new BorderLayout());
        mainFrame.setContentPane(top_panel);
        
        
        View view = new View();
        Controller.setPanel(top_panel);
        Controller.setView(view);
        /* Create ExampleWidget component and put into center
         * of content panel.
         */
        top_panel.add(view, BorderLayout.CENTER);

        /* Pack main frame and make visible. */

        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}

class StartGamePopUpPanel extends JPanel {
	
}