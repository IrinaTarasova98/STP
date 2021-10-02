import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class DB_content <T> {

    List<T> content;

    public void DB_content() {}

    public SessionFactory sessionFactory = null;

    public SessionFactory createSessionFactory() {
        StandardServiceRegistry registry = null;
        try {
            registry = new StandardServiceRegistryBuilder().configure().build();
            MetadataSources sources = new MetadataSources(registry);
            Metadata metadata = sources.getMetadataBuilder().build();
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            e.printStackTrace();
            if (registry != null) {
                StandardServiceRegistryBuilder.destroy(registry);
            }
        }
        return sessionFactory;
    }


    // получение данных из БД
    public List<T> get_content(String query, Class C) {
        content = new ArrayList<T>();
        try (Session session = createSessionFactory().openSession()) {
            content = session.createQuery(query, C).getResultList();
            System.out.println("Информация получена");
        } catch (Exception e) {
            System.out.println("Не удалось получить информацию");
            e.printStackTrace();
        }
        return content;
    }

    // сохранение данных в БД
    public void set_content(T content) {
        Transaction transaction = null;
        try (Session session = createSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(content);
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
    public void del_content(String query)
    {
        try (Session session = createSessionFactory().openSession()) {
            session.createQuery(query);
            System.out.println("Запись удалена");
        } catch (Exception e) {
            System.out.println("Удаление не удалоось");
            e.printStackTrace();
        }
    }

}
