import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class DbContent <T> {

    List<T> content;

    public DbContent() {
        content = new ArrayList<>();
    }

    public static SessionFactory sessionFactory = null;

    public static SessionFactory createSessionFactory() {
        StandardServiceRegistry registry = null;
        try {
            registry = new StandardServiceRegistryBuilder().configure().build();
            MetadataSources sources = new MetadataSources(registry);
            Metadata metadata = sources.getMetadataBuilder().build(); // выкидывает
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            e.printStackTrace();
            if (registry != null) {
                StandardServiceRegistryBuilder.destroy(registry);
            }
        }
        return sessionFactory;
    }

    // получение данных из таблицы
    public void getContent(String query) {
        try (Session session = createSessionFactory().openSession()) {
            Query q = session.createQuery("FROM Developer");
            content = q.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // сохранение данных в БД
    public void setContent(T item) {
        Transaction transaction = null;
        try (Session session = createSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(item);
            transaction.commit();
            System.out.println("Добавление успешно");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Добавление не получилось");
            e.printStackTrace();
        }
    }

    // удаление записи из таблицы
    public void delContent(String table, String tableId, String id)
    {
        try (Session session = createSessionFactory().openSession()) {
            session.createQuery("DELETE FROM " + table + " WHERE " + tableId + " = " + id);
            System.out.println("Запись удалена");
        } catch (Exception e) {
            System.out.println("Удаление не удалоось");
            e.printStackTrace();
        }
    }

    // обновление записи в таблице
    public void updateContent(String table, String field, String val, String tableId, String id)
    {
        try (Session session = createSessionFactory().openSession()) {
            session.createQuery(
                    "UPDATE " + table + " SET " + field + " = " + val + " WHERE " + tableId + " = " + id
            );
            System.out.println("Запись удалена");
        } catch (Exception e) {
            System.out.println("Удаление не удалоось");
            e.printStackTrace();
        }
    }
}
