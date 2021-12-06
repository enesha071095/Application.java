package DialogsAddDelShow;

import DialogSWing.CurrentTable;
import DialogSWing.NewDialog;
import SocketTCP.ClientTCPSocket;
import Tables.InterfaceTable;
import Tables.OneEmployer;
import Tables.OneSession;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class GetSessionDialog {
    protected NewDialog ThisFrameDataDialog;
    protected Class<? extends InterfaceTable> ClassThisTable;
    protected CurrentTable metaDialog;

    public GetSessionDialog(NewDialog ThisFrameDataDialog, Class<? extends InterfaceTable> ClassThisTable, CurrentTable metaDialog){
        this.ThisFrameDataDialog = ThisFrameDataDialog;
        this.ClassThisTable = ClassThisTable;
        this.metaDialog = metaDialog;
    }

    public void getStarted() {
        if(ThisFrameDataDialog.TablesList.get(0).getSelectedRowCount() < 1) {
            JOptionPane.showMessageDialog(ThisFrameDataDialog, "Не выбрана процедура!");
            return;
        }
        int id = CurrentTable.getData(ClassThisTable).get(ThisFrameDataDialog.TablesList.get(0).getSelectedRow()).getId();

        String message = prepareMessage(id);
        JOptionPane.showMessageDialog(ThisFrameDataDialog, message);
        saveData(message);
        metaDialog.updateData(ClassThisTable, ThisFrameDataDialog);
    }

    private String prepareMessage(int id){
        String stringToWhere = "id = " + id;
        OneSession session = (OneSession)ClientTCPSocket.SelectOneSessions("", stringToWhere).get(0);

        stringToWhere = "id = " + session.employer_id;
        OneEmployer employer = (OneEmployer)ClientTCPSocket.SelectOneEmployers("", stringToWhere).get(0);

        Date date = new Date();
        String message = "Вы записаны на сеанс:\n";
        message += "\tпроцедуру - " + session.name + ";\n";
        message += "\tДата - " + (date.getDay() + 2) + "." + (date.getMonth() + 1) + "." + (date.getYear()+1900) + ";\n";
        message += "\tРаботник - " + employer.name + " " + employer.lastname + "(балл: " + employer.point + ");\n";
        message += "\tЦена - " + session.price + ";\n";
        message += "Спасибо за заказ, ждём вас!";
        return message;
    }

    private void saveData(String message){
        try(FileWriter writer = new FileWriter("Your order.txt", false)) {
            writer.write(message);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println("Ошибка записи: " + ex.getMessage());
        }
    }

}
