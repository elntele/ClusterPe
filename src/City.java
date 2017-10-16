
public class City {
private Integer id;
private String name;
private String state = "PE";
private String country = "Brazil";
private double  longitude;
private double latitude;
private String type = "Node";
private int population;
private double idh;
private double gini;
private double pib;
public City(Integer id, String name, double longitude, 
		double latitude, int population, double iDH, 
		double gINI,double pIB) {
	this.id = id;
	this.name = name;
	this.longitude = longitude;
	this.latitude = latitude;
	this.population = population;
	this.idh = iDH;
	this.gini = gINI;
	this.pib = pIB;
}


public City(){
	
}


public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public double getLongitude() {
	return longitude;
}
public void setLongitude(double longitude) {
	this.longitude = longitude;
}
public double getLatitude() {
	return latitude;
}
public void setLatitude(double latitude) {
	this.latitude = latitude;
}
public int getPopulation() {
	return population;
}
public void setPopulation(int population) {
	this.population = population;
}
public double getIDH() {
	return idh;
}
public void setIDH(double idh) {
	this.idh = idh;
}
public double getGini() {
	return gini;
}
public void setGini(double gini) {
	this.gini = gini;
}
public double getPIB() {
	return pib;
}
public void setPIB(double pib) {
	this.pib = pib;
}



}
