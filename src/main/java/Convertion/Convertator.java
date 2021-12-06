package Convertion;

import Tables.*;

import java.util.ArrayList;

public class Convertator {

    public static ArrayList<OneSession> toOneSession(ArrayList<InterfaceTable> rowsFromTable){
        if(rowsFromTable == null) return null;

        ArrayList<OneSession> oneSessions = new ArrayList<>();
        for (int i = 0; i < rowsFromTable.size(); i++) {
            oneSessions.add((OneSession) rowsFromTable.get(i));
        }

        return oneSessions;
    }

    public static ArrayList<OneAnte> toOneAnte(ArrayList<InterfaceTable> rowsFromTable){
        if(rowsFromTable == null) return null;

        ArrayList<OneAnte> oneAntes = new ArrayList<>();
        for (int i = 0; i < rowsFromTable.size(); i++) {
            oneAntes.add((OneAnte) rowsFromTable.get(i));
        }

        return oneAntes;
    }

    public static ArrayList<OneEmployer> toOneEmployer(ArrayList<InterfaceTable> rowsFromTable){
        if(rowsFromTable == null) return null;

        ArrayList<OneEmployer> oneEmployers = new ArrayList<>();
        for (int i = 0; i < rowsFromTable.size(); i++) {
            oneEmployers.add((OneEmployer) rowsFromTable.get(i));
        }

        return oneEmployers;
    }

    public static ArrayList<OneUser> toOneUser(ArrayList<InterfaceTable> rowsFromTable){
        if(rowsFromTable == null) return null;

        ArrayList<OneUser> OneUsers = new ArrayList<>();
        for (int i = 0; i < rowsFromTable.size(); i++) {
            OneUsers.add((OneUser) rowsFromTable.get(i));
        }

        return OneUsers;
    }

}
