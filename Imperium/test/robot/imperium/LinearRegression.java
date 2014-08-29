package robot.imperium;

import java.util.*;

public class LinearRegression {
	
	private final Map<Integer, Double> values = new HashMap<Integer, Double>();
	public void addValue(int x, double y){
		values.put(x, y);
	}
	
	public double getForX(double x){
		return yInt()+slope()*x;
	}
	
	
	private double xBar(){
		double sum = 0;
		for(Map.Entry<Integer, Double> entry:values.entrySet())
			sum += entry.getKey();
		return sum/values.size();
	}
	private double yBar(){
		double sum = 0;
		for(Map.Entry<Integer, Double> entry:values.entrySet())
			sum += entry.getValue();
		return sum/values.size();
	}
	private double xyBar(){
		double sum = 0;
		for(Map.Entry<Integer, Double> entry:values.entrySet())
			sum += entry.getKey()*entry.getValue();
		return sum/values.size();
	}
	private double xxBar(){
		double sum = 0;
		for(Map.Entry<Integer, Double> entry:values.entrySet())
			sum += entry.getKey()*entry.getKey();
		return sum/values.size();
	}
	
	public double yInt(){
		return yBar()-slope()*xBar();
	}
	
	public double slope(){
		return ( xyBar()-xBar()*yBar() )/( xxBar() - xBar()*xBar() );
	}
	
	public double rSquared(){
		double yint = yInt();
		double slope = slope();
		double ybar = yBar();
		
		double SSerr = 0;
		for(Map.Entry<Integer, Double> entry:values.entrySet()){
			double f = slope*entry.getKey()+yint;
			SSerr += (entry.getValue()-f)*(entry.getValue()-f);
		}
		SSerr/=values.size();
		
		double SStot = 0;
		for(Map.Entry<Integer, Double> entry:values.entrySet()){
			SStot += (entry.getValue()-ybar)*(entry.getValue()-ybar);
		}
		SStot/=values.size();
		
		return 1-SSerr/SStot;
	}
}
