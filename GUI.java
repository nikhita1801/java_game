package project.tictactoe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

public class GUI {
    public GUI() {
        this("");
    }

    public GUI(String title) {
        this.setTitle(title);
    }

    public final void setTitle(String title) {
        JFrames.MAIN.get().setTitle(title);
    }

    public void buildGUI() {
        this.setButtonsSize();
        this.addButtonsToPanel();
        this.setGamefieldPanelLayout();
        this.buildRadioButtonsUnit();
        this.buildControlButtonsUnit();
        this.buildControlUnit();
        this.addAllComponents();
        this.setDefaultComponentSettings();
        this.setDefaultActionListeners();
        this.makeGuiReady();
    }

    public void show() {
        JFrames.MAIN.get().setVisible(true);
    }

    public void hide() {
        JFrames.MAIN.get().setVisible(false);
    }

    private void setButtonsSize() {
        for (JButtons button : JButtons.values()) {
            if (button.toString().contains("GAME_")) {
                button.get().setPreferredSize(new Dimension(120, 120));
            }
        }
    }

    private void addButtonsToPanel() {
        JPanels.GAME_FIELD.get().add(JButtons.GAME_TOP_LEFT.get());
        JPanels.GAME_FIELD.get().add(JButtons.GAME_TOP_CENTER.get());
        JPanels.GAME_FIELD.get().add(JButtons.GAME_TOP_RIGHT.get());
        JPanels.GAME_FIELD.get().add(JButtons.GAME_MIDDLE_LEFT.get());
        JPanels.GAME_FIELD.get().add(JButtons.GAME_MIDDLE_CENTER.get());
        JPanels.GAME_FIELD.get().add(JButtons.GAME_MIDDLE_RIGHT.get());
        JPanels.GAME_FIELD.get().add(JButtons.GAME_BOTTOM_LEFT.get());
        JPanels.GAME_FIELD.get().add(JButtons.GAME_BOTTOM_CENTER.get());
        JPanels.GAME_FIELD.get().add(JButtons.GAME_BOTTOM_RIGHT.get());
    }

    private void setGamefieldPanelLayout() {
        int d = 7;
        JPanels.GAME_FIELD.get().setBorder(new EmptyBorder(d, d, d, d));
        JPanels.GAME_FIELD.get().setLayout(new GridLayout(3, 3, d, d));
    }

    private void buildRadioButtonsUnit() {
        ButtonGroups.RADIO_BUTTONS.get().add(JRadioButtons.ONE_PLAYER.get());
        ButtonGroups.RADIO_BUTTONS.get().add(JRadioButtons.TWO_PLAYER.get());
        JPanels.CONTROL_RADIO_BUTTONS.get().add(JLabels.SELECT_PLAYER_COUNT.get());
        JPanels.CONTROL_RADIO_BUTTONS.get().add(JRadioButtons.ONE_PLAYER.get());
        JPanels.CONTROL_RADIO_BUTTONS.get().add(JRadioButtons.TWO_PLAYER.get());
    }

    private void buildControlButtonsUnit() {
        JButtons.START.get().setText("Starten");
        JButtons.RESET.get().setText("Zurücksetzen");
        JButtons.EXIT.get().setText("Beenden");
        JPanels.CONTROL_BUTTONS.get().add(JButtons.START.get());
        JPanels.CONTROL_BUTTONS.get().add(JButtons.RESET.get());
        JPanels.CONTROL_BUTTONS.get().add(JButtons.EXIT.get());
        JPanels.CONTROL_BUTTONS.get().setLayout(new FlowLayout());
    }

    private void buildControlUnit() {
        JPanels.CONTROL.get().add(JLabels.SELECT_PLAYER_COUNT.get());
        JPanels.CONTROL.get().add(JPanels.CONTROL_RADIO_BUTTONS.get());
        JPanels.CONTROL.get().add(JPanels.CONTROL_BUTTONS.get());
        JPanels.CONTROL.get().add(JLabels.USER_INFORMATION_OUTPUT.get());
        JPanels.CONTROL.get().add(JLabels.COPYRIGHT.get());
        JPanels.CONTROL.get().setLayout(new GridLayout(JPanels.CONTROL.get().getComponentCount(), 1));
    }

    private void addAllComponents() {
        JFrames.MAIN.get().add(JPanels.CONTROL.get());
        JFrames.MAIN.get().add(JPanels.GAME_FIELD.get());
    }

    private void setDefaultComponentSettings() {
        JFrames.MAIN.get().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrames.MAIN.get().setResizable(false);
        JRadioButtons.ONE_PLAYER.get().setText("Ein Spieler");
        JRadioButtons.TWO_PLAYER.get().setText("Zwei Spieler");
        JRadioButtons.ONE_PLAYER.get().setFocusPainted(false);
        JRadioButtons.TWO_PLAYER.get().setFocusPainted(false);
        JButtons.EXIT.get().setFocusPainted(false);
        JButtons.RESET.get().setFocusPainted(false);
        JButtons.START.get().setFocusPainted(false);
        JLabels.SELECT_PLAYER_COUNT.get().setText("Anzahl der Spieler bitte Auswählen");
        JLabels.USER_INFORMATION_OUTPUT.get().setText("Bitte Spieleranzahl wählen!");
        JLabels.USER_INFORMATION_OUTPUT.get().setForeground(Color.red);
        JButtons.START.get().setEnabled(false);
        JLabels.COPYRIGHT.get().setText("Philipp Schuster (@phip1611 | 2015)");
        JLabels.COPYRIGHT.get().setForeground(new Color(190, 190, 190));
        JLabels.COPYRIGHT.get().setBorder(new EmptyBorder(40, 0, 0, 0));
        prepareGamefield();
    }

