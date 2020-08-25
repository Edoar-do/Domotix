package domotix.view.listeners.view_listeners;

import domotix.controller.*;
import domotix.view.passiveView.PannelloCentro;
import domotix.view.passiveView.PannelloNord;
import domotix.view.listeners.utils.AutoCompletion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DettagliUnitaListener implements ActionListener {

    private PannelloCentro pannelloCentro;
    private PannelloNord pannelloNord;
    private Rappresentatore rappresentatore;
    private String[] nomiStanze;
    private JComboBox comboStanze;

    public DettagliUnitaListener(PannelloCentro pannelloCentro, PannelloNord pannelloNord, Rappresentatore rappresentatore){
        this.pannelloCentro = pannelloCentro;
        this.pannelloNord = pannelloNord;
        this.rappresentatore = rappresentatore;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

            if(actionCommand.equalsIgnoreCase( "Descrizione Completa Unità")) {
                pannelloCentro.getAreaVisualizzazione().setText(rappresentatore.getDescrizioneUnita(pannelloNord.getUnitaCorrente()));
            }else if(actionCommand.equalsIgnoreCase("stanze")) {
                nomiStanze = rappresentatore.getNomiStanze(pannelloNord.getUnitaCorrente());

                comboStanze = new JComboBox(nomiStanze);
                AutoCompletion.enable(comboStanze);

                Object[] widgets = new Object[]{
                        "Scegli una stanza di " + pannelloNord.getUnitaCorrente() + " da visualizzare: ",
                        comboStanze,
                        "Una volta scelta, clicca su 'Fatto' per visualizzarla"
                };

                String[] opz = new String[]{"Fatto", "Annulla"};

                int fatto = JOptionPane.showOptionDialog(null,
                        widgets,
                        "Selezione di una stanza da visualizzare",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opz,
                        null);

                if (fatto == 0)
                    pannelloCentro.getAreaVisualizzazione().setText(rappresentatore.getDescrizioneStanza(nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente()));
            }else {
                nomiStanze = rappresentatore.getNomiStanze(pannelloNord.getUnitaCorrente());
                JButton scegliStanza = new JButton("Scegli");
                comboStanze = new JComboBox(nomiStanze);
                AutoCompletion.enable(comboStanze);

                JPanel alto = new JPanel(new FlowLayout());
                alto.add(new JLabel("Scegli una stanza dell'unità " + pannelloNord.getUnitaCorrente() + " : "));
                alto.add(comboStanze);
                alto.add(scegliStanza);

                JDialog dialog = new JDialog((Dialog) null, "Scelta artefatto da visualizzare");
                dialog.getContentPane().add(alto, BorderLayout.NORTH);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

                scegliStanza.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        comboStanze.setEnabled(false);
                        String[] nomiArtefatti = rappresentatore.getNomiArtefatti(nomiStanze[comboStanze.getSelectedIndex()],
                                pannelloNord.getUnitaCorrente());
                        if (nomiArtefatti.length > 0) {
                            JComboBox comboArtefatti = new JComboBox(nomiArtefatti);
                            AutoCompletion.enable(comboArtefatti);
                            JButton scegliArtefatto = new JButton("Scegli");

                            JPanel centro = new JPanel(new FlowLayout());
                            centro.add(new JLabel("Scegli un artefatto da visualizzare: "));
                            centro.add(comboArtefatti);
                            centro.add(scegliArtefatto);

                            dialog.getContentPane().add(centro, BorderLayout.CENTER);
                            dialog.pack();

                            scegliArtefatto.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    pannelloCentro.getAreaVisualizzazione().setText(rappresentatore.getDescrizioneArtefatto(nomiArtefatti[comboArtefatti.getSelectedIndex()],
                                            nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente()));
                                    dialog.dispose();
                                }
                            });
                        } else {
                            JOptionPane.showOptionDialog(null, "Non sono presenti artefatti nella stanza scelta", "Impossibile visualizzare artefatto", -1, 1, null, null, null);
                            dialog.dispose();
                            return;
                        }
                    }
                });
                dialog.setVisible(true);
            }
    }
}