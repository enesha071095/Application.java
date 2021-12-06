package DialogsAddDelShow;

import DialogSWing.CurrentTable;
import DialogSWing.NewDialog;
import SocketTCP.ClientTCPSocket;
import Tables.*;
import Checker.Checker;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AddingDialog {
    private NewDialog addDataDialog;
    protected NewDialog ThisFrameDataDialog;
    protected Class<? extends InterfaceTable> ClassThisTable;
    protected ArrayList<InterfaceTable> rowsFromTable;
    protected CurrentTable metaDialog;

    public AddingDialog(NewDialog ThisFrameDataDialog, Class<? extends InterfaceTable> ClassThisTable, ArrayList<InterfaceTable> rowsFromTable, CurrentTable metaDialog){
        this.ThisFrameDataDialog = ThisFrameDataDialog;
        this.ClassThisTable = ClassThisTable;
        this.rowsFromTable = CurrentTable.updateData(this.ClassThisTable, this.ThisFrameDataDialog);
        this.metaDialog = metaDialog;
    }

    public void getStarted(String thisClientCommand) {
        boolean isEditCommand;
        isEditCommand = (thisClientCommand == "ДОБАВИТЬ") ? false : true;
        if(isEditCommand  && ThisFrameDataDialog.TablesList.get(0).getSelectedRowCount() < 1) {
            JOptionPane.showMessageDialog(ThisFrameDataDialog, "Не выбрано ничего!");
            return;
        }
        createDialog(isEditCommand);
    }

    private void createDialog(boolean isEditCommand){
        trainingAddData();
        if (isEditCommand) addDataLast();
        addEventAdd(isEditCommand);
        addDefFieldProp();
    }

    private void addDefFieldProp(){
        addDataDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addDataDialog.setVisible(true);
    }


    protected void trainingAddData(){
        addDataDialog = new NewDialog("Добавить\\Изменить Строку - ХЕББЕКУЛИЕВА ЭНЕШ}", 400, 350);

        int i;
        String columns[] = getColumns();
        for (i = 1; i < columns.length; i++) {
            addDataDialog.addLabel(300, 20,50, 10 + (i-1)*80, columns[i]);
            addDataDialog.addTextField(300, 20,30, 40 + (i-1)*80);
        }

        addDataDialog.addButton(300, 20,30, 90+(i-2)*80, "Добавить");
        addDataDialog.setSize(350, 180+(i-2)*80);
    }

    private String[] getColumns(){
        switch (ClassThisTable.getName()) {
            default:
            case "Tables.OneSession":      return OneSession.ThisRowAtribute;
            case "Tables.OneAnte":     return OneAnte.ThisRowAtribute;
            case "Tables.OneEmployer":     return OneEmployer.ThisRowAtribute;
            case "Tables.OneUser":         return OneUser.ThisRowAtribute;
        }
    }

    protected void addDataLast(){
        String stringToWhere = "id = " + CurrentTable.getData(ClassThisTable).get(ThisFrameDataDialog.TablesList.get(0).getSelectedRow()).getId();

        ArrayList<InterfaceTable> result = getData(stringToWhere);
        if(result == null){
            JOptionPane.showMessageDialog(addDataDialog, "Ошибка поиска!", "Ошибка!", JOptionPane.PLAIN_MESSAGE);
            addDataDialog.dispose();
        }
        InterfaceTable interfaceTable = result.get(0);
        addOneLastData(interfaceTable);
    }

    private ArrayList<InterfaceTable> getData(String stringToWhere){
        switch (ClassThisTable.getName()) {
            case "Tables.OneSession":      return ClientTCPSocket.SelectOneSessions("", stringToWhere);
            case "Tables.OneAnte":     return ClientTCPSocket.SelectOneAntes("", stringToWhere);
            case "Tables.OneEmployer":     return ClientTCPSocket.SelectOneEmployers("", stringToWhere);
            case "Tables.OneUser":         return ClientTCPSocket.SelectOneUsers("", stringToWhere);
            default: return null;
        }
    }

    private void addOneLastData(InterfaceTable interfaceTable){
        switch (ClassThisTable.getName()) {
            case "Tables.OneSession":         lastOneSession((OneSession) interfaceTable);             break;
            case "Tables.OneAnte":     lastOneAnte((OneAnte) interfaceTable);     break;
            case "Tables.OneEmployer":      lastOneEmployer((OneEmployer) interfaceTable);       break;
            case "Tables.OneUser":          lastOneUser((OneUser) interfaceTable);               break;
        }
    }

    private void lastOneSession(OneSession model) {
        addDataDialog.FieldsList.get(0).setText(model.name);
        addDataDialog.FieldsList.get(1).setText(String.valueOf(model.price));
        addDataDialog.FieldsList.get(2).setText(String.valueOf(model.employer_id));
    }

    private void lastOneAnte(OneAnte model) {
        addDataDialog.FieldsList.get(0).setText(String.valueOf(model.value));
        addDataDialog.FieldsList.get(1).setText(String.valueOf(model.employer_id));
    }

    private void lastOneEmployer(OneEmployer model) {
        addDataDialog.FieldsList.get(0).setText(model.name);
        addDataDialog.FieldsList.get(1).setText(String.valueOf(model.point));
        addDataDialog.FieldsList.get(2).setText(model.lastname);
    }

    private void lastOneUser(OneUser model) {
        addDataDialog.FieldsList.get(0).setText(model.name);
        addDataDialog.FieldsList.get(1).setText(model.password);
        addDataDialog.FieldsList.get(2).setText(String.valueOf(model.user_privileges));
    }



    protected void addEventAdd(final boolean isEditCommand){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(changeAndCheckOk()){
                    addData(isEditCommand, generateNewModel());
                    metaDialog.updateData(ClassThisTable, ThisFrameDataDialog);
                    addDataDialog.dispose();
                }
            }
        };
        addDataDialog.ButtonsList.get(0).addActionListener(actionListener);
    }

    private void addData(boolean isEditCommand, InterfaceTable interfaceTable){
        if(isEditCommand){
            int id = rowsFromTable.get(ThisFrameDataDialog.TablesList.get(0).getSelectedRow()).getId();
            if (!ClientTCPSocket.update(interfaceTable, CurrentTable.getCurrentTable(ClassThisTable), id)) {
                JOptionPane.showMessageDialog(addDataDialog, "Ошибка редактирования!");
            }
        } else {
            if (!ClientTCPSocket.insert(interfaceTable, CurrentTable.getCurrentTable(ClassThisTable))) {
                JOptionPane.showMessageDialog(addDataDialog, "Ошибка добавления!");
            }
        }
    }

    private InterfaceTable generateNewModel(){
        switch (ClassThisTable.getName()) {
            default:
            case "Tables.OneSession":      return createOneSession();
            case "Tables.OneAnte":     return createOneAnte();
            case "Tables.OneEmployer":     return createOneEmployer();
            case "Tables.OneUser":         return createOneUser();
        }
    }

    private InterfaceTable createOneSession() {
        OneSession model = new OneSession(
                0,
                addDataDialog.FieldsList.get(0).getText(),
                Integer.valueOf(addDataDialog.FieldsList.get(1).getText()),
                Integer.valueOf(addDataDialog.FieldsList.get(2).getText())
        );
        return (InterfaceTable) model;
    }

    private InterfaceTable createOneAnte() {
        OneAnte model = new OneAnte(
                0,
                Integer.valueOf(addDataDialog.FieldsList.get(0).getText()),
                Integer.valueOf(addDataDialog.FieldsList.get(1).getText())
        );
        return (InterfaceTable) model;
    }

    private InterfaceTable createOneEmployer() {
        OneEmployer model = new OneEmployer(
                0,
                addDataDialog.FieldsList.get(0).getText(),
                Integer.valueOf(addDataDialog.FieldsList.get(1).getText()),
                addDataDialog.FieldsList.get(2).getText()
        );
        return (InterfaceTable) model;
    }

    private InterfaceTable createOneUser() {
        OneUser model = new OneUser(
                0,
                addDataDialog.FieldsList.get(0).getText(),
                addDataDialog.FieldsList.get(1).getText(),
                Integer.valueOf(addDataDialog.FieldsList.get(2).getText())
        );
        return (InterfaceTable) model;
    }

    private boolean changeAndCheckOk(){
        switch (ClassThisTable.getName()) {
            case "Tables.OneSession":      return checkAddOk(OneSession.ThisRowAtribute, OneSession.INT);
            case "Tables.OneAnte":     return checkAddOk(OneAnte.ThisRowAtribute, OneAnte.INT);
            case "Tables.OneEmployer":     return checkAddOk(OneEmployer.ThisRowAtribute, OneEmployer.INT);
            case "Tables.OneUser":         return checkAddOk(OneUser.ThisRowAtribute, OneUser.INT);
            default: return false;
        }
    }

    private boolean checkAddOk(String columns[], boolean isThisStringIntegerColumns[]){
        String answer = "";
        for (int i = 1; i < isThisStringIntegerColumns.length; i++) {
                if(!isThisStringIntegerColumns[i]){
                if("".equals(addDataDialog.FieldsList.get(i-1).getText().trim())){
                    answer += columns[i] + " - это пуст!\r\n";
                }
            }  else {
                if(!Checker.isThisStringInteger(addDataDialog.FieldsList.get(i-1).getText())){
                    answer += columns[i] + " - это не  число!\r\n";
                }
            }
        }
        if(!answer.equals("")) JOptionPane.showMessageDialog(addDataDialog, "Ошибки:\r\n" + answer, "Ошибка!", JOptionPane.PLAIN_MESSAGE);
        return answer.equals("") ? true : false;
    }



}
