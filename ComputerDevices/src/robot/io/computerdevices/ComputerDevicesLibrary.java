package robot.io.computerdevices;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * @author Mitchell
 * 
 * Utility class to help unpack the native library from the jar
 *
 */
public class ComputerDevicesLibrary {
	/**
	 * The name of the native library
	 */
	public static final String LIBRARY_NAME = "ComputerDevicesNative";
	private ComputerDevicesLibrary(){}
	
	static{
		try {
			URL nativeLibarayURL = ComputerDevicesLibrary.class.getResource(System.mapLibraryName(LIBRARY_NAME));
			System.out.println("copying "+nativeLibarayURL);
			InputStream is = nativeLibarayURL.openStream();
			File tmpFile = new File(System.mapLibraryName(LIBRARY_NAME));
			tmpFile.deleteOnExit();
			OutputStream os = new FileOutputStream(tmpFile);
			
			byte[] copyBuffer = new byte[1024];
			int bytesRead;
			while((bytesRead = is.read(copyBuffer))>0){
				os.write(copyBuffer, 0, bytesRead);
			}
			os.close();
			is.close();
			System.load(tmpFile.getAbsolutePath());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param type
	 * @return a library of the given type mapped to native functions
	 */
	public static <T extends Library> T load(Class<T> type) {
		return (T) Native.loadLibrary(LIBRARY_NAME, type);
	}

}
