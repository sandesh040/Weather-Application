package application.weatherprediction;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class Weather
{
	public static void main(String[] args) throws IOException {
	String input;
	try{
	
	System.out.println("*****Temperature Predictor****");
	System.out.println("Enter the 5 digit Zip code of the city in USA");
	BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));

	
	while((input=br.readLine())!=null && !input.equals("exit") && !input.equals("quit")) {
		try {
		
			int zipcode = 75252;
			zipcode = Integer.parseInt(br.readLine());
			getDetails(zipcode);
		}catch (NumberFormatException e) {
			System.out.println("Please enter a valid zip code\n");
		}

		
		System.out.println("Want to check temperature at another location,\nthen please Enter the 5 digit Zip code of the city in USA or enter exit to quit");

	}
	
	
	System.out.println(".....Program Exited.....");

	}catch (IOException e) {
		e.printStackTrace();
	}
	}
public static void getDetails(int zipcode) {

	try {
	    
	// Make a URL to the web page
    URL url = new URL("https://weather.api.here.com/weather/1.0/report.json?app_id={YOUR_APP_ID}&app_code={YOUR_APP_CODE}&product=forecast_hourly&metric=false&zipcode=" + zipcode);
    url.openStream().close();
    // Get the input stream through URL Connection
    URLConnection con = url.openConnection();
    InputStream is = con.getInputStream();
    BufferedReader ar = new BufferedReader(new InputStreamReader(is));
    String line = null;
        
    line = ar.readLine();
    
	JSONObject obj = null;
	obj = new JSONObject(line);
	
	JSONArray arr = null;
	arr = obj.getJSONObject("hourlyForecasts").getJSONObject("forecastLocation").getJSONArray("forecast");
	String cityName = obj.getJSONObject("hourlyForecasts").getJSONObject("forecastLocation").getString("city");
	
	ArrayList<Double>  temp = new ArrayList<Double>();
	ArrayList<String> dateTime = new ArrayList<String>();
	int i = 0;
	String today = arr.getJSONObject(i).getString("weekday");
	for (i=1;today.equals(arr.getJSONObject(i).getString("weekday"));i++)
	
	{
	}
	System.out.println("i = " + i); 
	for (int j=0; j < 24; i++,j++)
	{
		temp.add(Double.parseDouble((arr.getJSONObject(i).getString("temperature"))));
		dateTime.add(arr.getJSONObject(i).getString("localTime"));
	}
	int min_index = temp.indexOf(Collections.min(temp));
	String time = dateTime.get(min_index).substring(0,2);
	String date = dateTime.get(min_index).substring(2, dateTime.get(min_index).length());
	date = date.substring(0,2) + "-" + date.substring(2,4) + "-" + date.substring(4,8);
	

	//System.out.println("coolest hour in " + cityName + " tomorrow would be at " + dateTime.get(min_index));
	System.out.println("coolest hour in " + cityName + " tomorrow on " + date + " would be at " + AMPM(time));
	System.out.println("and the temperature would be " + temp.get(min_index) +  " F");

	System.out.println("Time\tTemperature");
	for(int j=0; j<24; j++)
	{
		
		System.out.println(AMPM(dateTime.get(j).substring(0,2)) + "\t  " + temp.get(j) + " F");
	}
	
	

	
	}
	catch (IOException e) {
	System.out.println("City not found, please enter a valid Zip Code\n");
	return;
	}catch (JSONException e) {
		System.out.println(" Invalid API format ");
}
}


public static String AMPM(String time) {

String meridiem = "am";
System.out.println("time = " + time);
if(Integer.parseInt(time) > 11)
{
	if(!time.equals("12"))
		time = Integer.toString((Integer.parseInt(time) - 12));
	meridiem = "pm";
} else {
	if(time.equals("00"))
		time = "12";
}
return time+" "+meridiem;

}
}

		





