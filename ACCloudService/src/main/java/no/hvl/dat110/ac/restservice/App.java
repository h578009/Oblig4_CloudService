package no.hvl.dat110.ac.restservice;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;
import static spark.Spark.post;
import static spark.Spark.delete;

import com.google.gson.Gson;

/**
 * Hello world!
 *
 */
public class App {
	
	static AccessLog accesslog = null;
	static AccessCode accesscode = null;
	
	public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8080);
		}

		// objects for data stored in the service
		
		accesslog = new AccessLog();
		accesscode  = new AccessCode();

		
		after((req, res) -> {
  		  res.type("application/json");
  		});
		
		// for basic testing purposes
		get("/accessdevice/hello", (req, res) -> {
			
		 	Gson gson = new Gson();
		 	
		 	return gson.toJson("IoT Access Control Device");
		});
		
		// TODO: implement the routes required for the access control service
		// as per the HTTP/REST operations describined in the project description
		
		post("/accessdevice/log", (req, res) -> {
			Gson gson = new Gson();
			
			String message=gson.fromJson(req.body(), AccessMessage.class).getMessage();
			
			int id = accesslog.add(message);
			return ("{\n	\"id:\""+id+",\n 	\"message\": "+ "\""+message+"\"\n}");
		});
		
		get("/accessdevice/log/", (req, res) -> {
		 	return accesslog.toJson();
		});
		
		get("/accessdevice/log/:id", (req, res) -> {
			String idString=req.params("id");
			int requestId = Integer.parseInt(idString);
			AccessEntry a = accesslog.get(requestId);
			return ("{\n	\"id:\""+a.getId()+",\n 	\"message\": "+ "\""+a.getMessage()+"\"\n}");
		 	
		});
		
		put("/accessdevice/code", (req, res) -> {
			
			Gson gson = new Gson();
        	
			int[] code =  gson.fromJson(req.body(), AccessCode.class).getAccesscode();
        	accesscode.setAccesscode(code);
        
            return "{\n	\"accesscode\": [\n	"+code[0]+",\n	"+code[1]+"\n]\n}";
		});
		
		get("/accessdevice/code", (req, res) -> {
			
			int[] code = accesscode.getAccesscode();
		 	String jsonString="{\n	\"accesscode\": [\n	"+code[0]+",\n	"+code[1]+"\n]\n}";
			return jsonString;
//		 	Gson gson = new Gson();
//			return gson.toJson(accesscode);
		});
		
		delete("/accessdevice/log/", (req, res) -> {
			accesslog.clear();
			return(accesslog.toJson());
		});
		
		
		
		
		
    }
    
}
