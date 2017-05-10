package testproject;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by kirill on 26.04.17.
 */
@Stateless
public class authorizationEJB {
    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    public boolean createUser(String login, String password) {

        if (login.isEmpty() || password.isEmpty()) {
            return false;
        }

        userEntity usEntity = entityManager.find(userEntity.class, login);
        if (usEntity != null) {
            return false;
        }

        usEntity = new userEntity();
        usEntity.setLogin(login);
        usEntity.setPassword(password);
        entityManager.persist(usEntity);

        return true;

    }

    public boolean checkUser(String login, String password){
        if(login.isEmpty()||password.isEmpty()){
            return false;
        }

        userEntity usEntity = entityManager.find(userEntity.class,login);
        if(usEntity == null){
            return false;
        }

        if(password.equals(usEntity.getPassword())){
            return true;
        }

        return false;
    }
}