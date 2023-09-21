package com.demo.barcodeandqr;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;

public class FragmentTwo extends Fragment {
    ImageView barcodeImageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_two, container, false);

        barcodeImageView = view.findViewById(R.id.barcodeImageView);

        // Generate the barcode content (e.g., a product code)
        String barcodeContent = "123456789";

        // Generate the barcode bitmap
        try {
            Bitmap barcodeBitmap = generateBarcode(barcodeContent);
            barcodeImageView.setImageBitmap(barcodeBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }


        return view;
    }
    private Bitmap generateBarcode(String content) throws WriterException {
        Code128Writer code128Writer = new Code128Writer();
        BitMatrix bitMatrix = code128Writer.encode(content, BarcodeFormat.CODE_128, 512, 256);

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
}
