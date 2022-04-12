package com.rikkei.training.appadmin_vutruonggiang.ui;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rikkei.training.appadmin_vutruonggiang.R;
import com.rikkei.training.appadmin_vutruonggiang.modle.NhaHang;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentEditRestaurant extends Fragment {

    private View view;
    private ImageView imgUrl;
    private EditText edNameRes;
    private EditText edAddressRes;
    private EditText edIdRes;
    private TextView tvListFood;
    private TextView tvSave;
    private NumberPicker hourOpen, minuteOpen, hourClose, minuteClose;
    private MainActivity mainActivity;
    private ImageView imgCamera, imgBack;
    private final int REQUEST_CODE_GALLERY = 123;
    private final int REQUEST_CODE_CAMERA = 125;
    private Uri imageUri;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference riversRef;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    String idRes = "";
    String url = "";
    String name = "", address = "", open = "", close = "";
    String urlNew = "";
    List<String> idResList = new ArrayList<>();

    public static Fragment newInstance() {

        Bundle args = new Bundle();

        FragmentEditRestaurant fragment = new FragmentEditRestaurant();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragamnet_edit_restaurant, container, false);
        init();
        databaseReference = firebaseDatabase.getReference().child("nhaHang");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        riversRef = storageReference.child("imagesRes/");

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameRes = edNameRes.getText().toString();
                String addressRes = edAddressRes.getText().toString();
                String id = edIdRes.getText().toString();
                if (!edIdRes.isEnabled())
                    if (idResList.contains(id)) {
                        Toast.makeText(mainActivity, "ID already exists", Toast.LENGTH_SHORT).show();
                        return;
                    }
                if (nameRes.trim().equals("") || addressRes.trim().equals("") || id.trim().equals("")) {
                    String openRes = hourOpen.getValue() + ":" + minuteOpen.getValue();
                    String closeRes = hourClose.getValue() + ":" + minuteClose.getValue();
                    updateAvt(imageUri);
                    if (urlNew.equals("")) {
                        NhaHang nhaHang = new NhaHang(idRes, addressRes, nameRes, url, openRes, closeRes, 5, 0, 0);
                        databaseReference.child(idRes).setValue(nhaHang);
                    } else {
                        NhaHang nhaHang = new NhaHang(idRes, addressRes, nameRes, urlNew, openRes, closeRes, 5, 0, 0);
                        databaseReference.child(idRes).setValue(nhaHang);
                    }
                    Toast.makeText(mainActivity, "Updated!!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mainActivity, "Fail Updated!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getFragment(FragmentListFood.newInstance());
            }
        });
        tvListFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getFragment(FragmentListFood.newInstance());
            }
        });
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(mainActivity).create();
                alertDialog.setTitle("Allow us to access your device");
                alertDialog.setIcon(R.drawable.camera);
                alertDialog.setButton2("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        aksPermissionAndCapture();
                        openGallery();
                    }
                });
                alertDialog.setButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        aksPermissionAndCamera();
                        openCamera();
                    }
                });
                alertDialog.show();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null && data.getExtras() != null) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            imgUrl.setImageBitmap(bitmap);
            imageUri = saveImageCamera(bitmap);
        }
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imgUrl.setImageURI(imageUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_CAMERA: {
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(mainActivity, "Permission Denied", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case REQUEST_CODE_GALLERY: {
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    Toast.makeText(mainActivity, "Permission Denied", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    public void aksPermissionAndCapture() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        } else {
            int readPermission = ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.READ_EXTERNAL_STORAGE);
            int writePermission = ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
                mainActivity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY);
            }
        }
    }

    public void aksPermissionAndCamera() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        } else {
            int camera = ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.CAMERA);
            if (camera != PackageManager.PERMISSION_GRANTED) {
                mainActivity.requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_CAMERA);
            }
        }
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    public void updateAvt(Uri uri) {
        riversRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //databaseReference.child(idRes).child("url").setValue(uri.toString());
                        urlNew = urlNew + uri.toString();
                    }
                });
            }
        });
    }

    public Uri saveImageCamera(Bitmap bitmap) {
        ContextWrapper contextWrapper = new ContextWrapper(mainActivity.getApplicationContext());
        File fileCamera = contextWrapper.getDir("pathCamera", Context.MODE_PRIVATE);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy/hh/mm/ss");
        Calendar calendar = Calendar.getInstance();
        File path = new File(fileCamera, idRes + simpleDateFormat.format(calendar.getTime()));
        try {
            FileOutputStream fileOS = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOS);
            fileOS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Uri.fromFile(path);
    }

    public void init() {
        mainActivity = (MainActivity) getActivity();
        imgUrl = view.findViewById(R.id.imgRes);
        imgCamera = view.findViewById(R.id.imgCamera);
        tvListFood = view.findViewById(R.id.tvListFoodRestaurant);
        tvSave = view.findViewById(R.id.tvSaveRestaurant);
        imgBack = view.findViewById(R.id.butBack);
        edNameRes = view.findViewById(R.id.edName);
        edAddressRes = view.findViewById(R.id.edAddressRes);
        hourOpen = view.findViewById(R.id.NumPickerHourOpen);
        minuteOpen = view.findViewById(R.id.NumPickerMinOpen);
        hourClose = view.findViewById(R.id.NumPickerHourClose);
        minuteClose = view.findViewById(R.id.NumPickerMinClose);
        edIdRes = view.findViewById(R.id.edIdRes);

        hourOpen.setMinValue(0);
        hourOpen.setMaxValue(23);
        minuteOpen.setMinValue(0);
        minuteOpen.setMaxValue(59);
        hourClose.setMinValue(0);
        hourClose.setMaxValue(23);
        minuteClose.setMinValue(0);
        minuteClose.setMaxValue(59);
        getData();
    }

    public void getData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            url = bundle.getString("url", "");
            name = name + bundle.getString("nameRes", "");
            address = address + bundle.getString("addressRes", "");
            open = open + bundle.getString("open", "");
            close = close + bundle.getString("close", "");
            idRes = idRes + bundle.getString("idRes", "");
            idResList = bundle.getStringArrayList("listIDRes");

            if (!idRes.equals("")) {
                edIdRes.setText(idRes);
                edIdRes.setEnabled(false);
            }
            if (!url.equals("")) {
                Glide.with(mainActivity).load(url).into(imgUrl);
            }
            if (!name.equals("")) {
                edNameRes.setText(name);
            }
            if (!address.equals("")) {
                edAddressRes.setText(address);
            }
            if (!open.equals("")) {
                String[] array = open.split(":");
                hourOpen.setValue(Integer.parseInt(array[0]));
                minuteOpen.setValue(Integer.parseInt(array[1]));
            }
            if (!close.equals("")) {
                String[] array = close.split(":");
                hourClose.setValue(Integer.parseInt(array[0]));
                minuteClose.setValue(Integer.parseInt(array[1]));
            }
        }
    }
}
