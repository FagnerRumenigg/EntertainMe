package entertain_me.app.repository.user;

import java.util.Set;

public interface UserRepositoryCustom {
    Set<Object> findPreferencesByUserId(Long userId);

}
