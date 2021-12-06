package ManyTables;

import Tables.OneSession;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ManySession extends AbstractTableModel {
    private ArrayList<OneSession> manySessions;


    public ManySession(ArrayList<OneSession> models ) {
        super();
        this.manySessions = models;
    }

    @Override
    public int getRowCount() {
        return manySessions.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch (c){
            default:case 0: return manySessions.get(r).id;
            case 1: return manySessions.get(r).name;
            case 2: return manySessions.get(r).price;
            case 3: return manySessions.get(r).employer_id;
        }
    }

    @Override
    public String getColumnName(int c) {
        return OneSession.ThisRowAtributeRu[c];
    }
}
