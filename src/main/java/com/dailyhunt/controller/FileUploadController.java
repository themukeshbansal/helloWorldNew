package com.dailyhunt.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dailyhunt.model.Greeting;
import com.dailyhunt.model.User;
import com.dailyhunt.repository.UserRepository;
import com.dailyhunt.storage.StorageFileNotFoundException;
import com.dailyhunt.storage.StorageService;


@Controller
@RequestMapping(path="/api")
public class FileUploadController {
	
	@Autowired
    private final MainController MC;
	private final StorageService storageService;
    @Autowired
	private UserRepository userRepository;
    @Autowired
    public FileUploadController(MainController MC,StorageService storageService) {
        this.storageService = storageService;
        this.MC = MC;
    }
    @PostMapping("/upload")//TODO response body change to json object for the api to work
    public @ResponseBody Greeting handleFileUpload(@RequestParam("file") MultipartFile file,
    		@RequestParam String name,
    		@RequestParam String clientId,
    		@RequestParam String city,
    		@RequestParam String state,
    		@RequestParam String fileName,
    		@RequestParam String tags,
            RedirectAttributes redirectAttributes) {
    	//create a user entity and add the filename,id, name,clientId,city,state,date,Resolution 
    	//to the entity and return a json response of success.
        storageService.store(file); //if needed get the file name from here whichever you have stored
        User n = new User();
		n.setName(name);
		n.setClientId(clientId);
		n.setCity(city);
		String path = "/api/files/" + file.getOriginalFilename(); 
		n.setFileName(path);
		n.setState(state);
		n.setTags(name+","+","+city+","+state+","+","+tags);
		userRepository.save(n);
		return new Greeting(200,"Success");
    }
    @GetMapping(path="/getAll")
    public @ResponseBody Iterable<User> display(@RequestParam(value="name", required=false, defaultValue="SHOWALL") String name,
    		@RequestParam(value="city", required=false, defaultValue="SHOWALL") String city,
    		@RequestParam(value="state", required=false, defaultValue="SHOWALL") String state,
    		@RequestParam(value="tags", required=false, defaultValue="SHOWALL") String tags,
    		Model model) {
    	
    	return MC.process(name, city, state, tags);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}