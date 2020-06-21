package ftn.bsep.pkiapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.bsep.pkiapp.model.Csr;
import ftn.bsep.pkiapp.repositories.CSRRepository;

@Service
public class CSRService {
	@Autowired
	CSRRepository csrRepo;
	
	public Csr saveCSR(Csr csr) {
		return csrRepo.save(csr);
	}
	
	public List<Csr> findAll() {
		return csrRepo.findAll();	
	}
	
	public Csr getOne(Long id) {
		return csrRepo.findById(id).get();
	}
}
