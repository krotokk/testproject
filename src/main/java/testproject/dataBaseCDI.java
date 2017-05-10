package testproject;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kirill on 28.04.17.
 */


@ManagedBean(name="dtEditView")
@Named
@SessionScoped
public class dataBaseCDI implements Serializable {

    @EJB
    private dataBaseEJB carsEJB;
    private List<carEntity> cars;

    private carItem car;

    public dataBaseCDI() {
        car = new carItem();
    }

    private final static String [] colors;
    private final static String [] models;

    @PostConstruct
    public void init() {
        cars = carsEJB.getAllCars();
    }

    static{
        colors = new String[10];
        colors[0] = "Black";
        colors[1] = "White";
        colors[2] = "Green";
        colors[3] = "Red";
        colors[4] = "Blue";
        colors[5] = "Orange";
        colors[6] = "Silver";
        colors[7] = "Yellow";
        colors[8] = "Brown";
        colors[9] = "Maroon";

        models = new String[10];
        models[0] = "BMW";
        models[1] = "Mercedes";
        models[2] = "Volvo";
        models[3] = "Audi";
        models[4] = "Renault";
        models[5] = "Fiat";
        models[6] = "Volkswagen";
        models[7] = "Honda";
        models[8] = "Jaguar";
        models[9] = "Ford";
    }

    public List<String> getColors() {
        return Arrays.asList(colors);
    }

    public List<String> getModels() {
        return Arrays.asList(models);
    }

    private boolean inserting;

    public carItem getCar() {
        return car;
    }

    public void setCar(carItem car) {
        this.car = car;
    }

    public boolean isInserting() {
        return inserting;
    }

    public void insertInDB(){
        inserting = carsEJB.insertNewItem(car);
        if(isInserting()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Id is used!", "Please Try Again!"));
        }
    }

    public List<carEntity> getCars() {
        return cars;
    }

    public void setCars(List<carEntity> cars) {
        this.cars = cars;
    }


    public void onRowEdit(RowEditEvent event) {

        carsEJB.updateQuery((carEntity) event.getObject());

        FacesMessage msg = new FacesMessage("Car Edited", ((carEntity) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((carEntity) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteCar(String id){
        carsEJB.deleteQuery(id);
    }
}
