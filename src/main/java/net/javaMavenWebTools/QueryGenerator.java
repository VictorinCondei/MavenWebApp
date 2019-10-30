package net.javaMavenWebTools;
import java.util.Random;
import net.tools.*;

public class QueryGenerator {
	public String generateList() {
		
		String article = "NONE";
		SQLDBTools st=new SQLDBTools();
		if (st.SQLDBConnect() != null) {
			article=st.GetFromDB();
		}
		return String.format("The db content %s ", article);
	}
}
