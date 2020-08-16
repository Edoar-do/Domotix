package view.listeners.edit_listeners;

import domotix.controller.Interpretatore;
import domotix.controller.Rappresentatore;
import view.ModifySignal;
import view.PannelloNord;
import view.Presenter;
import view.listeners.utils.AutoCompletion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener per la gestione degli eventi legati al settaggi della modalità operativa degli attuatori associati a stanze e artefatti
 */
public class EditSetModalitaAttListener implements ActionListener, ModifySignal {
    private Interpretatore inter;
    private Rappresentatore rapp;
    private PannelloNord pannelloNord;
    private Presenter presenter;

    public EditSetModalitaAttListener(Interpretatore inter, Rappresentatore rapp, PannelloNord pannelloNord, Presenter presenter) {
        this.inter = inter;
        this.rapp = rapp;
        this.pannelloNord = pannelloNord;
        this.presenter = presenter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if(actionCommand.equalsIgnoreCase("Imposta modalità operativa attuatore collegato ad una stanza")){
            String[] nomiStanze = rapp.getNomiStanze(pannelloNord.getUnitaCorrente(), true);
            JComboBox comboStanze = new JComboBox(nomiStanze);
            AutoCompletion.enable(comboStanze);

            JButton selezionaStanza = new JButton("Scegli stanza");

            JPanel alto = new JPanel(new GridLayout(2,2));
            alto.add(new JLabel("Scegli una stanza: "));
            alto.add(new JLabel(""));
            alto.add(comboStanze);
            alto.add(selezionaStanza);

            JDialog dialog = new JDialog();
            dialog.setTitle("Impostazione modalità operativa attuatore in una stanza");
            dialog.getContentPane().add(alto, BorderLayout.NORTH);
            dialog.setLocationRelativeTo(null);
            dialog.pack();
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            selezionaStanza.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    comboStanze.setEnabled(false);
                    selezionaStanza.setEnabled(false);

                    String[] nomiAttuatori = rapp.getNomiAttuatori(nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente());
                    if(nomiAttuatori.length > 0) {
                        JComboBox comboAttuatori = new JComboBox(nomiAttuatori);

                        JButton scegliAttuatore = new JButton("Scegli attuatore");

                        JPanel medio = new JPanel(new GridLayout(2, 2));
                        medio.add(new JLabel("Scegli un attuatore a cui impostare la modalità operativa:"));
                        medio.add(new JLabel(""));
                        medio.add(comboAttuatori);
                        medio.add(scegliAttuatore);

                        dialog.getContentPane().add(medio, BorderLayout.CENTER);
                        dialog.pack();

                        scegliAttuatore.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                comboAttuatori.setEnabled(false);
                                scegliAttuatore.setEnabled(false);

                                String[] nomiModalita = rapp.getModalitaOperativeImpostabili(nomiAttuatori[comboAttuatori.getSelectedIndex()]);
                                if (nomiModalita.length == 0) {
                                    JOptionPane.showConfirmDialog(dialog, "L'attuatore " + nomiAttuatori[comboAttuatori.getSelectedIndex()] + " ha una sola modalità operativa",
                                            "Assenza di modalità operative impostabili", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null);
                                    dialog.dispose();
                                    return;
                                }
                                JComboBox comboModalita = new JComboBox(nomiModalita);
                                AutoCompletion.enable(comboModalita);
                                JButton imposta = new JButton("Imposta modalità operativa");

                                JPanel basso = new JPanel(new GridLayout(2, 2));
                                basso.add(new JLabel("Scegli la modalità da impostare"));
                                basso.add(new JLabel(""));
                                basso.add(comboModalita);
                                basso.add(imposta);

                                dialog.getContentPane().add(basso, BorderLayout.SOUTH);
                                dialog.pack();

                                imposta.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        comboModalita.setEnabled(false);
                                        imposta.setEnabled(false);
                                        if (inter.setModalitaOperativa(nomiAttuatori[comboAttuatori.getSelectedIndex()], nomiModalita[comboModalita.getSelectedIndex()])) {
                                            JOptionPane.showConfirmDialog(null, "Modalità impostata con successo", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                                            segnalaModifica(rapp.getDescrizioneUnita(pannelloNord.getUnitaCorrente()));
                                        }else
                                            JOptionPane.showConfirmDialog(null, "Modalità non impostata", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                                        dialog.dispose();
                                    }
                                });
                            }
                        });//qui sotto
                    }else{
                        JOptionPane.showOptionDialog(null, "Non sono presenti attuatori nella stanza scelta", "Assenza attuatori", -1, 1, null, null, null);
                        dialog.dispose();
                        return;
                    }
                }
            });
            dialog.setVisible(true);
        }else{ //set modalità attuatore su artefatto
            String[] nomiStanze = rapp.getNomiStanze(pannelloNord.getUnitaCorrente(), true);
            JComboBox comboStanze = new JComboBox(nomiStanze);
            AutoCompletion.enable(comboStanze);

            JButton selezionaStanza = new JButton("Scegli stanza");

            JPanel alto = new JPanel(new GridLayout(2,2));
            alto.add(new JLabel("Scegli una stanza: "));
            alto.add(new JLabel(""));
            alto.add(comboStanze);
            alto.add(selezionaStanza);

            JDialog dialog = new JDialog();
            dialog.setTitle("Impostazione modalità operativa attuatore su un artefatto");
            dialog.getContentPane().add(alto, BorderLayout.NORTH);
            dialog.setLocationRelativeTo(null);
            dialog.pack();
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            selezionaStanza.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    comboStanze.setEnabled(false);
                    selezionaStanza.setEnabled(false);

                    String[] nomiArtefatti = rapp.getNomiArtefatti(nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente());
                    if(nomiArtefatti.length > 0) {
                        JComboBox comboArtefatti = new JComboBox(nomiArtefatti);

                        JButton scegliArtefatto = new JButton("Scegli artefatto");

                        JPanel medio = new JPanel(new GridLayout(2, 2));
                        medio.add(new JLabel("Scegli un artefatto:"));
                        medio.add(new JLabel(""));
                        medio.add(comboArtefatti);
                        medio.add(scegliArtefatto);

                        dialog.getContentPane().add(medio, BorderLayout.CENTER);
                        dialog.pack();

                        scegliArtefatto.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                comboArtefatti.setEnabled(false);
                                scegliArtefatto.setEnabled(false);

                                String[] nomiAttuatori = rapp.getNomiAttuatori(nomiArtefatti[comboArtefatti.getSelectedIndex()], nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente());
                                if(nomiAttuatori.length > 0) {
                                    JComboBox comboAttuatori = new JComboBox(nomiAttuatori);
                                    AutoCompletion.enable(comboAttuatori);

                                    JButton scegliAttuatore = new JButton("Scegli attuatore");

                                    String[] nomiModalita = rapp.getModalitaOperativeImpostabili(nomiAttuatori[comboAttuatori.getSelectedIndex()]);
                                    if (nomiModalita.length == 0) {
                                        JOptionPane.showOptionDialog(null, "L'attuatore " + nomiArtefatti[comboArtefatti.getSelectedIndex()] + " ha una sola modalità operativa", "Assenza di modalità operative impostabili", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
                                        dialog.dispose();
                                        return;
                                    }
                                    JComboBox comboModalita = new JComboBox(nomiModalita);
                                    comboModalita.setEnabled(false);
                                    AutoCompletion.enable(comboModalita);
                                    JButton imposta = new JButton("Imposta modalità operativa");
                                    imposta.setEnabled(false);

                                    JPanel b1 = new JPanel(new GridLayout(2, 2));
                                    b1.add(new JLabel("Scegli un attuatore a cui impostare la modalità operarativa"));
                                    b1.add(new JLabel(""));
                                    b1.add(comboAttuatori);
                                    b1.add(scegliAttuatore);

                                    JPanel b2 = new JPanel(new GridLayout(2, 2));
                                    b2.add(new JLabel("Scegli la modalità da impostare"));
                                    b2.add(new JLabel(""));
                                    b2.add(comboModalita);
                                    b2.add(imposta);

                                    JPanel basso = new JPanel(new BorderLayout());
                                    basso.add(b1, BorderLayout.NORTH);
                                    basso.add(b2, BorderLayout.CENTER);

                                    dialog.getContentPane().add(basso, BorderLayout.SOUTH);
                                    dialog.pack();

                                    scegliAttuatore.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            comboAttuatori.setEnabled(false);
                                            scegliAttuatore.setEnabled(false);
                                            comboModalita.setEnabled(true);
                                            imposta.setEnabled(true);
                                        }
                                    });

                                    imposta.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            comboModalita.setEnabled(false);
                                            imposta.setEnabled(false);
                                            if (inter.setModalitaOperativa(nomiAttuatori[comboAttuatori.getSelectedIndex()], nomiModalita[comboModalita.getSelectedIndex()])) {
                                                JOptionPane.showConfirmDialog(null, "Modalità impostata con successo", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                                                segnalaModifica(rapp.getDescrizioneUnita(pannelloNord.getUnitaCorrente()));
                                            }else
                                                JOptionPane.showConfirmDialog(null, "Modalità non impostata", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                                            dialog.dispose();
                                        }
                                    });//qui sotto
                                }else{
                                    JOptionPane.showOptionDialog(null, "Non sono presenti attuatori collegati all'artefatto", "Assenza attuatori collegati all'artefatto", -1, 1, null, null, null);
                                    dialog.dispose();
                                    return;
                                }
                            }
                        });//qui sotto
                    }else{
                        JOptionPane.showOptionDialog(null, "Non sono presenti artefatti nella stanza scelta", "Impossibile accedere agli artefatti", -1, 1, null, null, null);
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
