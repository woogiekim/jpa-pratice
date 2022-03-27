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

        tx.begin();
    }

    @AfterEach
    void tearDown() {
        tx.rollback();

        em.close();
    }

    @AfterAll
    static void afterAll() {
        emf.close();
    }

    @Test
    void 멤버등록() {
        //given
        final Member member = new Member();
        member.setId("wook");
        member.setUsername("태욱");
        member.setAge(36);

        //when
        em.persist(member);

        em.flush();
        em.clear();

        //then
        Member findMember = em.find(Member.class, "wook");
        assertThat(findMember.getId()).isNotNull();
        assertThat(findMember.getUsername()).isEqualTo("태욱");
        assertThat(findMember.getAge()).isEqualTo(36);
    }

    @Test
    void 멤버수정() {
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
    }

    @Test
    void 멤버삭제() {
        //given
        final Member member = new Member();
        member.setId("wook");
        member.setUsername("태욱");
        member.setAge(36);

        em.persist(member);

        em.flush();
        em.clear();

        Member findMember1 = em.find(Member.class, "wook");
        assertThat(findMember1).isNotNull();

        //when
        em.remove(findMember1);

        //then
        Member findMember2 = em.find(Member.class, "wook");
        assertThat(findMember2).isNull();
    }

    @Test
    void 준영속_상태() {
        Member member = new Member();
        member.setId("woogie");
        member.setUsername("김태욱");
        member.setAge(36);

        em.persist(member);

        em.detach(member);

        em.find(Member.class, "woogie");
        em.find(Member.class, "woogie");
    }
}