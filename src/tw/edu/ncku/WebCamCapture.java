package tw.edu.ncku;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class WebCamCapture {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private JFrame frame;
	private JLabel imageLabel;

	public static void main(String[] args) {
		WebCamCapture app = new WebCamCapture();
		app.initGUI();
		app.runMainLoop(args);
	}

	private void initGUI() {
		frame = new JFrame("Camera Input Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640, 480);
		imageLabel = new JLabel();
		frame.add(imageLabel);
		frame.setVisible(true);
	}

	private void runMainLoop(String[] args) {
		ImageProcessor imageProcessor = new ImageProcessor();
		Mat webcamMatImage = new Mat();
		Image tempImage;
		VideoCapture capture = new VideoCapture(0);
		capture.set(Videoio.CAP_PROP_FRAME_WIDTH, 640);
		capture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);
		if (capture.isOpened()) {
			while (true) {
				capture.read(webcamMatImage);

				if (!webcamMatImage.empty()) {
					tempImage = imageProcessor.toBufferedImage(webcamMatImage);
					ImageIcon imageIcon = new ImageIcon(tempImage, "Captured video");
					imageLabel.setIcon(imageIcon);
					frame.pack();
				} else {
					System.out.println("Frame not capture");
					break;
				}
			}
		} else {
			System.out.println("Can't not open capture");
		}
	}

	public class ImageProcessor {

		public BufferedImage toBufferedImage(Mat matrix) {
			int type = BufferedImage.TYPE_BYTE_GRAY;
			if (matrix.channels() > 1) {
				type = BufferedImage.TYPE_3BYTE_BGR;
			}
			int bufferSize = matrix.channels() * matrix.cols() * matrix.rows();
			byte[] buffer = new byte[bufferSize];
			matrix.get(0, 0, buffer); // get all the pixels
			BufferedImage image = new BufferedImage(matrix.cols(), matrix.rows(), type);
			final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
			System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);
			return image;
		}

		public Mat blur(Mat input, int numberOfTimes) {
			Mat sourceImage = new Mat();
			Mat destImage = input.clone();
			for (int i = 0; i < numberOfTimes; i++) {
				sourceImage = destImage.clone();
				Imgproc.blur(sourceImage, destImage, new Size(3.0, 3.0));
			}
			return destImage;
		}
	}

}