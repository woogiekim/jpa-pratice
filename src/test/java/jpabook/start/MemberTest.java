package jpabook.start;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {
    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    private EntityManager em;
    private EntityTransaction tx;

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }

    @AfterEach
    void tearDown() {
        em.close();
    }

    @AfterAll
    static void afterAll() {
        emf.close();
    }

    @Test
    void 멤버등록() {
        try {
            tx.begin();

            //given
            final Member member = new Member();
            member.setId("wook");
            member.setUsername("태욱");
            member.setAge(36);

            //when
            em.persist(member);

            //then
            Member findMember = em.find(Member.class, "wook");
            assertThat(findMember.getId()).isNotNull();
            assertThat(findMember.getUsername()).isEqualTo("태욱");
            assertThat(findMember.getAge()).isEqualTo(36);

            em.remove(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }

    @Test
    void 멤버수정() {
        try {
            tx.begin();

            //given
            final Member member = new Member();
            member.setId("wook");
            member.setUsername("태욱");
            member.setAge(36);

            em.persist(member);

            Member findMember1 = em.find(Member.class, "wook");
            assertThat(findMember1.getAge()).isEqualTo(36);

            //when
            member.setAge(20);

            //then
            Member findMember2 = em.find(Member.class, "wook");
            assertThat(findMember2.getAge()).isEqualTo(20);

            em.remove(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }

    @Test
    void 멤버삭제() {
        try {
            tx.begin();

            //given
            final Member member = new Member();
            member.setId("wook");
            member.setUsername("태욱");
            member.setAge(36);

            em.persist(member);

            Member findMember1 = em.find(Member.class, "wook");
            assertThat(findMember1).isNotNull();

            //when
            em.remove(member);

            //then
            Member findMember2 = em.find(Member.class, "wook");
            assertThat(findMember2).isNull();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }
}