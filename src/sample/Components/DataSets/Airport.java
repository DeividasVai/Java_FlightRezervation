package sample.Components.DataSets;

public class Airport {
	public String AirportName;
	public String City;
	public int AirportID;
	
	public void SetAirportName(String name)
	{
		AirportName = name;
	}
	public String GetAirportName()
	{
		return AirportName;
	}
	
	public void SetAirportCity(String city)
	{
		City = city;
	}
	public String GetAirportCity()
	{
		return City;
	}
	
	public void SetAirportID(int id)
	{
		AirportID = id;
	}
	public int GetAirportID()
	{
		return AirportID;
	}
}
