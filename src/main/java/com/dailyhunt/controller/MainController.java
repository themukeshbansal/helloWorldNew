package com.dailyhunt.controller;


import java.util.ArrayList;

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
    @GetMapping("/addImage")
    public String addImage() {
        return "uploadForm";
    }
	public Iterable<User> process(String name,String city,String state,String tags) {
		int count = 0;
		ArrayList<String> searchTags= new ArrayList<>();
		if (!name.equals("SHOWALL"))
			searchTags.add(name);
		if (!city.equals("SHOWALL"))
			searchTags.add(city);
		if (!state.equals("SHOWALL"))
			searchTags.add(state);
		if (!tags.equals("SHOWALL")) {
			String[] temp = tags.split(",");
			for (int i = 0; i < temp.length; i++) {
				searchTags.add(temp[i]);
			}
		}
		count = searchTags.size();
		Iterable <User> results = null;
		switch (count) {
		case 0:
			results = userRepository.findAll();
			break;
		case 1:
			results = userRepository.findByTagsContaining(
					searchTags.get(0)
					);
			break;
		case 2:
			results = userRepository.findByTagsContainingAndTagsContaining(
					searchTags.get(0),
					searchTags.get(1)
					);
			break;
		case 3:
			results = userRepository.findByTagsContainingAndTagsContainingAndTagsContaining(
					searchTags.get(0),
					searchTags.get(1),
					searchTags.get(2)
					);
			break;
		case 4:
			results = userRepository.findByTagsContainingAndTagsContainingAndTagsContainingAndTagsContaining(
					searchTags.get(0),
					searchTags.get(1),
					searchTags.get(2),
					searchTags.get(3)
					);
			break;
		case 5:
			results = userRepository.findByTagsContainingAndTagsContainingAndTagsContainingAndTagsContainingAndTagsContaining(
					searchTags.get(0),
					searchTags.get(1),
					searchTags.get(2),
					searchTags.get(3),
					searchTags.get(4)
					);
			break;
		case 6:
			results = userRepository.findByTagsContainingAndTagsContainingAndTagsContainingAndTagsContainingAndTagsContainingAndTagsContaining(
					searchTags.get(0),
					searchTags.get(1),
					searchTags.get(2),
					searchTags.get(3),
					searchTags.get(4),
					searchTags.get(5)
					);
			break;
		case 7:
			results = userRepository.findByTagsContainingAndTagsContainingAndTagsContainingAndTagsContainingAndTagsContainingAndTagsContainingAndTagsContaining(
					searchTags.get(0),
					searchTags.get(1),
					searchTags.get(2),
					searchTags.get(3),
					searchTags.get(4),
					searchTags.get(5),
					searchTags.get(6)
					);
			break;
		case 8:
			results = userRepository.findByTagsContainingAndTagsContainingAndTagsContainingAndTagsContainingAndTagsContainingAndTagsContainingAndTagsContainingAndTagsContaining(
					searchTags.get(0),
					searchTags.get(1),
					searchTags.get(2),
					searchTags.get(3),
					searchTags.get(4),
					searchTags.get(5),
					searchTags.get(6),
					searchTags.get(7)
					);
			break;
		default:
			results = userRepository.findAll();
			break;
		}
		return results;
	}
}