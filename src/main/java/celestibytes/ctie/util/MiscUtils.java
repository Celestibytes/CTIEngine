package celestibytes.ctie.util;

import java.nio.ByteOrder;

public class MiscUtils {
	
	public static byte floatColorToByte(float c) {
		return (byte)(c*255);
	}
	
	public static byte[] floatToBytes(float f) {
		byte[] ret = new byte[4];
		int flt = Float.floatToIntBits(f);
		
		if(ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
			ret[0] = (byte) (flt & 255);
			ret[1] = (byte) ((flt >> 8) & 255);
			ret[2] = (byte) ((flt >> 16) & 255);
			ret[3] = (byte) ((flt >> 24) & 255);
		} else {
			ret[3] = (byte) (flt & 255);
			ret[2] = (byte) ((flt >> 8) & 255);
			ret[1] = (byte) ((flt >> 16) & 255);
			ret[0] = (byte) ((flt >> 24) & 255);
		}
		return ret;
	}
	
	public static void floatToBytes(float f, byte[] arr) {
		if(arr == null || arr.length != 4) {
			return;
		}
		int flt = Float.floatToIntBits(f); // Not sure if needed
		
		if(ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
			arr[0] = (byte) (flt & 255);
			arr[1] = (byte) ((flt >> 8) & 255);
			arr[2] = (byte) ((flt >> 16) & 255);
			arr[3] = (byte) ((flt >> 24) & 255);
		} else {
			arr[3] = (byte) (flt & 255);
			arr[2] = (byte) ((flt >> 8) & 255);
			arr[1] = (byte) ((flt >> 16) & 255);
			arr[0] = (byte) ((flt >> 24) & 255);
		}
	}
}
