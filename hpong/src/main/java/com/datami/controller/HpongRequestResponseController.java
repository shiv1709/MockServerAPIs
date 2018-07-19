package com.datami.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author Shiv Pratap Singh
 *
 */

@Api(tags = "Hpong Request Response Controller")
@RestController
public class HpongRequestResponseController {
	
	@ApiOperation(value = "Write request to file", produces = "application/json", consumes = "application/json")
	@RequestMapping(value = "/request/{request}", method = RequestMethod.POST, headers = "Accept=application/json")
	public  ResponseEntity<JSONObject> writeRequestToFile(
			final @RequestBody String requestBody,
			@ApiParam(value = "request", required = true) final @PathVariable String request) throws IOException, ParseException {
			BufferedWriter out = null;
			try {
				//System.getProperty returns absolute path
			    File f = new File("/Users/shivpratapsingh/Desktop" + "/request/" + request + "/"  +  request + ".json");
			    if(!f.getParentFile().exists()){
			        f.getParentFile().mkdirs();
			    }
			    //Remove if clause if you want to overwrite file
			    if(!f.exists()){
			        try {
			            f.createNewFile();
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
			    }
			    	    
			    /*
			    Reader reader = new FileReader("/Users/shivpratapsingh/Desktop/requestCounter.txt");  
	            int idValue= reader.read();  
	            System.out.println(idValue);
	            reader.close();
	            
	            */
	            
	            String content = new String(Files.readAllBytes(Paths.get("/Users/shivpratapsingh/Desktop/requestCounter.txt")));
	            int idValue = Integer.parseInt(content);
			    
				FileWriter fstream = new FileWriter("/Users/shivpratapsingh/Desktop/request/" + request + "/"  +  request + ".json",false); //true tells to append data.
			    out = new BufferedWriter(fstream);
			    
			    JSONParser parser = new JSONParser();
			    Object obj = parser.parse(requestBody);
			    JSONObject jsonObject =  (JSONObject) obj;
			    jsonObject.put("id", idValue);
			    
			    out.write(jsonObject.toJSONString());
			    
			    JSONObject output = new JSONObject();
			    output.put("id", idValue);
			    
			    idValue += 1;
			    
			    
	            Writer writer = new FileWriter("/Users/shivpratapsingh/Desktop/requestCounter.txt");
			    writer.write(String.valueOf(idValue));
			    writer.close();
			    
			    
			    return new ResponseEntity<JSONObject>(output,HttpStatus.OK);	    
		}
		catch( IOException ex) {
			ex.printStackTrace();
			return new ResponseEntity<JSONObject>(HttpStatus.NOT_FOUND);
		}
		finally
		{
		    if(out != null) {
		        out.close();
		    }
		}
		
}
	
	@ApiOperation(value = "Read request from file", produces = "application/json")
	@RequestMapping(value = "/request/{request}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<JSONObject> readRequestFromFile(
			@ApiParam(value = "request", required = true) final @PathVariable String request) throws ParseException {
			
		JSONParser parser = new JSONParser();

        try {
        	Object obj = parser.parse(new FileReader("/Users/shivpratapsingh/Desktop/request/" + request + "/"  +  request + ".json"));

            JSONObject jsonObject =  (JSONObject) obj ;
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e) {
        	e.printStackTrace();
        }
        
        return new ResponseEntity<JSONObject>(HttpStatus.NOT_FOUND);
	}
	
	
	@ApiOperation(value = "Write response to file", produces = "application/json", consumes = "application/json")
	@RequestMapping(value = "/request/{request}/response/{response}", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<JSONObject> writeResponseToFile(
			final @RequestBody String requestBody,
			@ApiParam(value = "request", required = true) final @PathVariable String request,
			@ApiParam(value = "response", required = true) final @PathVariable String response) throws IOException, ParseException {
			BufferedWriter out = null;
			try {
			   File f2 = new File("/Users/shivpratapsingh/Desktop/request/" + request  + "/response/" + response + ".json");
			   
			    if(!f2.getParentFile().exists()){
			        f2.getParentFile().mkdirs();
			    }
			    
			    if(!f2.exists()){
			        try {
			            f2.createNewFile();
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
			    }
			    
	            String content = new String(Files.readAllBytes(Paths.get("/Users/shivpratapsingh/Desktop/responseCounter.txt")));
	            int idValue = Integer.parseInt(content);
	            
			    FileWriter fstream = new FileWriter("/Users/shivpratapsingh/Desktop/request/" + request + "/"  + "response/" + response + ".json",false); //true tells to append data.
			    out = new BufferedWriter(fstream);
			    
			    JSONParser parser = new JSONParser();
			    Object obj = parser.parse(requestBody);
			    JSONObject jsonObject =  (JSONObject) obj;
			    jsonObject.put("id", idValue);
			    
			    out.write(jsonObject.toJSONString());
			    
			    JSONObject output = new JSONObject();
			    output.put("id", idValue);
			    
			    idValue += 1;
			    
			    Writer writer = new FileWriter("/Users/shivpratapsingh/Desktop/responseCounter.txt");
			    writer.write(String.valueOf(idValue));
			    writer.close();
			    
			    return new ResponseEntity<JSONObject>(output,HttpStatus.OK);	
		}
		catch( IOException ex) {
			ex.printStackTrace();
			return new ResponseEntity<JSONObject>(HttpStatus.NOT_FOUND);
		}
		finally
		{
		    if(out != null) {
		        out.close();
		    }
		}
}
	
	@ApiOperation(value = "Read response from file", produces = "application/json")
	@RequestMapping(value = "/request/{request}/response/{response}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<JSONObject> readResponseFromFile(
			@ApiParam(value = "request", required = true) final @PathVariable String request,
			@ApiParam(value = "response", required = true) final @PathVariable String response) throws ParseException {
			
		JSONParser parser = new JSONParser();

        try {
        	Object obj = parser.parse(new FileReader("/Users/shivpratapsingh/Desktop/request/" + request + "/response/"  + response + ".json"));

            JSONObject jsonObject =  (JSONObject) obj ;
            return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace(); 
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e) {
        	e.printStackTrace();
        }
        
        return new ResponseEntity<JSONObject>(HttpStatus.NOT_FOUND);
	}
	
}
