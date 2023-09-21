package com.demo.barcodeandqr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class FragmentThree extends Fragment {
    ImageView qrCodeImageView, logoImageView;
    Bitmap qrCodeBitmap, logoBitmap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_three, container, false);

        qrCodeImageView = view.findViewById(R.id.qrCodeImageView);
        logoImageView = view.findViewById(R.id.logoImageView); // Replace with your logo image view
        logoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wifi32);

        // Generate the QR code content (e.g., a URL)
        String qrCodeContent = "123456789";

        // Generate the QR code bitmap

        try {
            qrCodeBitmap = generateQRCode(qrCodeContent);
            //qrCodeImageView.setImageBitmap(qrCodeBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        if(logoBitmap == null){
            Log.e("Bitmap Error", "logo bitmap is null");
        }
        if(qrCodeBitmap == null){
            Log.e("Bitmap Error", "QR bitmap is null");
        }
        if (qrCodeBitmap != null && logoBitmap != null) {
            // Overlay the logo on the QR code
            Bitmap combinedBitmap = overlayLogoOnQRCode(qrCodeBitmap, logoBitmap);

            // Display the combined image in an ImageView
            qrCodeImageView.setImageBitmap(combinedBitmap);
        } else {
            Log.e("Bitmap Error", "QR code or logo bitmap is null");
        }

        return view;
    }
    private Bitmap generateQRCode(String content) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 512, 512);

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bitmap.setPixel(x, y, bitMatrix.get(x, y) ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white));
            }
        }

        return bitmap;
    }
    private Bitmap overlayLogoOnQRCode(Bitmap qrCodeBitmap, Bitmap logoBitmap) {
        int qrCodeWidth = qrCodeBitmap.getWidth();
        int qrCodeHeight = qrCodeBitmap.getHeight();
        int logoWidth = logoBitmap.getWidth();
        int logoHeight = logoBitmap.getHeight();

        // Calculate the position to center the logo on the QR code
        int left = (qrCodeWidth - logoWidth) / 2;
        int top = (qrCodeHeight - logoHeight) / 2;

        // Create a new Bitmap to hold the QR code with the logo
        Bitmap combinedBitmap = Bitmap.createBitmap(qrCodeWidth, qrCodeHeight, qrCodeBitmap.getConfig());

        // Create a Canvas and draw the QR code on it
        Canvas canvas = new Canvas(combinedBitmap);
        canvas.drawBitmap(qrCodeBitmap, 0, 0, null);

        // Draw the logo on top of the QR code
        canvas.drawBitmap(logoBitmap, left, top, null);

        return combinedBitmap;
    }

}
