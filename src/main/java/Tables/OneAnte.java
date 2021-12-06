package Tables;

public class OneAnte implements InterfaceTable {
    public final static String[] ThisRowAtribute = {"id", "value", "employer_id"};
    public final static String[] ThisRowAtributeRu = {"ИД", "Выручка за процедуру", "ИД работника"};
    public final static boolean[] INT = {true, true, true};

    public int id;
    public int value;
    public int employer_id;

    @Override
    public int getId() {
        return id;
    }

    public OneAnte(int id, int value, int employer_id){
        this.id = id;
        this.value = value;
        this.employer_id = employer_id;

    }
}
