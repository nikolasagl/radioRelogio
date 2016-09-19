/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radiorelogio.control;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import radiorelogio.view.jMain;

/**
 *
 * @author a1502727
 */
public class ThreadHorario implements Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                recuperarHoraSistema();
                if(jMain.jLblMinuto.getText().equals("00"))
                    jMain.falarHora = true;
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadHorario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void recuperarHoraSistema() {

        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String hora = sdf.format(new Date());
        jMain.jLblHora.setText(hora);

        sdf = new SimpleDateFormat("mm");
        String minuto = sdf.format(new Date());
        jMain.jLblMinuto.setText(minuto);

        sdf = new SimpleDateFormat("ss");
        String segundo = sdf.format(new Date());
        jMain.jLblSegundos.setText(segundo);
    }
}
