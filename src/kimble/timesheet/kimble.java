package kimble.timesheet;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;



public class kimble{
	WebDriver driver;
	int x,y=0;
	
	public void setUP(){
		File file=new File("chromedriver.exe");
		System.setProperty("webdriver.chrome.driver",file.getAbsolutePath());
		DesiredCapabilities cap=DesiredCapabilities.chrome();
		ChromeOptions option=new ChromeOptions();
		option.addArguments("silent");
		option.addArguments("disable-extensions");
		option.setExperimentalOption("excludeSwitches", Arrays.asList("ignore-certificate-errors"));
		cap.setCapability(ChromeOptions.CAPABILITY, option);
		driver=new ChromeDriver(cap);		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	public void login(String user, String pass){
		driver.get("https://login.salesforce.com");
		driver.findElement(By.id("username")).sendKeys(user);
		driver.findElement(By.id("password")).sendKeys(pass);
		driver.findElement(By.id("Login")).click();
		if(driver.findElements(By.xpath("//div[contains(text(),'Your login attempt has failed.')]")).size()==0){
			System.out.println("login successful");
			
		}
		else{
			System.out.println("login failed");
			driver.quit();
		}
		
		
	}

	public void Kimble(String user, String pass, String monthepassed, String datepassed, String Project) throws InterruptedException
	{
		setUP();
		login(user,pass);
		Thread.sleep(8000);
		String monthname=monthepassed;
		String dateTostart = datepassed;
		String Proj=Project;
		driver.findElement(By.id("tsidButton")).click();
//		driver.findElement(By.xpath("//div[@id='tsid-menuItems']/a[2]")).click();
		List<WebElement> findElements = driver.findElement(By.id("tsid-menuItems")).findElements(By.tagName("a"));
		for (WebElement webElement : findElements) {
			String app=webElement.getText();
			if(app.equals("Kimble Time & Expense")){
				webElement.click();
				break;
			}
		}
		
		WebDriverWait wait=new WebDriverWait(driver, 10);
		WebElement element=wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ui-datepicker-trigger")));
		element.click();
		calenderMonth(monthname,dateTostart);
//		List<WebElement> calenderrows= driver.findElements(By.xpath("//table[@class='ui-datepicker-calendar']/tbody/tr"));
//		int totalrow=calenderrows.size();
//		driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']/tbody/tr/td[2]/a")).click();
		String d=Calenderdate(dateTostart,monthname);
		
		ArrayList<Integer> mnum = monthnum(monthname);
		
		Thread.sleep(3000);
		int p=0;
		int h=0;
//		h<mnum.get(1)
		WebDriverWait waits=new WebDriverWait(driver, 20);
		for( int j=x;;j++){
//			driver.findElement(By.className("ui-datepicker-trigger")).click();
//			List<WebElement> dateday= driver.findElements(By.xpath("//table[@class='ui-datepicker-calendar']/tbody/tr["+j+"]/td"));
////			System.out.println("Dates are "+dateday.size());
//			
//			for (WebElement webElement : dateday) {
//				day=webElement.getText();
////				System.out.println(day);
//				int t=Integer.parseInt(day);
//				if(j==totalrow && t==1){break;}
//				
//			}
//			driver.findElement(By.className("ui-datepicker-trigger")).click();
		for(int i=1;;i++){
//			System.out.println("Iteration "+i);
			
			
			WebElement elements = null;
			try {
				String bodydate=driver.findElement(By.xpath("//div[@id='TimesheetBody']/div/div[2]/div["+i+"]/div")).getText();
				bodydate=bodydate.substring(0, 2);
				h=Integer.parseInt(bodydate);
				
				
				elements = waits.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='TimesheetBody']/div/div[2]/div["+i+"]/div[2]/div/p/a")));
			} catch (NumberFormatException e) {
				
				System.out.println("it was last date of the month");
				break;
			}
			catch(NoSuchElementException e){
				System.out.println("it was last date of the month");
				break;
			}
			
			if(elements.getText().equals("+ Forecast Time Entry"))
			{
				p=1;
				System.out.println("Congratulation, Timesheet filled and upto date");
				System.out.println("Next is future date");
				break;
				}
			if(j==x && h<Integer.parseInt(dateTostart) ){
//				System.out.println("skipping iteration");
				continue;
				}
//			if(j==x && d.equalsIgnoreCase("Su") || j==x && d.equalsIgnoreCase("Sa")){ 
//			System.out.println("Skipped: Saturday or Sunday ");
//			Thread.sleep(2000);
//			break;
//			}
			
			
			String date1day=driver.findElement(By.xpath("//div[@id='TimesheetBody']/div/div[2]/div["+i+"]")).getAttribute("class");
			if(date1day.equals("TimeEntryPeriodContainer NonBusDay")){
				System.out.println("Skipping : Saturday / Sunday");
				break;
			}
			
			String cont=driver.findElement(By.xpath("//div[@id='TimesheetBody']/div/div[2]/div["+i+"]/div[2]/div/div/div/div/div")).getText();
			if(cont.contains("# Annual Leave")){ 
				System.out.println("Annual leave");
				continue;
				}
			elements.click();
			String date1=driver.findElement(By.xpath("//div[@id='TimesheetBody']/div/div[2]/div["+i+"]/div")).getText();
			System.out.println("Initiating for "+date1);
			
			
			
//		WebElement datelink=driver.findElement(By.xpath("//div[@id='TimesheetBody']/div/div[2]/div["+i+"]/div[2]/div/p/a"));
		
//		datelink.click();
		Thread.sleep(10000);
		Select sel=new Select(waits.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[@class='data2Col first']/div/select"))));
//		sel.selectByVisibleText("Make Positive (Products)-Provar (Test Analyst )");
//		sel.selectByVisibleText("Informatica-Informatica - Managed Services");
		try {
			sel.selectByVisibleText(Proj);
		} catch (Exception e1) {
			System.out.println("xxxxxxxxxxxxx-Incorrect Project Name-xxxxxxxxxxxxxx");
			p=1;
			break;
		}
		Thread.sleep(8000);
		
		WebElement el=waits.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='detailList']/tbody/tr[4]/td/div/input")));
		el.sendKeys("8");
//		driver.findElement(By.xpath("//table[@class='detailList']/tbody/tr[4]/td/div/input")).sendKeys("8");
		WebElement ele;
		try {
			ele = driver.findElement(By.xpath("//td[@class='data2Col last']/div/select"));
		
		Select selct=new Select(ele);
		selct.selectByVisibleText("Development & Launch");
		
		Thread.sleep(10000);
		} catch (Exception e) {
			System.out.println("No task for this item");
		}
		driver.findElement(By.xpath("//div[@id='AddTimeEntryPopup']/span/form/span[2]/div/div/div[2]/table/tbody/tr/td[2]/input[2]")).click();
		}
		
		
		if(p==1){break;}
		Thread.sleep(5000);
		WebElement e = waits.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@title='Next Period']")));
		e.click();
//		driver.findElement(By.xpath("//a[@title='Next Period']")).click();
		}
		System.out.println("Execution finished");
		setDown();
	}
	
	
	public String Calenderdate(String startdate, String month)
	{
		String dday=null;
		String day = null;
		calenderMonth(month,startdate);
		
//		driver.findElement(By.className("ui-datepicker-trigger")).click();
		List<WebElement> calenderrows= driver.findElements(By.xpath("//table[@class='ui-datepicker-calendar']/tbody/tr"));
		int totalrow=calenderrows.size();
		for(int j=1;j<=totalrow;j++){
		List<WebElement> dateday= driver.findElements(By.xpath("//table[@class='ui-datepicker-calendar']/tbody/tr["+j+"]/td"));
		for (WebElement webElement : dateday) {
			day=webElement.getText();
//			System.out.println(day);
			int t=Integer.parseInt(day);
//			System.out.println("date is "+t);
			if(j==totalrow && t==1)
			{
				break;
				}
			
		}
//		System.out.println("calendar dates are "+dateday.size());
		
//		for (WebElement webElement : dateday) {
//		
//			String day=webElement.getText();
////			System.out.println(day);
//			if(day.equals(startdate)){
//				System.out.println("inside if " +webElement.getTagName());
//				webElement.click();
//				
//				x=j;
//				break;
//			}
//			else{
//				System.out.println("its "+ day);
//			}
		for(int n=0;n<dateday.size();n++){
//			System.out.println("calendar n "+dateday.get(n));
			if(j==1 && Integer.parseInt(dateday.get(0).getText())>7 && Integer.parseInt(dateday.get(0).getText())<21) 
			{break;}
			if(dateday.get(n).getText().equals(startdate) ){
//				System.out.println("for " +dateday.get(n).getText());
//				System.out.println("n is "+n);
				dateday.get(n).click();
				x=j;
				y=n;
				driver.findElement(By.className("ui-datepicker-trigger")).click();
				calenderMonth(month,startdate);
				dday=driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']/thead/tr/th["+(y+1)+"]/span")).getText();
				break;
			}
//			System.out.println("Index is at "+ dateday.indexOf(startdate));
		}
		if(x!=0){break;}
		}
		System.out.println("you are starting from : "+startdate);
		return dday;
		}
	/**
	 * @param month
	 */
	private void calenderMonth(String month,String date) {
		for(int m=1;m<12;m++){
		String currentmonth=driver.findElement(By.className("ui-datepicker-month")).getText();
//		System.out.println(month);
		ArrayList<Integer> ar=monthnum(month);
		int val1=ar.get(0);
		ArrayList<Integer> ar2=monthnum(currentmonth);
		int val2=ar2.get(0);
		if(Integer.parseInt(date)>ar.get(1) || Integer.parseInt(date)<1){
			System.out.println("Invalid date");
			driver.quit();
			System.exit(0);
		}
		if(month.equalsIgnoreCase(currentmonth)){
			break;
		}
		else if(val1>val2){
			
			driver.findElement(By.className("ui-icon ui-icon-circle-triangle-e")).click();
		}
		else if(val1<val2){
			driver.findElement(By.xpath("//span[@class='ui-icon ui-icon-circle-triangle-w']")).click();
		}
		}
	}

	
	
	public ArrayList<Integer> monthnum(String month)
	{
		ArrayList<Integer> num=new ArrayList<>();
		
		String m=month.toLowerCase();
		
		switch(m){
		
		case "january":
			num.add(1);
			num.add(31);
			break;

		case "february":
			num.add(2);
			num.add(28);
			break;
		case "march":
			num.add(3);
			num.add(31);
			break;
		case "april":
			num.add(4);
			num.add(30);
			break;
		case "may":
			num.add(5);
			num.add(31);
			break;
		case "june":
			num.add(6);
			num.add(30);
			break;
		case "july":
			num.add(7);
			num.add(31);
			break;
		case "august":
			num.add(8);
			num.add(31);
			break;
		case "september":
			num.add(9);
			num.add(30);
			break;
		case "october":
			num.add(10);
			num.add(31);
			break;
		case "november":
			num.add(11);
			num.add(30);
			break;
		case "december":
			num.add(12);
			num.add(31);
			break;	
		default:
			System.out.println("Invalid Month");
			driver.quit();
			break;
			
		}
	
			
		
		return num;
			
	}

	public void setDown(){
		driver.quit();
		System.exit(0);
	}
	
}
