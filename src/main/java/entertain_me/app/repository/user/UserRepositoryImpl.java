package entertain_me.app.repository.user;

import entertain_me.app.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Object> findPreferencesByUserId(Long userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<User> user = cq.from(User.class);

        List<Selection<?>> selections = new ArrayList<>();

        Join<User, Demographic> demographicJoin = user.join("demographics");
        selections.add(demographicJoin);

        Join<User, Genre> genreJoin = user.join("genres");
        selections.add(genreJoin);

        Join<User, Studio> studioJoin = user.join("studios");
        selections.add(studioJoin);

        Join<User, Theme> themeJoin = user.join("themes");
        selections.add(themeJoin);

        cq.multiselect(selections).where(cb.equal(user.get("id"), userId));

        List<Object[]> resultList = entityManager.createQuery(cq).getResultList();

        return resultList.stream().flatMap(Arrays::stream).collect(Collectors.toSet());
    }

}
