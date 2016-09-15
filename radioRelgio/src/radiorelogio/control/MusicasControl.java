/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radiorelogio.control;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Musica;
import radiorelogio.view.jMain;

/**
 *
 * @author a1502727
 */
public class MusicasControl {

    private ArrayList<Musica> listaMusicas;
    private ThreadMusicas player = new ThreadMusicas();
    private Thread threadMusica;

    public MusicasControl() {
        listaMusicas = new ArrayList<>();
    }

    public void OpenFile() throws IOException {
        JFileChooser fileWindow = new javax.swing.JFileChooser();
        fileWindow.setFileFilter(new FileNameExtensionFilter("Arquivos MP3", "mp3"));
        fileWindow.setMultiSelectionEnabled(true);

        int returnFileVal = fileWindow.showOpenDialog(fileWindow);

        if (returnFileVal == JFileChooser.APPROVE_OPTION) {

            File[] files = fileWindow.getSelectedFiles();

            for (int i = 0; i < files.length; i++) {
                Musica musica = new Musica();
                try {
                    Mp3File mp3file = new Mp3File(files[i].getAbsolutePath());
                    Float tempo = (float)mp3file.getLengthInSeconds() / 60;
                    musica.setEnderecoMusica(files[i].getAbsolutePath());
                    musica.setNomeMusica(files[i].getName());
                    musica.setTempoMusica(tempo.toString());

                    listaMusicas.add(musica);
                    jMain.modelo.addRow(new Object[]{musica.getNomeMusica(), musica.getTempoMusica()});;
                    
                } catch (UnsupportedTagException ex) {
                    Logger.getLogger(MusicasControl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidDataException ex) {
                    Logger.getLogger(MusicasControl.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public Musica getMusica(int i) {
        return listaMusicas.get(i);
    }

    public void removerMusica(int i) {
        listaMusicas.remove(i);
    }

    public void limparLista() {
        listaMusicas = null;
    }

    public void play(int i) {
        threadMusica = new Thread(player);
        threadMusica.start();
    }

    public void stop() {
        threadMusica.stop();
    }

    public void pause() {

    }
}
