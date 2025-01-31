package plainkanban.backend.user;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
  private String email;

  private String password;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // no roles yet
    return new ArrayList<>();
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

}
