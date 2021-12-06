package Main;

import DialogSWing.NewDialog;
import DialogSWing.MainClientDialog;
import SocketTCP.ClientTCPSocket;
import Tables.InterfaceTable;
import Tables.OneUser;


import javax.swing.*;
import java.awt.event.*;

public class Main {
    private NewDialog firstDialogFrame = null;

    public static void main(String[] args) {
        Main main = new Main();
        main.getStarted();
    }

    public void getStarted(){
        String answer = ClientTCPSocket.getStartedConnection();
        firstDialogFrame = new NewDialog("Вход в систему", 350, 450);
        creatCurrentDialog();
        firstDialogFrame.setVisible(true);
        if(!answer.equals("")) {
            JOptionPane.showMessageDialog(firstDialogFrame, answer);
            firstDialogFrame.dispose();
        }
    }

    private void creatCurrentDialog(){
        trainingAuthorizationDialog();
        setEventsForAuthorizationDialog();
        setEventsForRegistry();
        setEventsForCloseAuthorizationDialog();
    }

    private void trainingAuthorizationDialog(){
        firstDialogFrame.addLabel(200, 30,30, -5, "Есть аккаунт? Вход:");
        firstDialogFrame.addLabel(120, 30,30, 20, "Логин:");
        firstDialogFrame.addTextField(200, 30,120, 20);
        firstDialogFrame.addLabel(120, 30,30, 70, "Пароль:");
        firstDialogFrame.addPasswordField(200, 30,120, 70);
        firstDialogFrame.addButton(290, 30, 30, 120, "ВОЙТИ");

        firstDialogFrame.addLabel(200, 30, 30, 180, "Нет аккаунта? Регистрация:");
        firstDialogFrame.addLabel(120, 30,30, 220, "Логин:");
        firstDialogFrame.addTextField(200, 30,120, 220);
        firstDialogFrame.addLabel(120, 30,30, 270, "Пароль:");
        firstDialogFrame.addPasswordField(200, 30,120, 270);
        firstDialogFrame.addLabel(120, 30,30, 320, "Подвердите:");
        firstDialogFrame.addPasswordField(200, 30,120, 320);
        firstDialogFrame.addButton(290, 30, 30, 370, "ЗАРЕГЕСТРИРОВАТЬСЯ");
    }

    public void setEventsForAuthorizationDialog() {
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(isCorrectLogAndPass()) {
                    OneUser this_user_enter;
                    if((this_user_enter = getAuthorisationDataFromServer(firstDialogFrame.FieldsList.get(0).getText().trim(),
                            firstDialogFrame.PasswordFieldsList.get(0).getText().trim())) != null){
                            addDefAutorJob(this_user_enter);
                    } else {
                        JOptionPane.showMessageDialog(firstDialogFrame, "Некорректные данные!");
                    }
                }
            }
            private void addDefAutorJob(OneUser this_user_enter){
                MainClientDialog creator = new MainClientDialog();
                creator.createDialog(firstDialogFrame, this_user_enter.user_privileges);
                creator.getThisFrameDataDialog();
                clearDialogAuth();
            }
        };
        firstDialogFrame.ButtonsList.get(0).addActionListener(actionListener);
    }

    public void setEventsForRegistry() {
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(isCorrectLogAndPassRegistry()) {
                    OneUser this_user_enter;
                    if((this_user_enter = getRegistryDataFromServer(firstDialogFrame.FieldsList.get(1).getText().trim(),
                            firstDialogFrame.PasswordFieldsList.get(1).getText().trim())) != null){
                        addDefAutorJob(this_user_enter);
                    } else {
                        JOptionPane.showMessageDialog(firstDialogFrame, "Логин занят!");
                    }
                }
            }
            private void addDefAutorJob(OneUser this_user_enter){
                MainClientDialog creator = new MainClientDialog();
                creator.createDialog(firstDialogFrame, this_user_enter.user_privileges);
                creator.getThisFrameDataDialog();
                clearDialogAuth();
            }
        };
        firstDialogFrame.ButtonsList.get(1).addActionListener(actionListener);
    }

    private void clearDialogAuth(){
        firstDialogFrame.FieldsList.get(0).setText("");
        firstDialogFrame.FieldsList.get(1).setText("");
        firstDialogFrame.PasswordFieldsList.get(0).setText("");
        firstDialogFrame.PasswordFieldsList.get(1).setText("");
        firstDialogFrame.PasswordFieldsList.get(2).setText("");
        firstDialogFrame.dispose();
    }

    public void setEventsForCloseAuthorizationDialog() {
        WindowListener windowListener = new WindowListener() {
            @Override
            public void windowClosing(WindowEvent event) {
                ClientTCPSocket.exit();
            }
            public void windowActivated(WindowEvent event) {}
            public void windowClosed(WindowEvent event) {}
            public void windowDeactivated(WindowEvent event) {}
            public void windowDeiconified(WindowEvent event) {}
            public void windowIconified(WindowEvent event) {}
            public void windowOpened(WindowEvent event) {}
        };
        firstDialogFrame.addWindowListener(windowListener);
    }

    private boolean isCorrectLogAndPass(){
        boolean isOk = true;
        String login, password, errors = "";
        login = firstDialogFrame.FieldsList.get(0).getText().trim();
        password = firstDialogFrame.PasswordFieldsList.get(0).getText().trim();
        if("".equals(login)){
            errors += "Логин пуст!\n";
            isOk = false;
        }
        if("".equals(password)){
            errors += "Пароль пуст!\n";
            isOk = false;
        }

        if (!isOk) JOptionPane.showMessageDialog(firstDialogFrame, errors, "Ошибка ввода данных!", JOptionPane.PLAIN_MESSAGE);
        return isOk;
    }

    private boolean isCorrectLogAndPassRegistry(){
        boolean isOk = true;
        String login, password, passwordConfirm, errors = "";
        login = firstDialogFrame.FieldsList.get(1).getText().trim();
        password = firstDialogFrame.PasswordFieldsList.get(1).getText().trim();
        passwordConfirm = firstDialogFrame.PasswordFieldsList.get(2).getText().trim();
        if("".equals(login)){
            errors += "Логин пуст!\n";
            isOk = false;
        }
        if("".equals(password)){
            errors += "Пароль пуст!\n";
            isOk = false;
        }
        if(!passwordConfirm.equals(password)){
            errors += "Пароли не совпадают!\n";
            isOk = false;
        }

        if (!isOk) JOptionPane.showMessageDialog(firstDialogFrame, errors, "Ошибка ввода данных!", JOptionPane.PLAIN_MESSAGE);
        return isOk;
    }

    private OneUser getAuthorisationDataFromServer(String login, String password){
        return ClientTCPSocket.checkAuthorization(new OneUser (0, login, password,1));
    }

    private OneUser getRegistryDataFromServer(String login, String password){
        if(!ClientTCPSocket.insert(new OneUser (0, login, password,1), "users")){
            return null;
        }
        return (OneUser) ClientTCPSocket.SelectOneUsers("id desc", "").get(0);
    }

}
