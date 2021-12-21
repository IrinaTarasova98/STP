package classes;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;

public class DataContainer <T> {

    public static SessionFactory sessionFactory = null;

    public static SessionFactory createSessionFactory() {
        StandardServiceRegistry registry = null;
        try {
            registry = new StandardServiceRegistryBuilder().configure().build();
            MetadataSources sources = new MetadataSources(registry);
            Metadata metadata = sources.getMetadataBuilder().build();
            sessionFactory = metadata.getSessionFactoryBuilder().build();
            System.out.print("Session started\n");
        } catch (Exception e) {
        	System.out.print("Cant open session\n");
            e.printStackTrace();
            if (registry != null) {
                StandardServiceRegistryBuilder.destroy(registry);
            }
        }
        return sessionFactory;
    }

    // получение элемента из таблицы cls с идентификатором id
    public static <T> T getElement(int id, Class <T> cls) {
        try (Session session = createSessionFactory().openSession()) {
            // получить из БД запись с полученным id
            T obj = session.load(cls, id);
            return unProxy(obj);
        }
        catch (Exception e)
        {
            System.out.println("Operation 'get element' failed: " + e.getMessage());
        }
        return  null;
    }


    // получение данных из таблицы cls по запросу query
    public static <T> ArrayList<T> getList(String query, Class <T> cls) {
        try (Session session = createSessionFactory().openSession()) {
            return (ArrayList<T>) session.createQuery(query, cls).getResultList();
        } catch (Exception e) {
            System.out.println("Operation 'get list' failed: " + e.getMessage());
        }
        return null;
    }

    // удаление записи из таблицы cls с идентификатором id
    public static <T> void delElement(int id, Class <T> cls)
    {
        try (Session session = createSessionFactory().openSession()) {
            // получить из БД запись с указанным id
            Object temp = getElement(id, cls);
            // удаление
            session.beginTransaction();
            session.remove(temp);
            session.getTransaction().commit();
        }
        catch (Exception e)
        {
            System.out.println("Operation 'delete element' failed: " + e.getMessage());
        }
    }

    // добавить элемент в таблицу (если нужно, то отправлять класс объекта)
    public static void addElement(Object item) {
        try (Session session = createSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(item);
            transaction.commit();
        }
        catch (Exception e)
        {
            System.out.println("Operation 'add element' failed: " + e.getMessage());
        }
    }

    // обновление записи в таблице
    public static void updateElement(Object item)
    {
        try (Session session = createSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(item);
            session.getTransaction().commit();
        }
        catch (Exception e)
        {
            System.out.println("Operation 'update element' failed: " + e.getMessage());
        }
    }

    public static <T> T unProxy(T entity) {
        if (entity instanceof HibernateProxy) {
            entity =  (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
        }
        return entity;
    }
}