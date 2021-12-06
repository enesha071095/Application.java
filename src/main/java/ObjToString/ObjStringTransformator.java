package ObjToString;

import Tables.InterfaceTable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ObjStringTransformator {
    public static String serialiseObj(InterfaceTable interfaceTable) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(interfaceTable);
    }

    public static InterfaceTable deserialiseStr(String string, Class<? extends InterfaceTable> modelType) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(string, modelType);
    }

    public static String serialiseObjs(ArrayList<? extends InterfaceTable> models) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(models);
    }


    public static ArrayList<InterfaceTable> deserialiseStrs(String string, Type type) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(string, type);
    }
}
