package usach.cl.gamat.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "request")
public class Request  implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRequest;

    @NotNull
    private String state;

    private String observation;

    @Column(name="create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @PrePersist
    protected void onCreate(){
        date = new Date();
    }

    @Column(name = "totalPrice")
    private Integer totalPrice;

    @Column(name = "shippingPrice")
    private Integer shippingPrice;

    @Column(name = "administrationPrice")
    private Integer administrationPrice;

    @Temporal(TemporalType.DATE)
    @Column(name = "dateBudget")
    private Date dateBudget;

    @Temporal(TemporalType.DATE)
    @Column(name = "expirationBudget")
    private Date expirationBudget;

    @Column(name = "payCondition")
    private String payCondition;

    @Column(name = "driverValidation")
    @ColumnDefault("0")
    private Boolean driverValidation;

    @Column(name = "managerValidation")
    @ColumnDefault("0")
    private Boolean managerValidation;

    //@JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
   // @JsonIgnore
    private Manager manager;

    //@JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="building_id")
    //@JsonIgnore
    private Building building;

    //@JsonIgnore
    @OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name="request_id")
    private List<Item> items;

    
    @OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name="request_id")
    private List<Log> logs;

    //@JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="driver_id")
    //@JsonIgnore
    private Driver driver;
    
    
    public Request clone() throws CloneNotSupportedException{
        Request clon = (Request) super.clone();
        return clon;
   }

    public Integer getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(Integer shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public Integer getAdministrationPrice() {
        return administrationPrice;
    }

    public void setAdministrationPrice(Integer administrationPrice) {
        this.administrationPrice = administrationPrice;
    }

    public Date getDateBudget() {
        return dateBudget;
    }

    public void setDateBudget(Date dateBudget) {
        this.dateBudget = dateBudget;
    }

    public Date getExpirationBudget() {
        return expirationBudget;
    }

    public void setExpirationBudget(Date expirationBudget) {
        this.expirationBudget = expirationBudget;
    }

    public String getPayCondition() {
        return payCondition;
    }

    public void setPayCondition(String payCondition) {
        this.payCondition = payCondition;
    }

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

    public Boolean getDriverValidation() {
        return driverValidation;
    }

    public void setDriverValidation(Boolean driverValidation) {
        this.driverValidation = driverValidation;
    }

    public Boolean getManagerValidation() {
        return managerValidation;
    }

    public void setManagerValidation(Boolean managerValidation) {
        this.managerValidation = managerValidation;
    }
    
    
    
    public List<Log> getLogs() {
		return logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

	public static Request filterItems(List<Item> itemAprobados,List<Item>itemPendientes,Request request,String aprobado, String pendiente) throws CloneNotSupportedException{
    	Request nuevaRequest= null;
    	Integer idNull= null;
    	for (Item item:request.getItems()){
            if (item.getState().equals(pendiente)){
//            	Item newItem =item.clone();
//            	newItem.setIdItem(0);
//            	itemPendientes.add(newItem);
            	Item newItem = new Item (); 
            	newItem.setComment(item.getComment()); 
            	newItem.setDescription(item.getDescription()); 
            	newItem.setDistributor(item.getDistributor()); 
            	newItem.setMeasure(item.getMeasure()); 
            	newItem.setObservation(item.getObservation()); 
            	newItem.setPrice(item.getPrice()); 
            	newItem.setName(item.getName()); 
            	newItem.setQuantity(item.getQuantity()); 
            	newItem.setState("Aprobado"); 
            	newItem.setTotalPrice(item.getTotalPrice()); 
            	newItem.setTotalWeight(item.getTotalWeight()); 
            	newItem.setWeight(item.getWeight()); 
            	newItem.setUrgency(item.isUrgency()); 
            	itemPendientes.add(newItem); 
            }
            else if (item.getState().equals(aprobado)) {
                itemAprobados.add(item);
            }
        }
        if(itemPendientes.size() > 0){
          
            
        	
            nuevaRequest.setObservation("Solicitud creada de: " + request.getObservation());
        	nuevaRequest.setItems(itemPendientes); 
            nuevaRequest.setState("Aprobado"); 
            nuevaRequest.setManager(request.getManager()); 
            nuevaRequest.setDriver(request.getDriver()); 
            nuevaRequest.setBuilding(request.getBuilding()); 
          
            nuevaRequest.setPayCondition(request.getPayCondition()); 
           
        }
        return  nuevaRequest;
    }
}
