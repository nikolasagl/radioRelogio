/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radiorelogio.control;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javazoom.jl.player.Player;
import model.Musica;
import radiorelogio.view.jMain;
import static radiorelogio.view.jMain.modelo;
import static radiorelogio.view.jMain.musicas;

/**
 *
 * @author a1502727
 */
public class MusicasControl {

    private ArrayList<Musica> listaMusicas;
    private Thread threadMusica;
    private Player player;
    private int index;

    public MusicasControl() {
        listaMusicas = new ArrayList<>();
        index = -1;
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
                    Float tempo = (float) mp3file.getLengthInSeconds() / 60;
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

    public void play(int i) {

        if (i == -1) {
            i = 0;
        }

        if (index != i) {
            index = i;
            if (modelo.getRowCount() > 0) {
                inicarThread();
            } else {
                JOptionPane.showMessageDialog(null, "A lista de música esta vazia. Inclua pelo menos uma música");
            }
        }
    }

    private void tocar() {
        if (index < jMain.modelo.getRowCount()) {

            Musica m = jMain.musicas.getMusica(index);

            if (index > -1) {
                jMain.jTxtOuvindo.setText(musicas.getMusica(index).getNomeMusica());
            } else {
                jMain.jTxtOuvindo.setText(musicas.getMusica(index).getNomeMusica());
            }

            try {
                FileInputStream stream = new FileInputStream(new File(m.getEnderecoMusica()));
                BufferedInputStream buffer = new BufferedInputStream(stream);
                player = new Player(buffer);
                player.play();
            } catch (Exception ex) {
                Logger.getLogger(jMain.class.getName()).log(Level.SEVERE, null, ex);
            }

            index++;
            tocar();
        }else{
            index = 0;
            tocar();
        }
    }

    private void inicarThread() {
        threadMusica = new Thread(new Runnable() {
            public void run() {
                tocar();
            }
        });

        threadMusica.start();
    }

    public void stop() {
        this.threadMusica.stop();
        index = -1;
    }

    public void removerMusica(int i) {
        listaMusicas.remove(i);
        if(i == index){
            this.stop();
            this.play(i++);
        }
    }

    public void limparLista() {
        listaMusicas = null;
    }

    public void pause() throws InterruptedException {
        this.threadMusica.wait();
    }
}
