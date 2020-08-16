package view.listeners.file_listeners.nuovo;

import domotix.controller.Interpretatore;
import domotix.controller.Rappresentatore;
import view.ModifySignal;
import view.PannelloNord;
import view.Presenter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NuovoUnitaListener implements ActionListener, ModifySignal {

    private PannelloNord pannelloNord;
    private Interpretatore interpretatore;
    private Rappresentatore rappresentatore;
    private Presenter presenter;

    public NuovoUnitaListener(PannelloNord pannelloNord, Interpretatore interpretatore, Rappresentatore rappresentatore, Presenter presenter){
        this.pannelloNord = pannelloNord;
        this.interpretatore = interpretatore;
        this.presenter = presenter;
        this.rappresentatore = rappresentatore;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            JTextField campoNome = new JTextField(25);
            Object[] widgets = new Object[]{
                    "Inserisci il nome della nuova unità immobiliare: ",
                    campoNome,
                    "Una volta inserito, clicca su 'Fatto' per lavorare sulla nuova unità"
            };
            String[] opz = new String[]{"Fatto", "Annulla"};
            int fatto = JOptionPane.showOptionDialog(null,
                    widgets,
                    "Aggiunta di una nuova unità immobiliare",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opz,
                    null);

            if(fatto == 0){
                if(interpretatore.aggiungiUnitaImmobiliare(campoNome.getText())) {
                    pannelloNord.setUnitaCorrente(campoNome.getText());
                    JOptionPane.showConfirmDialog(null, "Unità immobiliare inserita correttamente", "Successo operazione", -1, 1);
                    segnalaModifica(rappresentatore.getDescrizioneUnita(campoNome.getText()));
                }else
                    JOptionPane.showConfirmDialog(null, "Unità immobiliare non inserita correttamente", "Fallimento operazione", -1, 0);
            }
    }

    @Override
    public void segnalaModifica(String descrizione) {
        presenter.show(descrizione);
    }
}