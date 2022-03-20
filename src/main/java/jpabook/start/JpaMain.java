package jpabook.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.StringJoiner;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            logic(em);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    public static void logic(EntityManager em) {
        final Member member = new Member();

        member.setId("wook");
        member.setUsername("태욱");
        member.setAge(36);

        em.persist(member);

        member.setAge(20);

        Member findMember = em.find(Member.class, "wook");
        System.out.println(
                new StringJoiner(", ", "findMember=", "")
                        .add(findMember.getUsername())
                        .add(String.valueOf(findMember.getAge()))
        );

        em.remove(member);
    }
}
