package com.skilldistillery.overflow.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.overflow.entities.Address;
import com.skilldistillery.overflow.entities.Employer;
import com.skilldistillery.overflow.entities.EmployerDTO;
import com.skilldistillery.overflow.entities.User;
import com.skilldistillery.overflow.respositories.AddressRepository;
import com.skilldistillery.overflow.respositories.EmployerRepository;
import com.skilldistillery.overflow.respositories.UserRepository;

@Service
public class EmployerServiceImpl implements EmployerService{
	@Autowired
	private EmployerRepository empRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AddressRepository addressRepo;

	@Override
	public List<Employer> getAllEmployers() {
		return empRepo.findAll();
	}

	@Override
	public Employer findEmployerById(int empId) {
		return empRepo.findById(empId).get();
	}

	@Override
	public Employer createNewEmployer(int userId, EmployerDTO dto, String username) {
		User user = userRepo.findById(userId).get();
		if (user.getUsername().equals(username)) {
			// MAKE A NEW ADDRESS WITH DTO FORM DATA
			Address address = new Address();
			address.setStreet(dto.getEmployerStreet());
			address.setStreet(dto.getEmployerStreet2());
			address.setCity(dto.getEmployerCity());
			address.setState(dto.getEmployerState());
			address.setCountry(dto.getEmployerCountry());
			address.setZip(dto.getEmployerZip());
			// MAKE A NEW EMPLOYER WITH DTO FORM DATA, SET ADDRESS AS IT'S ADDRESS
			Employer employer = new Employer();
			employer.setName(dto.getEmployerName());
			employer.setHiring(dto.getEmployerHiring());
			employer.setAddress(address);
			// SAVE EMPLOYER, ADDRESS WILL BE CASCADED
			empRepo.saveAndFlush(employer);
		}
		return null;
	}

	@Override
	public Employer updateEmployerById(int userId, int empId, EmployerDTO dto, String username) {
		Optional<Employer> opEmployer = empRepo.findById(empId);
		if (opEmployer.isPresent()) {
			// GRAB THE EMPLOYER TO BE EDITED
			Employer managedEmployer = opEmployer.get();
			Optional<Address> opAddress = addressRepo.findById(managedEmployer.getAddress().getId());
			if (opAddress.isPresent()) {
				// GRAB THE ADDRESS TO BE EDITED AND SET ITS VALUES TO THE DTO FORM DATA
				Address managedAddress = opAddress.get();
				managedAddress.setStreet(dto.getEmployerStreet());
				managedAddress.setStreet2(dto.getEmployerStreet2());
				managedAddress.setCity(dto.getEmployerCity());
				managedAddress.setState(dto.getEmployerState());
				managedAddress.setCountry(dto.getEmployerCountry());
				managedAddress.setZip(dto.getEmployerZip());
				// SET THE VALUES OF THE EMPLOYER TO THE DTO FORM DATA AND ITS ADDRESS TO THE EDITED ADDRESS
				managedEmployer.setName(dto.getEmployerName());
				managedEmployer.setHiring(dto.getEmployerHiring());
				managedEmployer.setAddress(managedAddress);
				// SAVE THE EDIT TO EMPLOYER AND CASCADE ADDRESS
				return empRepo.saveAndFlush(managedEmployer);
			}
		}
		return null;
	}

	@Override
	public Boolean deleteEmployerById(int userId, int empId, String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
