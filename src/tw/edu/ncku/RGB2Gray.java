package tw.edu.ncku;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class RGB2Gray {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	public static void main(String[] args) {
		Mat src = Imgcodecs.imread("C://image//image_001.jpg");
		Mat dst = new Mat(src.rows(),src.cols(),src.type());
		Imgproc.cvtColor(src, dst, Imgproc.COLOR_RGB2GRAY);
		Imgcodecs.imwrite("C://image//image_001_1.jpg", dst);

	}

}
