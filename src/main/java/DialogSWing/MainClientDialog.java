package DialogSWing;


import SocketTCP.ClientTCPSocket;
import Tables.*;

import javax.swing.*;
import java.awt.event.*;


public class MainClientDialog {
    protected NewDialog ThisFrameDataDialog;
    private NewDialog firstDialogFrame;
    protected int adminChecking;

    public NewDialog getThisFrameDataDialog() {
        ThisFrameDataDialog.setVisible(true);
        return ThisFrameDataDialog;
    }

    public void createDialog(NewDialog firstDialogFrame, int adminChecking){
        this.adminChecking = adminChecking;
        ThisFrameDataDialog = new NewDialog("Выбор таблицы!", 380, 350);
        setEventsForCloseDialog();
        trainingThisFrameDataDialog();
        this.firstDialogFrame = firstDialogFrame;
    }

    public void setEventsForCloseDialog() {
        WindowListener windowListener = new WindowListener() {
            public void windowActivated(WindowEvent event) {}
            public void windowClosed(WindowEvent event) {}
            public void windowDeactivated(WindowEvent event) {}
            public void windowDeiconified(WindowEvent event) {}
            public void windowIconified(WindowEvent event) {}
            public void windowOpened(WindowEvent event) {}

            public void windowClosing(WindowEvent event) {
                String answer = ClientTCPSocket.exit();
                if(!answer.equals("")) {
                    JOptionPane.showMessageDialog(ThisFrameDataDialog, answer);
                }
            }
        };
        ThisFrameDataDialog.addWindowListener(windowListener);
    }

    public void trainingThisFrameDataDialog(){
        ThisFrameDataDialog.addButton(300, 30,40, 60, "Таблица \"Записи\"");
        ThisFrameDataDialog.addButton(300, 30,40, 110, "Таблица \"Процедура в час\"");
        ThisFrameDataDialog.addButton(300, 30,40, 160, "Таблица \"Работники\"");
        ThisFrameDataDialog.addButton(300, 30,40, 210, "Таблица \"Пользователи\"");
        ThisFrameDataDialog.addButton(300, 30,40, 260, "Выход из Учётной Записи");
        addEvents();
    }

    private void addEvents(){
        setEventOneSession();
        setEventOneAnte();
        setEventOneEmployer();
        setEventOneUser();
        setEventExit();
    }



    protected void setEventOneSession(){
        ThisFrameDataDialog.ButtonsList.get(0).addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CurrentTable dialog = new CurrentTable(ThisFrameDataDialog, OneSession.class, "Таблица \"Записи\"", adminChecking, 1);
                dialog.create();
            }
        });
    }

    protected void setEventOneAnte(){
        ThisFrameDataDialog.ButtonsList.get(1).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CurrentTable dialog = new CurrentTable(ThisFrameDataDialog, OneAnte.class, "Таблица \"Процедура в час\"", adminChecking, 2);
                dialog.create();
            }
        });
    }

    protected void setEventOneEmployer(){
        ThisFrameDataDialog.ButtonsList.get(2).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CurrentTable dialog = new CurrentTable(ThisFrameDataDialog, OneEmployer.class, "Таблица \"Работники\"", adminChecking, 3);
                dialog.create();
            }
        });
    }


    protected void setEventOneUser(){
        ThisFrameDataDialog.ButtonsList.get(3).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CurrentTable dialog = new CurrentTable(ThisFrameDataDialog, OneUser.class, "Таблица \"Пользователи\"", adminChecking, 4);
                dialog.create();
            }
        });
    }


    protected ActionListener setEventExit(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ThisFrameDataDialog.dispose();
                firstDialogFrame.setVisible(true);
            }
        };
        ThisFrameDataDialog.ButtonsList.get(4).addActionListener(actionListener);
        return actionListener;
    }
}
