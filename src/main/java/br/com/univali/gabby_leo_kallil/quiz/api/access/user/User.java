package br.com.univali.gabby_leo_kallil.quiz.api.access.user;

import br.com.univali.gabby_leo_kallil.quiz.api.access.role.Role;
import br.com.univali.gabby_leo_kallil.quiz.api.access.user.DTO.UserDTOResponse;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnTransformer;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(	name = "\"user\"",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "registration")
        })
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name="user_generator", sequenceName="user_id_seq", allocationSize=1)
    @Column(updatable=false, nullable=false)
    private Integer id;

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String registration;

    @NotNull
    private Boolean active;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude
    private Set<Role> roles = new HashSet<>();



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;

        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return 562048007;
    }

    public UserDTOResponse getDTOResponse(){
        UserDTOResponse dto = new UserDTOResponse();
        dto.setId(getId());
        dto.setUsername(getUsername());
        dto.setEmail(getEmail());
        dto.setRoles(getRoles());
        dto.setActive(getActive());
        return dto;
    }

}
