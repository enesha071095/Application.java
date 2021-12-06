package Tables;

public class OneEmployer implements InterfaceTable {
    public final static String[] ThisRowAtribute = {"id", "name", "point", "lastname"};
    public final static String[] ThisRowAtributeRu = {"ИД", "Имя", "Средний балл", "Фамилия"};
    public final static boolean[] INT = {true, false, true, false};

    public int id;
    public String name;
    public int point;
    public String lastname;

    @Override
    public int getId() {
        return id;
    }

    public OneEmployer(int id, String name, int point, String lastname){
        this.id = id;
        this.name = name;
        this.point = point;
        this.lastname = lastname;
    }
}
