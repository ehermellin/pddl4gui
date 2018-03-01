package pddl4gui.gui.panel;

import pddl4gui.gui.Solver;
import pddl4gui.gui.VAL;
import pddl4gui.gui.tools.FileTools;
import pddl4gui.gui.tools.Icons;
import pddl4gui.gui.tools.TokenList;

import javax.swing.*;
import java.io.File;

public class MenuSolverPanel extends JPanel {

    final private JButton valButton, saveTxtButton, saveJsonButton;

    public JButton getValButton() {
        return valButton;
    }

    public JButton getSaveTxtButton() {
        return saveTxtButton;
    }

    public JButton getSaveJsonButton() {
        return saveJsonButton;
    }

    public MenuSolverPanel(Solver parent) {
        valButton = new JButton(Icons.getValidateIcon());
        valButton.setBounds(10, 10, 40, 40);
        valButton.setToolTipText("VAL on solution");
        valButton.setEnabled(false);
        valButton.addActionListener(e -> {
            if (parent.getTokenListPanel().getTokenJList().getSelectedValue().isSolved()) {
                new VAL(parent.getTokenListPanel().getTokenJList().getSelectedValue());
            }
        });
        add(valButton);

        saveTxtButton = new JButton(Icons.getSaveIcon());
        saveTxtButton.setBounds(50, 10, 40, 40);
        saveTxtButton.setToolTipText("Save solution (txt)");
        saveTxtButton.addActionListener(e -> {
            if (parent.getTokenListPanel().getTokenJList().getSelectedValue().isSolved()) {
                File tempFile = FileTools.saveFile(this, 1);
                if (!FileTools.checkFile(tempFile)) {
                    FileTools.writeInFile(tempFile, parent.getTokenListPanel().getTokenJList()
                            .getSelectedValue().getResult().getSolutionString());
                }
            }
        });
        saveTxtButton.setEnabled(false);
        add(saveTxtButton);

        saveJsonButton = new JButton(Icons.getSaveIcon());
        saveJsonButton.setBounds(110, 10, 40, 40);
        saveJsonButton.setToolTipText("Save solution (json)");
        saveJsonButton.addActionListener(e -> {
            if (parent.getTokenListPanel().getTokenJList().getSelectedValue().isSolved()) {
                File tempFile = FileTools.saveFile(this, 5);
                if (!FileTools.checkFile(tempFile)) {
                    FileTools.writeInFile(tempFile, parent.getTokenListPanel().getTokenJList()
                            .getSelectedValue().getResult().getSolutionJSON());
                }
            }
        });
        saveJsonButton.setEnabled(false);
        add(saveJsonButton);

        final JButton resetButton = new JButton(Icons.getResetIcon());
        resetButton.setBounds(160, 10, 40, 40);
        resetButton.setToolTipText("Reset the solver");
        resetButton.setEnabled(true);
        resetButton.addActionListener(e -> {
            valButton.setEnabled(false);
            saveTxtButton.setEnabled(false);
            saveJsonButton.setEnabled(false);
            parent.getResultPanel().clearResult();
            parent.getStatisticsPanel().clearStats();
            parent.getEngine().getTokenList().clear();
            TokenList.getListModel().clear();
        });
        add(resetButton);

        JButton exitButton = new JButton(Icons.getExitIcon());
        exitButton.setBounds(210, 10, 40, 40);
        exitButton.setToolTipText("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);
    }
}
