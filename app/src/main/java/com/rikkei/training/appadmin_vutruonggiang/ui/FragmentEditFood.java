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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rikkei.training.appadmin_vutruonggiang.R;
import com.rikkei.training.appadmin_vutruonggiang.modle.Food;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentEditFood extends Fragment {

    private View view;
    String id = "", idNh = "", name = "", detail = "", type = "", url = "";
    Float price = 0f;
    private ImageView imgBack;
    private TextView tvSave;
    private ImageView imgCamera;
    private ImageView imgSquare;
    private EditText edId;
    private EditText edName;
    private EditText edDetail;
    private EditText edPrice;
    MainActivity mainActivity;
    private final int REQUEST_CODE_GALLERY = 123;
    private final int REQUEST_CODE_CAMERA = 125;
    private Uri imageUri;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference riversRef;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    String urlNew = "";
    List<String> idFoodList = new ArrayList<>();
    String idRes="";

    public static Fragment newInstance() {

        Bundle args = new Bundle();

        FragmentEditFood fragment = new FragmentEditFood();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_food, container, false);
        init();
        databaseReference = firebaseDatabase.getReference().child("food");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        riversRef = storageReference.child("imagesFood/");
        getData();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentListFood fragmentHome = new FragmentListFood();
                Bundle bundle = new Bundle();
                bundle.putString("IdRes", idRes);
                fragmentHome.setArguments(bundle);
                mainActivity.getFragment(fragmentHome);
            }
        });

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idFood = edId.getText().toString().trim();
                String nameFood = edName.getText().toString().trim();
                String detailFood = edDetail.getText().toString().trim();
                String priceFood = edPrice.getText().toString().trim();
                if (!edId.isEnabled())
                    if (idFoodList.contains(idFood)) {
                        Toast.makeText(mainActivity, "ID already exists", Toast.LENGTH_SHORT).show();
                        return;
                    }
                if (idFood.equals("") || nameFood.equals("") || detailFood.equals("") || priceFood.equals("")) {
                    updateImg(imageUri);
                    if (urlNew.equals("")) {

                    } else {

                    }
                } else {
                    Toast.makeText(mainActivity, "You need to enter enough information", Toast.LENGTH_SHORT).show();
                }
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

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null && data.getExtras() != null) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            imgSquare.setImageBitmap(bitmap);
            imageUri = saveImageCamera(bitmap);
        }
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imgSquare.setImageURI(imageUri);
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

    public void updateImg(Uri uri) {
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
        File path = new File(fileCamera, idNh + id + simpleDateFormat.format(calendar.getTime()));
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
        imgBack = view.findViewById(R.id.butBack);
        tvSave = view.findViewById(R.id.tvSave);
        imgCamera = view.findViewById(R.id.imgCamera);
        imgSquare = view.findViewById(R.id.imgFood);
        edId = view.findViewById(R.id.edIdFood);
        edName = view.findViewById(R.id.edNameFood);
        edDetail = view.findViewById(R.id.edDetail);
        edPrice = view.findViewById(R.id.edPriceFood);
    }

    public void getData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            url = bundle.getString("url", "");
            name = name + bundle.getString("nameFood", "");
            idNh = idNh + bundle.getString("idRes", "");
            id = id + bundle.getString("id", "");
            type = type + bundle.getString("type", "");
            detail = type + bundle.getString("detailFood", "");
            price = price + bundle.getFloat("price", 0);
            idFoodList = bundle.getStringArrayList("listIDFood");
            idRes=idRes+bundle.getString("IdRes","");
            if (!id.equals("")) {
                edId.setText(id);
                edId.setEnabled(false);
            }
            if (!name.equals("")) {
                edName.setText(name);
            }
            if (!detail.equals("")) {
                edDetail.setText(detail);
            }
            if (price != 0) {
                edPrice.setText(customFormat("###.###", price));
            }
            if (!url.equals("")) {
                Glide.with(mainActivity).load(url).into(imgSquare);
            }
        }
    }

    public String customFormat(String pattern, float value) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(value);
        return output;
    }
}
