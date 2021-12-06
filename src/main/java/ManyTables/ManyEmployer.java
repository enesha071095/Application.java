package ManyTables;

import Tables.OneEmployer;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ManyEmployer extends AbstractTableModel {
    private ArrayList<OneEmployer> manyEmployers;

    public ArrayList<OneEmployer> getmanyEmployers() {
        return manyEmployers;
    }

    public ManyEmployer(ArrayList<OneEmployer> manyEmployers) {
        super();
        this.manyEmployers = manyEmployers;
    }


    @Override
    public int getRowCount() {
        return manyEmployers.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch (c){
            default:case 0: return manyEmployers.get(r).id;
            case 1: return manyEmployers.get(r).name;
            case 2: return manyEmployers.get(r).point;
            case 3: return manyEmployers.get(r).lastname;
        }
    }

    @Override
    public String getColumnName(int c) {
        return OneEmployer.ThisRowAtributeRu[c];
    }

}
