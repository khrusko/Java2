package hr.algebra.khruskoj2.model;

import java.io.Serializable;

public class EntryScreenState implements Serializable {
    private String btnStartText;
    private double btnStartLayoutX;
    private boolean btnPauseDisabled;
    private boolean btnContinueDisabled;

    public EntryScreenState(String btnStartText, double btnStartLayoutX, boolean btnPauseDisabled, boolean btnContinueDisabled) {
        this.btnStartText = btnStartText;
        this.btnStartLayoutX = btnStartLayoutX;
        this.btnPauseDisabled = btnPauseDisabled;
        this.btnContinueDisabled = btnContinueDisabled;
    }

    public String getBtnStartText() {
        return btnStartText;
    }

    public double getBtnStartLayoutX() {
        return btnStartLayoutX;
    }

    public boolean isBtnPauseDisabled() {
        return btnPauseDisabled;
    }

    public boolean isBtnContinueDisabled() {
        return btnContinueDisabled;
    }
}
