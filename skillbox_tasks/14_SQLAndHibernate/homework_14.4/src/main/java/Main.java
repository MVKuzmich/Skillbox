import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();

        List<PurchaseList> purchaseList = session.createQuery("From PurchaseList", PurchaseList.class).getResultList();

        for (PurchaseList purchase : purchaseList) {

            Query studentQuery = session.createQuery("From Student Where name = :name");
            studentQuery.setParameter("name", purchase.getId().getStudentName());
            Student student = (Student) studentQuery.getSingleResult();

            // получаем курс по имени, используя HQL
            Query courseQuery = session.createQuery("From Course Where name = :name");
            courseQuery.setParameter("name", purchase.getId().getCourseName());
            Course course = (Course) courseQuery.getSingleResult();

            LinkedPurchaseList link = new LinkedPurchaseList();
            link.setStudentId(student.getId());
            link.setCourseId(course.getId());
            session.save(link);
        }

//            String hql = "INSERT INTO LinkedPurchaseList (studentId, courseId) " +
//                    "SELECT st.id, cour.id " +
//                    "FROM Course cour " +
//                    "INNER JOIN Subscription sub ON sub.id = cour.id " +
//                    "INNER JOIN Student st ON st.id = sub.id " +
//                    "WHERE (st.name = :studentName) AND (cour.name = :courseName)";
//            Query query = session.createQuery(hql);
//            query.setParameter("studentName", purchase.getId().getStudentName());
//            query.setParameter("courseName", purchase.getId().getCourseName());
//        }

            session.close();


            sessionFactory.close();
        }
    }




