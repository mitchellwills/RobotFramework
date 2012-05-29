package robot.io.computerdevices;

import com.sun.jna.*;
import com.sun.jna.win32.*;
/**
 * http://stackoverflow.com/questions/3434719/how-to-get-the-remaining-battery-life-in-a-windows-system
 *
 */
public interface MyKernel32 extends StdCallLibrary {


	public MyKernel32 INSTANCE = (MyKernel32) Native.loadLibrary("Kernel32", MyKernel32.class);

	/**
	 * @see "http://msdn2.microsoft.com/en-us/library/aa373232.aspx"
	 */
	public class SYSTEM_POWER_STATUS extends Structure {
		public byte ACLineStatus;
		public byte BatteryFlag;
		public byte BatteryLifePercent;
		public byte Reserved1;
		public int BatteryLifeTime;
		public int BatteryFullLifeTime;

		/**
		 * @return The AC power status
		 */
		public String getACLineStatusString() {
			switch (ACLineStatus) {
			case (0): return "Offline";
			case (1): return "Online";
			default: return "Unknown";
			}
		}

		/**
		 * @return true if the computer is plugged in
		 */
		public boolean isPluggedIn(){
			return ACLineStatus!=0;
		}

		/**
		 * @return The battery charge status
		 */
		public String getBatteryFlagString() {
			switch (BatteryFlag) {
			case (1): return "High, more than 66 percent";
			case (2): return "Low, less than 33 percent";
			case (4): return "Critical, less than five percent";
			case (8): return "Charging";
			case ((byte) 128): return "No system battery";
			default: return "Unknown";
			}
		}

		/**
		 * @return The percentage of full battery charge remaining
		 */
		public double getBatteryLifePercent() {
			return (BatteryLifePercent == (byte) 255) ? Double.NaN : BatteryLifePercent/100d;
		}

		/**
		 * @return The number of seconds of battery life remaining
		 */
		public int getBatteryLifeTime() {
			return (BatteryLifeTime == -1) ? -1 : BatteryLifeTime;
		}

		/**
		 * @return The number of seconds of battery life when at full charge
		 */
		public int getBatteryFullLifeTime() {
			return (BatteryFullLifeTime == -1) ? -1 : BatteryFullLifeTime;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("ACLineStatus: " + getACLineStatusString() + "\n");
			sb.append("Battery Flag: " + getBatteryFlagString() + "\n");
			sb.append("Battery Life: " + getBatteryLifePercent() + "\n");
			sb.append("Battery Left: " + getBatteryLifeTime() + "\n");
			sb.append("Battery Full: " + getBatteryFullLifeTime() + "\n");
			return sb.toString();
		}
	}

	/**
	 * Fill the structure.
	 * @param result the structure to fill
	 * @return return status
	 */
	public int GetSystemPowerStatus(SYSTEM_POWER_STATUS result);
}