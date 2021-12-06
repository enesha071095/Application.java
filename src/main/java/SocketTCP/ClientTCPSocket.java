package SocketTCP;


import Tables.*;
import com.google.gson.reflect.TypeToken;
import ObjToString.ObjStringTransformator;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class ClientTCPSocket {
    private static ClientTCPSocket thisClientTCPSocket;
    private ObjectOutputStream objectStreamout;
    private ObjectInputStream objectStreamin;
    private java.net.Socket this_socket;
    public boolean isHaveError;

    public void getAccepted(){
        try {
            this_socket = new java.net.Socket("127.0.0.1",7777);
            objectStreamout = new ObjectOutputStream(this_socket.getOutputStream());
            objectStreamin = new ObjectInputStream(this_socket.getInputStream());

            isHaveError = false;
        } catch(Exception e){
            isHaveError = true;
        }
    }

    public String closeSocket(){
        try {
            goCommand("exit", "");
            objectStreamout.close();
            objectStreamin.close();
            this_socket.close();
            return "";
        } catch (Exception e){
            return "Ошибка разсоединения!";
        }
    }


    public String goCommand(String thisClientCommand, String data){
        try {
            objectStreamout.writeObject(thisClientCommand);
            objectStreamout.writeObject(data);
            isHaveError = false;

            return objectStreamin.readObject().toString();
        } catch (Exception e){
            isHaveError = true;
            return "";
        }

    }

    public static String getStartedConnection(){
        thisClientTCPSocket = new ClientTCPSocket();
        thisClientTCPSocket.getAccepted();
        return thisClientTCPSocket.isHaveError ? "Ошибка подключения" : "";
    }

    public static OneUser checkAuthorization(OneUser user) {
        String userJSON = ObjStringTransformator.serialiseObj(user), thisClientCommand = "authorization", answer;
        answer = thisClientTCPSocket.goCommand(thisClientCommand, userJSON);
        return  !("".equals(answer)) ? (OneUser) ObjStringTransformator.deserialiseStr(answer, OneUser.class) : null;
    }


    public static ArrayList<InterfaceTable> SelectOneSessions(String stringToOrderBy, String stringToWhere){
        String answer = SelectModels("select_sessions", stringToOrderBy, stringToWhere);
        return !("".equals(answer)) ? ObjStringTransformator.deserialiseStrs(answer, new TypeToken<ArrayList<OneSession>>(){}.getType()) : null;
    }

    public static ArrayList<InterfaceTable> SelectOneAntes(String stringToOrderBy, String stringToWhere){
        String answer = SelectModels("select_antes", stringToOrderBy, stringToWhere);
        return !("".equals(answer)) ? ObjStringTransformator.deserialiseStrs(answer, new TypeToken<ArrayList<OneAnte>>(){}.getType()) : null;
    }

    public static ArrayList<InterfaceTable> SelectOneEmployers(String stringToOrderBy, String stringToWhere){
        String answer = SelectModels("select_employers", stringToOrderBy, stringToWhere);
        return !("".equals(answer)) ? ObjStringTransformator.deserialiseStrs(answer, new TypeToken<ArrayList<OneEmployer>>(){}.getType()) : null;
    }

    public static ArrayList<InterfaceTable> SelectOneUsers(String stringToOrderBy, String stringToWhere){
        String answer = SelectModels("select_users", stringToOrderBy, stringToWhere);
        if(!("".equals(answer))) {
            return ObjStringTransformator.deserialiseStrs(answer, new TypeToken<ArrayList<OneUser>>() {}.getType());
        }
        else {
            return null;
        }

    }

    private static String SelectModels(String thisClientCommand, String stringToOrderBy, String stringToWhere) {
        String data = "";
        if(!stringToOrderBy.trim().equals("") || !stringToWhere.trim().equals("")) {
            data = stringToOrderBy + " ~~~~~ " + stringToWhere;
        }
        return thisClientTCPSocket.goCommand(thisClientCommand, data);
    }


    public static boolean insert(InterfaceTable interfaceTable, String table){
        String thisClientCommand = "insert_", data = ObjStringTransformator.serialiseObj(interfaceTable);
        if("".equals(thisClientTCPSocket.goCommand(thisClientCommand + table, data)))
            return false;
        else
            return true;
    }

    public static boolean update(InterfaceTable interfaceTable, String table, int id){
        String thisClientCommand = "update_", data = ObjStringTransformator.serialiseObj(interfaceTable) + "~~~~~" + id;
        if("".equals(thisClientTCPSocket.goCommand(thisClientCommand + table, data)))
            return false;
        else
            return true;
    }

    public static boolean delete(int id, String table){
        String thisClientCommand = "delete", data = table + "~~~~~" + id;
        if("".equals(thisClientTCPSocket.goCommand(thisClientCommand, data)))
            return false;
        else
            return true;
    }

    public static String exit( ){
        return thisClientTCPSocket.closeSocket();
    }


}
