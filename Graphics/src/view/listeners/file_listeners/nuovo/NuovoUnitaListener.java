package view.listeners.file_listeners.nuovo;

import domotix.controller.Interpretatore;
import view.PannelloNord;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NuovoUnitaListener implements ActionListener {

    private PannelloNord pannelloNord;
    private Interpretatore interpretatore;

    public NuovoUnitaListener(PannelloNord pannelloNord, Interpretatore interpretatore){
        this.pannelloNord = pannelloNord;
        this.interpretatore = interpretatore;
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
                }else
                    JOptionPane.showConfirmDialog(null, "Unità immobiliare non inserita correttamente", "Fallimento operazione", -1, 0);
            }
    }
}