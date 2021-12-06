package DialogsAddDelShow;


import DialogSWing.NewDialog;
import SocketTCP.ClientTCPSocket;
import ManyTables.*;
import Tables.*;
import Checker.Checker;
import Convertion.Convertator;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;


public class SearchAndSortDialog {
    private NewDialog searchingDataDialog;
    private NewDialog sortingDataDialog;
    protected NewDialog metaDialogDialog;
    protected String stringToOrderBy = "";
    protected String stringToWhere = "";
    protected Class<? extends InterfaceTable> ClassThisTable;

    public SearchAndSortDialog(NewDialog metaDialogDialog, Class<? extends InterfaceTable> ClassThisTable){
        this.metaDialogDialog = metaDialogDialog;
        this.ClassThisTable = ClassThisTable;
    }

    public void getStartedSort() {
        sortingDataDialog = new NewDialog("Сортировка", 400, 170);

        sortingDataDialog.addLabel(300, 20,50, 10, "Поле для сортировки:");
        sortingDataDialog.addComboBox(false,300, 20,50, 40);
        choseDefCols(sortingDataDialog);
        sortingDataDialog.addButton(300,20,50, 90, "Получить");

        addListenerSort();
        sortingDataDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        sortingDataDialog.setVisible(true);
    }

    private void addListenerSort(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stringToOrderBy = sortingDataDialog.ComboBoxesList.get(0).getSelectedItem().toString();
                getData();
                sortingDataDialog.dispose();
            }
        };
        sortingDataDialog.ButtonsList.get(0).addActionListener(actionListener);
    }

    public void getStartedSearch() {
        searchingDataDialog = new NewDialog("Поиск", 400, 250);

        searchingDataDialog.addLabel(300, 20,50, 10, "Поле для поиска:");
        searchingDataDialog.addComboBox(false,300, 20,50, 40);
        choseDefCols(searchingDataDialog);
        searchingDataDialog.addLabel(300, 20,50, 90, "Значение поиска:");
        searchingDataDialog.addTextField(300, 20,50, 120);
        searchingDataDialog.addButton(300, 20,50, 170, "Найти");
        addListenerSearch();

        searchingDataDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchingDataDialog.setVisible(true);
    }

    private void addListenerSearch(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chosestringToWhereStr();
                if(!choseMessageOk()) return;
                getData();
                searchingDataDialog.dispose();
            }
        };
        searchingDataDialog.ButtonsList.get(0).addActionListener(actionListener);
    }

    private void chosestringToWhereStr(){
        String apos = "";
        switch (ClassThisTable.getName()) {
            case "Tables.OneSession":
                apos = trainingstringToWhereString(OneSession.ThisRowAtribute, OneSession.INT, apos); break;
            case "Tables.OneAnte":
                apos = trainingstringToWhereString(OneAnte.ThisRowAtribute, OneAnte.INT, apos); break;
            case "Tables.OneEmployer":
                apos = trainingstringToWhereString(OneEmployer.ThisRowAtribute, OneEmployer.INT, apos); break;
            case "Tables.OneUser":
                apos = trainingstringToWhereString(OneUser.ThisRowAtribute, OneUser.INT, apos); break;
        }
        stringToWhere += " = " + apos + searchingDataDialog.FieldsList.get(0).getText() + apos;
    }

    private String trainingstringToWhereString(String cols[], boolean isThisStringInteger[], String apos){
        stringToWhere = searchingDataDialog.ComboBoxesList.get(0).getSelectedItem().toString();
        for (int i = 0; i < cols.length; i++) {
            if(stringToWhere.equals(cols[i])) {
                if(!isThisStringInteger[i]) return "'";
                else return "";
            }
        }
        return "";
    }


    private boolean choseMessageOk(){
        switch (ClassThisTable.getName()) {
            case "Tables.OneSession":      return messageIntOk(OneSession.INT);
            case "Tables.OneAnte":     return messageIntOk(OneAnte.INT);
            case "Tables.OneEmployer":     return messageIntOk(OneEmployer.INT);
            case "Tables.OneUser":         return messageIntOk(OneUser.INT);
            default: return false;
        }
    }

    private boolean messageIntOk(boolean isStr[]){
        int selecterIndex = searchingDataDialog.ComboBoxesList.get(0).getSelectedIndex();
        boolean isThisStringIntegerColumns = selecterIndex == 0;
        for (int i = 0; i < isStr.length; i++) {
            if(!isStr[i]){
                isThisStringIntegerColumns = isThisStringIntegerColumns || selecterIndex == i+1;
            }
        }

        return checkCulumns(isThisStringIntegerColumns);
    }

    private boolean checkCulumns(boolean isThisStringIntegerColumns){
        if(isThisStringIntegerColumns) {
            if (!Checker.isThisStringInteger(searchingDataDialog.FieldsList.get(0).getText())) {
                String mes = "Введено не целое число для числового поля!";
                JOptionPane.showMessageDialog(searchingDataDialog, mes, "Ошибка!", JOptionPane.PLAIN_MESSAGE);
                return false;
            }
        }
        return true;
    }

    protected void choseDefCols(NewDialog dialog){
        switch (ClassThisTable.getName()) {
            case "Tables.OneSession":      addDefCols(dialog, OneSession.ThisRowAtribute); break;
            case "Tables.OneAnte":     addDefCols(dialog, OneAnte.ThisRowAtribute); break;
            case "Tables.OneEmployer":     addDefCols(dialog, OneEmployer.ThisRowAtribute); break;
            case "Tables.OneUser":         addDefCols(dialog, OneUser.ThisRowAtribute); break;
        }
    }

    private void addDefCols(NewDialog dialog, String items[]){
        for (int i = 0; i < items.length; i++) {
            dialog.ComboBoxesList.get(0).addItem(items[i]);
        }
    }

    protected void getData(){
        switch (ClassThisTable.getName()) {
            case "Tables.OneSession":      selectOneSession(); break;
            case "Tables.OneAnte":     selectOneAnte(); break;
            case "Tables.OneEmployer":     selectOneEmployer(); break;
            case "Tables.OneUser":         selectOneUser(); break;
        }
    }


    private void selectOneSession(){
        ArrayList<InterfaceTable> result;
        if((result = ClientTCPSocket.SelectOneSessions(stringToOrderBy, stringToWhere)) == null){
            JOptionPane.showMessageDialog(metaDialogDialog, "Ошибка сортировки!", "Ошибка!", JOptionPane.PLAIN_MESSAGE);
        } else {
            metaDialogDialog.TablesList.get(0).setModel(new ManySession(Convertator.toOneSession(result)));
        }
    }


    private void selectOneAnte(){
        ArrayList<InterfaceTable> result;
        if((result = ClientTCPSocket.SelectOneAntes(stringToOrderBy, stringToWhere)) == null){
            JOptionPane.showMessageDialog(metaDialogDialog, "Ошибка сортировки!", "Ошибка!", JOptionPane.PLAIN_MESSAGE);
        } else {
            metaDialogDialog.TablesList.get(0).setModel(new ManyAnte(Convertator.toOneAnte(result)));
        }
    }


    private void selectOneEmployer(){
        ArrayList<InterfaceTable> result;
        if((result = ClientTCPSocket.SelectOneEmployers(stringToOrderBy, stringToWhere)) == null){
            JOptionPane.showMessageDialog(metaDialogDialog, "Ошибка сортировки!", "Ошибка!", JOptionPane.PLAIN_MESSAGE);
        } else {
            metaDialogDialog.TablesList.get(0).setModel(new ManyEmployer(Convertator.toOneEmployer(result)));
        }
    }

    private void selectOneUser(){
        ArrayList<InterfaceTable> result;
        if((result = ClientTCPSocket.SelectOneUsers(stringToOrderBy, stringToWhere)) == null){
            JOptionPane.showMessageDialog(metaDialogDialog, "Ошибка сортировки!", "Ошибка!", JOptionPane.PLAIN_MESSAGE);
        } else {
            metaDialogDialog.TablesList.get(0).setModel(new ManyUser(Convertator.toOneUser(result)));
        }
    }

}
