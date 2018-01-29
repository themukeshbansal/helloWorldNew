package com.dailyhunt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dailyhunt.model.User;
import com.dailyhunt.repository.UserRepository;


@Controller
public class MainController {
	@Autowired
	private UserRepository userRepository;
    
    @GetMapping("/display")//TODO check expression to return all results if there is no default value present.
    //Something like if empty find all otherwise find the one that is mentioned
    public String display(@RequestParam(value="name", required=false, defaultValue="SHOWALL") String name,
    		@RequestParam(value="clientId", required=false, defaultValue="SHOWALL") String clientId,
    		@RequestParam(value="city", required=false, defaultValue="SHOWALL") String city,
    		@RequestParam(value="state", required=false, defaultValue="SHOWALL") String state,
    		@RequestParam(value="tags", required=false, defaultValue="SHOWALL") String tags,
    		Model model) {
    	model.addAttribute("users",this.process(name,city,state,tags));
        return "display";
    }
    
    
	public Iterable<User> process(String name,String city,String state,String tags) {
		if (name.equals("SHOWALL") && city.equals("SHOWALL") && state.equals("SHOWALL") && tags.equals("SHOWALL")) {
    		return userRepository.findAll();
		}
    	
    	else
    		return userRepository.findByNameOrCityOrStateOrTags(name,city,state,tags);
	}

}