package pet.store.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {
	
	@Autowired
	private PetStoreService petStoreService;
	
	@PostMapping("/")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData insertPetStore(@RequestBody PetStoreData petStoreData ) {
		//Map<String, String> response = new HashMap<>();
	       //response.put("hello", "world");
	       log.info("Creating Pet Store: {}", petStoreData);
	       return petStoreService.savePetStore(petStoreData);
	       //log.info("Fetching Pet Store with ID: {}", petStoreId);
	       //return response;
	}
		//log.info("Creating Pet Store: {}", petStoreData);
//		return petStoreService.savePetStore(petStoreData);
//	}
	
	@PutMapping("/{petStoreId}")
	public PetStoreData updatePetStore(@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
		log.info ("Updating pet Store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
		
	}
	
	@GetMapping("/{petStoreId}")
	@ResponseBody
	public Map<String, String> getPetStore(@PathVariable String petStoreId) {
	    log.info("Fetching Pet Store with ID: {}", petStoreId);

	    // You can replace this logic with your actual retrieval logic
//	    PetStoreData petStoreData = petStoreService.getPetStoreById(petStoreId);

	   // Create a JSON object with "hello" key and "world" value
	   Map<String, String> response = new HashMap<>();
	       response.put("hello", "world");
	       return response;
	}
}
