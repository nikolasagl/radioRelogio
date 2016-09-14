/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radiorelogio.control;

import java.io.File;
import java.util.ArrayList;
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

    public MusicasControl() {
        listaMusicas = new ArrayList<>();
    }

    public void OpenFile() {
        JFileChooser fileWindow = new javax.swing.JFileChooser();
        fileWindow.setFileFilter(new FileNameExtensionFilter("Arquivos MP3", "mp3"));
        fileWindow.setMultiSelectionEnabled(true);

        int returnFileVal = fileWindow.showOpenDialog(fileWindow);

        if (returnFileVal == JFileChooser.APPROVE_OPTION) {

            File[] files = fileWindow.getSelectedFiles();

            for (int i = 0; i < files.length; i++) {
                Musica musica = new Musica();
                musica.setEnderecoMusica(files[i].getAbsolutePath());
                musica.setNomeMusica(files[i].getName());
                musica.setTempoMusica("0min");

                listaMusicas.add(musica);
                jMain.modelo.addRow(new Object[]{listaMusicas.get(i).getNomeMusica(), listaMusicas.get(i).getTempoMusica()});;
            }
        }
    }

    public Musica getMusica(int i) {
        return listaMusicas.get(i);
    }
    
    
    public void removerMusica(int i){
        listaMusicas.remove(i);
    }
    
    public void limparLista(){
        listaMusicas = null;
    }
}
