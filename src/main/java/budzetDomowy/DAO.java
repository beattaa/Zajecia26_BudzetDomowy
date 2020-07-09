package budzetDomowy;

import java.sql.Date;

public interface DAO {
    void select(int id);
    void update(String value, int id);
    void delete(int id);
    void create(int type, String desc, double amount, Date date);
    void close();
}
