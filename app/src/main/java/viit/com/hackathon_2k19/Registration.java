package viit.com.hackathon_2k19;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import viit.com.hackathon_2k19.Modal_class.Store_details;

public class Registration extends AppCompatActivity {

    private EditText ed_name,ed_email,ed_phno;
    //private TextView ed_phno;
    private String st_name,st_phno,st_email,download_url;
    private CircleImageView cir_pro_img;
    private ImageView add_img;
    private Button btn_reg,cam,gal,cancel;
    private final static int PICK_IMAGE_REQUEST=70,CAMERA_REQUEST_CODE=1;
    private Bitmap bmp;
    private byte[] bitmap_data;
    private ProgressDialog pd;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private TextView app_nme;
    private Typeface typeface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
//        typeface=Typeface.createFromAsset(getAssets(), "C:\\Users\\VEDANT\\AndroidStudioProjects\\Hackathon_2k19\\app\\src\\main\\assets\\fonts\\amatic.ttf");
//        app_nme=findViewById(R.id.app_nm);
//        app_nme.setTypeface(typeface);


        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();


        ed_name=findViewById(R.id.edit_name);
        ed_email=findViewById(R.id.edit_email_id);
        try
        {
            st_phno=this.getIntent().getExtras().getString("pno");
            ed_phno=findViewById(R.id.edit_phno);
            ed_phno.setText(st_phno);
        }
        catch(Exception e)
        {
            Toast.makeText(this, "error.."+e, Toast.LENGTH_SHORT).show();
            Log.d("error",""+e);
        }

        cir_pro_img=findViewById(R.id.profile_img);
        add_img=findViewById(R.id.upload_profile_img);
        btn_reg=findViewById(R.id.btn_register);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                st_email=ed_email.getText().toString().trim();
                st_name=ed_name.getText().toString().trim();
                if(st_name.isEmpty() || st_email.isEmpty())
                {
                    Toast.makeText(Registration.this, "Please enter the all details.", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(download_url!=null)
                    {
                        Store_details obj=new Store_details(download_url,st_name,st_phno,st_email);
                        databaseReference.child(user.getUid()).child("user_info").setValue(obj);
                        startActivity(new Intent(Registration.this,NavigationDrawer.class));
                        finish();
                    }
                    else if(download_url==null)
                    {
                        Store_details obj=new Store_details("none",st_name,st_phno,st_email);
                        databaseReference.child(user.getUid()).child("user_info").setValue(obj);
                        startActivity(new Intent(Registration.this,NavigationDrawer.class));
                        finish();
                    }
                }
            }
        });

        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater=(LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View show_option=layoutInflater.inflate(R.layout.show_select_option,null);
                cam=show_option.findViewById(R.id.btn_select_camera);
                gal=show_option.findViewById(R.id.btn_select_gallery);
                cancel=show_option.findViewById(R.id.btn_cancel_select);
                AlertDialog.Builder builder=new AlertDialog.Builder(Registration.this);
                builder.setView(show_option);
                final AlertDialog dialog=builder.create();
                dialog.setCancelable(false);
                dialog.show();
                cam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Registration.this, "Camera Selected.", Toast.LENGTH_SHORT).show();
                        open_cammera();
                        dialog.dismiss();
                    }
                });
                gal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Registration.this, "Gallery Selected.", Toast.LENGTH_SHORT).show();
                        open_gallery();
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        pd=new ProgressDialog(this);
        pd.setMessage("Please wait.....");
    }
    private void open_cammera()
    {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }
        else
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);

        }
    }
    private void open_gallery()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
                Uri uri = data.getData();
                cir_pro_img.setImageURI(uri);
                pd.show();
                if (uri == null)
                {
                    Toast.makeText(this, "Please select the image", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
                else {

                    StorageReference filepath = storageReference.child("profile_images").child(user.getUid()).child("profile");
                    filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            try {
                                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        download_url=uri.toString();
                                        pd.dismiss();
                                        Toast.makeText(Registration.this, "IMAGE UPLOAD DONE", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            } catch (Exception e) {
                                Toast.makeText(Registration.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
                                Log.w("hi", "" + e);
                            }
                        }
                    });
                }
            }
            if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
                Bundle bundle = data.getExtras();
                bmp = (Bitmap) bundle.get("data");
                Toast.makeText(Registration.this, "" + bmp, Toast.LENGTH_SHORT).show();
                pd.show();
                cir_pro_img.setImageBitmap(bmp);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                bitmap_data = baos.toByteArray();
                StorageReference filepath = storageReference.child("profile_images").child(user.getUid()).child("profile");
                filepath.putBytes(bitmap_data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                download_url=uri.toString();
                                pd.dismiss();
                                Toast.makeText(Registration.this, "IMAGE UPLOAD DONE", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        } catch (Exception e) {
            Toast.makeText(Registration.this, "" + e, Toast.LENGTH_LONG).show();
        }
    }
}
