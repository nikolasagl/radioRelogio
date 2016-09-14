/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radiorelogio.control;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.player.Player;
import model.Musica;
import radiorelogio.view.jMain;

/**
 *
 * @author CLEYTON
 */
public class ThreadMusicas implements Runnable {

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void play(int i) {

        Musica m = new Musica();
        
        try {
            FileInputStream stream = new FileInputStream(new File(m.getEnderecoMusica()));
            BufferedInputStream buffer = new BufferedInputStream(stream);
            Player player = new Player(buffer);
            player.play();
        } catch (Exception ex) {
            Logger.getLogger(jMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
