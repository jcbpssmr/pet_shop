package pet.store.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
//import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {
	
	@Autowired
	private EmployeeDao petStoreEmployeeDao;
	
	@Autowired
	private PetStoreDao petStoreDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);

		copyPetStoreFields(petStore, petStoreData);
		return new PetStoreData(petStoreDao.save(petStore));
	}

	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPetStoreName(petStoreData.getPetStoreName());
        petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
        petStore.setPetStoreCity(petStoreData.getPetStoreCity());
        petStore.setPetStoreState(petStoreData.getPetStoreState());
        petStore.setPetStoreZip(petStoreData.getPetStoreZip());
        petStore.setPetStorePhone(petStoreData.getPetStorePhone());
		petStore.setPetStoreId(petStoreData.getPetStoreId());
	}

	private PetStore findOrCreatePetStore(Long petStoreId) {
		if(petStoreId == 0) {
			return new PetStore();
	
		} else {
			return findPetStoreByID(petStoreId);
		}
	}

	private PetStore findPetStoreByID(Long petStoreId) {
		return petStoreDao.findById(petStoreId).orElseThrow(() -> new NoSuchElementException("That pet store doesn't exist."));
	}
	
	//This is for the employees I am getting a 401 cod in the rest client need help tracing that
	
	@Transactional(readOnly = false)
	
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
		
        PetStore petStore = findPetStoreByID(petStoreId);
        Long employeeId = petStoreEmployee.getEmployeeId();
        Employee employee = findOrCreateEmployee(petStoreId, employeeId);
        copyEmployeeFields(employee, petStoreEmployee);
        employee.setPetStore(petStore);
        petStore.getEmployees().add(employee);
        Employee savedEmployee = petStoreEmployeeDao.save(employee);
        return new PetStoreEmployee(savedEmployee);
		
		}

	private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
		petStoreEmployee.setEmployeeFirstName(employee.getEmployeeFirstName());
        petStoreEmployee.setEmployeeLastName(employee.getEmployeeLastName());
        petStoreEmployee.setEmployeePhone(employee.getEmployeePhone());
        petStoreEmployee.setEmployeeJobTitle(employee.getEmployeeJobTitle());
		
	}

	private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
        if (petStoreId == null) {
            return new Employee();
        }
        return findEmployeeById(petStoreId, employeeId);
	}
	
	private Employee findEmployeeById(Long petStoreId, Long employeeId ) {
		
        Employee employee = petStoreEmployeeDao.findById(employeeId).orElse(null);

        if (employee == null) {
            throw new NoSuchElementException("Employee not found with ID: " + employeeId);
        }

        if (!Objects.equals(employee.getPetStore().getPetStoreId(), petStoreId)) {
            throw new IllegalArgumentException("Employee does not belong to the specified Pet Store");
        }
		return employee;
	
	}
	//This Is for the customers I am not sure how to do the many to many relationship on this block of code 
	@Transactional(readOnly = false)
	
	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
		
        PetStore petStore = findPetStoreByID(petStoreId);
        Long customerId = petStoreCustomer.getCustomerId();
        Customer customer = findOrCreateCustomer(petStoreId, customerId);
        copyCustomerFields(customer, petStoreCustomer);
        customer.setPetStores(petStore);
        petStore.getCustomers().add(customer);
        Customer savedCustomer = customerDao.save(customer);
        return new PetStoreCustomer(savedCustomer);
		
		}

	private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
		petStoreCustomer.setCustomerFistName(customer.getCustomerFirstName());
        petStoreCustomer.setCustomerLastName(customer.getCustomerLastName());
        petStoreCustomer.setCustomerEmail(customer.getCustomerEmail());
		
	}

	private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
        if (petStoreId == null) {
            return new Customer();
        }
        return findCustomerById(petStoreId, customerId);
	}
	
	private Customer findCustomerById(Long petStoreId, Long customerId ) {
		Customer customer1 =customerDao.findById(customerId).orElse(null);
		
        Customer customer = customerDao.findById(customerId).orElse(null);

        if (customer == null) {
            throw new NoSuchElementException("Employee not found with ID: " + customerId);
        }

        if (!Objects.equals(customer.getPetStore().getPetStoreId(), petStoreId)) {
            throw new IllegalArgumentException("Employee does not belong to the specified Pet Store");
        }
		return customer;
	}
	//LIST all the pet stores I need Help with this don't know how to convert the types
	@Transactional(readOnly = false)
	public List<PetStoreData> retrieveAllPetStores() {
		List<PetStore> petStores = petStoreDao.findAll();
		List<Pet>
		for (PetStore petStoreData : petStores) {
	            petStoreData.setCustomers(null);
	            petStoreData.setEmployees(null);
	  
		 }
		return petStores
	}
	
	//Delete 
	public void deletePetStoreById(Long id) {
		findPetStoreByID(id);
		petStoreDao.deleteById(id);
	}
	
	
}
	
