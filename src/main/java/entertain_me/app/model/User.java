package entertain_me.app.model;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import entertain_me.app.enums.UserRoleEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails{

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_user", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name ="name")
	private String name;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "role")
	private UserRoleEnum role;

	@Column(name = "register_date")
	private LocalDateTime registerDate;

	@Column(name = "login_date")
	private LocalDateTime loginDate;

	@ManyToMany
	@JoinTable(
			name = "user_preference_demographic",
			joinColumns = @JoinColumn(name = "id_user"),
			inverseJoinColumns = @JoinColumn(name = "id_demographic")
	)
	private Set<Demographic> demographics;

	@ManyToMany
	@JoinTable(
			name = "user_preference_genre",
			joinColumns = @JoinColumn(name = "id_user"),
			inverseJoinColumns = @JoinColumn(name = "id_genre")
	)
	private Set<Genre> genres;

	@ManyToMany
	@JoinTable(
			name = "user_preference_studio",
			joinColumns = @JoinColumn(name = "id_user"),
			inverseJoinColumns = @JoinColumn(name = "id_studio")
	)
	private Set<Studio> studios;


	@ManyToMany
	@JoinTable(
			name = "user_preference_theme",
			joinColumns = @JoinColumn(name = "id_user"),
			inverseJoinColumns = @JoinColumn(name = "id_theme")
	)
	private Set<Theme> themes;

	public User(String name, String email, String password, UserRoleEnum role, LocalDateTime registerDate) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.registerDate = registerDate;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(this.role == UserRoleEnum.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