    private void setDefaultActionListeners() {
        JButtons.EXIT.get().addActionListener(e -> System.exit(0));
        JButtons.RESET.get().addActionListener(e -> {
            ButtonGroups.RADIO_BUTTONS.get().clearSelection();
            JButtons.START.get().setEnabled(false);
            JRadioButtons.ONE_PLAYER.get().setEnabled(true);
            JRadioButtons.TWO_PLAYER.get().setEnabled(true);
        });
        JButtons.START.get().addActionListener(e -> {
            JButtons.START.get().setEnabled(false);
            JRadioButtons.ONE_PLAYER.get().setEnabled(false);
            JRadioButtons.TWO_PLAYER.get().setEnabled(false);
            setGamefieldEnabled(true);
        });

        JRadioButtons.ONE_PLAYER.get().addActionListener(new RadioButtonsActionListener());
        JRadioButtons.TWO_PLAYER.get().addActionListener(new RadioButtonsActionListener());
    }

    private class RadioButtonsActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButtons.START.get().setEnabled(true);
            JLabels.USER_INFORMATION_OUTPUT.get().setText("");
        }
    }

    private void makeGuiReady() {
        JFrames.MAIN.get().setLayout(new FlowLayout());
        JFrames.MAIN.get().pack();
    }

    public void addActionListener(GUIComponent c, ActionListener al) {
        if (c instanceof JButtons) {
            JButtons.valueOf(c.toString()).get().addActionListener(al);
        }
    }

    public void setGamefieldButtonValue(JButtons button, String text) {
        button.get().setText(text);
    }

    public boolean canUseThisButton(JButtons button) {
        return "".equals(button.get().getText());
    }

    public int getNumberOfPlayers() {
        if (GUI.JRadioButtons.ONE_PLAYER.get().isSelected()) {
            return 1;
        } else if (GUI.JRadioButtons.TWO_PLAYER.get().isSelected()) {
            return 2;
        } else {
            return 0;
        }
    }

    private void prepareGamefield() {
        for (JButtons button : JButtons.values()) {
            if (button.toString().contains("GAME_")) {
                button.get().setFont(new Font("Arial", Font.PLAIN, 80));
                button.get().setFocusPainted(false);
                button.get().setEnabled(false);
            }
        }
    }

    public void setGamefieldEnabled(boolean enabled) {
        for (JButtons button : JButtons.values()) {
            if (button.toString().contains("GAME_")) {
                button.get().setEnabled(enabled);
            }
        }
    }

    public void resetGameField() {
        for (JButtons button : JButtons.values()) {
            if (button.toString().contains("GAME_")) {
                button.get().setText("");
            }
        }
    }

    public void setUserinformationText(String text) {
        JLabels.USER_INFORMATION_OUTPUT.get().setText(text);
    }

    public enum JFrames implements GUIComponent {
        MAIN;
        private final JFrame jframe = new JFrame();
        private JFrame get() {
            return jframe;
        }
    }

    public enum JLabels implements GUIComponent {
        SELECT_PLAYER_COUNT,
        USER_INFORMATION_OUTPUT,
        COPYRIGHT;
        private final JLabel jlabel = new JLabel();
        private JLabel get() {
            return jlabel;
        }
    }

    public enum JButtons implements GUIComponent {
        START,
        EXIT,
        RESET,
        GAME_TOP_LEFT,
        GAME_TOP_CENTER,
        GAME_TOP_RIGHT,
        GAME_MIDDLE_LEFT,
        GAME_MIDDLE_CENTER,
        GAME_MIDDLE_RIGHT,
        GAME_BOTTOM_LEFT,
        GAME_BOTTOM_CENTER,
        GAME_BOTTOM_RIGHT;
        private final JButton jbutton = new JButton();
        private JButton get() {
            return jbutton;
        }
    }

    public enum JPanels implements GUIComponent {
        CONTROL,
        CONTROL_RADIO_BUTTONS,
        CONTROL_BUTTONS,
        GAME_FIELD;
        private final JPanel jpanel = new JPanel();
        private JPanel get() {
            return jpanel;
        }
    }

    public enum JRadioButtons implements GUIComponent {
        ONE_PLAYER,
        TWO_PLAYER;
        private final JRadioButton jRadioButton = new JRadioButton();
        private JRadioButton get() {
            return jRadioButton;
        }
    }

    public enum ButtonGroups implements GUIComponent {
        RADIO_BUTTONS;
        private final ButtonGroup buttonGroup = new ButtonGroup();
        private ButtonGroup get() {
            return buttonGroup;
        }
    }
}

interface GUIComponent {
}
