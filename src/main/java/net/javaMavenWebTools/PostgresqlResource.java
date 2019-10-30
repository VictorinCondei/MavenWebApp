package net.javaMavenWebTools;

import java.util.HashMap;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
@RequestScoped
@Path("/postgresql")
public class PostgresqlResource {  
    @GET()  
    @Produces("application/json")  
    public HashMap<String,String> getAll() {  
        HashMap<String, String> theRow = new HashMap<String, String>();  
        theRow.put("content", new QueryGenerator().generateList());  
        return theRow; 
    }
}
