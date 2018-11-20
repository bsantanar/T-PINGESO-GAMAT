package usach.cl.gamatbackend.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import usach.cl.gamatbackend.entities.Building;
import usach.cl.gamatbackend.entities.Request;
import usach.cl.gamatbackend.facadeBd.IServiceBd;

@CrossOrigin
@RestController
@RequestMapping("/buildings")
public class BuildingService {
	
	@Autowired
	
	private IServiceBd serviceBd;
	
	@GetMapping
	@ResponseBody
	public Set<Building> getAll(){
		return serviceBd.getAllBuilding();
	}
	// esto es para obtener las request de una determina obra, por parte de un aprobador
	@GetMapping("/requests")
	@ResponseBody
	public Set<Request> getAllRequest(){
		return serviceBd.getRequestOfBuilding();
	}
	
	@PostMapping
	@ResponseBody
	public Building createBuilding(@RequestBody Building building) {
		if(building !=null)
			return serviceBd.saveBuilding(building);
		return null;
	}
	
	@PutMapping
	@ResponseBody
	public Building updateBuilding(@RequestBody Building building) {
		if(building != null)
			return serviceBd.updateBuilding(building);
		return null;
	}
	
	@DeleteMapping("/{id}")
	@ResponseBody
	public HttpStatus deleteBuilding(@PathVariable("id") Integer idBuilding) {
		if(serviceBd.deleteBuilding(idBuilding))
			return HttpStatus.OK;
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
	

}
