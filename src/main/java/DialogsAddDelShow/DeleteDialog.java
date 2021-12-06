package DialogsAddDelShow;

import DialogSWing.CurrentTable;
import DialogSWing.NewDialog;
import SocketTCP.ClientTCPSocket;
import Tables.InterfaceTable;

import javax.swing.*;

public class DeleteDialog {
    protected NewDialog ThisFrameDataDialog;
    protected Class<? extends InterfaceTable> ClassThisTable;
    protected CurrentTable metaDialog;

    public DeleteDialog(NewDialog ThisFrameDataDialog, Class<? extends InterfaceTable> ClassThisTable, CurrentTable metaDialog){
        this.ThisFrameDataDialog = ThisFrameDataDialog;
        this.ClassThisTable = ClassThisTable;
        this.metaDialog = metaDialog;
    }

    public void getStarted() {
        if(ThisFrameDataDialog.TablesList.get(0).getSelectedRowCount() < 1) {
            JOptionPane.showMessageDialog(ThisFrameDataDialog, "Не выбрано ничего!");
            return;
        }
        int id = CurrentTable.getData(ClassThisTable).get(ThisFrameDataDialog.TablesList.get(0).getSelectedRow()).getId();
        if(!ClientTCPSocket.delete(id, CurrentTable.getCurrentTable(ClassThisTable))) {
            JOptionPane.showMessageDialog(ThisFrameDataDialog, "Ошибка удаления!");
        }
        metaDialog.updateData(ClassThisTable, ThisFrameDataDialog);
    }


}
