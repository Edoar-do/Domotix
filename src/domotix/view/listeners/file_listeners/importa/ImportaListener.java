package domotix.view.listeners.file_listeners.importa;

import domotix.controller.Importatore;
import domotix.view.passiveView.PannelloNord;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ImportaListener implements ActionListener {
    private Importatore importatore;
    private JTextArea areaErroriImport;
    private JScrollPane scrollPane;
    private Object[] widgets;

    /**
     * costruttore del listener di importazione alla pressione degli menuitem importaXXX
     * @param importatore controller da fornire per importare entità
     */
    public ImportaListener(Importatore importatore){
        this.importatore = importatore;
        areaErroriImport = new JTextArea(10, 10);
        scrollPane = new JScrollPane(areaErroriImport);
        widgets = new Object[]{
                "Si sono verificati i seguenti errori durante l'importazione",
                scrollPane,
                "Si consiglia di prendere visione delle cause di errore tramite il pulsante 'Help' "
        };
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        ArrayList<String> msgs;
        switch(actionCommand){
            case PannelloNord.UNITA:
                msgs = (ArrayList<String>) importatore.importaUnitaImmobiliari();
                if(msgs == null) {
                    JOptionPane.showConfirmDialog(null, "Importazione unità immobiliari fallita", "Fallimento operazione", -1, 0);
                    break;
                }
                if(msgs.size() > 0) {
                    areaErroriImport.setText("");
                    msgs.forEach(s -> areaErroriImport.append(String.format("Unita Immobiliare %s non importata" + "\n", s)));
                    JOptionPane.showOptionDialog(null, widgets, "Errori Importazione", -1,0, null, null, null);
                }else JOptionPane.showConfirmDialog(null, "Importazione unità immobiliari avvenuta con successo", "Successo operazione", -1, 1);
                break;
            case PannelloNord.CAT_ATT:
                    msgs = (ArrayList<String>) importatore.importaCategorieAttuatori();
                    if(msgs == null) {
                        JOptionPane.showConfirmDialog(null, "Importazione categorie attuatori fallita", "Fallimento operazione", -1, 0);
                        break;
                    }
                    if(msgs.size() > 0) {
                        areaErroriImport.setText("");
                        msgs.forEach(s -> areaErroriImport.append(String.format("Categoria attautori %s non importata" + "\n", s)));
                        JOptionPane.showOptionDialog(null, widgets, "Errori Importazione", -1,0, null, null, null);
                    }else JOptionPane.showConfirmDialog(null, "Importazione categorie attuatori avvenuta con successo", "Successo operazione", -1, 1);
                break;
            case PannelloNord.CAT_SENS:
                msgs = (ArrayList<String>) importatore.importaCategorieSensori();
                if(msgs == null) {
                    JOptionPane.showConfirmDialog(null, "Importazione categorie sensori fallita", "Fallimento operazione", -1, 0);
                    break;
                }
                if(msgs.size() > 0) {
                    areaErroriImport.setText("");
                    msgs.forEach(s -> areaErroriImport.append(String.format("Categoria sensori %s non importata" + "\n", s)));
                    JOptionPane.showOptionDialog(null, widgets, "Errori Importazione", -1,0, null, null, null);
                }else JOptionPane.showConfirmDialog(null, "Importazione categorie sensori avvenuta con successo", "Successo operazione", -1, 1);
                break;
        }

    }
}
