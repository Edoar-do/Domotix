package domotix.view.listeners.edit_listeners;

import domotix.controller.Interpretatore;
import domotix.controller.Rappresentatore;
import view.ModifySignal;
import view.PannelloNord;
import view.Presenter;
import domotix.view.listeners.utils.AutoCompletion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener per la gestione degli eventi legati all'aggiunta di attuatori e loro rimozione da una stanza da selezionare
 */
public class EditAttuatoreStanzaListener implements ActionListener, ModifySignal {

    private Interpretatore inter;
    private Rappresentatore rapp;
    private PannelloNord pannelloNord;
    private Presenter presenter;

    public EditAttuatoreStanzaListener(Interpretatore inter, Rappresentatore rapp, PannelloNord pannelloNord, Presenter presenter) {
        this.inter = inter;
        this.rapp = rapp;
        this.pannelloNord = pannelloNord;
        this.presenter = presenter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equalsIgnoreCase("Aggiungi un attuatore ad una stanza")){ //aggiungi attuatore a stanza
            String[] nomiStanze = rapp.getNomiStanze(pannelloNord.getUnitaCorrente(), true);
            JComboBox comboNomiStanze = new JComboBox(nomiStanze);
            AutoCompletion.enable(comboNomiStanze);
            String[] nomiCategorie = rapp.getNomiCategorieAttuatori();
            JComboBox comboCategorie = new JComboBox(nomiCategorie);
            AutoCompletion.enable(comboCategorie);
            JTextField campoNome = new JTextField(20);
            Object[] oggetti = new Object[]{
                    "Seleziona una stanza a cui aggiungere il nuovo attuatore: ",
                    comboNomiStanze,
                    "Seleziona la categoria del nuovo attuatore: ",
                    comboCategorie,
                    "Inserisci il nome del nuovo attuatore: ",
                    campoNome
            };
            int ok = JOptionPane.showOptionDialog(null, oggetti, "Aggiunta Attuatore ad una stanza", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if(ok == 0){
                if(inter.aggiungiAttuatore(campoNome.getText(), nomiCategorie[comboCategorie.getSelectedIndex()], nomiStanze[comboNomiStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente())) {
                    JOptionPane.showConfirmDialog(null, "Attuatore inserito con successo", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    segnalaModifica(rapp.getDescrizioneStanza(nomiStanze[comboNomiStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente()));
                }else
                    JOptionPane.showConfirmDialog(null, "Attuatore non inserito", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        }else{ //rimozione attuatore da stanza
            String[] nomiStanze = rapp.getNomiStanze(pannelloNord.getUnitaCorrente(), true);
            JComboBox comboNomiStanze = new JComboBox(nomiStanze);
            AutoCompletion.enable(comboNomiStanze);

            JButton selezionaStanza = new JButton("Seleziona stanza");

            JPanel alto = new JPanel(new GridLayout(2,2));
            alto.add(new JLabel("Seleziona una stanza da cui rimuovere un attuatore: "));
            alto.add(new JLabel(""));
            alto.add(comboNomiStanze);
            alto.add(selezionaStanza);

            JDialog dialog = new JDialog();
            dialog.setTitle("Rimozione attuatore da una stanza");
            dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            dialog.setLocationRelativeTo(null);
            dialog.getContentPane().add(alto, BorderLayout.NORTH);
            dialog.pack();

            selezionaStanza.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    comboNomiStanze.setEnabled(false);
                    selezionaStanza.setEnabled(false);
                    String[] nomiAttuatori = rapp.getNomiAttuatori(nomiStanze[comboNomiStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente());
                    if(nomiAttuatori.length > 0) {
                        JComboBox comboAttuatori = new JComboBox(nomiAttuatori);
                        AutoCompletion.enable(comboAttuatori);

                        JButton selezionaAttuatore = new JButton("Seleziona attuatore");

                        JPanel medio = new JPanel(new GridLayout(2, 2));
                        medio.add(new JLabel("Seleziona un attuatore da rimuovere: "));
                        medio.add(new JLabel(""));
                        medio.add(comboAttuatori);
                        medio.add(selezionaAttuatore);

                        dialog.getContentPane().add(medio, BorderLayout.CENTER);
                        dialog.pack();

                        selezionaAttuatore.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (inter.rimuoviAttuatore(nomiAttuatori[comboAttuatori.getSelectedIndex()], nomiStanze[comboNomiStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente())) {
                                    JOptionPane.showConfirmDialog(null, "Attuatore rimosso con successo", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                                    segnalaModifica(rapp.getDescrizioneStanza(nomiStanze[comboNomiStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente()));
                                }else
                                    JOptionPane.showConfirmDialog(null, "Attuatore non rimosso", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                                dialog.dispose();
                            }
                        });//qui sotto
                    }else{
                        JOptionPane.showOptionDialog(null, "Non sono presenti attuatori da rimuovere", "Impossibile rimuovere attuatori", -1, 1, null, null, null);
                        dialog.dispose();
                        return;
                    }
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
