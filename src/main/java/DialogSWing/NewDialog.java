package DialogSWing;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class NewDialog extends JFrame {
    private JPanel currentDialogPanelOnj;
    public ArrayList<JButton> ButtonsList = new ArrayList<>();
    public ArrayList<JTextField> FieldsList = new ArrayList<>();
    public ArrayList<JPasswordField> PasswordFieldsList = new ArrayList<>();
    public ArrayList<JLabel> LabelsList = new ArrayList<>();
    public ArrayList<JTable> TablesList = new ArrayList<>();
    public ArrayList<JComboBox> ComboBoxesList = new ArrayList<>();

    public NewDialog(String headMessage, int width, int height){
        super(headMessage);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        currentDialogPanelOnj = new JPanel();
        currentDialogPanelOnj.setLayout(null);
        setLocationRelativeTo(null);
    }


    public void addButton(int w, int h, int x, int y, String headMessage){
        JButton myButton = new JButton(headMessage);
        myButton.setSize(w, h);
        myButton.setLocation(x,y);

        currentDialogPanelOnj.add(myButton);
        setContentPane(currentDialogPanelOnj);

        ButtonsList.add(myButton);
    }


    public void addTextField(int w, int h, int x, int y){
        JTextField myTextField = new JTextField("");
        myTextField.setSize(w, h);
        myTextField.setLocation(x,y);

        currentDialogPanelOnj.add(myTextField);

        FieldsList.add(myTextField);
    }


    public void addPasswordField(int w, int h, int x, int y){
        JPasswordField myPasswordField = new JPasswordField("");
        myPasswordField.setSize(w, h);
        myPasswordField.setLocation(x,y);

        currentDialogPanelOnj.add(myPasswordField);

        PasswordFieldsList.add(myPasswordField);
    }

    public void addLabel(int w, int h, int x, int y, String headMessage){
        JLabel myLabel = new JLabel(headMessage);
        myLabel.setSize(w, h);
        myLabel.setLocation(x,y);

        currentDialogPanelOnj.add(myLabel);

        LabelsList.add(myLabel);
    }


    public void addComboBox(boolean isEditable, int w, int h, int x, int y){
        JComboBox myComboBox = new JComboBox();
        myComboBox.setEditable(isEditable);
        myComboBox.setSize(w, h);
        myComboBox.setLocation(x,y);
        currentDialogPanelOnj.add(myComboBox);

        ComboBoxesList.add(myComboBox);
    }

    public void addTable(int w, int h, int x, int y, AbstractTableModel model){

        JTable myTable = new JTable(model);
        myTable.setSize(w, h);
        myTable.setLocation(x,y);

        JScrollPane scrollPane = new JScrollPane(myTable);
        scrollPane.setSize(w, h);
        scrollPane.setLocation(x,y);

        currentDialogPanelOnj.add(scrollPane);
        TablesList.add(myTable);
    }


}




