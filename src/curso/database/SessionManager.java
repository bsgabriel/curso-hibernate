package curso.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {
    private static SessionManager instance;
    private final SessionFactory factory;
    private Session session;

    private SessionManager() {
        factory = new Configuration().configure("curso/database/config/derby/hibernate.cfg.xml").buildSessionFactory();
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    private Session getSession() {
        if (session == null || !session.isOpen()) {
            session = factory.openSession();
        }
        return session;
    }

    public void save(Object obj) {
        Transaction tx = getSession().beginTransaction();
        getSession().save(obj);
        tx.commit();
    }

    public void update(Object obj) {
        Transaction tx = getSession().beginTransaction();
        getSession().update(obj);
        tx.commit();
    }

    public void saveOrUpdate(Object obj) {
        Transaction tx = getSession().beginTransaction();
        getSession().saveOrUpdate(obj);
        tx.commit();
    }


    public void delete(Object obj) {
        Transaction tx = getSession().beginTransaction();
        getSession().delete(obj);
        tx.commit();
    }

    public List<Object> createQuery(String query) {
        return new ArrayList<Object>(getSession().createQuery("from " + query).list());
    }


    public Object searchByCode(Class clazz, Integer cod) {
        return getSession().get(clazz, cod);
    }

}
