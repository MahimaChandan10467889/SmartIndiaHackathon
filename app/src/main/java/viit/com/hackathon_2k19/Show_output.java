package viit.com.hackathon_2k19;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import viit.com.hackathon_2k19.Modal_class.Add_calci_data;

import static android.widget.Toast.LENGTH_SHORT;

public class Show_output extends AppCompatActivity {

    Button save_button,close;
    private static final int STORAGE_CODE = 1000;

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private int i;
    private ImageView ar_core;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_output);

        save_button = findViewById(R.id.save_as_pdf);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        close=findViewById(R.id.goto_navigation_bar);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Show_output.this,NavigationDrawer.class));
                finish();
            }
        });
        ar_core=findViewById(R.id.play_ar_core);
        ar_core.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Show_output.this,Show_bridge_design.class));
                finish();
            }
        });
        //sample
        pd=new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setTitle("Please Wait..");
        pd.setMessage("Processing your inputs");

        // handling button click
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // handling run time permission of the device
                pd.show();

                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    // system os > marshmallow, check permission is enabled or not
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == getPackageManager().PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions,STORAGE_CODE);
                    }
                    else {

                        // permission already granted
                        try {
                            savePDF();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }

                    }
                }
                else {
                    // no requirement to check run time permission
                    try {
                        savePDF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }
    private void savePDF() throws IOException, DocumentException {
        // create object of document class

        Document my_document = new Document();
        String input = "INPUTS";
        String output = "OUTPUT";

        // pdf file name
        String my_file_name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        // pdf file path
        String my_file_path = Environment.getExternalStorageDirectory() + "/" + "output.pdf";

        // String dest = "C:\\Users\\Mayur_Aitavadekar\\AndroidStudioProjects\\PdfDemo\\app\\src\\main\\res\\drawable\\a.jpg";


        try {

            PdfPTable input_table = new PdfPTable(new float[] {2,2});
            PdfPTable output_table = new PdfPTable(new float[] {2,2});

            // input table code
            input_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            input_table.addCell("Factors");
            input_table.addCell("Values");
            input_table.setHeaderRows(1);

            PdfPCell[] input_cells = input_table.getRow(0).getCells();
            for(int i=0;i<input_cells.length;i++) {
                input_cells[i].setBackgroundColor(BaseColor.GRAY);
            }

          /*  for(int i=0;i<5;i++) {
                input_table.addCell("this is the factor.");
                input_table.addCell("this is factor");
            }
*/
            for(int i=0;i<1;i++)
            {
                input_table.addCell("Length of bridge");
                input_table.addCell("100");
            }
            String value[]=new String[30];
            i=0;
            databaseReference.child(user.getUid()).child("calci_info").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Add_calci_data obj=dataSnapshot.getValue(Add_calci_data.class);

                    Toast.makeText(Show_output.this, ""+obj.catch_area, Toast.LENGTH_LONG).show();
                    for(int i=0;i<1;i++)
                    {
                        input_table.addCell("Length of bridge");
                        input_table.addCell(obj.length);
                    }

/*
                    input_table.addCell("Breadth of bridge");
                    input_table.addCell(obj.bridth);
                    input_table.addCell("Total Live Load on the bridge");
                    input_table.addCell(obj.live_load);
                    input_table.addCell("Catchment area");
                    input_table.addCell(obj.catch_area);
                    input_table.addCell("MIHFL");
                    input_table.addCell(obj.mihfl);
                    input_table.addCell("OFL");
                    input_table.addCell(obj.ofl);
                    input_table.addCell("OHFL");
                    input_table.addCell(obj.ohfl);
                    input_table.addCell("Bed Level at U/S");
                    input_table.addCell(obj.us);
                    input_table.addCell("Bed Levelst D/S");
                    input_table.addCell(obj.ds);
                    input_table.addCell("Bed Width");
                    input_table.addCell(obj.bed);
                    input_table.addCell("Bank Width");
                    input_table.addCell(obj.bank);
                    input_table.addCell("Rugosity Coefficent for Comp I");
                    input_table.addCell(obj.rug1);
                    input_table.addCell("Rugosity Coefficent for Comp II");
                    input_table.addCell(obj.rug2);
                    input_table.addCell("Rugosity Coefficent for Comp III");
                    input_table.addCell(obj.rug3);
                    input_table.addCell("Angle of skew");
                    input_table.addCell(obj.skew);
                    input_table.addCell("Seismic Zone");
                    input_table.addCell("Zone II");
                    input_table.addCell("Lowest Bed Level");
                    input_table.addCell(obj.low_bed);
                    input_table.addCell("Vertical Clearence");
                    input_table.addCell("1.5m");
                    input_table.addCell("Afflux");
                    input_table.addCell("0.300m");
                    input_table.addCell("Slab Thickness");
                    input_table.addCell(obj.slab);
                    input_table.addCell("Abutment left Batter");
                    input_table.addCell(obj.abt_left);
                    input_table.addCell("Abutment Right Batter");
                    input_table.addCell(obj.abt_right);
                    input_table.addCell("Wearing coat value");
                    input_table.addCell("0.075m");
                    input_table.addCell("Diameter of bed material particle");
                    input_table.addCell(obj.dia_soil);
                    input_table.addCell("Width of piers");
                    input_table.addCell(obj.width_pier);
                    input_table.addCell("Width of Abutment");
                    input_table.addCell(obj.width_abut);*/
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w("tmz", "Failed to read value.", databaseError.toException());
                }
            });



            // output table code
            output_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            output_table.addCell("Factors");
            output_table.addCell("Values");
            output_table.setHeaderRows(1);
            PdfPCell[] output_cells = input_table.getRow(0).getCells();
            for(int i=0;i<output_cells.length;i++) {
                output_cells[i].setBackgroundColor(BaseColor.GRAY);
            }

            for(int i=0;i<5;i++) {
               output_table.addCell("this is the factor.");
                output_table.addCell("this is the value");
            }

            PdfWriter.getInstance(my_document,new FileOutputStream(my_file_path));
            my_document.open();
            Paragraph p = new Paragraph();
            p.add("\tINPUTS");
            p.add("\n");
            p.setAlignment(Element.ALIGN_CENTER);
            my_document.add(p);
            my_document.add(input_table);
            Paragraph q = new Paragraph();
            q.add("\tOUTPUTS");
            q.add("\n");
            q.setAlignment(Element.ALIGN_CENTER);
            my_document.add(q);
            my_document.add(output_table);


            // adding images


            my_document.close();
            Log.d("tag","DONE.");
            // get text from edit text
                /*String temp_text = my_text.getText().toString();
                String temp_text_2 = my_text_2.getText().toString();
                // add author of the document
                my_document.addAuthor("mayur aitavadekar");  // add dynamically usng firebase UID

                // add paragraph to document
                my_document.add(new Paragraph(temp_text));
                my_document.add(new Paragraph(temp_text_2));

                // close document
                my_document.close(); */

            Toast.makeText(this,my_file_name+".pdf\nis saved to\n"+my_file_name, LENGTH_SHORT).show();
            pd.dismiss();
        }
        catch(Exception e){
            Toast.makeText(this, e.getMessage(), LENGTH_SHORT).show();
        }

    }


    // handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case STORAGE_CODE:
                if(grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                    // permission was granted using popup, call savePDF
                    try {
                        savePDF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    // permission was denied from pop, show error message
                    Log.d("tag","permission denied.");
                    Toast.makeText(this, "Permission Denied", LENGTH_SHORT).show();
                }
        }
    }
}
