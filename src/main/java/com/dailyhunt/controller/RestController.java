package com.dailyhunt.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dailyhunt.model.Greeting;
import com.dailyhunt.model.Image;


@org.springframework.web.bind.annotation.RestController
public class RestController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/jsonGreeting")
    public Greeting jsonGreeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
    @RequestMapping(value = "/post", method = RequestMethod.POST)
	public String post(@RequestBody Image image) {
		System.out.println("/POST request with " + image.toString());
		// save Image to C:\\server folder
		String path = "/home/mukesh/" + image.getName();
		Utility.decoder(image.getData(), path);
		return "/Post Successful!";
	}
 
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public Image get(@RequestParam("name") String name ) {
//		name ="test.jpg";
		System.out.println(String.format("/GET info: imageName = %s", name));
		String imagePath = "/home/mukesh/" + name;
		String imageBase64 = Utility.encoder(imagePath);
		
		if(imageBase64 != null){
			Image image = new Image(name, imageBase64);
			return image;
		}
		return null;
	}
	
 
}
