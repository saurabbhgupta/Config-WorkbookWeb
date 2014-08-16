package ConfigWoorkbookWeb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigurationProperties {
	//Method to get values from property file
	public static Properties getPropValues() {
		
		Properties prop = new Properties();
		//Initialising outputfile
		OutputStream outputFile = null;
		//Initialising inputfile
		InputStream inputFile = null;
		//Creates new file named "config.properties"
		//File configfile = new File("C:\\eclipse\\config.properties");
		File configfile = new File("config.properties");
		try {
			//Checks if the file already exists
			if (!configfile.exists()) {
				outputFile = new FileOutputStream(configfile);
				prop.store(outputFile, null);
			}
			//Creates and load input file
			inputFile = new FileInputStream(configfile);
			prop.load(inputFile);
			/*Properties p = new Properties();
			ConfigurationProperties obj = new ConfigurationProperties();
			InputStream in = obj.getClass().getClassLoader().getResourceAsStream("src/ConfigWoorkbookWeb/config.properties");
			     p.load(in);*/
		}

		catch (IOException e) {
			
			System.out.println(e.getMessage());
		}

		return prop;
	}
	//Method to set values in property file
	public static void setPropValues(Properties prop) {

		try {
			prop.store(new FileOutputStream("config.properties"), null);
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}
}
