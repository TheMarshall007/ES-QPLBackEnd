package br.com.univali.gabby_leo_kallil.quiz.api.access.passwordReset;

import br.com.univali.gabby_leo_kallil.quiz.api.access.passwordReset.DTO.PasswordResetDTOResponse;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name="password_reset")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PasswordReset  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "password_reset_generator")
	@SequenceGenerator(name="password_reset_generator", sequenceName="password_reset_id_seq", allocationSize=1)
	@Column(updatable=false, nullable=false)
	private Integer id;
	
	@NotNull
	private String email;
	
	@NotNull
	private String code;
	
	@NotNull
	@Type(type="org.hibernate.type.TextType")
	private String token;
	
	public PasswordResetDTOResponse getPasswordResetDTOResponse() {
		PasswordResetDTOResponse passwordResetDTOResponse = new PasswordResetDTOResponse();
		passwordResetDTOResponse.setId(getId());
		passwordResetDTOResponse.setEmail(getEmail());
		passwordResetDTOResponse.setCode(getCode());
		passwordResetDTOResponse.setToken(getToken());
		return passwordResetDTOResponse;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		PasswordReset that = (PasswordReset) o;
		return id != null && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}