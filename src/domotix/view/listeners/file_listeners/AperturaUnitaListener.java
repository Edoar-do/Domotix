package domotix.view.listeners.file_listeners;

import domotix.controller.*;
import domotix.view.ModifySignal;
import domotix.view.PannelloNord;
import domotix.view.Presenter;
import domotix.view.listeners.utils.AutoCompletion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AperturaUnitaListener implements ActionListener, ModifySignal {

    private Rappresentatore rappresentatore;
    private PannelloNord pannelloNord;
    private Presenter presenter;

    public AperturaUnitaListener(PannelloNord pannelloNord, Rappresentatore rappresentatore, Presenter presenter){
        this.rappresentatore = rappresentatore;
        this.pannelloNord = pannelloNord;
        this.presenter = presenter;
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

        if(fatto == 0){
            pannelloNord.setUnitaCorrente(nomiUnita[comboUnita.getSelectedIndex()]);
            segnalaModifica(rappresentatore.getDescrizioneUnita(nomiUnita[comboUnita.getSelectedIndex()]));
        }

    }

    @Override
    public void segnalaModifica(String descrizione) {
            presenter.show(descrizione);
    }
}
