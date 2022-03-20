package jpabook.start;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBER")
@Getter @Setter
public class Member {
    /**
     * 아이디
     **/
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 이름
     **/
    @Column(name = "NAME")
    private String username;

    /**
     * 나이
     **/
    @Column(name = "AGE")
    private Integer age;
}
