package com.skilldistillery.overflow.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.overflow.entities.Address;
import com.skilldistillery.overflow.entities.Employer;
import com.skilldistillery.overflow.entities.Profile;
import com.skilldistillery.overflow.entities.ProfileDTO;
import com.skilldistillery.overflow.entities.Technology;
import com.skilldistillery.overflow.respositories.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private ProfileRepository profileRepo;

	@Override
	public Profile updateProfileByLoggedInUser(ProfileDTO dto, String username) {
		// GET PROFILE
		Profile profile = profileRepo.findProfileByUserUsername(username);
		// CHECK IF THERE IS AN EXISTING PROFILE ADDRESS, IF SO EDIT IT
		if (profile.getAddress() != null) {
			Address managedAddress = profile.getAddress();
			managedAddress.setStreet(dto.getAddressStreet());
			managedAddress.setStreet2(dto.getAddressStreet2());
			managedAddress.setCity(dto.getAddressCity());
			managedAddress.setState(dto.getAddressState());
			managedAddress.setCountry(dto.getAddressCountry());
			managedAddress.setZip(dto.getAddressZip());
			profile.setAddress(managedAddress);
		}
		// CHECK IF THERE IS AN EXISTING PROFILE ADDRESS, IF NOT MAKE A NEW ONE
		if (profile.getAddress() == null) {
			Address address = new Address();
			address.setStreet(dto.getAddressStreet());
			address.setStreet2(dto.getAddressStreet2());
			address.setCity(dto.getAddressCity());
			address.setState(dto.getAddressState());
			address.setCountry(dto.getAddressCountry());
			address.setZip(dto.getAddressZip());
			profile.setAddress(address);
		}
		// CHECK IF THERE IS AN EXISTING EMPLOYER, IF SO EDIT IT
		if (profile.getEmployer() != null) {
			Employer employer = profile.getEmployer();
			employer.setName(dto.getEmployerName());
			employer.setHiring(dto.getEmployerHiring());
			// CHECK IF THERE IS AN EXISTING EMPLOYER ADDRESS, IF SO EDIT IT
			if (profile.getEmployer().getAddress() != null) {
				Address managedEmployerAddress = profile.getEmployer().getAddress();
				managedEmployerAddress.setStreet(dto.getEmployerAddressStreet());
				managedEmployerAddress.setStreet2(dto.getEmployerAddressStreet2());
				managedEmployerAddress.setCity(dto.getEmployerAddressCity());
				managedEmployerAddress.setState(dto.getEmployerAddressState());
				managedEmployerAddress.setCountry(dto.getEmployerAddressCountry());
				managedEmployerAddress.setZip(dto.getEmployerAddressZip());
				employer.setAddress(managedEmployerAddress);
			}
			// CHECK IF THERE IS AN EXISTING EMPLOYER ADDRESS, IF NOT MAKE A NEW ONE
			if (profile.getEmployer().getAddress() == null) {
				Address employerAddress = new Address();
				employerAddress.setStreet(dto.getEmployerAddressStreet());
				employerAddress.setStreet2(dto.getEmployerAddressStreet2());
				employerAddress.setCity(dto.getEmployerAddressCity());
				employerAddress.setState(dto.getEmployerAddressState());
				employerAddress.setCountry(dto.getEmployerAddressCountry());
				employerAddress.setZip(dto.getEmployerAddressZip());
				employer.setAddress(employerAddress);

			}
			// ADD EMPLOYER AND THE EMPLOYER ADDRESS
			profile.setEmployer(employer);
		}
		// CHECK IF THERE IS AN EXISTING EMPLOYER, IF NOT MAKE A NEW ONE
		if (profile.getEmployer() == null) {
			Employer employer = new Employer();
			employer.setName(dto.getEmployerName());
			employer.setHiring(dto.getEmployerHiring());

			Address employerAddress = new Address();
			employerAddress.setStreet(dto.getEmployerAddressStreet());
			employerAddress.setStreet2(dto.getEmployerAddressStreet2());
			employerAddress.setCity(dto.getEmployerAddressCity());
			employerAddress.setState(dto.getEmployerAddressState());
			employerAddress.setCountry(dto.getEmployerAddressCountry());
			employerAddress.setZip(dto.getEmployerAddressZip());
			
			// ADD EMPLOYER AND THE EMPLOYER ADDRESS
			employer.setAddress(employerAddress);
			profile.setEmployer(employer);
		}
		profile.setFirstName(dto.getProfileFirstName());
		profile.setLastName(dto.getProfileLastName());
		profile.setEmail(dto.getProfileEmail());
		profile.setCohort(dto.getProfileCohort());
		profile.setEmployed(dto.isProfileEmployed());
		return profileRepo.saveAndFlush(profile);
	}
	
	@Override
	public Profile getProfileByUsername(String username) {
		Profile p = profileRepo.findProfileByUserUsername(username);
		return p;
	}

	@Override
	public Profile addTechnologyForLoggedInUser(Technology technology, String username) {
		Profile profile = profileRepo.findProfileByUserUsername(username);
		profile.addTechnology(technology);
		profileRepo.saveAndFlush(profile);
		return profile;
	}
	
	@Override
	public Profile deleteTechnologyForLoggedInUser(Technology technology, String username) {
		Profile profile = profileRepo.findProfileByUserUsername(username);
		profile.deleteTechnology(technology);
		profileRepo.saveAndFlush(profile);
		return profile;	
	}
	
}
