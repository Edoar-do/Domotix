package domotix.view.passiveView;



import domotix.controller.Interpretatore;
import domotix.controller.Verificatore;
import domotix.controller.Rappresentatore;
import domotix.controller.Importatore;

import javax.swing.*;
import java.awt.*;

public class MyViewPanel extends JFrame {

    private Container container;
    private JPanel pannelloNord, pannelloCentro;


    /**
     * Costruttore della finestra principale, gerarchicamente contiene pannello nord e pannello centro
     * @param titolo della finestra
     * @param inter controller interpretatore
     * @param ver controller verificatore
     * @param rapp controller rappresentatore
     * @param imp controller importatore
     */
    public MyViewPanel(String titolo, Interpretatore inter, Verificatore ver, Rappresentatore rapp, Importatore imp){
        super(titolo);
        container = getContentPane();
        container.setLayout(new BorderLayout());
        pannelloCentro = new PannelloCentro();
        pannelloNord = new PannelloNord((PannelloCentro) pannelloCentro, inter, rapp, imp, ver);
        container.add(pannelloNord, BorderLayout.NORTH); //pannello con menu bar a sinistra a radio button a destra
        container.add(pannelloCentro, BorderLayout.CENTER); //pannello con textArea di visualizzazione scrollabile

    }

    /**
     * Metodo che restituisce il jpanel in alto nella finestra principale.
     * E' il contenitore della menubar
     * @return pannello nord
     */
    public JPanel getPannelloNord(){ return pannelloNord; }

}
