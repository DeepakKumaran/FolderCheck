package testCases;

import org.testng.annotations.Test;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.cropper.indent.IndentCropper;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.coordinates.Coords;
import ru.yandex.qatools.ashot.coordinates.CoordsPreparationStrategy;
import ru.yandex.qatools.ashot.coordinates.CoordsProvider;
import ru.yandex.qatools.ashot.coordinates.JqueryCoordsProvider;
import ru.yandex.qatools.ashot.cropper.DefaultCropper;
import ru.yandex.qatools.ashot.cropper.ImageCropper;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;
import ru.yandex.qatools.ashot.shooting.SimpleShootingStrategy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.singletonList;
import static ru.yandex.qatools.ashot.coordinates.CoordsPreparationStrategy.intersectingWith;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import org.openqa.selenium.Point;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import utility.Constant;
//import automation.common.CommonMethods;

public class LaunchTest implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public WebDriver driver;
	static PrintWriter logwriter;
	
  @BeforeTest
  public void beforeTest() {
	  System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
	  driver = new ChromeDriver();
	  //System.setProperty("webdriver.gecko.driver", "driver/geckodriver.exe");
	  //driver = new FirefoxDriver();
	  driver.manage().window().maximize();
	  //driver.manage().window().setSize(new Dimension(1920,1080));
	  
	  
	  
		try {
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
			StackTraceElement e = stacktrace[2];//maybe this number needs to be corrected
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			String methodName = e.getClassName()+timeStamp+".txt";
			logwriter=new PrintWriter(new OutputStreamWriter(new FileOutputStream(Constant.LogFile_Path+methodName,true)));
			
			String scrFolder = Constant.Screenshot_Path + new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(Calendar.getInstance().getTime()).toString();
			new File(scrFolder).mkdir();
			
			System.setProperty("scr.folder", scrFolder);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
  
  @Test
  public void AzureScreenShot()
  {
	  try {
	  driver.get(Constant.URL);
	  ThreadSleep(5000);
  
	  driver.findElement(By.xpath("//*[@id='cred_userid_inputtext']")).sendKeys(Constant.UserName);
	  driver.findElement(By.xpath("//*[@id='cred_password_inputtext']")).sendKeys(Constant.Password);
	  //ThreadSleep(5000);
	  Click("//*[@id='cred_password_inputtext']",1000,"Just");
	  Click("//*[@id='cred_sign_in_button']",5000,"Sign In");
	  ExplicitWaitforVisibility("//*[@id='mso_account_tile']",5000);
	  Click("//*[@id='mso_account_tile']",10000,"Account");
	  ExplicitWaitforVisibility("//*[@id='i0118']",5000);
	  Click("//*[@id='i0118']",10000,"Second Sign In");
	  driver.findElement(By.xpath("//*[@id='i0118']")).sendKeys(Constant.Password);
	  Click("//*[@id='idSIButton9']",10000,"Sign In");
	  //Selecting the category
	  ExplicitWaitforVisibility("//*[@id='web-container']/div[2]/div[1]/div[1]/ul/li",5000);
	  List<WebElement> xPath = driver.findElements(By.xpath("//*[@id='web-container']/div[2]/div[1]/div[1]/ul/li"));
	  int totalLiSize = xPath.size();
	  System.out.println("Total Links by Way1 : " + totalLiSize);
	  
	  int liCount = driver.findElements(By.xpath("//*[@id='web-container']/div[2]/div[1]/div[1]/ul/li")).size();
	  System.out.println("Total Links by Way2 : " + liCount);
	  //For VM machines
		  for(int i=1;i<=totalLiSize-1;i++)
			  {
				  String LiFirstPart = "//*[@id='web-container']/div[2]/div[1]/div[1]/ul/li[";
				  String LiSecondPart = "]/a";
				  String FinalLiPart = LiFirstPart+i+LiSecondPart;
				  String Title = driver.findElement(By.xpath(FinalLiPart)).getAttribute("title");
				  System.out.println(Title);

				  if(Title.contains(Constant.CategoryName1))
				  {
					  System.out.println(Constant.CategoryName1);
					  Click(FinalLiPart,5000,Constant.CategoryName1);
		
					  MonitorScreen_VM(Constant.VM_Server1);
					  MonitorScreen_VM(Constant.VM_Server2);
					  MonitorScreen_VM(Constant.VM_DB1);
					  MonitorScreen_VM(Constant.VM_DB2);
					  //return;
				  }
				  else
				  {
					  System.out.println("Category not found");
				  }
	
			  }
		  //For DB machines
		  for(int j=1;j<=totalLiSize-1;j++)
		  {
			  String LiFirstPart = "//*[@id='web-container']/div[2]/div[1]/div[1]/ul/li[";
			  String LiSecondPart = "]/a";
			  String FinalLiPart = LiFirstPart+j+LiSecondPart;
			  String Title = driver.findElement(By.xpath(FinalLiPart)).getAttribute("title");
			  System.out.println(Title);

			  if(Title.contains(Constant.CategoryName2))
			  {
				  System.out.println(Constant.CategoryName2);
				  Click(FinalLiPart,5000,Constant.CategoryName2);
	
				  MonitorScreen_DB(Constant.SQL_DB1);
	
				  return;
			  }
			  else
			  {
				  System.out.println("Category not found");
			  }
		  }
	  
		} 
	  	catch (Exception e) 
	  	{
			e.printStackTrace();
		}
  }
  
  
  public void MonitorScreen_DB(String DBName)
  {
	  try
	  {
	
	  FillElementValue("//*[@id='web-container']/div[2]/main/div[2]/div/div[1]/div/div[1]/div[3]/div[2]/div/div/div[2]/div/div[2]/div[2]/div/div[1]/div[2]/div[1]/div[2]/div/div/input",DBName,"Server name");
	  ThreadSleep(5000);
	  String Server_Table_Path = "//*[@id='web-container']/div[2]/main/div[2]/div/div/div/div[1]/div[3]/div[2]/div/div/div[2]/div/div[2]/div[2]/div/div[3]/div/div[1]/div/div/div[1]/table/tbody";
	  WebElement DB_List = driver.findElement(By.xpath(Server_Table_Path));
	  List<WebElement> row_table = DB_List.findElements(By.tagName("tr"));
	  int row_count = row_table.size();
	  System.out.println("Total SQL DB's available is : "+row_count);
	  for(int r=1;r<=row_count;r++)
	  {
		  String DB_FirstPart = "//*[@id='web-container']/div[2]/main/div[2]/div/div/div/div[1]/div[3]/div[2]/div/div/div[2]/div/div[2]/div[2]/div/div[3]/div/div[1]/div/div/div[1]/table/tbody/tr[";
		  String DB_SecondPart = "]/td[1]/span/span[3]";
		  String DB_FinalPart = DB_FirstPart+r+DB_SecondPart;
		  
		  String Filter_Name = "//*[@id='web-container']/div[2]/main/div[2]/div/div[1]/div/div[1]/div[3]/div[2]/div/div/div[2]/div/div[2]/div[2]/div/div[3]/div/div[1]/div/div/div[1]/table/tbody/tr/td[1]/span/span[3]";
		  String Replication_Role = ".//*[@id='web-container']/div[2]/main/div[2]/div/div/div/div[1]/div[3]/div[2]/div/div/div[2]/div/div[2]/div[2]/div/div[3]/div/div[1]/div/div/div[1]/table/tbody/tr[1]/td[3]/span";
		  String Static_Role_Name = "Primary";
		  String DB_Name = driver.findElement(By.xpath(Filter_Name)).getText();
		  String Role_Name = driver.findElement(By.xpath(Replication_Role)).getText();
		  System.out.println(DB_Name);
		  if(DB_Name.contains(DBName)&&Role_Name.contains(Static_Role_Name))
		  {
			  System.out.println("Required machine is found");
			  Click(DB_FinalPart,8000,"DB");
			  ScrollToElement("//*[@id='web-container']/div[2]/main/div[2]/div/div[2]/div/div[2]/div[3]/div[2]/div/div/div[2]/div/div[1]/div[2]",3000,"Monitoring");
			  //----------------Percentage CPU:
			  //Click on Map
			  Click("//*[@id='web-container']/div[2]/main/div[2]/div/div[2]/div/div[2]/div[3]/div[2]/div/div/div[2]/div/div[1]/div[2]",9000,"Map");
			  //Scroll to Map
			  ScrollToElement("//*[@id='web-container']/div[2]/main/div[2]/div/div[2]/div/div[2]/div[3]/div[2]/div/div/div[2]/div/div[1]/div[2]",3000,"Monitoring 1");
			  //Clicking edit and Ok
			  Click_Edit("//*[@id='web-container']/div[2]/main/div[2]/div/div[2]/div/div[2]/div[3]/div[2]/div/div/div[2]/div/div[1]/div[2]/div[2]/div/div[1]");
			  //Again clicking on Map to refresh
			  Click("//*[@id='web-container']/div[2]/main/div[2]/div/div[2]/div/div[2]/div[3]/div[2]/div/div[1]/div[2]/div/div/div[2]",8000,"Map after date entered");
			  takeScreenshot(driver);

			  return;
		  }
		  else
		  {
			  System.out.println("Server not found");
		  }
	  }
	  }
	  catch (Exception e)
	  {
		  e.printStackTrace();
	  }

  }
  
  public void MonitorScreen_VM(String ServerName)
  {
	  try
	  {
	
	  FillElementValue("//*[@id='web-container']/div[2]/main/div[2]/div/div[1]/div/div[1]/div[3]/div[2]/div/div/div[2]/div/div[2]/div[2]/div/div[1]/div[2]/div[1]/div[2]/div/div/input",ServerName,"Server name");
	  ThreadSleep(5000);
	  String SQL_Table_Path = "//*[@id='web-container']/div[2]/main/div[2]/div/div/div/div[1]/div[3]/div[2]/div/div/div[2]/div/div[2]/div[2]/div/div[3]/div/div[1]/div/div/div[1]/table/tbody";
	  WebElement SQL_DB_List = driver.findElement(By.xpath(SQL_Table_Path));
	  List<WebElement> row_table = SQL_DB_List.findElements(By.tagName("tr"));
	  int row_count = row_table.size();
	  System.out.println("Total SQL DB's available is : "+row_count);
	  for(int r=1;r<=row_count;r++)
	  {
		  String DB_FirstPart = "//*[@id='web-container']/div[2]/main/div[2]/div/div/div/div[1]/div[3]/div[2]/div/div/div[2]/div/div[2]/div[2]/div/div[3]/div/div[1]/div/div/div[1]/table/tbody/tr[";
		  String DB_SecondPart = "]/td[1]/span/span[3]";
		  String DB_FinalPart = DB_FirstPart+r+DB_SecondPart;
		  
		  String Filter_Name = "//*[@id='web-container']/div[2]/main/div[2]/div/div[1]/div/div[1]/div[3]/div[2]/div/div/div[2]/div/div[2]/div[2]/div/div[3]/div/div[1]/div/div/div[1]/table/tbody/tr/td[1]/span/span[3]";
		  String DB_Name = driver.findElement(By.xpath(Filter_Name)).getText();
		  System.out.println(DB_Name);
		  if(DB_Name.contains(ServerName))
		  {
			  System.out.println("Required machine is found");
			  Click(DB_FinalPart,8000,"DB");
			  ScrollToElement("//*[@id='web-container']/div[2]/main/div[2]/div/div[2]/div/div[2]/div[3]/div[2]/div/div/div[2]/div/div[1]/div[2]",3000,"Monitoring");
			  //----------------Percentage CPU:
			  //Click on Map
			  Click("//*[@id='web-container']/div[2]/main/div[2]/div/div[2]/div/div[2]/div[3]/div[2]/div/div/div[2]/div/div[1]/div[2]",9000,"Map");
			  //Scroll to Map
			  ScrollToElement("//*[@id='web-container']/div[2]/main/div[2]/div/div[2]/div/div[2]/div[3]/div[2]/div/div/div[2]/div/div[1]/div[2]",3000,"Monitoring 1");
			  //Clicking edit and Ok
			  Click_Edit("//*[@id='web-container']/div[2]/main/div[2]/div/div[2]/div/div[2]/div[3]/div[2]/div/div/div[2]/div/div[1]/div[2]/div[2]/div/div[1]");
			  //Again clicking on Map to refresh
			  Click("//*[@id='web-container']/div[2]/main/div[2]/div/div[2]/div/div[2]/div[3]/div[2]/div/div/div[2]/div/div[1]/div[2]",8000,"Percentage CPU");
			  //shootWebElement("//*[@id='web-container']/div[2]/main/div[2]/div/div[2]/div/div[2]/div[3]/div[2]/div/div/div[2]/div/div[1]/div[2]");
			  takeScreenshot(driver);
			  
			  /*WebElement myWebElement = driver.findElement(By.xpath("//*[@id='web-container']/div[2]/main/div[2]/div/div[2]/div/div[2]/div[3]/div[2]/div/div/div[2]/div/div[1]/div[2]"));
			  Screenshot Scrn1 = new AShot().takeScreenshot(driver, myWebElement);
			  Screenshot Scrn2 = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1080)).takeScreenshot(driver);
			  //Screenshot new AShot().withCropper(new IndentCropper().addIndentFilter(blur())).takeScreenshot(driver, myWebElement); 
			  
			  try 
			  {
				  ImageIO.write(Scrn1.getImage(), "PNG", new File("C:\\Users\\Administrator\\Desktop\\Azure\\Shoot111.png"));
				  ImageIO.write(Scrn2.getImage(), "PNG", new File("C:\\Users\\Administrator\\Desktop\\Azure\\Shoot112.png"));
			  } catch (IOException e) 
			  {
				  e.printStackTrace();
			  }*/
			  
			  
			  
			  
			  //---------------Disk Read Bytes and Disk Write Bytes:
			  //Click on Map
			  //Click("//*[@id='web-container']/div[2]/main/div[2]/div/div[2]/div/div[2]/div[3]/div[2]/div/div/div[2]/div/div[1]/div[2]",9000,"Map");
			  //Scroll to Map
			  ScrollToElement("//*[@id='web-container']/div[2]/main/div[2]/div/div[2]/div/div[2]/div[3]/div[2]/div/div/div[2]/div/div[2]/div[2]",3000,"Monitoring 2");
			  //Clicking edit and Ok
			  Click_Edit("//*[@id='web-container']/div[2]/main/div[2]/div/div[2]/div/div[2]/div[3]/div[2]/div/div/div[2]/div/div[2]/div[2]/div[2]/div/div[1]");
			  //Again clicking on Map to refresh
			  Click("//*[@id='web-container']/div[2]/main/div[2]/div/div[2]/div/div[2]/div[3]/div[2]/div/div/div[2]/div/div[2]/div[2]",8000,"Percentage CPU");
			  takeScreenshot(driver);
			  
			  //---------------Network In and Network Out:
			  //Click on Map
			  //Click("//*[@id='web-container']/div[2]/main/div[2]/div/div[2]/div/div[2]/div[3]/div[2]/div/div/div[2]/div/div[1]/div[2]",9000,"Map");
			  //Scroll to Map
			  ScrollToElement("//*[@id='web-container']/div[2]/main/div[2]/div/div[2]/div/div[2]/div[3]/div[2]/div/div/div[2]/div/div[3]/div[2]",3000,"Monitoring 2");
			  //Clicking edit and Ok
			  Click_Edit("//*[@id='web-container']/div[2]/main/div[2]/div/div[2]/div/div[2]/div[3]/div[2]/div/div/div[2]/div/div[3]/div[2]/div[2]/div/div[1]");
			  //Again clicking on Map to refresh
			  Click("//*[@id='web-container']/div[2]/main/div[2]/div/div[2]/div/div[2]/div[3]/div[2]/div/div/div[2]/div/div[3]/div[2]",8000,"Percentage CPU");
			  takeScreenshot(driver);
			  
			  return;
		  }
		  else
		  {
			  System.out.println("Server not found");
		  }
	  }
	  }
	  catch (Exception e)
	  {
		  e.printStackTrace();
	  }

  }
  
  
  
  public void Click_Edit(String Edit_path)
  {
	  Click(Edit_path,5000,"Edit");
	  Click("//*[@id='web-container']/div[2]/div[3]/div[1]/div/div[1]/div[3]/div[2]/div/div/div[2]/div/div[2]/div[2]/div/div[1]/div[2]/div/ul/li[4]",5000,"Custom");
	  Click("//*[@id='web-container']/div[2]/div[3]/div[1]/div/div[1]/div[3]/div[2]/div/div/div[2]/div/div[2]/div[2]/div/div[2]/div[1]/div[2]/div/div/input",1000,"FromTime");
	  String FromTime = SetTime(Constant.FromTime);
	  FillElementValue("//*[@id='web-container']/div[2]/div[3]/div[1]/div/div[1]/div[3]/div[2]/div/div/div[2]/div/div[2]/div[2]/div/div[2]/div[1]/div[2]/div/div/input",FromTime,"From Date & Time");

	  Click("//*[@id='web-container']/div[2]/div[3]/div[1]/div/div[1]/div[3]/div[2]/div/div/div[2]/div/div[2]/div[2]/div/div[2]/div[2]/div[2]/div/div/input",1000,"ToTime");
	  String ToTime = SetTime(Constant.ToTime);
	  FillElementValue("//*[@id='web-container']/div[2]/div[3]/div[1]/div/div[1]/div[3]/div[2]/div/div/div[2]/div/div[2]/div[2]/div/div[2]/div[2]/div[2]/div/div/input",ToTime,"To Date & Time");
	  Click("//*[@id='web-container']/div[2]/div[3]/div[1]/div/div[1]/div[3]/div[2]/div/div/div[2]/div/div[2]/div[2]/div",1000,"Outer frame");
	  Click("//*[@id='web-container']/div[2]/div[3]/div[1]/div/div[1]/div[4]/div/div[1]/div[3]/div",5000,"Ok");
	  
	  
	  ThreadSleep(5000);
	  
  }
  
  
  public String SetTime(String Time)
  {
	  try
	  {
		  //Date today = new Date();
		  Calendar cal = Calendar.getInstance();
		  
		  cal.add(Calendar.DAY_OF_MONTH, -1); 
		  Date yesterday = cal.getTime(); 
		  System.out.println("Yesterday was " + toddMMyy(yesterday));

		  Time = toddMMyy(yesterday)+Time;
	  }
	  catch (Exception e)
	  {
		  e.printStackTrace();
	  }
	  return Time;
  }
  
  public static String toddMMyy(Date day)
  { 
	  SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy"); 
	  String date = formatter.format(day); 
	  return date; 
  }

  
  public static void takeScreenshot(WebDriver driver) throws IOException 
  {
	    //get the screenshot folder location from enviroment variable set in beginning of test
	    String scrFolder = System.getProperty("scr.folder");
	    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	    //copy screenshot to screenshot folder
	    FileUtils.copyFile(scrFile,new File(scrFolder+ "/screenshot"+ new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime()).toString()+ ".png"));
	}
  
  
  public void shootWebElement(WebElement element) throws IOException  {

      File screen = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);
      BufferedImage img = ImageIO.read(screen);
      Point p = element.getLocation();
     
      int width = element.getSize().getWidth();
      int height = element.getSize().getHeight();
      
      BufferedImage dest = img.getSubimage(p.getX(), p.getY(),width,height);
      ImageIO.write(dest, "png", screen);
      File f = new File("C:\\Users\\Administrator\\Desktop\\Azure\\Test1.png");
      FileUtils.copyFile(screen, f);
}
  
	public void ThreadSleep(int milliseconds){
		try{
			Thread.sleep(milliseconds);
		}
		catch(InterruptedException ie){
			ie.printStackTrace();
		}
	}
	
	public void ScrollToElement(String path,int sleeptime,String elementName)
	{
		try
		{
		WebElement element = driver.findElement(By.xpath(path));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
		Thread.sleep(sleeptime);
		PassPrint("Scrolled to "+elementName, 0);

		}
		catch(Exception e)
		{
			e.printStackTrace();
			FailPrint("Unable to scroll to "+elementName, 0);

		}
	}
  
	 public boolean Click(String path,int miliseconds,String ElementName)
	 {
		 try
		 {
			driver.findElement(By.xpath(path)).click();
			PassPrint(ElementName +" is Clicked", 0);
			Thread.sleep(miliseconds);
			return true;
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			 FailPrint(e.getStackTrace().toString(),2);
			 return false;
		 }
	 }
	 
	 public void ExplicitWaitforVisibility(String path,int seconds)
	 {
	 	//This method used to wait the driver until the particular xpath visible
	 	try
	 	{
	 		WebDriverWait wait = new WebDriverWait(driver, seconds);
	 		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
	 	}
	 	catch(Exception e)
	 	{
	 		e.printStackTrace();
	 	}
	 }
  
	 public void PassPrint(String text,int type)    //Method to print Pass scenario
	 {
		 if(type==0) //print all details
		 {
		 System.out.println(text);
		 System.out.println("PASS");
		 System.out.println("----------------------------------------------------------------");
		 
		 logwriter.println(text);
		 logwriter.println("PASS");
		 logwriter.println("----------------------------------------------------------------");
		 }
		 else if(type==1) //Print text value with pass
		 {
			 System.out.println(text);
			 System.out.println("PASS");
			 
			 logwriter.println(text);
			 logwriter.println("PASS");
		 }
		 else if(type==2)
		 {
			 System.out.println(text); //print text value only.
			 
			 logwriter.println(text); //print text value only.
		 }
	 }
	 
	 public void FailPrint(String text,int type)     //Method to print Fail scenario
	 {
		 if(type==0) //print all details.
		 {
		 System.out.println(driver.getCurrentUrl());
		 System.out.println(text);
		 System.out.println("Fail");
		 System.out.println("----------------------------------------------------------------");
		 
		 logwriter.println(driver.getCurrentUrl());
		 logwriter.println(text);
		 logwriter.println("Fail");
		 logwriter.println("----------------------------------------------------------------");
		 }
		 else if(type==1) //print text with fail
		 {
			 System.out.println(text);
			 System.out.println("Fail");
			 
			 logwriter.println(text);
			 logwriter.println("Fail");
		 }
		 else if(type==2) //print text only.
		 {
			 System.out.println(text);
			 
			 logwriter.println(text);
		 }
		 try {
			getScreenShot(Constant.Failed_Screenshot_Path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 } 
	 
	 public void getScreenShot(String path) throws IOException
	 {
		//String path="E:\\Automation\\Solid\\HanzePlanboard\\Screenshot";
		EventFiringWebDriver edriver=new EventFiringWebDriver(driver);
		File imgSrc=edriver.getScreenshotAs(OutputType.FILE);
		File imgDst=new File(path);
		org.apache.commons.io.FileUtils.copyFileToDirectory(imgSrc, imgDst);
	 }
	 
	public boolean FillElementValue(String path,String value,String elementName){
			 
		 boolean status=false;
		 
			 try{
				 clearElementValue(path, elementName);
				 driver.findElement(By.xpath(path)).sendKeys(value);
				 PassPrint(value +" entered in the "+elementName+" input box", 0);
				 status=true;
			 }
			 catch(Exception e){
				 e.printStackTrace();
				 FailPrint("Failed to enter the value in the input box", 0);
		 }
			return status;
		}
		
		public boolean clearElementValue(String path,String elementName){
			 
			 boolean status=false;
			 
			 try{
				 driver.findElement(By.xpath(path)).clear();
				 PassPrint(elementName +":Field value cleared", 0);
				 status=true;
			 }
			 catch(Exception e){
				 e.printStackTrace();
				 FailPrint(elementName +":Failed to clear field value", 0);
			 }
			return status;
		}
		
		
		
		
		
  
  @AfterTest
  public void afterTest() 
  {
	  //driver.quit();
  }

}
