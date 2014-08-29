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
	
	private static final int numUpdateRate = 20;//the number of updates per second for a number test
	private static final int numTimeMeasure = 5;
	public static void main(String[] args){
		double baseline = getBaseline(numUpdateRate);
		System.out.println("Baseline: "+baseline);
		test(new DigitalInputObjectSource(), baseline);
		test(new DigitalOutputObjectSource(), baseline);
		test(new AnalogInputObjectSource(), baseline);
		test(new ServoOutputObjectSource(), baseline);
		test(new PWMOutputObjectSource(), baseline);
	}
	
	public static void test(ObjectSource source, double baseline){
		
		LinearRegression updateTimePerRW = new LinearRegression();
		for(int i = 10; i<=100; i+=20){
			TestResult result = test(source, source.getMaxNum(), i);
			updateTimePerRW.addValue(i, result.time);
		}
		
		LinearRegression updateTimePerObject = new LinearRegression();
		for(int i = 1; i<=source.getMaxNum(); ++i){
			TestResult result = test(source, i, numUpdateRate);
			updateTimePerObject.addValue(i, result.time);
		}
		
		double rwCost = updateTimePerRW.slope();//cost for read and write
		double initialUpdateCost = updateTimePerObject.yInt()-baseline;//the cost (us) initially when any object of this type is used
		double updateCost = updateTimePerObject.slope()-rwCost*numUpdateRate/source.getMaxNum();//the update cost (us) per object of the type
		double updateCost2 = (updateTimePerRW.yInt()-baseline-initialUpdateCost)/source.getMaxNum();//update cost calculated a different way
		System.out.printf("%s - Initial Update Cost: %.3f,    RW Cost: %.3f,    Update: %.3f, %.3f\n",
				source.getName(), initialUpdateCost, rwCost, updateCost, updateCost2);
		System.out.printf("R^2: %.3f, %.3f\n", updateTimePerRW.rSquared(), updateTimePerObject.rSquared());
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
	private static class TestResult{
		public final double numUpdates;
		public final double time;

		public TestResult(double numUpdates, double time){
			this.numUpdates = numUpdates;
			this.time = time;
		}
	}
	
	public static TestResult test(ObjectSource source, int num, int update){
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
		System.out.println(source.getName()+" - "+num+" - "+1000000/measuredUpdate+" - "+measuredUpdate);
		device.stop();
		port.close();
		return new TestResult(measuredUpdate, time);
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
	public static class PWMOutputObjectSource implements ObjectSource{
		@Override
		public ImperiumDeviceObject getObject(ImperiumDevice device, int number) {
			switch(number){
			case 0:
				return new ImperiumServoOutput(device, "PB7");
			case 1:
				return new ImperiumServoOutput(device, "PD1");
			case 2:
				return new ImperiumServoOutput(device, "PC4");
			case 3:
				return new ImperiumServoOutput(device, "PC5");
			case 4:
				return new ImperiumServoOutput(device, "PC6");
			case 5:
				return new ImperiumServoOutput(device, "PB6");
			case 6:
				return new ImperiumServoOutput(device, "PB5");
			case 7:
				return new ImperiumServoOutput(device, "PB4");
			}
			return null;
		}
		@Override
		public String getName() {
			return "PWMOutput";
		}
		@Override
		public int getMaxNum() {
			return 5;
		}
	}
}
