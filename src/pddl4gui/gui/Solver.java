package pddl4gui.gui;

import pddl4gui.planners.Planner;
import pddl4gui.planners.PlannerFactory;
import pddl4gui.engine.Engine;
import pddl4gui.gui.panel.EngineStatusPanel;
import pddl4gui.gui.panel.MenuSolverPanel;
import pddl4gui.gui.panel.ResultPanel;
import pddl4gui.gui.panel.SetupSolverPanel;
import pddl4gui.gui.panel.StatisticsPanel;
import pddl4gui.gui.panel.TokenListPanel;
import pddl4gui.gui.tools.WindowsManager;
import pddl4gui.token.Token;
import pddl4gui.gui.tools.TokenList;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Vector;

public class Solver extends JFrame {

    private Engine engine;

    final private SetupSolverPanel setupPanel;
    final private StatisticsPanel statisticsPanel;
    final private ResultPanel resultPanel;
    final private MenuSolverPanel menuSolverPanel;
    final private EngineStatusPanel engineStatusPanel;
    final private TokenListPanel tokenListPanel;

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public SetupSolverPanel getSetupPanel() {
        return setupPanel;
    }

    public ResultPanel getResultPanel() {
        return resultPanel;
    }

    public EngineStatusPanel getEngineStatusPanel() {
        return engineStatusPanel;
    }

    public TokenListPanel getTokenListPanel() {
        return tokenListPanel;
    }

    public Solver(Engine engine) {
        this.engine = engine;

        final int width = 1200;
        final int height = 600;
        final int marging = 10;

        setLayout(null);
        setSize(width, height);
        setTitle(WindowsManager.NAME);
        WindowsManager.setPoint(this.getLocation());
        WindowsManager.setWidth(width);

        menuSolverPanel = new MenuSolverPanel(this);
        menuSolverPanel.setBounds(marging, marging, 330, 40);
        add(menuSolverPanel);

        setupPanel = new SetupSolverPanel(this);
        setupPanel.setBounds(marging, 60, 330, 320);
        add(setupPanel);

        tokenListPanel = new TokenListPanel(this);
        tokenListPanel.setBounds(350, 260, 330, 300);
        add(tokenListPanel);

        engineStatusPanel = new EngineStatusPanel(this);
        engineStatusPanel.setBounds(marging, 390, 330, 170);
        add(engineStatusPanel);

        statisticsPanel = new StatisticsPanel();
        statisticsPanel.setBounds(350, marging, 330, 240);
        add(statisticsPanel);

        resultPanel = new ResultPanel();
        resultPanel.setBounds(690, marging, 500, 550);
        add(resultPanel);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void resolve(File domainFile, Vector<File> problemFiles) {
        final Planner planner = PlannerFactory.create(setupPanel.getPlanner(),
                setupPanel.getHeuristic(),
                (double) setupPanel.getWeightSpinner().getValue(),
                (double) setupPanel.getTimeoutSpinner().getValue());

        for(File file : problemFiles) {
            final Token token = new Token(domainFile, file, planner);

            if (token.isRunnable() && engineStatusPanel.getCirclePanel().getColor() != Color.RED) {
                if (!TokenList.getListModel().contains(token)) {
                    TokenList.getListModel().addElement(token);
                    engine.addToken(token);
                }
            }

            engineStatusPanel.setTokensRemaining(engine.getTokenList().size());
        }
    }

    public void displayResult(Token token) {
        resultPanel.displayResult(token);
        statisticsPanel.displayStats(token.getResult().getStatistics());
        menuSolverPanel.getValButton().setEnabled(true);
        menuSolverPanel.getSaveJsonButton().setEnabled(true);
        menuSolverPanel.getSaveTxtButton().setEnabled(true);
    }

    public void displayError(Token token) {
        statisticsPanel.clearStats();
        resultPanel.clearResult();
        resultPanel.displayError(token);
    }

    public void displayProgress(Token token) {
        statisticsPanel.clearStats();
        resultPanel.clearResult();
        resultPanel.diplayProgress(token);
    }

    public void resetSolver(){
        resultPanel.clearResult();
        statisticsPanel.clearStats();
        engine.getTokenList().clear();
        TokenList.getListModel().clear();
    }
}
