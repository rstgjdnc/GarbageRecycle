package tw.edu.ncku;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class RGB2Canny {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
		Mat src = Imgcodecs.imread("D://ComputerVision//image//image_002.jpg");
		Mat gray = new Mat(src.rows(), src.cols(), src.type());
		Imgproc.cvtColor(src, gray, Imgproc.COLOR_RGB2GRAY);
		Mat blur = new Mat();
		Imgproc.GaussianBlur(gray, blur, new Size(3, 3), 0);
//		Mat edge = new Mat();
//		Imgproc.Canny(blur, edge, 100, 200);
		Imgcodecs.imwrite("D://ComputerVision//image//image_002blur.jpg", blur);

	}

}
