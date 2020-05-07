package no.hvl.dat110.ac.restservice;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.gson.Gson;

public class AccessLog {
	
	// atomic integer used to obtain identifiers for each access entry
	private AtomicInteger cid;
	protected ConcurrentHashMap<Integer, AccessEntry> log;
	
	public AccessLog () {
		this.log = new ConcurrentHashMap<Integer,AccessEntry>();
		cid = new AtomicInteger(0);
	}

	// TODO: add an access entry to the log for the provided message and return assigned id
	public int add(String message) {
		int id =cid.getAndIncrement();
		AccessEntry entry = new AccessEntry(id, message);
		log.put(id, entry);
		return id;
	}
		
	// TODO: retrieve a specific access entry from the log
	public AccessEntry get(int id) {
		
		return log.get(id);
		
	}
	
	// TODO: clear the access entry log
	public void clear() {
		log.clear();
		cid.set(0);
	}
	
	// TODO: return JSON representation of the access log
	public String toJson () {
		
		String json = "[\n";
    	int i = 0;
		if(log.size()!=0) {
			while(i<log.size()-1) {
				json+=("{\n	\"id:\" "+i+",\n 	\"message\": "+ "\" "+log.get(i).getMessage()+"\"\n},\n");
//				json+= ("{ id: "+i+", message:"+log.get(i).getMessage()+"},");
				i++;
			}
			
			json+=("{\n	\"id:\" "+i+",\n 	\"message\": "+ "\""+log.get(i).getMessage()+"\"\n}");
//			json+= ("{ id: "+i+", message:"+log.get(i).getMessage()+"}");
			
		}
		json+="\n]";
		return json;
	}
}
