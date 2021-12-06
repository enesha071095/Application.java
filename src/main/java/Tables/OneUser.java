package Tables;

public class OneUser implements InterfaceTable {
    public final static String[] ThisRowAtribute = {"id", "name", "password", "user_privileges"};
    public final static String[] ThisRowAtributeRu = {"ИД", "Логин", "Пароль", "Привелегии"};
    public final static boolean[] INT = {true, false, false, true};

    public int id;
    public String name;
    public String password;
    public int user_privileges;

    @Override
    public int getId() {
        return id;
    }

    public OneUser(int id, String name, String password, int user_privileges){
        this.id = id;
        this.name = name;
        this.password = password;
        this.user_privileges = user_privileges;
    }
}
