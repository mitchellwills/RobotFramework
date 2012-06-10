package robot.imperium;

import robot.imperium.hardware.*;
import robot.imperium.objects.*;
import robot.io.computerdevices.rxtx.*;
import robot.thread.*;
import robot.util.*;

public class ImperiumTest {
	
	public static interface ObjectSource{
		String getName();
		int getMaxNum();
		ImperiumDeviceObject getObject(ImperiumDevice device, int number);
	}
	
	private static final int numTimeMeasure = 3;
	public static void main(String[] args){
		double baseline = getBaseline(20);
		System.out.println("Baseline: "+baseline);
		test(new DigitalInputObjectSource(), baseline);
		test(new DigitalOutputObjectSource(), baseline);
		test(new AnalogInputObjectSource(), baseline);
		test(new ServoOutputObjectSource(), baseline);
	}
	
	public static void test(ObjectSource source, double baseline){
		
		LinearRegression updateRegression = new LinearRegression();
		for(int i = 10; i<=100; i+=10)
			updateRegression.addValue(i, test(source, source.getMaxNum(), i));
		
		LinearRegression numRegression = new LinearRegression();
		for(int i = 1; i<=source.getMaxNum(); ++i)
			numRegression.addValue(i, test(source, i, 20));
		double rwCost = updateRegression.slope()*20/source.getMaxNum();
		double updateCost = numRegression.slope()-rwCost;
		double initialUpdateCost = numRegression.yInt()-baseline;
		double updateCost2 = (updateRegression.yInt()-baseline-initialUpdateCost)/source.getMaxNum();
		System.out.println(source.getName()+" - Initial Update Cost: "+initialUpdateCost+",    RW Cost: "+rwCost+",    Update: "+updateCost+", "+updateCost2);
		System.out.println("R^2: "+updateRegression.rSquared()+", "+numRegression.rSquared());
	}
	
	
	public static double getBaseline(int update){
		RxTxComputerSerialPort port = new RxTxComputerSerialPort("COM16", 115200);
		ImperiumDevice device = new AT90USB1286(new RobotThreadFactoryImpl(), port, update);
		ImperiumDebug debug = new ImperiumDebug(device);
		
		double updateSum = 0;
		RobotUtil.sleep(400);
		for(int i = 0; i<numTimeMeasure; ++i){
			RobotUtil.sleep(1100);
			updateSum += debug.getUpdateRate();
		}
		
		double time = 1000000/(updateSum/numTimeMeasure);
		device.stop();
		port.close();
		return time;
	}
	
	public static double test(ObjectSource source, int num, int update){
		RxTxComputerSerialPort port = new RxTxComputerSerialPort("COM16", 115200);
		ImperiumDevice device = new AT90USB1286(new RobotThreadFactoryImpl(), port, update);
		ImperiumDebug debug = new ImperiumDebug(device);
		for(int i = 0; i<num; ++i)
			source.getObject(device, i);
		
		double measuredUpdateSum = 0;
		RobotUtil.sleep(400);
		for(int i = 0; i<numTimeMeasure; ++i){
			RobotUtil.sleep(1100);
			measuredUpdateSum += debug.getUpdateRate();
		}
		double measuredUpdate = measuredUpdateSum/numTimeMeasure;
		double time = 1000000/measuredUpdate;
		//System.out.println(source.getName()+" - "+num+" - "+time+" - "+measuredUpdate);
		device.stop();
		port.close();
		return time;
	}
	
	
	
	
	
	
	public static class DigitalInputObjectSource implements ObjectSource{
		@Override
		public ImperiumDeviceObject getObject(ImperiumDevice device, int number) {
			return new ImperiumDigitalInput(device, "PB"+number);
		}
		@Override
		public String getName() {
			return "DigitalInput";
		}
		@Override
		public int getMaxNum() {
			return 8;
		}
	}
	
	public static class DigitalOutputObjectSource implements ObjectSource{
		@Override
		public ImperiumDeviceObject getObject(ImperiumDevice device, int number) {
			return new ImperiumDigitalOutput(device, "PB"+number);
		}
		@Override
		public String getName() {
			return "DigitalOutput";
		}
		@Override
		public int getMaxNum() {
			return 8;
		}
	}
	public static class AnalogInputObjectSource implements ObjectSource{
		@Override
		public ImperiumDeviceObject getObject(ImperiumDevice device, int number) {
			return new ImperiumAnalogInput(device, "ADC"+number);
		}
		@Override
		public String getName() {
			return "AnalogInput";
		}
		@Override
		public int getMaxNum() {
			return 8;
		}
	}
	public static class ServoOutputObjectSource implements ObjectSource{
		@Override
		public ImperiumDeviceObject getObject(ImperiumDevice device, int number) {
			return new ImperiumServoOutput(device, "PB"+number);
		}
		@Override
		public String getName() {
			return "ServoOutput";
		}
		@Override
		public int getMaxNum() {
			return 5;
		}
	}
}
