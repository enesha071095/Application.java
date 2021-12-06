package ManyTables;

import Tables.OneAnte;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ManyAnte extends AbstractTableModel {
    private ArrayList<OneAnte> manyAntes;


    public ManyAnte(ArrayList<OneAnte> models ) {
        super();
        this.manyAntes = models;
    }

    public ArrayList<OneAnte> getManyAntes() {
        return manyAntes;
    }

    @Override
    public int getRowCount() {
        return manyAntes.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch (c){
            default:case 0: return manyAntes.get(r).id;
            case 1: return manyAntes.get(r).value;
            case 2: return manyAntes.get(r).employer_id;
        }
    }

    @Override
    public String getColumnName(int c) {
        return OneAnte.ThisRowAtributeRu[c];
    }
}
