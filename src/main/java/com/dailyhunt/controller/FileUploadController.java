package com.dailyhunt.controller;
import java.io.IOException;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dailyhunt.model.User;
import com.dailyhunt.repository.UserRepository;
import com.dailyhunt.storage.StorageFileNotFoundException;
import com.dailyhunt.storage.StorageService;


@Controller
public class FileUploadController {

    private final StorageService storageService;
    @Autowired
	private UserRepository userRepository;


    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/viewForm")
    public String listUploadedFiles(Model model) throws IOException {
    	
    	model.addAttribute("users",userRepository.findAll());
    	
//        model.addAttribute("files", storageService.loadAll().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
//                        "serveFile", path.getFileName().toString()).build().toString())
//                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    
    @GetMapping("/get/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getSingelFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/")//TODO response body change to json object for the api to work
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
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
		n.setFileName(file.getOriginalFilename());
		n.setState(state);
		n.setTags(tags);
		userRepository.save(n);
       
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/viewForm";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}