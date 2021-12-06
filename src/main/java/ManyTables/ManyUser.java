package ManyTables;


import Tables.OneUser;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ManyUser extends AbstractTableModel {
    private ArrayList<OneUser> manyUsers;


    public ManyUser(ArrayList<OneUser> models) {
        super();
        this.manyUsers = models;
    }

    @Override
    public int getRowCount() {
        return manyUsers.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch (c){
            default:case 0: return manyUsers.get(r).id;
            case 1: return manyUsers.get(r).name;
            case 2: return cryptePassword(manyUsers.get(r).password);
            case 3: return manyUsers.get(r).user_privileges;
        }
    }

    private String cryptePassword(String password){
        StringBuilder hiddenPassword = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            hiddenPassword.append("*");
        }
        return hiddenPassword.toString();
    }

    @Override
    public String getColumnName(int c) {
        return OneUser.ThisRowAtributeRu[c];
    }

}
