/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ann.utility;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author seve
 */

public class ImageProcessing {

    public static BufferedImage createFromMatrix(double[][] matrix)
    {
        BufferedImage image = null;            
        try{
            Dimension imgDim = new Dimension(matrix[0].length, matrix.length);
            image = new BufferedImage(imgDim.width, imgDim.height, BufferedImage.TYPE_INT_ARGB);
            
        
            for(int y = 0; y < matrix.length; y++)
            {            
                int diagonaleA = y +1;
                int diagonaleB = matrix[y].length-y;
                int centroY = (int)(matrix.length/2);
                int centroX = (int)(matrix[y].length/2);
                for(int x = 0; x < matrix[y].length; x++)
                {
                    int pixel = (int)matrix[y][x];
                    int R, G, B;                    
                    if(pixel == 1)
                    {
                        float mul = (float)(Math.random() - 0.1)/5;
                        int rand = (int) (255 * mul);
                        if(rand > 200)
                            rand= 200;
                        if(rand < 0)
                            rand = 0;
                        R = rand; G = rand; B = rand;
                    }else{
                        R = 255; G = 255; B = 255;                        
                    }
                    Color newColor;
//                    if(centroY == y || centroX == x)
//                        newColor = new Color(0,0,B, 255);
//                    else                        
                        newColor = new Color(R,G,B, 255);

                    // applico il nuovo colore del pixel
                    image.setRGB(x, y, newColor.getRGB());
                }
            }
        }catch(Exception e)
        {
            throw e;
        }
        int fattoreMultX = 20;
        int fattoreMultY = 20;
        image = scale(
                image,
                BufferedImage.TYPE_BYTE_INDEXED,
                matrix[0].length*fattoreMultX,
                matrix.length*fattoreMultY,
                fattoreMultX,
                fattoreMultY
        );
        return image;
    }
    
    
    public static BufferedImage scale(BufferedImage sbi, int imageType, int dWidth, int dHeight, double fWidth, double fHeight) {
        BufferedImage dbi = null;
        if(sbi != null) {
            dbi = new BufferedImage(dWidth, dHeight, imageType);
            Graphics2D g = dbi.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
            g.drawRenderedImage(sbi, at);
        }
        
        Dimension imgDim = new Dimension(dbi.getWidth(), dbi.getHeight());
        Graphics2D g2d = dbi.createGraphics();
        g2d.setColor(Color.BLACK);
        BasicStroke bs = new BasicStroke(2);
        g2d.setStroke(bs);
        
        for(int y = 0; y < imgDim.height; y++)
        {
            // Diagonali
            g2d.drawLine(0, 0, imgDim.width-1,imgDim.height-1);
            g2d.drawLine(imgDim.width-1,0, 0, imgDim.height-1);
            int centroY = (int)(imgDim.height/2);
            int centroX = (int)(imgDim.width/2);
            g2d.setBackground(Color.BLUE);
            g2d.setColor(Color.BLUE);
            g2d.fillRect(centroX-10, centroY-10, 20, 20);
            g2d.setBackground(Color.BLUE);
            g2d.setColor(Color.RED);
            g2d.fillOval(centroX-10, centroY-10, 20, 20);
        }
            
        return dbi;
    }
    
    
    /**
     * LUMINOSITA'<br />
     * Aumenta decrementa i canali (ARGB) con value
     * @param src (Bitmap)
     * @param value (da 0 a 255)
     * @return Bitmap
     */	
    public static BufferedImage doBrightness(BufferedImage src, int value) {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();

        // color info
        int A, R, G, B;
        int pixel;

        // scan ogni pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getRGB(x, y);

                // filtro i 4 canali
                A = 0;
                R = (pixel>> 16) & 0x000000FF;
                G = (pixel>> 8) & 0x000000FF;
                B = (pixel) & 0x000000FF;

                // aumento/decremento ogni canale con value
                R += value;
                if(R > 255) { R = 255; }
                else if(R < 0) { R = 0; }

                G += value;
                if(G > 255) { G = 255; }
                else if(G < 0) { G = 0; }

                B += value;
                if(B > 255) { B = 255; }
                else if(B < 0) { B = 0; }

                Color newColor = new Color(R,G,B);

                // applico il nuovo colore del pixel
                src.setRGB(x, y, newColor.getRGB());
            }
        }

        // return bitmap modificata
        return src;
    }
	
	
