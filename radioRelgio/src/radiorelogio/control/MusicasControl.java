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
import radiorelogio.model.Musica;
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

            for (File file : files) {
                Musica musica = new Musica();
                try {
                    Mp3File mp3file = new Mp3File(file.getAbsolutePath());
                    Long segundos = mp3file.getLengthInSeconds() % 60;
                    Long minutos =  mp3file.getLengthInSeconds() / 60;
                    
                    String tempo = minutos + "min"+ segundos + "s";
                    musica.setEnderecoMusica(file.getAbsolutePath());
                    musica.setNomeMusica(file.getName());
                    musica.setTempoMusica(tempo);
                    listaMusicas.add(musica);
                    jMain.modelo.addRow(new Object[]{musica.getNomeMusica(), musica.getTempoMusica()});
                } catch (UnsupportedTagException | InvalidDataException ex) {
                    Logger.getLogger(MusicasControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public Musica getMusica(int i) {
        return listaMusicas.get(i);
    }

    public void play(int i) {
        if (!jMain.tocando) {
            jMain.parar = false;

            if (i == -1) {
                i = 0;
            }

            if (index != i) {
                if (modelo.getRowCount() > 0) {
                    index = i;
                    inicarThread();
                } else {
                    JOptionPane.showMessageDialog(null, "A lista de música esta vazia. Inclua pelo menos uma música");
                }
            }
        }else{
            jMain.tocando = false;
            player.close();
            play(i);
        }
    }

    private void inicarThread() {
        threadMusica = new Thread(new Runnable() {
            @Override
            public void run() {
                tocar();
            }
        });

        threadMusica.start();
    }

    private void tocar() {
        jMain.tocando = true;
        if (!jMain.parar) {
            if (index < jMain.modelo.getRowCount()) {
                Musica m = jMain.musicas.getMusica(index);

                if (index > -1) {
                    jMain.jTxtOuvindo.setText(musicas.getMusica(index).getNomeMusica());
                } else {
                    jMain.jTxtOuvindo.setText(musicas.getMusica(0).getNomeMusica());
                }

                try {
                    FileInputStream stream = new FileInputStream(new File(m.getEnderecoMusica()));
                    BufferedInputStream buffer = new BufferedInputStream(stream);
                    player = new Player(buffer);
                    player.play();
                } catch (Exception ex) {
                }

                if (!jMain.jChkRepetirUma.isSelected()) {
                    index++;
                }

                tocar();
            } else if (jMain.jChkRepetirTudo.isSelected()) {
                index = 0;
                tocar();
            }
        }
        jMain.tocando = false;
    }

    public void removerMusica(int i) {
        listaMusicas.remove(i);
        if (i == index) {
            jMain.jTxtOuvindo.setText("");
            this.play(i++);
        }
    }

    public void limparLista() {
        listaMusicas = null;
    }

    public void stop() {
        if (jMain.tocando) {
            player.close();
            jMain.parar = true;
            jMain.tocando = false;
        }
    }

}
