package com.rentprop.util;

import com.rentprop.dto.LoginDTO;
import com.rentprop.dto.ResidentDTO;

public class AddResidentValidationUtil {
	public String mandatory(ResidentDTO residentDTO){
		
		if(residentDTO.getLoginDto().getFirstName().isEmpty() && residentDTO.getLoginDto().getLastName().isEmpty() 
				&& residentDTO.getLoginDto().getEmailId().isEmpty() && residentDTO.getAddress().isEmpty() 
				&& residentDTO.getLoginDto().getUsername().isEmpty() && residentDTO.getLoginDto().getPassword().isEmpty()){
			return "All fields are Mandatory";
		}
		
		else if(residentDTO.getLoginDto().getFirstName().isEmpty() || residentDTO.getLoginDto().getFirstName().trim().length() <= 0){
			return "First Name is mandatory!";
		}
		
		else if(residentDTO.getLoginDto().getLastName().isEmpty() || residentDTO.getLoginDto().getLastName().trim().length() <= 0){
			return "Last Name is mandatory!";
		}
		
		else if(residentDTO.getLoginDto().getEmailId().isEmpty() || residentDTO.getLoginDto().getEmailId().trim().length() <= 0){
			return "Email Id is mandatory!";
		}
		
		else if(residentDTO.getLoginDto().getUsername().isEmpty() || residentDTO.getLoginDto().getUsername().trim().length() <= 0){
			return "User Id is mandatory!";
		}
		else if(residentDTO.getLoginDto().getPassword().isEmpty() || residentDTO.getLoginDto().getPassword().trim().length() <= 0){
			return "Password is mandatory!";
		}
		
		return "success";
		
		
	}
	public String parameters_resident(LoginDTO loginDto){
		 
		if(loginDto.getFirstName().matches("[a-zA-Z]+")){
			if(loginDto.getLastName().matches("[a-zA-Z]+")){
				if(loginDto.getEmailId().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")){
					if(loginDto.getUsername().matches("[a-zA-Z0-9]+")){
						if(loginDto.getPassword().matches("[a-zA-Z0-9]+")){
							return "success";
						}
						else{
							return "Password should be alph-numeric";
						}
					}
					else{
						return "User Id should be alpha-numeric";
					}
					
			}
				else{
					return "Invalid Email Id";
				}
				}
			else{
				return "Last Name should be alphabets only!";
			}
				
			}
		else{
			return "First Name should be alphabets only!";
		}
		
	}
}
