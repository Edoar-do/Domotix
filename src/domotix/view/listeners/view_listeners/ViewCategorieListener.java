package domotix.view.listeners.view_listeners;

import domotix.controller.*;
import domotix.view.PannelloCentro;
import domotix.view.PannelloNord;
import domotix.view.listeners.utils.AutoCompletion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewCategorieListener implements ActionListener {

    private Rappresentatore rappresentatore;
    private PannelloCentro pannelloCentro;

    private Object[] widgets;
    private JComboBox comboCategorie;
    private String[] nomiCategorie;
    private String[] opz;
    private int fatto;

    public ViewCategorieListener(PannelloCentro pannelloCentro, Rappresentatore rappresentatore){
        this.pannelloCentro = pannelloCentro;
        this.rappresentatore = rappresentatore;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand){
            case PannelloNord.CAT_SENS:
                nomiCategorie = rappresentatore.getNomiCategorieSensori();
                if(nomiCategorie.length == 0){
                    JOptionPane.showOptionDialog(null, "Non sono presenti categorie di sensori da visualizzare", "Assenza categorie sensori", -1, 1, null, null, null);
                    break;
                }

                comboCategorie = new JComboBox(nomiCategorie);
                AutoCompletion.enable(comboCategorie);

                widgets = new Object[]{
                        "Scegli una categoria di sensori da visualizzare: ",
                        comboCategorie,
                        "Una volta scelta, clicca su 'Fatto' per visualizzarla"
                };

                opz = new String[]{"Fatto", "Annulla"};

                fatto = JOptionPane.showOptionDialog(null,
                        widgets,
                        "Selezione di una categoria di sensori: ",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opz,
                        null);

                if(fatto == 0)
                    pannelloCentro.getAreaVisualizzazione().setText(rappresentatore.getDescrizioneCategoriaSensori(nomiCategorie[comboCategorie.getSelectedIndex()]));
                break;
            case PannelloNord.CAT_ATT:
                nomiCategorie = rappresentatore.getNomiCategorieAttuatori();
                if(nomiCategorie.length == 0){
                    JOptionPane.showOptionDialog(null, "Non sono presenti categorie di attuatori da visualizzare", "Assenza categorie attuatori", -1, 1, null, null, null);
                    break;
                }

                comboCategorie = new JComboBox(nomiCategorie);
                AutoCompletion.enable(comboCategorie);

                widgets = new Object[]{
                        "Scegli una categoria di attuatori da visualizzare: ",
                        comboCategorie,
                        "Una volta scelta, clicca su 'Fatto' per visualizzarla"
                };

                opz = new String[]{"Fatto", "Annulla"};

                fatto = JOptionPane.showOptionDialog(null,
                        widgets,
                        "Selezione di una categoria di attuatori: ",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opz,
                        null);

                if(fatto == 0)
                    pannelloCentro.getAreaVisualizzazione().setText(rappresentatore.getDescrizioneCategoriaAttuatori(nomiCategorie[comboCategorie.getSelectedIndex()]));
                break;
        }

    }
}