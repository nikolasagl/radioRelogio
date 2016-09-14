/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author a1502727
 */
public class MusicasTableModel extends AbstractTableModel{

    private ArrayList<Musica> listagem;
    private String[] colunas = {"Nome", "Endereço", "Tempo"};
    
    public MusicasTableModel(){
        listagem = new ArrayList<>();
    }
    
    public void addRow(Musica m){
        this.listagem.add(m);
        this.fireTableDataChanged();
    }
    
    public String getColumnName(int num){
        return this.colunas[num];
    }
    
    @Override
    public int getRowCount() {
        return listagem.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        return listagem.get(linha).getEnderecoMusica();
    }
    
    public void removeRow(int linha){
        this.listagem.remove(linha);
        this.fireTableRowsDeleted(linha, linha);
    }
    
}
