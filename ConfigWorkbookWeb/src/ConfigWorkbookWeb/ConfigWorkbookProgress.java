package ConfigWorkbookWeb;

import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.ws.ConnectionException;





public class ConfigWorkbookProgress {
	
	MetadataConnection metadataConnection;
	
	public  void step1(String username, String password,String endpoint){
		
		try {
			System.out.println("Logging In..");
			metadataConnection = SalesforceLogin
					.getMetadataConnection(username, password, endpoint);
			System.out.println("Logged In Successfully..");
		}
		catch (ConnectionException e){
			System.out.println(e);
		}
	}
	
	public void step2(){
		try{
			System.out.println(" Retrieving Metadata Components..");
		FileBasedMetadataCalls.retrieve(metadataConnection);
		System.out.println(" Metadata Retrieved..");
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void step3(){
		try{
			System.out.println(" Writing to Zip files...");
			CreateConfigBook.configBookWrite();
			System.out.println(" Config Workbook Created...");
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
		
}
