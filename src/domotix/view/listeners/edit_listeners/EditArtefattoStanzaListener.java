package domotix.view.listeners.edit_listeners;

import domotix.controller.Interpretatore;
import domotix.controller.Rappresentatore;
import domotix.view.ModifySignal;
import domotix.view.PannelloNord;
import domotix.view.Presenter;
import domotix.view.listeners.utils.AutoCompletion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditArtefattoStanzaListener implements ActionListener, ModifySignal {

    private Interpretatore inter;
    private Rappresentatore rapp;
    private PannelloNord pannelloNord;
    private Presenter presenter;

    public EditArtefattoStanzaListener(Interpretatore inter, Rappresentatore rapp, PannelloNord pannelloNord, Presenter presenter) {
        this.inter = inter;
        this.rapp = rapp;
        this.pannelloNord = pannelloNord;
        this.presenter = presenter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if(actionCommand.equalsIgnoreCase("Aggiungi un artefatto ad una stanza")){ //aggiunta artefatto a stanza
            String[] nomiStanze = rapp.getNomiStanze(pannelloNord.getUnitaCorrente(), true);
            JComboBox comboStanze = new JComboBox(nomiStanze);
            AutoCompletion.enable(comboStanze);
            JTextField campoNomeArtefatto = new JTextField(20);
            campoNomeArtefatto.setToolTipText("Nome nuovo artefatto");
            Object[] oggetti = new Object[]{"Seleziona la stanza a cui aggiungere l'artefatto: ", comboStanze, "Nome artefatto: ", campoNomeArtefatto };
            int ok = JOptionPane.showOptionDialog(null, oggetti, "Aggiunta artefatto a una stanza", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if(ok == 0){
                if(inter.aggiungiArtefatto(campoNomeArtefatto.getText(), nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente())) {
                    JOptionPane.showConfirmDialog(null, "Artefatto inserito con successo", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    segnalaModifica(rapp.getDescrizioneStanza(nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente()));
                }else
                    JOptionPane.showConfirmDialog(null, "Artefatto non inserito", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        }else{ //rimozione artefatto da stanza
            String[] nomiStanze = rapp.getNomiStanze(pannelloNord.getUnitaCorrente(), true);
            JComboBox comboNomiStanze = new JComboBox(nomiStanze);
            AutoCompletion.enable(comboNomiStanze);

            JButton selezionaStanza = new JButton("Seleziona stanza");

            JPanel alto = new JPanel(new GridLayout(2,2));
            alto.add(new JLabel("Seleziona una stanza da cui rimuovere un artefatto: "));
            alto.add(new JLabel(""));
            alto.add(comboNomiStanze);
            alto.add(selezionaStanza);

            JDialog dialog = new JDialog();
            dialog.setTitle("Rimozione artefatto da una stanza");
            dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            dialog.setLocationRelativeTo(null);
            dialog.getContentPane().add(alto, BorderLayout.NORTH);
            dialog.pack();

            selezionaStanza.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    comboNomiStanze.setEnabled(false);
                    selezionaStanza.setEnabled(false);
                    String[] nomiArtefatti = rapp.getNomiArtefatti(nomiStanze[comboNomiStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente());
                    JComboBox comboArtefatti = new JComboBox(nomiArtefatti);
                    AutoCompletion.enable(comboArtefatti);

                    JButton selezionaArtefatto = new JButton("Seleziona artefatto");

                    JPanel medio = new JPanel(new GridLayout(2,2));
                    medio.add(new JLabel("Seleziona un artefatto da rimuovere: "));
                    medio.add(new JLabel(""));
                    medio.add(comboArtefatti);
                    medio.add(selezionaArtefatto);

                    dialog.getContentPane().add(medio, BorderLayout.CENTER);
                    dialog.pack();

                    selezionaArtefatto.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(inter.rimuoviArtefatto(nomiArtefatti[comboArtefatti.getSelectedIndex()], nomiStanze[comboNomiStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente())) {
                                JOptionPane.showConfirmDialog(null, "Artefatto rimosso con successo", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                                segnalaModifica(rapp.getDescrizioneStanza(nomiStanze[comboNomiStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente()));
                            }else
                                JOptionPane.showConfirmDialog(null, "Artefatto non rimosso", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                            dialog.dispose();
                        }
                    });
                }
            });
            dialog.setVisible(true);
        }

    }

    @Override
    public void segnalaModifica(String descrizione) {
        presenter.show(descrizione);
    }
}
