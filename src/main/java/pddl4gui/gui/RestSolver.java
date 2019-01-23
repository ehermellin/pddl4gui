package pddl4gui.gui;

import pddl4gui.gui.panel.LogPanel;
import pddl4gui.gui.panel.ResultPanel;
import pddl4gui.gui.panel.rest.InfoRestPanel;
import pddl4gui.gui.panel.rest.MenuRestPanel;
import pddl4gui.gui.panel.rest.SetupRestPanel;
import pddl4gui.gui.tools.TriggerAction;
import pddl4gui.gui.tools.WindowsManager;

import java.io.Serializable;
import javax.swing.JFrame;

/**
 * This class implements the RestSolver class of <code>PDDL4GUI</code>.
 * This JFrame displays all the Panel used in PDDL4GUI (REST). It's the main JFrame.
 *
 * @author E. Hermellin
 * @version 1.0 - 12.02.2018
 */
public class RestSolver extends JFrame implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Create a new RestSolver.
     */
    public RestSolver() {
        final int width = 1200;
        final int height = 835;
        final int marging = 10;

        setLayout(null);
        setSize(width, height);
        setTitle(WindowsManager.NAME + " | REST solver");
        WindowsManager.setPoint(this.getLocation());
        WindowsManager.setWidth(width);
        WindowsManager.setHeight(height);

        final SetupRestPanel setupRestPanel;
        final MenuRestPanel menuRestPanel;
        final InfoRestPanel infoRestPanel;
        final ResultPanel resultPanel;
        final LogPanel logPanel;

        setupRestPanel = new SetupRestPanel();
        setupRestPanel.setBounds(marging, marging, 360, 570);
        add(setupRestPanel);

        menuRestPanel = new MenuRestPanel();
        this.setJMenuBar(menuRestPanel);

        infoRestPanel = new InfoRestPanel();
        infoRestPanel.setBounds(380, marging, 300, 570);
        add(infoRestPanel);

        resultPanel = new ResultPanel();
        resultPanel.setBounds(690, marging, 500, 570);
        add(resultPanel);

        logPanel = new LogPanel();
        logPanel.setBounds(marging, 585, 1180, 175);
        add(logPanel);

        TriggerAction.setupPanel(resultPanel, menuRestPanel, infoRestPanel);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
