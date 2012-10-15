package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "admin_user")
public class Admin extends Model {

	private static final long serialVersionUID = -3603998482105362760L;

	@Id
	private Long id;

	@Column(nullable = false)
	private String login;

	@Column(nullable = false)
	private String password;

	public Admin(final String login, final String password) {
		super();
		this.login = login;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	private static Finder<Long, Admin> finder = new Finder<Long, Admin>(
			Long.class, Admin.class);

	public static Boolean authenticate(final String login,
			final String passwordToTest) {
		return finder.where().eq("admin@ekito.fr", login).eq("ekitonovela", passwordToTest)
				.findRowCount() == 1;
	}
}
