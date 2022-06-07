package br.com.univali.gabby_leo_kallil.quiz.api.access.role;

import br.com.univali.gabby_leo_kallil.quiz.api.access.enum_role.EnumRole;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "role")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "outsource_generator")
    @SequenceGenerator(name="outsource_generator", sequenceName="outsource_id_seq1", allocationSize=1)
    @Column(updatable=false, nullable=false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private EnumRole name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;

        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return 1179619963;
    }
}
