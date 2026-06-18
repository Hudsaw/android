package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class CameraActivity extends AppCompatActivity {
    ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    PreviewView pView;
    ImageButton imgBtn;
    Button btnCapture, btnSave, btnBack;
    ImageView imgCaptured;

    private ImageCapture imageCapture;
    private CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
    private Bitmap photoBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        pView = findViewById(R.id.pView);
        btnCapture = findViewById(R.id.btnCapture);
        imgBtn = findViewById(R.id.imgBtn);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
        imgCaptured = findViewById(R.id.imgCaptured);

        btnBack.setOnClickListener(v -> finish());
        imgBtn.setOnClickListener(v -> trocaCamera());
        btnCapture.setOnClickListener(v -> takePicture());
        btnSave.setOnClickListener(v -> savePicture());

        if (checkAndRequestPermission()) {
            cameraProviderFuture = ProcessCameraProvider.getInstance(this);
            cameraProviderFuture.addListener(() -> {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindPreview(cameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Erro ao iniciar câmera", Toast.LENGTH_SHORT).show();
                }
            }, ContextCompat.getMainExecutor(this));
        }
    }

    private void bindPreview(ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();

        Preview preview = new Preview.Builder().build();
        preview.setSurfaceProvider(pView.getSurfaceProvider());

        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();

        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
    }

    private void trocaCamera() {
        if (cameraSelector.equals(CameraSelector.DEFAULT_BACK_CAMERA)) {
            cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA;
        } else {
            cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
        }

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
                Toast.makeText(this, "Câmera trocada", Toast.LENGTH_SHORT).show();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void takePicture() {
        if (imageCapture == null) {
            Toast.makeText(this, "Câmera não disponível", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(getExternalCacheDir(), "temp_" + System.currentTimeMillis() + ".jpg");
        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(file).build();

        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                photoBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

                if (photoBitmap != null) {
                    android.graphics.Matrix matrix = new android.graphics.Matrix();
                    matrix.postRotate(90);
                    photoBitmap = Bitmap.createBitmap(photoBitmap, 0, 0,
                            photoBitmap.getWidth(), photoBitmap.getHeight(), matrix, true);

                    runOnUiThread(() -> {
                        imgCaptured.setImageBitmap(photoBitmap);
                        imgCaptured.setVisibility(View.VISIBLE);
                        pView.setVisibility(View.GONE);
                        btnCapture.setVisibility(View.GONE);
                        btnSave.setVisibility(View.VISIBLE);
                    });
                }
                file.delete();
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                runOnUiThread(() ->
                        Toast.makeText(CameraActivity.this, "Erro ao capturar: " + exception.getMessage(), Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    private void savePicture() {
        if (photoBitmap == null) {
            Toast.makeText(this, "Nenhuma foto para salvar", Toast.LENGTH_SHORT).show();
            return;
        }

        android.provider.MediaStore.Images.Media.insertImage(
                getContentResolver(),
                photoBitmap,
                "foto_" + System.currentTimeMillis() + ".jpg",
                "Foto salva"
        );

        Toast.makeText(this, "Foto salva!", Toast.LENGTH_SHORT).show();

        imgCaptured.setVisibility(View.GONE);
        pView.setVisibility(View.VISIBLE);
        btnCapture.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.GONE);
        photoBitmap = null;
    }

    public boolean checkAndRequestPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            cameraProviderFuture = ProcessCameraProvider.getInstance(this);
            cameraProviderFuture.addListener(() -> {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindPreview(cameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }, ContextCompat.getMainExecutor(this));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cameraProviderFuture != null && cameraProviderFuture.isDone()) {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                cameraProvider.unbindAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}