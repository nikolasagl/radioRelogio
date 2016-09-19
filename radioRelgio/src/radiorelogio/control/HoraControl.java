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
import radiorelogio.view.jMain;

/**
 *
 * @author CLEYTON
 */
public class HoraControl {

    private Thread threadHoraCerta;

    public void iniciarThreadHoraAtual() {
        threadHoraCerta = new Thread(new Runnable() {
            @Override
            public void run() {
                falarHora();
            }
        });

        threadHoraCerta.start();
    }

    public void falarHora() {
        FileInputStream stream;
        BufferedInputStream buffer;
        Player horario;

        try {
            stream = new FileInputStream(new File("voices/HRS" + jMain.jLblHora.getText() + ".mp3"));
            buffer = new BufferedInputStream(stream);
            horario = new Player(buffer);
            horario.play();
            if (!jMain.jLblMinuto.equals("00")) {
                stream = new FileInputStream(new File("voices/MIN" + jMain.jLblMinuto.getText() + ".mp3"));
                buffer = new BufferedInputStream(stream);
                horario = new Player(buffer);
                horario.play();
            }
        } catch (Exception ex) {
            Logger.getLogger(MusicasControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        jMain.falarHora = false;
    }
}
