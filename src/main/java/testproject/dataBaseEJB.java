package testproject;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by kirill on 28.04.17.
 */

@Stateless
public class dataBaseEJB {
    @PersistenceContext(unitName = "carRentPU")

    private EntityManager entityManager;

    public boolean insertNewItem(carItem newCar){
        if (newCar.getId().isEmpty() || newCar.getModel().isEmpty() || newCar.getColor().isEmpty()) {
            return false;
        }

        carEntity newCarEntity = entityManager.find(carEntity.class, newCar.getId());
        if (newCarEntity != null) {
            return false;
        }

        newCarEntity = new carEntity(null,0,null,null,0);
        newCarEntity.setId(newCar.getId());
        newCarEntity.setModel(newCar.getModel());
        newCarEntity.setColor(newCar.getColor());
        newCarEntity.setEnable(newCar.isEnable());
        newCarEntity.setYear(newCar.getYear());
        newCarEntity.setPrice(newCar.getPrice());

        entityManager.persist(newCarEntity);

        return true;
    }

    public List<carEntity> getAllCars(){
        Query query = entityManager.createQuery("select e.id, e.year, e.model, e.color, e.price from carEntity e");
        List<Object[]> rows =  query.getResultList();
        List<carEntity> result = new ArrayList<>(rows.size());
        for (Object[] row : rows) {
            result.add(new carEntity((String) row[0], (Integer) row[1],(String) row[2],(String) row[3],(Double) row[4]));
        }
        return result;
    }

    public void updateQuery(carEntity newCar){
        String upd = "update carEntity car set car.year = :year, car.model = :brand, car.color = :color, car.price=:price where car.id = :id";
        entityManager.createQuery(upd).
                setParameter("year",newCar.getYear()).
                setParameter("brand",newCar.getModel()).
                setParameter("color",newCar.getColor()).
                setParameter("id",newCar.getId()).
                setParameter("price", newCar.getPrice()).
                executeUpdate();
    }

    public void deleteQuery(String Id){
        String dlt = "delete from carEntity car where car.id = :id";
        entityManager.createQuery(dlt).setParameter("id",Id).executeUpdate();
    }

}
