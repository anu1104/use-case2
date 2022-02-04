package com.flightadmin.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightadmin.dto.FlightDetailsDTO;
import com.flightadmin.dto.RequestFlightDetailsDTO;
import com.flightadmin.dto.ResponseFlightDetailsDTO;
import com.flightadmin.dto.UserServiceRequestDTO;
import com.flightadmin.dto.UserServiceResponseDTO;
import com.flightadmin.service.AdminService;

@RestController
@RequestMapping("/api/v1.0/admin/flight")
public class AdminController {
	
	private final AdminService adminService;
	private final ModelMapper modelMapper;
	
	public AdminController(AdminService adminService, ModelMapper modelMapper) {
		super();
		this.adminService = adminService;
		this.modelMapper = modelMapper;
	}
	
	@GetMapping("/")
	public String getString() {
		return "anu";
	}
	@PostMapping("/airline/inventory/add")
	public ResponseEntity<ResponseFlightDetailsDTO> addFlightSchedule(@RequestBody RequestFlightDetailsDTO
			requestFlightDetailsDTO){
		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		FlightDetailsDTO flightDetailsDTO = modelMapper.map(requestFlightDetailsDTO, FlightDetailsDTO.class);
		
		ResponseFlightDetailsDTO responseFlightDetailsDTO = adminService.addFlightDetails(flightDetailsDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseFlightDetailsDTO);
	}
	
	@DeleteMapping("/airline/inventory/block/{flightNo}")
	public String blockedAirline(@PathVariable("flightNo") int flightNo) {
		
		adminService.blockedFlight(flightNo);
		return "Flight has blocked";
	}
	
	@GetMapping("/airline/inventory/search/{flightNo}")
	public ResponseEntity<FlightDetailsDTO> searchFlight(@PathVariable("flightNo") int flightNo
			//@RequestHeader("Access-Control-Allow-Origin") String req
			) {
		
		FlightDetailsDTO flightDetails = adminService.searchedFlight(flightNo);
		return ResponseEntity.status(HttpStatus.OK).body(flightDetails);
	}
	
	@GetMapping("/airline/inventory/viewAll")
	public ResponseEntity<List<FlightDetailsDTO>> searchAllFlights() {
		
		List<FlightDetailsDTO> flightDetails = adminService.viewAllFlights();
		return ResponseEntity.status(HttpStatus.OK).body(flightDetails);
	}
	
	@PostMapping("/airline")
	public ResponseEntity<List<UserServiceResponseDTO>> getFlightDetails(@RequestBody UserServiceRequestDTO 
			userServiceRequestDTO){
		
	List<UserServiceResponseDTO> userServiceResponseDTO = adminService.getFlightDetails(userServiceRequestDTO);
		return ResponseEntity.status(HttpStatus.OK).body(userServiceResponseDTO);
	}
	
	

}
