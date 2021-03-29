package com.example.shark.view.ui.activiteis;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.shark.R;
import com.example.shark.services.ImagePickerActivity;
import com.example.shark.services.Utils;
import com.example.shark.services.Validation;
import com.example.shark.view.ui.BaseActivity;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;
import com.santalu.maskedittext.MaskEditText;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsInfosActivity extends BaseActivity {


    public static final int REQUEST_IMAGE = 100;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.phone)
    MaskEditText phone;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.cpf)
    MaskEditText cpf;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.SettingsInfos)
    CoordinatorLayout SettingsInfos;

    private ParseFile file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Utils.setupUI(this, findViewById(R.id.SettingsInfos));
        setInformation();

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_settings_infos;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @OnClick({R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                AlertDialog alertDialog = Utils.showConfirmAlert(this,
                        "Alerta",
                        "Deseja mesmo alterar suas informações pessoais?");
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(view2 -> {
                    alertDialog.dismiss();
                });


                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view2 -> {
                    if (Validation.isValidUserName(this, name.getText().toString())) {
                        if (Validation.isValidPhoneNumber(this, Objects.requireNonNull(phone.getRawText()))) {
                            if (Validation.isValidEmail(this, username.getText().toString())) {
                                if (Validation.isValidCPF(this, Objects.requireNonNull(cpf.getRawText()))) {
                                    alertDialog.dismiss();
                                    loading.setVisibility(View.VISIBLE);
                                    Toast.makeText(getBaseContext(), "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        }
                    }
                    alertDialog.dismiss();
                });
                break;
        }
    }

    public void setInformation() {
        name.setText(String.format("%s", "Shark Tester"));
        username.setText(String.format("%s", "shark.tester@shark.com.br"));
        address.setText(String.format("%s", "Avenina Shark"));
        phone.setText(String.format("%s", "31933399999"));
        cpf.setText(String.format("%s", "98783376003"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    bitmap = transform(bitmap);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    bitmap.recycle();

                    file = new ParseFile("profile.jpg", byteArray);
                    file.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                        }
                    });


                    // loading profile image from local cache
                    loadProfile(uri.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        setInformation();
    }

    private void loadProfile(String url) {

        Glide.with(this).load(url)
                .into(imageView);
        imageView.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }

    @OnClick(R.id.imageView)
    public void onViewClicked() {
        showImagePickerOptions();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }
        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
        Canvas canvas = new Canvas(bitmap);
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG);
        paint.setShader(shader);
        float r = size / 2f;
        canvas.drawCircle(r, r, r - 1, paint);
        // Make the thin border:
        Paint paintBorder = new Paint();
        paintBorder.setStyle(Paint.Style.STROKE);
        paintBorder.setColor(Color.argb(84, 0, 0, 0));
        paintBorder.setAntiAlias(true);
        paintBorder.setStrokeWidth(1);
        canvas.drawCircle(r, r, r - 1, paintBorder);

        squaredBitmap.recycle();
        return bitmap;
    }
}