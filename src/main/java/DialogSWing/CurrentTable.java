package DialogSWing;

import DialogsAddDelShow.GetSessionDialog;
import ObjToString.ObjStringTransformator;
import SocketTCP.ClientTCPSocket;
import ManyTables.*;
import Tables.*;
import DialogsAddDelShow.AddingDialog;
import DialogsAddDelShow.DeleteDialog;
import DialogsAddDelShow.SearchAndSortDialog;
import Convertion.Convertator;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class CurrentTable {
    private NewDialog ThisFrameDataDialog;
    private NewDialog this_dialog_one;
    private String headMessage;
    private Class<? extends InterfaceTable> ClassThisTable;
    private ArrayList<InterfaceTable> rowsFromTable = new ArrayList<>();
    private int adminChecking;
    private int idCurr;

    public CurrentTable(NewDialog ThisFrameDataDialog, Class<? extends InterfaceTable> ClassThisTable, String headMessage, int adminChecking, int idCurr){
        this.ThisFrameDataDialog = ThisFrameDataDialog;
        this.ClassThisTable = ClassThisTable;
        this.headMessage = "Table: " + headMessage;
        this.adminChecking = adminChecking;
        this.idCurr = idCurr;
    }

    public void create(){
        this_dialog_one = new NewDialog(headMessage, 675, 590);
        addWindowListener();
        trainingDialog();
        setEventsForButtonsList();
        this_dialog_one.setVisible(true);
    }

    private void addWindowListener(){
        this_dialog_one.addWindowListener(new WindowListener() {
            public void windowActivated(WindowEvent event) {}
            public void windowClosed(WindowEvent event) {}
            public void windowDeactivated(WindowEvent event) {}
            public void windowDeiconified(WindowEvent event) {}
            public void windowIconified(WindowEvent event) {}
            public void windowOpened(WindowEvent event) {
                ThisFrameDataDialog.setVisible(false);
            }
            public void windowClosing(WindowEvent event) {
                ThisFrameDataDialog.setVisible(true);
            }
        });
        this_dialog_one.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void trainingDialog(){
        if((rowsFromTable = CurrentTable.getData(ClassThisTable)) == null) {
            JOptionPane.showMessageDialog(this_dialog_one, "Ошибка: данные не обнаружены!");
            this_dialog_one.dispose();
            return;
        }

        this_dialog_one.addButton(200, 30, 50, 10, "ПОИСК");
        this_dialog_one.addButton(200, 30,300, 10, "СОРТИРОВКА");
        this_dialog_one.addButton(200, 30,50, 60, "СБРОСИТЬ ФИЛЬТРЫ");
        if(adminChecking == 0) {
            this_dialog_one.addButton(200, 30, 300, 60, "ДОБАВИТЬ");
            this_dialog_one.addButton(200, 30, 50, 110, "РЕДАКТИРОВАТЬ");
            this_dialog_one.addButton(200, 30, 300, 110, "УДАЛИТЬ");
        }
        int y = adminChecking == 0 ? 160 : 110;
        this_dialog_one.addButton(600, 30,30, y, "СОХРАНИТЬ");
        this_dialog_one.addTable(600, 300, 30, y+50, getModel(rowsFromTable, ClassThisTable));
        if(ClassThisTable.getName().equals("Tables.OneSession"))
            this_dialog_one.addButton(100, 130, 530, 10, "ЗАПИСЬ!");
        if(ClassThisTable.getName().equals("Tables.OneAnte")) addMouseListener();

    }

    private void addMouseListener(){
        MouseAdapter mouseAdapter = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int id = this_dialog_one.TablesList.get(0).getSelectedRow(), sum = 0, count = 0;
                    OneAnte ante = ((ManyAnte)this_dialog_one.TablesList.get(0).getModel()).getManyAntes().get(id);
                    ArrayList<OneSession> sessions = Convertator.toOneSession(
                            ClientTCPSocket.SelectOneSessions("", " employer_id = " + ante.employer_id + " "));
                    for (int i = 0; i < sessions.size(); i++) {
                        count++;
                        sum += sessions.get(i).price;
                    }
                    ante.value = (int)Math.round(((double)sum/count)*100);
                    ClientTCPSocket.update((InterfaceTable)ante, "antes", ante.id);
                    updateData(ClassThisTable, this_dialog_one);
                }
            }
        };
        this_dialog_one.TablesList.get(0).addMouseListener(mouseAdapter);
    }

    public static ArrayList<InterfaceTable> getData(Class<? extends InterfaceTable> ClassThisTable){
        switch (ClassThisTable.getName()) {
            case "Tables.OneSession":      return ClientTCPSocket.SelectOneSessions("", "");
            case "Tables.OneAnte":     return ClientTCPSocket.SelectOneAntes("", "");
            case "Tables.OneEmployer":     return ClientTCPSocket.SelectOneEmployers("", "");
            case "Tables.OneUser":         return ClientTCPSocket.SelectOneUsers("", "");
            default: return null;
        }
    }

    private static AbstractTableModel getModel(ArrayList<InterfaceTable> interfaceTable, Class<? extends InterfaceTable> ClassThisTable){
        switch (ClassThisTable.getName()) {
            case "Tables.OneSession":      return new ManySession(Convertator.toOneSession(interfaceTable));
            case "Tables.OneAnte":     return new ManyAnte(Convertator.toOneAnte(interfaceTable));
            case "Tables.OneEmployer":     return new ManyEmployer(Convertator.toOneEmployer(interfaceTable));
            case "Tables.OneUser":         return new ManyUser(Convertator.toOneUser(interfaceTable));
            default: return null;
        }
    }

    private void setEventsForButtonsList(){
        addAdmin();
        setEventSearch();
        setEventSort();
        setEventReset();
        setEventScrin(adminChecking);
        if(ClassThisTable.getName().equals("Tables.OneSession"))
            setEventGetSession();
    }

    private void addAdmin() {
        if(adminChecking == 0) {
            setEventAddRed();
            setEventDel();
        }
    }


    private void setEventAddRed(){
        final AddingDialog baddDataDialog =  new AddingDialog(this_dialog_one, ClassThisTable, rowsFromTable, this);
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                   baddDataDialog.getStarted(e.getActionCommand());

            }
        };
        this_dialog_one.ButtonsList.get(3).addActionListener(actionListener);
        this_dialog_one.ButtonsList.get(4).addActionListener(actionListener);
    }

    private void setEventDel(){
        final DeleteDialog bdelListener =  new DeleteDialog(this_dialog_one, ClassThisTable, this);
        this_dialog_one.ButtonsList.get(5).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bdelListener.getStarted();
            }
        });
    }

    private void setEventGetSession(){
        final GetSessionDialog listener =  new GetSessionDialog(this_dialog_one, ClassThisTable, this);
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.getStarted();
            }
        };

        if(adminChecking == 0) {
            this_dialog_one.ButtonsList.get(7).addActionListener(actionListener);
        } else {
            this_dialog_one.ButtonsList.get(4).addActionListener(actionListener);
        }
    }

    private void setEventSearch(){
        final SearchAndSortDialog searchAndSortDialog =  new SearchAndSortDialog(this_dialog_one, ClassThisTable );
        this_dialog_one.ButtonsList.get(0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchAndSortDialog.getStartedSearch();
            }
        });
    }

    private void setEventSort(){
        final SearchAndSortDialog searchAndSortDialog =  new SearchAndSortDialog(this_dialog_one, ClassThisTable);
        this_dialog_one.ButtonsList.get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchAndSortDialog.getStartedSort();
            }
        });
    }

    private void setEventReset(){
        this_dialog_one.ButtonsList.get(2).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rowsFromTable = updateData(ClassThisTable, this_dialog_one);
            }
        });
    }

    private void setEventScrin(int adminChecking){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    //BufferedImage image =// new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                    BufferedImage image = newBufferImage();
                    this_dialog_one.paint( image.getGraphics() );
                    ImageIO.write(image, "png", new File(ClassThisTable.getSimpleName()+".png"));
                } catch (Exception exep){
                    JOptionPane.showMessageDialog(this_dialog_one, "Ошибка скрининга!");
                }
            }
            private BufferedImage newBufferImage (){
                return new BufferedImage(
                        this_dialog_one.getWidth(),
                        this_dialog_one.getHeight(),
                        BufferedImage.TYPE_INT_RGB
                );
            }
        };
        if(adminChecking == 0) {
            this_dialog_one.ButtonsList.get(6).addActionListener(actionListener);
        } else {
            this_dialog_one.ButtonsList.get(3).addActionListener(actionListener);
        }
    }

    public static ArrayList<InterfaceTable> updateData(Class<? extends InterfaceTable> ClassThisTable, NewDialog thisDialog){
        ArrayList<InterfaceTable> table;
        if((table = CurrentTable.getData(ClassThisTable)) == null) {
            JOptionPane.showMessageDialog(thisDialog, "Ошибка получения данных!");
            thisDialog.dispose();
        }
        thisDialog.TablesList.get(0).setModel(getModel(table, ClassThisTable));
        return table;
    }

    public static String getCurrentTable(Class<? extends InterfaceTable> ClassThisTable){
        switch (ClassThisTable.getName()) {
            default:
            case "Tables.OneSession":         return "sessions";
            case "Tables.OneAnte":     return "antes";
            case "Tables.OneEmployer":     return "employers";
            case "Tables.OneUser":         return "users";
        }
    }


}