//	public static Bitmap intercectColor(Bitmap src, int r, int g, int b) {
//		// constanti
//		final double GS_RED = 0.299;
//		final double GS_GREEN = 0.587;
//		final double GS_BLUE = 0.114;
//
//		// image size
//	    int width = src.getWidth();
//	    int height = src.getHeight();
//	    // output bitmap
//	    Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
//	    // color info
//	    int A, R, G, B;
//	    int pixel;
//	 
//	    // scan ogni pixels
//		for(int x = 0; x < width; ++x) {
//			for(int y = 0; y < height; ++y) {
//				// get pixel color
//	            pixel = src.getPixel(x, y);
//	            
//	            // filtro i 4 canali
//	            A = Color.alpha(pixel);
//	            R = Color.red(pixel);
//	            G = Color.green(pixel);
//	            B = Color.blue(pixel);
//	            int tolleranza = 30;
//				if(Math.abs(r-R) < tolleranza && Math.abs(g-G) < tolleranza && Math.abs(b-B) < tolleranza)
//				{
//					A=R=G=0;
//					B=255;
//				}
//
//	            // applico il nuovo colore del pixel
//				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
//			}
//		}
//
//	    // return bitmap modificata
//		return bmOut;
//	}
//	
//
//	public static Bitmap pixelInverter(Bitmap src) {
//		// constanti
//		final double GS_RED = 0.299;
//		final double GS_GREEN = 0.587;
//		final double GS_BLUE = 0.114;
//
//		// image size
//	    int width = src.getWidth();
//	    int height = src.getHeight();
//	    // output bitmap
//	    Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
//	    // color info
//	    int A, R, G, B;
//	    int pixel;
//	 
//	    // scan ogni pixels
//		for(int x = 0; x < width; ++x) {
//			for(int y = 0; y < height; ++y) {
//				// get pixel color
//	            pixel = src.getPixel(x, y);
//	            
//	            // filtro i 4 canali
//	            A = Color.alpha(pixel);
//	            R = Color.red(pixel);
//	            G = Color.green(pixel);
//	            B = Color.blue(pixel);
//	            
//				// applicao ai 3 canali lo stesso valore (grigio)
//	            // L'intensità di questo grigio sarà calcolata grazie alle costanti
//				R = 255-R;
//				G = 255-G;
//				B = 255-B;
//
//	            // applico il nuovo colore del pixel
//				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
//			}
//		}
//
//	    // return bitmap modificata
//		return bmOut;
//	}
//	
//	
//	/**
//	 *  SCALA DI GRIGI
//	 * @param src (Bitmap)
//	 * @return Bitmap
//	 */
//	
//	public static Bitmap doGreyscale(Bitmap src) {
//		// constanti
//		final double GS_RED = 0.299;
//		final double GS_GREEN = 0.587;
//		final double GS_BLUE = 0.114;
//
//		// image size
//	    int width = src.getWidth();
//	    int height = src.getHeight();
//	    // output bitmap
//	    Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
//	    // color info
//	    int A, R, G, B;
//	    int pixel;
//	 
//	    // scan ogni pixels
//		for(int x = 0; x < width; ++x) {
//			for(int y = 0; y < height; ++y) {
//				// get pixel color
//	            pixel = src.getPixel(x, y);
//	            
//	            // filtro i 4 canali
//	            A = Color.alpha(pixel);
//	            R = Color.red(pixel);
//	            G = Color.green(pixel);
//	            B = Color.blue(pixel);
//	            
//				// applicao ai 3 canali lo stesso valore (grigio)
//	            // L'intensità di questo grigio sarà calcolata grazie alle costanti
//				R = G = B = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);
//
//	            // applico il nuovo colore del pixel
//				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
//			}
//		}
//
//	    // return bitmap modificata
//		return bmOut;
//	}
//
//	
//	
//	
//	/**
//	 *  CONTRASTO
//	 * @param src (Bitmap)
//	 * @param value
//	 * @return Bitmap
//	 */
//	public static Bitmap createContrast(Bitmap src, double value) {
//	    // image size
//	    int width = src.getWidth();
//	    int height = src.getHeight();
//	    // create output bitmap
//	    Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
//	    // color information
//	    int A, R, G, B;
//	    int pixel;
//	    // get contrast value
//	    double contrast = Math.pow((100 + value) / 100, 2);
//	 
//	    // scan through all pixels
//	    for(int x = 0; x < width; ++x) {
//	        for(int y = 0; y < height; ++y) {
//	            // get pixel color
//	            pixel = src.getPixel(x, y);
//	            A = Color.alpha(pixel);
//	            // apply filter contrast for every channel R, G, B
//	            R = Color.red(pixel);
//	            R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
//	            if(R < 0) { R = 0; }
//	            else if(R > 255) { R = 255; }
//	 
//	            G = Color.green(pixel);
//	            G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
//	            if(G < 0) { G = 0; }
//	            else if(G > 255) { G = 255; }
//	 
//	            B = Color.blue(pixel);
//	            B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
//	            if(B < 0) { B = 0; }
//	            else if(B > 255) { B = 255; }
//	 
//	            // set new pixel color to output bitmap
//	            bmOut.setPixel(x, y, Color.argb(A, R, G, B));
//	        }
//	    }
//	 
//	    // return final image
//	    return bmOut;
//	}
//	
//	
//	
//	
//	
//	/**
//	 *  CONTRASTO SOLO SCALA DI GRIGI
//	 * @param src
//	 * @param value
//	 * @return
//	 */	
//	public static Bitmap createContrastB(Bitmap src, double value) {
//	    // image size
//	    int width = src.getWidth();
//	    int height = src.getHeight();
//	    // create output bitmap
//	    Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
//	    // color information
//	    int A, R, G, B;
//	    int pixel;
//	    // get contrast value
//	    double contrast = Math.pow((100 + value) / 100, 2);
//	 
//	    // scan through all pixels
//	    for(int x = 0; x < width; ++x) {
//	        for(int y = 0; y < height; ++y) {
//	            // get pixel color
//	            pixel = src.getPixel(x, y);
//	            A = Color.alpha(pixel);
//	            // apply filter contrast for every channel R, G, B
//	            R = Color.red(pixel);
//	            R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
//	            if(R < 0) { R = 0; }
//	            else if(R > 255) { R = 255; }
//	 
//	            G = Color.red(pixel);
//	            G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
//	            if(G < 0) { G = 0; }
//	            else if(G > 255) { G = 255; }
//	 
//	            B = Color.red(pixel);
//	            B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
//	            if(B < 0) { B = 0; }
//	            else if(B > 255) { B = 255; }
//	 
//	            // set new pixel color to output bitmap
//	            bmOut.setPixel(x, y, Color.argb(A, R, G, B));
//	        }
//	    }
//	 
//	    // return final image
//	    return bmOut;
//	}
//	
//	
//	
//	
//	/**
//	 *  EFFETTO SEPPIA
//	 * @param src (Bitmap)
//	 * @param depth
//	 * @param red
//	 * @param green
//	 * @param blue
//	 * @return Bitmap
//	 */
//	public static Bitmap createSepiaToningEffect(Bitmap src, int depth, double red, double green, double blue) {
//	    // image size
//	    int width = src.getWidth();
//	    int height = src.getHeight();
//	    // create output bitmap
//	    Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
//	    // constant grayscale
//	    final double GS_RED = 0.3;
//	    final double GS_GREEN = 0.59;
//	    final double GS_BLUE = 0.11;
//	    // color information
//	    int A, R, G, B;
//	    int pixel;
//	 
//	    // scan through all pixels
//	    for(int x = 0; x < width; ++x) {
//	        for(int y = 0; y < height; ++y) {
//	            // get pixel color
//	            pixel = src.getPixel(x, y);
//	            // get color on each channel
//	            A = Color.alpha(pixel);
//	            R = Color.red(pixel);
//	            G = Color.green(pixel);
//	            B = Color.blue(pixel);
//	            // apply grayscale sample
//	            B = G = R = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);
//	 
//	            // apply intensity level for sepid-toning on each channel
//	            R += (depth * red);
//	            if(R > 255) { R = 255; }
//	 
//	            G += (depth * green);
//	            if(G > 255) { G = 255; }
//	 
//	            B += (depth * blue);
//	            if(B > 255) { B = 255; }
//	 
//	            // set new pixel color to output image
//	            bmOut.setPixel(x, y, Color.argb(A, R, G, B));
//	        }
//	    }
//	 
//	    // return final image
//	    return bmOut;
//	}
//	
//	
//	
//
//	public static Bitmap bilanciamentoColore(Bitmap src, int depth, double red, double green, double blue) {
//	    // image size
//		int width = src.getWidth();
//	    int height = src.getHeight();
//	    // create output bitmap
//	    Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
//
//	    // constant grayscale
//	    final double GS_RED = 0.3;
//	    final double GS_GREEN = 0.59;
//	    final double GS_BLUE = 0.11;
//	    // color information
//	    double A, R, G, B;
//	    int pixel;
//	 
//	    // scan through all pixels
//	    for(int x = 0; x < width; ++x) {
//	        for(int y = 0; y < height; ++y) {
//	            // get pixel color
//	            pixel = src.getPixel(x, y);
//	            // get color on each channel
//	            A = Color.alpha(pixel);
//	    	    final double test = 0.9;
//	    	    
//	            R = ( Color.red(pixel)*test);
//	            G = ( Color.green(pixel)*test);
//	            B = ( Color.blue(pixel)*test);
//	            // apply grayscale sample
//	            //B = G = R = (GS_RED * R + GS_GREEN * G + GS_BLUE * B);
//	            
//	            // apply intensity level for sepid-toning on each channel
//	            R += (depth * red);
//	            if(R > 255) { R = 255; }
//	 
//	            G += (depth * green);
//	            if(G > 255) { G = 255; }
//	 
//	            B += (depth * blue);
//	            if(B > 255) { B = 255; }
//	    		
//	            // set new pixel color to output image
//	            bmOut.setPixel(x, y, Color.argb((int)A, (int)R, (int)G,(int) B));
//	        }
//	    }
//	 
//	    // return final image
//	    return bmOut;
//	}
//	
//	
//	
//	
//	public static Bitmap bilanciamentoColore2(Bitmap src, int depth, double red, double green, double blue) {
//	    // image size
//		 int width = src.getWidth();
//	    int height = src.getHeight();
//	    // create output bitmap
//	    Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
//	    // constant 
//	    final double R_CONST = 0.212671;
//		final double G_CONST = 0.71516;
//		final double B_CONST = 0.072169;		
//		
//
//		double valore = (((double)depth/100) + 1);
//		
//		double i = 1-valore;
//		
//
//		double R_CONV = R_CONST * i;
//		double G_CONV = G_CONST * i;
//		double B_CONV = B_CONST * i;
//		
//		
//		
//	    // color information
//		double A, R, G, B;
//	    int pixel;
//	 
//	    // scan through all pixels
//	    for(int x = 0; x < width; ++x) {
//	        for(int y = 0; y < height; ++y) {
//	            // get pixel color
//	            pixel = src.getPixel(x, y);
//	            // get color on each channel
//	            A = Color.alpha(pixel);
//
//
//	            
//				// applicao ai 3 canali 
//	            R =  ( (Color.red(pixel) * (R_CONV+red)	) + ( Color.green(pixel) * G_CONV)  + ( Color.blue(pixel) * B_CONV)   ) ; 
//	            G =  ((Color.red(pixel) * (R_CONV)	) + ( Color.green(pixel) * (G_CONV+green))  + ( Color.blue(pixel) * B_CONV)) ; 
//	            B =  ((Color.red(pixel) * (R_CONV)	) + ( Color.green(pixel) * G_CONV)  + ( Color.blue(pixel) * (B_CONV+blue))) ; 
//	    		
//	            // set new pixel color to output image
//	            bmOut.setPixel(x, y, Color.argb((int)A, (int)R, (int)G,(int) B));
//	        }
//	    }
//	 
//	    // return final image
//	    return bmOut;
//	}
//	
//	
//	public static Bitmap applySatyrazione(Bitmap src, int value)
//	{
//		final double R_CONST = 0.212671;
//		final double G_CONST = 0.71516;
//		final double B_CONST = 0.072169;		
//		
//
//		double valore = (((double)value/100) + 1);
//		
//		double i = 1-valore;
//		
//
//		double R_CONV = R_CONST * i;
//		double G_CONV = G_CONST * i;
//		double B_CONV = B_CONST * i;
//		
//		
//		// image size
//	    int width = src.getWidth();
//	    int height = src.getHeight();
//	    // output bitmap
//	    Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
//	    // color info
//	    double A, R, G, B;
//	    int pixel;
//	 
//	    // scan ogni pixels
//		for(int x = 0; x < width; ++x) {
//			for(int y = 0; y < height; ++y) {
//				// get pixel color
//	            pixel = src.getPixel(x, y);
//	            
//	            // filtro i 4 canali
//	            A = Color.alpha(pixel);
//	           // R = Color.red(pixel);
//	           // G = Color.green(pixel);
//	           // B = Color.blue(pixel);
//	            
//				// applicao ai 3 canali 
//	            R =  ( (Color.red(pixel) * (R_CONV+valore)	) + ( Color.green(pixel) * G_CONV)  + ( Color.blue(pixel) * B_CONV)   ) ; 
//	            G =  ((Color.red(pixel) * (R_CONV)	) + ( Color.green(pixel) * (G_CONV+valore))  + ( Color.blue(pixel) * B_CONV)) ; 
//	            B =  ((Color.red(pixel) * (R_CONV)	) + ( Color.green(pixel) * G_CONV)  + ( Color.blue(pixel) * (B_CONV+valore))) ; 
//	            
//	            //Log.d("COLORE"," r:"+R+" g: "+G+" b:"+B+" r:"+Color.red(pixel)+" g: "+Color.green(pixel)+" b:"+Color.blue(pixel)+" val: "+valore);
//	            
//	            
//	            R = (R < 0?0:(R > 255?255:R));
//	            B = (B < 0?0:(B > 255?255:B));
//	            G = (G < 0?0:(G > 255?255:G));
//	            
//	            
//	            // applico il nuovo colore del pixel
//				bmOut.setPixel(x, y, Color.argb((int)A, (int)R, (int)G, (int)B));
//			}
//		}
//
//	    // return bitmap modificata
//		return bmOut;
//		
//		
//	}
//	
//	/**
//	 * SATURAZIONE
//	 * @param source
//	 * @param level
//	 * @return
//	 */
//	public static Bitmap applySaturationFilter(Bitmap source, int level) {
//	    // get image size
//	    int width = source.getWidth();
//	    int height = source.getHeight();
//	    int[] pixels = new int[width * height];
//	    float[] HSV = new float[3];
//	    // get pixel array from source
//	    source.getPixels(pixels, 0, width, 0, 0, width, height);
//	 
//	    int index = 0;
//	    // iteration through pixels
//	    for(int y = 0; y < height; ++y) {
//	        for(int x = 0; x < width; ++x) {
//	            // get current index in 2D-matrix
//	            index = y * width + x;
//	            // convert to HSV
//	            Color.colorToHSV(pixels[index], HSV);
//	            // increase Saturation level
//	            HSV[1] *= level;
//	            HSV[1] = (float) Math.max(0.0, Math.min(HSV[1], 1.0));
//	            // take color back
//	            pixels[index] |= Color.HSVToColor(HSV);
//	        }
//	    }
//	    // output bitmap
//	    Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//	    bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
//	    return bmOut;
//	}
//
//
//	public static Bitmap rotateImage(Bitmap myImg, float gradi) {
//		Matrix matrix = new Matrix();
//		matrix.postRotate((float) gradi);
//
//		// Nuova immagine ruotata
//		Bitmap rotated = Bitmap.createBitmap(myImg, 0, 0, myImg.getWidth(),
//				myImg.getHeight(), matrix, true);
//
//		return rotated;
//	}
//
//	public static Bitmap cropCenter(Bitmap src, int width, int height)
//	{
//		// image size
//	    int w = src.getWidth();
//	    int h = src.getHeight();
//	    	    
//	    int sW;
//	    if(w > 0)
//	    	sW=(int)(w/2)-(width/2);
//	    else
//	    	sW=(int)(width/2);
//
//	    if(sW < 0)
//	    	sW=0;
//	    
//	    int sH;
//	    if(h > 0)
//	    	sH=(int)(h/2)-(height/2);
//	    else
//	    	sH=(height/2);
//
//	    if(sH < 0)
//	    	sH=0;
//	    
//	    
//	    Bitmap croppedBmp = src;
//	    if( (sW+width ) > w ) 
//    	{
//	    	Bitmap tempScale  = scaleImage(src, width);
//	    	croppedBmp = Bitmap.createBitmap(
//	    			tempScale, //
//					sW, //
//					sH, //
//					width, //
//					height); //
//    	}
//	    else{
//	    	croppedBmp = Bitmap.createBitmap(
//					src, //
//					sW, //
//					sH, //
//					width, //
//					height); //
//			
//	    }
//		return croppedBmp;
//			
//	}
//	
//	public static Bitmap scaleImage(Bitmap bitmap, int value)
//	{
//
//	    // Get current dimensions AND the desired bounding box
//	    int width = bitmap.getWidth();
//	    int height = bitmap.getHeight();
//	    int bounding = dpToPx(value);
//	    Log.i("Test", "original width = " + Integer.toString(width));
//	    Log.i("Test", "original height = " + Integer.toString(height));
//	    Log.i("Test", "bounding = " + Integer.toString(bounding));
//
//	    // Determine how much to scale: the dimension requiring less scaling is
//	    // closer to the its side. This way the image always stays inside your
//	    // bounding box AND either x/y axis touches it.  
//	    float xScale = ((float) bounding) / width;
//	    float yScale = ((float) bounding) / height;
//	    float scale = (xScale >= yScale) ? xScale : yScale;
//	    Log.i("Test", "xScale = " + Float.toString(xScale));
//	    Log.i("Test", "yScale = " + Float.toString(yScale));
//	    Log.i("Test", "scale = " + Float.toString(scale));
//
//	    // Create a matrix for the scaling and add the scaling data
//	    Matrix matrix = new Matrix();
//	    matrix.postScale(scale, scale);
//	    
//
//	    // Create a new bitmap and convert it to a format understood by the ImageView 
//	    Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
//	  
//
//	    // Apply the scaled bitmap
//	    return scaledBitmap;
//	}
//
//	private static int dpToPx(int dp)
//	{
//	    return Math.round((float)dp * 1);
//	}

}