/*package automation.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CommonMethods 
{
	public WebDriver driver;

	public void ThreadSleep(int milliseconds){
		try{
			Thread.sleep(milliseconds);
		}
		catch(InterruptedException ie){
			ie.printStackTrace();
		}
	}
	
	public boolean FillElementValue(String path,String value){
		 
		 boolean status=false;
		 
		 try{
			 
			 driver.findElement(By.xpath(path)).sendKeys(value);
			 status=true;
		 }
		 catch(Exception e){
			 e.printStackTrace();
			 
		 }
		return status;
	}
	
	 public void Click(String path,int miliseconds)
	 {
		 try
		 {
			driver.findElement(By.xpath(path)).click();
			Thread.sleep(miliseconds);
			
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			
		 }
	 }
	
	public boolean clearElementValue(String path,String elementName){
		 
		 boolean status=false;
		 
		 try{
			 driver.findElement(By.xpath(path)).clear();
			 status=true;
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		return status;
	}
}
*/