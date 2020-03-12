package domotix.model.bean.device;

import domotix.controller.util.StringUtil;

import java.util.ArrayList;

/** @author Edoardo Coppola */
public class CategoriaSensore {

    private String nome;
    private String testoLibero;
    private ArrayList<InfoRilevabile> informazioneRilevabile;

    /**
     * Costruttore privato comune a tutte le alternative
     * @param nome  stringa contenente il nome della categoria
     * @param testoLibero   stringa contenente il testo libero della categoria
     */
    public CategoriaSensore(String nome, String testoLibero) {
        this.nome = nome;
        this.testoLibero = testoLibero;
        this.informazioneRilevabile = new ArrayList<>();
    }

    /**
     * Costruttore mantenuto per retro-compatibilita'. Qui infatti viene passata il solo nome di una informazione rilevabile,
     * essendo le specifiche della versione 1 per categorie con una sola informazione rilevabile e di tipo numerico, questa
     * viene aggiunta in elenco con tale indicazione.
     * Posto comunque il tag di deprecato per sfavorirne l'uso
     *
     * @param nome  stringa contenente il nome della categoria
     * @param testoLibero   stringa contenente il testo libero della categoria
     * @param informazioneRilevabile    stringa contenente il nome della prima informazione rilevabile
     */
    @Deprecated
    public CategoriaSensore(String nome, String testoLibero, String informazioneRilevabile){
        this(nome, testoLibero);

        this.informazioneRilevabile.add(new InfoRilevabile(informazioneRilevabile, true));
    }

    /**
     * Costruttore base dove viene indicata la prima (ed obbligatoria) informazione rilevabile
     *
     * @param nome  stringa contenente il nome della categoria
     * @param testoLibero   stringa contenente il testo libero della categoria
     * @param infoRilevabile prima informazione rilevabile
     */
    public CategoriaSensore(String nome, String testoLibero, InfoRilevabile infoRilevabile) {
        this(nome, testoLibero);
        this.informazioneRilevabile.add(infoRilevabile);
    }

    /**
     * Costruttore base dove viene indicata la prima (ed obbligatoria) informazione rilevabile e un elenco facoltativo di altre informazioni.
     * Viene comunque effettuato il controllo di presenza per ciascuna informazione indicata e, nel caso vi siano duplicati questi non
     * saranno aggiunti.
     *
     * @param nome  stringa contenente il nome della categoria
     * @param testoLibero   stringa contenente il testo libero della categoria
     * @param infoRilevabile prima informazione rilevabile (obbligatoria
     * @param infoExtra elenco facoltativo di altre informazioni rilevabili da aggiungere
     */
    public CategoriaSensore(String nome, String testoLibero, InfoRilevabile infoRilevabile, InfoRilevabile ...infoExtra) {
        this(nome, testoLibero, infoRilevabile);
        for (InfoRilevabile i : infoExtra)
            addInformazioneRilevabile(i);
    }

    /**
     * Metodo che recupera il nome della CategoriaSensore.
     * @return Il nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo che imposta il nome della CategoriaSensore.
     * @param nome Il nome da impostare
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo che recupera il testo libero descrittivo della CategoriaSensore.
     * @return Il testo libero
     */
    public String getTestoLibero() {
        return testoLibero;
    }

    /**
     * Metodo che imposta il testo libero descrittivo della CategoriaSensore.
     * @param testoLibero Il testo libero
     */
    public void setTestoLibero(String testoLibero) {
        this.testoLibero = testoLibero;
    }

    /**
     * Metodo, presente per retro-compatibilita', che recupera il nome della prima informazione rilevabile dal Sensore di CategoriaSensore.
     * Dalle specifiche della versione 1 una categoria sensore puo' avere solo un'informazione rilevabile e questo metodo ne ritorna il nome,
     * pertando viene riportata la funzione sebbene si ponga  il tag di deprecato per sfavorirne l'uso.
     *
     * @return L'informazione rilevabile
     */
    @Deprecated
    public String getInformazioneRilevabile() {
        return informazioneRilevabile.get(0).getNome();
    }

    /**
     * Ritorna l'elenco, eventualmente singoletto, di informazioni rilevabili contenute in categoria sensore.
     *
     * @return  ArrayList di informazioni rilevabili
     */
    public ArrayList<InfoRilevabile> getInformazioniRilevabili() {
        return new ArrayList<>(this.informazioneRilevabile);
    }

    /**
     * Recupera l'informazione rilevabile identificata per nome indicato se presente, null se non trovata.
     *
     * @param nome  stringa contenente il nome con cui identificare l'informazione rilevabile
     * @return  informazione rilevabile identificata dal nome se presente; null altrimenti
     */
    public InfoRilevabile getInformazioneRilevabile(String nome) {
        for(InfoRilevabile i : informazioneRilevabile) {
            if (i.getNome().equals(nome))
                return i;
        }
        return null;
    }

    /**
     * Recupera l'informazione rilevabile identificata dall'indice indicato.
     *
     * @param index indice dell'informazione rilevabile
     * @return  informazione rilevabile
     * @throws IndexOutOfBoundsException    lanciata nel caso l'indice indicato non rispetti la dimensione dell'elenco di
     *  informazioni rilevabili contenuto.
     */
    public InfoRilevabile getInformazioneRilevabile(int index) {
        return informazioneRilevabile.get(index);
    }

    /**
     * Verifica se presente un'informazione rilevabile identificata dal nome passato
     * @param nome  stringa contenente il nome con cui cercare l'informazione rilevabile
     * @return  true: nome identifica un'informazione rilevabile contenuta; false: altrimenti
     */
    public boolean containsInformazioneRilevabie(String nome) {
        for (InfoRilevabile i : informazioneRilevabile) {
            if (i.getNome().equals(nome))
                return true;
        }
        return false;
    }

    /**
     * Aggiunge un'informazione rilevabile se non gia' presente (identificata per nome).
     * @param infoRilevabile    informazione rilevabile da aggiungere
     * @return  true: informazione rilevabile aggiunta senza problemi; false: altrimenti
     */
    public boolean addInformazioneRilevabile(InfoRilevabile infoRilevabile) {
        if (containsInformazioneRilevabie(infoRilevabile.getNome()))
            return false;

        informazioneRilevabile.add(infoRilevabile);
        return true;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getNome() + ":\n");
        buffer.append("\tTESTO LIBERO:\n");
        buffer.append(StringUtil.indent(getTestoLibero() + "\n", 2));
        buffer.append("\tINFORMAZIONI RILEVABILI:");
        for(InfoRilevabile i : informazioneRilevabile)
            buffer.append(StringUtil.indent("\n" + i.toString(), 2));
        return buffer.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof CategoriaSensore)) return false;
        CategoriaSensore other = (CategoriaSensore) obj;
        return other.nome.equals(this.nome);
    }
}
