package com.dailyhunt.controller;


import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
//[END import_libraries]

import org.springframework.web.multipart.MultipartFile;

//[START import_libraries]

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionScopes;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.AnnotateImageResponse;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.common.collect.ImmutableList;


public class VisionController {
	
  private static final String APPLICATION_NAME = "helloWorld=PhotoFly/1.0";
  public static final String classPath = "/home/mukesh/Public/Eclipse_Projects/helloWorldNew/src/main/resources/static/";
  private static final int MAX_LABELS = 5;
  
  private static final String keyPath = classPath + "key.json";

  // [START run_application]
  /**
   * Annotates an image using the Vision API.
   */
  public static String execute(MultipartFile file) throws IOException, GeneralSecurityException {
    VisionController app = new VisionController(getVisionService());
    List<EntityAnnotation> labels = app.labelImageFile(file, MAX_LABELS);
    String temp = "";
    for (EntityAnnotation label : labels)
            temp += "," + label.getDescription();
   return temp;
  }
  // [START authenticate]
  private final Vision vision;

  public VisionController(Vision vision) {
    this.vision = vision;
  }
  public List<EntityAnnotation> labelImageFile(MultipartFile file, int maxResults) throws IOException {
	    // [START construct_request]
	    byte[] data = file.getBytes();

	    AnnotateImageRequest request =
	        new AnnotateImageRequest()
	            .setImage(new Image().encodeContent(data))
	            .setFeatures(ImmutableList.of(
	                new Feature()
	                    .setType("LABEL_DETECTION")
	                    .setMaxResults(maxResults)));
	    Vision.Images.Annotate annotate =
	        vision.images()
	            .annotate(new BatchAnnotateImagesRequest().setRequests(ImmutableList.of(request)));
	    // Due to a bug: requests to Vision API containing large images fail when GZipped.
	    annotate.setDisableGZipContent(true);
	    // [END construct_request]

	    // [START parse_response]
	    BatchAnnotateImagesResponse batchResponse = annotate.execute();
	    assert batchResponse.getResponses().size() == 1;
	    AnnotateImageResponse response = batchResponse.getResponses().get(0);
	    if (response.getLabelAnnotations() == null) {
	      throw new IOException(
	          response.getError() != null
	              ? response.getError().getMessage()
	              : "Unknown error getting image annotations");
	    }
	    return response.getLabelAnnotations();
	    // [END parse_response]
	  }
  public static Vision getVisionService() throws IOException, GeneralSecurityException {
	    GoogleCredential credential =
	        GoogleCredential
	        .fromStream(new FileInputStream(keyPath),GoogleNetHttpTransport.newTrustedTransport(),JacksonFactory.getDefaultInstance())
	        .createScoped(VisionScopes.all());
	    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
	    return new Vision.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, credential)
	            .setApplicationName(APPLICATION_NAME)
	            .build();
	  }
	  // [END authenticate]

}