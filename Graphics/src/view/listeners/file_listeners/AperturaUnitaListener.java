package view.listeners.file_listeners;

import domotix.controller.*;
import view.PannelloNord;
import view.listeners.utils.AutoCompletion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AperturaUnitaListener implements ActionListener {

    private Rappresentatore rappresentatore;
    private PannelloNord pannelloNord;

    public AperturaUnitaListener(PannelloNord pannelloNord, Rappresentatore rappresentatore){
        this.rappresentatore = rappresentatore;
        this.pannelloNord = pannelloNord;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String[] nomiUnita = rappresentatore.getNomiUnita();

        JComboBox comboUnita = new JComboBox(nomiUnita);
        AutoCompletion.enable(comboUnita);

        Object[] widgets = new Object[]{
                "Scegli un'unità immobiliare su cui lavorare: ",
                comboUnita,
                "Una volta scelta, clicca su 'Fatto' per lavorare sull'unità selezionata"
        };

        String[] opz = new String[]{"Fatto", "Annulla"};

        int fatto = JOptionPane.showOptionDialog(null,
                widgets,
                "Selezione di un'unità immobiliare",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opz,
                null);

        if(fatto == 0)
            pannelloNord.setUnitaCorrente(nomiUnita[comboUnita.getSelectedIndex()]);
    }
}
