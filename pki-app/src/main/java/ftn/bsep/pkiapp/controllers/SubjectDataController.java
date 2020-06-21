package ftn.bsep.pkiapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.bsep.pkiapp.dto.SubjectDataDTO;


@RestController
@RequestMapping(value = "/subject-data")
public class SubjectDataController {
		
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<SubjectDataDTO> addSubjectData(@RequestBody SubjectDataDTO subjectDataDto){
		System.out.println("Usao sam u funkciju new subject Data!\n");
		System.out.println("SubjectData email: " + subjectDataDto.getEmail());
		System.out.println("SubjectData extension: " + subjectDataDto.getExtensions().get(0).toString());

		System.out.println("SubjectData extension: " + subjectDataDto.getExtensions().get(1).toString());
		
        	// Perform the authentication

            // Reload user details so we can generate token
            //SubjectData subjectDate = new SubjectData(subjectDataDto);
            		
            
        
        return new ResponseEntity<>(subjectDataDto, HttpStatus.OK);
	}
}
