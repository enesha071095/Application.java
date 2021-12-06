package Tables;

public class OneSession implements InterfaceTable {
    public final static String[] ThisRowAtribute = {"id", "name", "price", "employer_id"};
    public final static String[] ThisRowAtributeRu = {"ИД", "Название процедуры", "Цена", "ИД Работника"};
    public final static boolean[] INT = {true, false, true, true};

    public int id;
    public String name;
    public int price;
    public int employer_id;

    @Override
    public int getId() {
        return id;
    }

    public OneSession(int id, String name, int price, int employer_id){
        this.id = id;
        this.name = name;
        this.price = price;
        this.employer_id = employer_id;
    }

}
