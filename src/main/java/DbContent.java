import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.ArrayList;
import java.util.List;

public class DbContent <T> {

    List<T> content;

    public DbContent() {
        content = new ArrayList<>();
    }

    // получение данных из таблицы
    public void getContent(String query, Session session) {
        try {
            Query q = session.createQuery(query);
            content = q.list();
        }
        catch (Exception e)
        {
            System.out.println("Данные не получены: " + e.getMessage());
        }
    }

    // сохранение данных в БД
    public void setContent(T item, Session session) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(item);
            transaction.commit();
            System.out.println("Добавление успешно");
        }
        catch (Exception e)
        {
            System.out.println("Данные не добавлены: " + e.getMessage());
        }
    }

    // удаление записи из таблицы
    public void delContent(T item, Session session)
    {
        try {
            session.beginTransaction();
            session.remove(item);
            session.getTransaction().commit();
            System.out.println("Запись удалена");
        }
        catch (Exception e)
        {
            System.out.println("Данные не удалены: " + e.getMessage());
        }
    }

    // обновление записи в таблице
    public void updateContent(T item, Session session)
    {
        try {
            session.beginTransaction();
            session.update(item);
            session.getTransaction().commit();
            System.out.println("Запись обновлена");
        }
        catch (Exception e)
        {
            System.out.println("Данные не обновлены: " + e.getMessage());
        }
    }
}
