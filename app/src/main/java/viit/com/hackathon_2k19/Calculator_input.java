package viit.com.hackathon_2k19;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.util.Strings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import viit.com.hackathon_2k19.Modal_class.Add_calci_data;
import viit.com.hackathon_2k19.Modal_class.Add_chainage_value;
import viit.com.hackathon_2k19.Modal_class.Fetch_final_output;
import viit.com.hackathon_2k19.Modal_class.Fetch_flood;


public class Calculator_input extends AppCompatActivity {

    private DatabaseReference databaseReference;
    public Fetch fetch_obj_mihfl_c1[]=new Fetch[100];
    public Fetch fetch_obj_mihfl_c2[]=new Fetch[100];
    public Fetch fetch_obj_mihfl_c3[]=new Fetch[100];

    public Fetch fetch_obj_ofl_c1[]=new Fetch[100];
    public Fetch fetch_obj_ofl_c2[]=new Fetch[100];
    public Fetch fetch_obj_ofl_c3[]=new Fetch[100];

    public Fetch fetch_obj_ohfl_c1[]=new Fetch[100];
    public Fetch fetch_obj_ohfl_c2[]=new Fetch[100];
    public Fetch fetch_obj_ohfl_c3[]=new Fetch[100];
    private FirebaseAuth auth;
    private FirebaseUser user;
    String[] sesmic_zone_value={"2","3","4"};
    //String[] chainage_value={"1","2","3","4","5","6","7","8","9","10"};
    String[] flood_value={"Choose one","MIHFL","OHFL","OFL"};
    String[] compartment={"Compartment 1","Compartment 2","Compartment 3"};
    private EditText et_chain_number,et_catch_area,et_mihfl,et_ofl,et_ohfl,et_us,et_ds,et_rug_soe,et_bed,et_bank,et_skew,et_low_bed,et_abt_left,et_abt_right,et_slab,et_dia_soil,et_width_pier,et_width_abut,et_length,et_bridth,et_live_load,et_compartment,et_gl,et_cv,et_compartment2,et_compartment3,et_flood_depth;
    private Spinner sp_compartment,sp_sessmic,sp_flood_value;
    private String OHFL,OFL,dm,rug1,rug2,rug3,MIHFL,catch_a,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15,s16,s17,s18,s19,s20,s21,s22,s23,s24,s25,s26,st_compartment;
    private int counter,cn,i,size;
    private Button calci,add,add_cv,add_flood;
    private Double us,ds;
    private int cnt[]=new int[3];
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_input);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        et_catch_area=findViewById(R.id.edit_catchment_area);
        et_mihfl=findViewById(R.id.edit_MIHFL);
        et_ofl=findViewById(R.id.edit_OFL);
        et_ohfl=findViewById(R.id.edit_OHFL);
        et_us=findViewById(R.id.edit_us);
        et_ds=findViewById(R.id.edit_ds);

        et_bed=findViewById(R.id.edit_bed_width);
        et_bank=findViewById(R.id.edit_bank_width);
        et_skew=findViewById(R.id.edit_angle_skew);
        et_low_bed=findViewById(R.id.edit_low_bed_level);
        et_abt_left=findViewById(R.id.edit_abt_left);
        et_abt_right=findViewById(R.id.edit_abt_right);
        et_slab=findViewById(R.id.edit_slab);
        et_dia_soil=findViewById(R.id.edit_dia_bed_material);
        et_width_pier=findViewById(R.id.edit_width_pier);
        et_width_abut=findViewById(R.id.edit_width_abutment);
        et_length=findViewById(R.id.edit_bridge_length);
        et_bridth=findViewById(R.id.edit_bridge_breadth);
        et_live_load=findViewById(R.id.edit_live_load);
        et_compartment=findViewById(R.id.edit_regosity_c1);
        et_compartment2=findViewById(R.id.edit_regosity_c2);
        et_compartment3=findViewById(R.id.edit_regosity_c3);

        et_chain_number=findViewById(R.id.edit_compartment);
        et_flood_depth=findViewById(R.id.edit_flood_depth);
        sp_flood_value=findViewById(R.id.select_flood_option);
        add_flood=findViewById(R.id.add_flood_depth);

        sp_sessmic=findViewById(R.id.spinner_sesmic_zone);
        ArrayAdapter<String> sessmicArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sesmic_zone_value);
        sessmicArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_sessmic.setAdapter(sessmicArrayAdapter);

        sp_compartment=findViewById(R.id.select_compartment);
        ArrayAdapter<String> compartmentArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, compartment);
        compartmentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_compartment.setAdapter(compartmentArrayAdapter);
        sp_compartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                st_compartment=compartment[position];
                //   Toast.makeText(Calculator_input.this, ""+ st_compartment, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> floodArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, flood_value);
        floodArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_flood_value.setAdapter(floodArrayAdapter);
        add_flood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long sfv=sp_flood_value.getSelectedItemId();
                s25=et_flood_depth.getText().toString().trim();
                if(s25.isEmpty())
                {
                    et_flood_depth.setError("Required field");
                }
                else
                {
                    if(sfv==0)
                    {
                        et_flood_depth.setError("required value");
                    }
                    if(sfv==1)
                    {
                        s26=flood_value[(int)sfv];
                        databaseReference.child(user.getUid()).child("flood_depth").child("f_mihfl").setValue(s25);
                    }
                    else if(sfv==2)
                    {
                        s26=flood_value[(int)sfv];
                        databaseReference.child(user.getUid()).child("flood_depth").child("f_ohfl").setValue(s25);
                    }
                    else if (sfv==3)
                    {
                        s26=flood_value[(int)sfv];
                        databaseReference.child(user.getUid()).child("flood_depth").child("f_ofl").setValue(s25);
                    }

                }
            }
        });

        add=findViewById(R.id.add_chain_value);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cn=Integer.parseInt(et_chain_number.getText().toString().trim());
                if(!et_chain_number.getText().toString().isEmpty()) {
                    long position = sp_compartment.getSelectedItemId();
                    if (position == 0) {
                        databaseReference.child(user.getUid()).child("compartment_size").child("comp1_size").setValue(cn);
                    } else if (position == 1) {
                        databaseReference.child(user.getUid()).child("compartment_size").child("comp2_size").setValue(cn);
                    } else if (position == 2) {
                        databaseReference.child(user.getUid()).child("compartment_size").child("comp3_size").setValue(cn);
                    }
                }

                counter=cn;
                i=1;
                LayoutInflater layoutInflater=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View add_chain=layoutInflater.inflate(R.layout.add_compartment_value,null);
                add_cv=add_chain.findViewById(R.id.add_chainage);
                et_gl=add_chain.findViewById(R.id.edit_ground_level);
                et_cv=add_chain.findViewById(R.id.edit_chainage);

                AlertDialog.Builder add_chain_value=new AlertDialog.Builder(Calculator_input.this);
                add_chain_value.setView(add_chain);
                AlertDialog dialog=add_chain_value.create();
                //dialog.setCancelable(false);
                dialog.setTitle("Add chainage value!!");
                dialog.show();

                add_cv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(counter!=0)
                        {
                            s1=et_gl.getText().toString().trim();
                            s2=et_cv.getText().toString().trim();
                            Add_chainage_value obj=new Add_chainage_value(s2,s1);
                            databaseReference.child(user.getUid()).child("Chainage_value").child(st_compartment).child(String.valueOf(i++)).setValue(obj);
                            et_gl.setText(" ");
                            et_cv.setText(" ");
                            counter--;
                        }
                        else if(counter==0)
                        {
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        calci=findViewById(R.id.btn_calculate);
        pd=new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setTitle("Please Wait..");
        pd.setMessage("Processing your inputs");
        calci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
   /*           s1=et_catch_area.getText().toString().trim();
                s2=et_mihfl.getText().toString().trim();
                s3=et_ofl.getText().toString().trim();
                s4=et_ohfl.getText().toString().trim();
                s5=et_us.getText().toString().trim();
                s6=et_ds.getText().toString().trim();

                s8=et_bed.getText().toString().trim();
                s9=et_bank.getText().toString().trim();
                s10=et_skew.getText().toString().trim();
                s11=et_low_bed.getText().toString().trim();
                s12=et_abt_left.getText().toString().trim();
                s13=et_abt_right.getText().toString().trim();
                s14=et_slab.getText().toString().trim();
                s15=et_dia_soil.getText().toString().trim();
                s16=et_width_pier.getText().toString().trim();
                s17=et_width_abut.getText().toString().trim();
                s18=et_length.getText().toString().trim();
                s19=et_bridth.getText().toString().trim();
                s20=et_live_load.getText().toString().trim();
                s22=et_compartment2.getText().toString().trim();
                s23=et_compartment3.getText().toString().trim();
                s24=et_compartment.getText().toString().trim();

                s21=sesmic_zone_value[(int)sp_sessmic.getSelectedItemId()];
                Double soil_dia=Double.parseDouble(s15);

                if(soil_dia>=0.04 && soil_dia<=2.0)
                {
                    if(soil_dia==0.04)
                    {
                        databaseReference.child(user.getUid()).child("Common_answer").child("type_of_bed_material").setValue("Coarse silt");
                    }
                    else if(soil_dia>=0.081 && soil_dia <=0.158)
                    {
                        databaseReference.child(user.getUid()).child("Common_answer").child("type_of_bed_material").setValue("Silt/Fine sand");
                    }
                    else if(soil_dia>=0.233 && soil_dia <=0.505)
                    {
                        databaseReference.child(user.getUid()).child("Common_answer").child("type_of_bed_material").setValue("Medium sand");
                    }
                    else if(soil_dia==0.725)
                    {
                        databaseReference.child(user.getUid()).child("Common_answer").child("type_of_bed_material").setValue("Coarse sand");
                    }
                    else if(soil_dia==0.988)
                    {
                        databaseReference.child(user.getUid()).child("Common_answer").child("type_of_bed_material").setValue("Fine bajiri and sand");
                    }
                    else if(soil_dia>=1.29 && soil_dia<=2.00)
                    {
                        databaseReference.child(user.getUid()).child("Common_answer").child("type_of_bed_material").setValue("Heavy sand");
                    }
                    Add_calci_data obj=new Add_calci_data(s1,s2,s3,s4,s5,s6,s24,s22,s23,s8,s9,s10,s11,s12,s13,s14,s15,s16,s17,s18,s19,s20,s21);
                    databaseReference.child(user.getUid()).child("calci_info").setValue(obj);
                    compartment1();
                }
                else
                {
                    et_dia_soil.setError("Value must be between 0.04 & 2.0");
                }
*/
               // calculateQinglish();
//                calculateKSF();
                //calculateMIHFL();
               compartment1();
            }
        });
    }

    private void compartment1() {
        calculateQinglish_comp1();
    }
    private void compartment2() {
        calculateQinglish_comp2();
    }

    private void compartment3() {
        calculateQinglish_comp3();
    }

//calculation for compartment 1
    private void calculateQinglish_comp1() {
      databaseReference.child(user.getUid()).child("calci_info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Add_calci_data sam_obj=dataSnapshot.getValue(Add_calci_data.class);
             //   Toast.makeText(Calculator_input.this, ""+sam_obj.catch_area, Toast.LENGTH_SHORT).show();
                catch_a=sam_obj.getCatch_area();
                Log.d("catch_area"," "+catch_a);
                Double catch_area=Double.parseDouble(sam_obj.catch_area);
                Double n1=123.2*catch_area;
                Double n2=70.0*catch_area;
                Double d=(Double) Math.sqrt(10.4+catch_area);
                Double q1=n1/d;
                Double q2=n2/d;
                databaseReference.child(user.getUid()).child("general_ans").child("Qinglish").setValue(q1);
                databaseReference.child(user.getUid()).child("general_ans").child("Qmodified_inglish").setValue(q2);
                databaseReference.child(user.getUid()).child("general_ans_OFL").child("Qinglish").setValue(q1);
                databaseReference.child(user.getUid()).child("general_ans_OFL").child("Qmodified_inglish").setValue(q2);
                databaseReference.child(user.getUid()).child("general_ans_OHFL").child("Qinglish").setValue(q1);
                databaseReference.child(user.getUid()).child("general_ans_OHFL").child("Qmodified_inglish").setValue(q2);
                calculateUS_comp1(catch_area);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
     // catch_a="1038.51";
    }

    private void calculateUS_comp1(Double catch_area) {

        if(catch_area>0 && catch_area <3)
        {
            databaseReference.child(user.getUid()).child("general_ans").child("us").setValue(100);
            databaseReference.child(user.getUid()).child("general_ans").child("ds").setValue(0);
            databaseReference.child(user.getUid()).child("general_ans_OFL").child("us").setValue(100);
            databaseReference.child(user.getUid()).child("general_ans_OFL").child("ds").setValue(0);
            us=100d;
            ds=0d;
            calculateBed_gradient_c1(us,ds);
        }
        else if(catch_area>=3 && catch_area <=15)
        {
            databaseReference.child(user.getUid()).child("general_ans").child("us").setValue(300);
            databaseReference.child(user.getUid()).child("general_ans").child("ds").setValue(0);
            databaseReference.child(user.getUid()).child("general_ans_OFL").child("us").setValue(300);
            databaseReference.child(user.getUid()).child("general_ans_OFL").child("ds").setValue(0);
            us=300d;
            ds=0d;
            calculateBed_gradient_c1(us,ds);
        }
        else
        {
            databaseReference.child(user.getUid()).child("general_ans").child("us").setValue(500);
            databaseReference.child(user.getUid()).child("general_ans").child("ds").setValue(0);
            databaseReference.child(user.getUid()).child("general_ans_OFL").child("us").setValue(500);
            databaseReference.child(user.getUid()).child("general_ans_OFL").child("ds").setValue(0);
            us=500d;
            ds=0d;
            calculateBed_gradient_c1(us,ds);
        }

    }

    private void calculateBed_gradient_c1(Double us, Double ds) {
        Double chain_age=us-ds;
        databaseReference.child(user.getUid()).child("calci_info").child("ds").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String st_ds=dataSnapshot.getValue(String.class);
                databaseReference.child(user.getUid()).child("calci_info").child("us").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String st_us=dataSnapshot.getValue(String.class);
                        //  Toast.makeText(Calculator_input.this, ""+catch_a, Toast.LENGTH_SHORT).show();
                        Double bed_level=Double.parseDouble(st_us)-Double.parseDouble(st_ds);
                        Double bed_gradient=bed_level/chain_age;
                        databaseReference.child(user.getUid()).child("general_ans").child("bed_gradient").setValue(bed_gradient);
                        databaseReference.child(user.getUid()).child("general_ans_OFL").child("bed_gradient").setValue(bed_gradient);

                        calculateMIHFL_c1(bed_gradient);
                        calculateOFL_c1(bed_gradient);
                        calculateOHFL_c1(bed_gradient);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void calculateOHFL_c1(Double bed_gradient) {
        //fetching mihfl


        databaseReference.child(user.getUid()).child("compartment_size").child("comp1_size").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                size = dataSnapshot.getValue(Integer.class);
                fetch_obj_ohfl_c1=new Fetch[size+1];
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        databaseReference.child(user.getUid()).child("Chainage_value").child("Compartment 1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                i = 1;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Add_chainage_value obj = dataSnapshot1.getValue(Add_chainage_value.class);
                    fetch_obj_ohfl_c1[i] = new Fetch(obj.cv, obj.gl, i);
                    i++;
                }

                //fetching ohfl
                databaseReference.child(user.getUid()).child("calci_info").child("ohfl").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        OHFL = dataSnapshot.getValue(String.class);

                        //fetching rug1
                        databaseReference.child(user.getUid()).child("calci_info").child("rug1").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Double height[] = new Double[size+1];
                                for (int j = 1; j <= size; j++) {
                                    height[j] = Double.parseDouble(OHFL) - Double.parseDouble(fetch_obj_ohfl_c1[j].fetch_gl);
                                }
                                Double[] mean_diff = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    mean_diff[j] = (height[j - 1] + height[j]) / 2;
                                }

                                Double length[] = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    length[j] = Double.parseDouble(fetch_obj_ohfl_c1[j].fetch_cv) - Double.parseDouble(fetch_obj_ohfl_c1[j - 1].fetch_cv);
                                }

                                Double area[] = new Double[size+1];
                                Double sum_area = 0d;
                                for (int j = 2; j <= size; j++) {
                                    area[j] = mean_diff[j] * length[j];
                                    sum_area += area[j];
                                }
                                databaseReference.child(user.getUid()).child("general_ans_OHFL").child("total_area").setValue(sum_area);
                                Double diff_height[] = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    diff_height[j] = height[j] - height[j - 1];
                                    if(diff_height[j]<0)
                                    {
                                        diff_height[j]*=-1;
                                    }
                                }

                                Double wet_p[] = new Double[size+1];
                                Double sum_wet_p = 0d;
                                for (int j = 2; j <= size; j++) {
                                    if(diff_height[j]==0)
                                    {
                                        wet_p[j]=0d;
                                    }
                                    else {
                                        wet_p[j] = Math.sqrt(Math.pow(diff_height[j], 2) + Math.pow(length[j], 2));
                                    }
                                    sum_wet_p += wet_p[j];
                                }
                                rug1 = dataSnapshot.getValue(String.class);
                                //    Toast.makeText(Calculator_input.this, ""+rug1, Toast.LENGTH_SHORT).show();
                                Double hydro_min_depth = sum_area / sum_wet_p;
                                Double min_compartmental_velocity = (Math.pow(hydro_min_depth, 0.6666) * Math.sqrt(bed_gradient)) / Double.parseDouble(rug1);
                                Double tot_discharge = sum_area * min_compartmental_velocity;

                                databaseReference.child(user.getUid()).child("general_ans_OHFL").child("Hydro_min_depth").setValue(hydro_min_depth);
                                databaseReference.child(user.getUid()).child("general_ans_OHFL").child("min_compartmental_velocity").setValue(min_compartmental_velocity);
                                databaseReference.child(user.getUid()).child("general_ans_OHFL").child("tot_discharge").setValue(tot_discharge);

                                calculateKSF_c1();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void calculateMIHFL_c1(Double bed_gradient) {

        //fetching mihfl
        databaseReference.child(user.getUid()).child("calci_info").child("mihfl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MIHFL = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference.child(user.getUid()).child("compartment_size").child("comp1_size").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                size = dataSnapshot.getValue(Integer.class);
                fetch_obj_mihfl_c1=new Fetch[size+1];
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child(user.getUid()).child("Chainage_value").child("Compartment 1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                i = 1;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Add_chainage_value obj = dataSnapshot1.getValue(Add_chainage_value.class);
                    fetch_obj_mihfl_c1[i] = new Fetch(obj.cv, obj.gl, i);
                    i++;
                }

                //fetching mihfl
                databaseReference.child(user.getUid()).child("calci_info").child("mihfl").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        MIHFL = dataSnapshot.getValue(String.class);

                        //fetching rug1
                        databaseReference.child(user.getUid()).child("calci_info").child("rug1").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Double height[] = new Double[size+1];
                                for (int j = 1; j <= size; j++) {
                                    height[j] = Double.parseDouble(MIHFL) - Double.parseDouble(fetch_obj_mihfl_c1[j].fetch_gl);
                                }
                                Double[] mean_diff = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    mean_diff[j] = (height[j - 1] + height[j]) / 2;
                                }

                                Double length[] = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    length[j] = Double.parseDouble(fetch_obj_mihfl_c1[j].fetch_cv) - Double.parseDouble(fetch_obj_mihfl_c1[j - 1].fetch_cv);
                                }

                                Double area[] = new Double[size+1];
                                Double sum_area = 0d;
                                for (int j = 2; j <= size; j++) {
                                    area[j] = mean_diff[j] * length[j];
                                    sum_area += area[j];
                                }
                                databaseReference.child(user.getUid()).child("general_ans").child("total_area").setValue(sum_area);
                                Double diff_height[] = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    diff_height[j] = height[j] - height[j - 1];
                                    if(diff_height[j]<0)
                                    {
                                        diff_height[j]*=-1;
                                    }
                                }

                                Double wet_p[] = new Double[size+1];
                                Double sum_wet_p = 0d;
                                for (int j = 2; j <= size; j++) {
                                    if(diff_height[j]==0)
                                    {
                                        wet_p[j]=0d;
                                    }
                                    else {
                                        wet_p[j] = Math.sqrt(Math.pow(diff_height[j], 2) + Math.pow(length[j], 2));
                                    }
                                    sum_wet_p += wet_p[j];
                                }
                                rug1 = dataSnapshot.getValue(String.class);
                            //    Toast.makeText(Calculator_input.this, ""+rug1, Toast.LENGTH_SHORT).show();
                                Double hydro_min_depth = sum_area / sum_wet_p;
                                Double min_compartmental_velocity = (Math.pow(hydro_min_depth, 0.6666) * Math.sqrt(bed_gradient)) / Double.parseDouble(rug1);
                                Double tot_discharge = sum_area * min_compartmental_velocity;

                                databaseReference.child(user.getUid()).child("general_ans").child("Hydro_min_depth").setValue(hydro_min_depth);
                                databaseReference.child(user.getUid()).child("general_ans").child("min_compartmental_velocity").setValue(min_compartmental_velocity);
                                databaseReference.child(user.getUid()).child("general_ans").child("tot_discharge").setValue(tot_discharge);

                                calculateKSF_c1();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//-----------------------------------------------------------------------------------------------mihfl_c1
    private void calculateKSF_c1() {
        databaseReference.child(user.getUid()).child("calci_info").child("dia_soil").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dm=dataSnapshot.getValue(String.class);
                Double ksf=1.76*Math.sqrt(Double.parseDouble(dm));
                databaseReference.child(user.getUid()).child("general_ans").child("KSF").setValue(ksf);
                databaseReference.child(user.getUid()).child("general_ans_OFL").child("KSF").setValue(ksf);
                databaseReference.child(user.getUid()).child("general_ans_OHFL").child("KSF").setValue(ksf);

                compartment2();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//calculation for compartment 2
    private void calculateQinglish_comp2() {
        databaseReference.child(user.getUid()).child("calci_info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Add_calci_data sam_obj=dataSnapshot.getValue(Add_calci_data.class);
             //   Toast.makeText(Calculator_input.this, ""+sam_obj.catch_area, Toast.LENGTH_SHORT).show();
                // catch_a=sam_obj.getCatch_area();
                Log.d("catch_area"," "+catch_a);
                Double catch_area=Double.parseDouble(sam_obj.getCatch_area());
                Double n1=123.2*catch_area;
                Double n2=70.0*catch_area;
                Double d=(Double) Math.sqrt(10.4+catch_area);
                Double q1=n1/d;
                Double q2=n2/d;
                databaseReference.child(user.getUid()).child("general_ans_c2").child("Qinglish").setValue(q1);
                databaseReference.child(user.getUid()).child("general_ans_c2").child("Qmodified_inglish").setValue(q2);
                databaseReference.child(user.getUid()).child("general_ans_OFL_c2").child("Qinglish").setValue(q1);
                databaseReference.child(user.getUid()).child("general_ans_OFL_c2").child("Qmodified_inglish").setValue(q2);


                calculateUS_comp2(catch_area);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void calculateUS_comp2(Double catch_area) {

        if(catch_area>0 && catch_area <3)
        {
            databaseReference.child(user.getUid()).child("general_ans_c2").child("us").setValue(100);
            databaseReference.child(user.getUid()).child("general_ans_c2").child("ds").setValue(0);
            databaseReference.child(user.getUid()).child("general_ans_OFL_c2").child("us").setValue(100);
            databaseReference.child(user.getUid()).child("general_ans_OFL_c2").child("ds").setValue(0);
            us=100d;
            ds=0d;
            calculateBed_gradient_c2(us,ds);
        }
        else if(catch_area>=3 && catch_area <=15)
        {
            databaseReference.child(user.getUid()).child("general_ans_c2").child("us").setValue(300);
            databaseReference.child(user.getUid()).child("general_ans_C2").child("ds").setValue(0);
            databaseReference.child(user.getUid()).child("general_ans_OFL_c2").child("us").setValue(300);
            databaseReference.child(user.getUid()).child("general_ans_OFL_c2").child("ds").setValue(0);
            us=300d;
            ds=0d;
            calculateBed_gradient_c2(us,ds);
        }
        else
        {
            databaseReference.child(user.getUid()).child("general_ans_c2").child("us").setValue(500);
            databaseReference.child(user.getUid()).child("general_ans_c2").child("ds").setValue(0);
            databaseReference.child(user.getUid()).child("general_ans_OFL_c2").child("us").setValue(500);
            databaseReference.child(user.getUid()).child("general_ans_OFL_c2").child("ds").setValue(0);
            us=500d;
            ds=0d;
            calculateBed_gradient_c2(us,ds);
        }

    }

    private void calculateBed_gradient_c2(Double us, Double ds) {
        Double chain_age=us-ds;
        databaseReference.child(user.getUid()).child("calci_info").child("ds").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String st_ds=dataSnapshot.getValue(String.class);
                databaseReference.child(user.getUid()).child("calci_info").child("us").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String st_us=dataSnapshot.getValue(String.class);
                        //  Toast.makeText(Calculator_input.this, ""+catch_a, Toast.LENGTH_SHORT).show();
                        Double bed_level=Double.parseDouble(st_us)-Double.parseDouble(st_ds);
                        Double bed_gradient=bed_level/chain_age;
                        databaseReference.child(user.getUid()).child("general_ans_c2").child("bed_gradient").setValue(bed_gradient);
                        databaseReference.child(user.getUid()).child("general_ans_OFL_c2").child("bed_gradient").setValue(bed_gradient);

                        calculateMIHFL_c2(bed_gradient);
                        calculateOFL_c2(bed_gradient);
                       calculateOHFL_c2(bed_gradient);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void calculateOHFL_c2(Double bed_gradient) {
        //fetching mihfl


        databaseReference.child(user.getUid()).child("compartment_size").child("comp2_size").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                size = dataSnapshot.getValue(Integer.class);
                fetch_obj_ohfl_c2=new Fetch[size+1];
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference.child(user.getUid()).child("Chainage_value").child("Compartment 2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                i = 1;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Add_chainage_value obj = dataSnapshot1.getValue(Add_chainage_value.class);
                    fetch_obj_ohfl_c2[i] = new Fetch(obj.cv, obj.gl, i);
                    i++;
                }

                //fetching ohfl
                databaseReference.child(user.getUid()).child("calci_info").child("ohfl").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        OHFL = dataSnapshot.getValue(String.class);

                        //fetching rug1
                        databaseReference.child(user.getUid()).child("calci_info").child("rug2").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Double height[] = new Double[size+1];
                                for (int j = 1; j <= size; j++) {
                                    height[j] = Double.parseDouble(OHFL) - Double.parseDouble(fetch_obj_ohfl_c2[j].fetch_gl);
                                }
                                Double[] mean_diff = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    mean_diff[j] = (height[j - 1] + height[j]) / 2;
                                }

                                Double length[] = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    length[j] = Double.parseDouble(fetch_obj_ohfl_c2[j].fetch_cv) - Double.parseDouble(fetch_obj_ohfl_c2[j - 1].fetch_cv);
                                }

                                Double area[] = new Double[size+1];
                                Double sum_area = 0d;
                                for (int j = 2; j <= size; j++) {
                                    area[j] = mean_diff[j] * length[j];
                                    sum_area += area[j];
                                }
                                databaseReference.child(user.getUid()).child("general_ans_OHFL_c2").child("total_area").setValue(sum_area);
                                Double diff_height[] = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    diff_height[j] = height[j] - height[j - 1];
                                    if(diff_height[j]<0)
                                    {
                                        diff_height[j]*=-1;
                                    }
                                }

                                Double wet_p[] = new Double[size+1];
                                Double sum_wet_p = 0d;
                                for (int j = 2; j <= size; j++) {
                                    if(diff_height[j]==0)
                                    {
                                        wet_p[j]=0d;
                                    }
                                    else {
                                        wet_p[j] = Math.sqrt(Math.pow(diff_height[j], 2) + Math.pow(length[j], 2));
                                    }
                                    sum_wet_p += wet_p[j];
                                }
                                rug1 = dataSnapshot.getValue(String.class);
                                //    Toast.makeText(Calculator_input.this, ""+rug1, Toast.LENGTH_SHORT).show();
                                Double hydro_min_depth = sum_area / sum_wet_p;
                                Double min_compartmental_velocity = (Math.pow(hydro_min_depth, 0.6666) * Math.sqrt(bed_gradient)) / Double.parseDouble(rug1);
                                Double tot_discharge = sum_area * min_compartmental_velocity;

                                databaseReference.child(user.getUid()).child("general_ans_OHFL_c2").child("Hydro_min_depth").setValue(hydro_min_depth);
                                databaseReference.child(user.getUid()).child("general_ans_OHFL_c2").child("min_compartmental_velocity").setValue(min_compartmental_velocity);
                                databaseReference.child(user.getUid()).child("general_ans_OHFL_c2").child("tot_discharge").setValue(tot_discharge);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void calculateMIHFL_c2(Double bed_gradient) {

        //fetching mihfl
        databaseReference.child(user.getUid()).child("calci_info").child("mihfl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MIHFL = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference.child(user.getUid()).child("compartment_size").child("comp2_size").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                size = dataSnapshot.getValue(Integer.class);
                fetch_obj_mihfl_c2=new Fetch[size+1];
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference.child(user.getUid()).child("Chainage_value").child("Compartment 2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                i = 1;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Add_chainage_value obj = dataSnapshot1.getValue(Add_chainage_value.class);
                    fetch_obj_mihfl_c2[i] = new Fetch(obj.cv, obj.gl, i);
                    i++;
                }

                //fetching mihfl
                databaseReference.child(user.getUid()).child("calci_info").child("mihfl").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        MIHFL = dataSnapshot.getValue(String.class);

                        //fetching rug2
                        databaseReference.child(user.getUid()).child("calci_info").child("rug2").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Double height_c2[] = new Double[size+1];
                                for (int j = 1; j <= size; j++) {
                                    height_c2[j] = Double.parseDouble(MIHFL) - Double.parseDouble(fetch_obj_mihfl_c2[j].fetch_gl);
                                }
                                Double[] mean_diff_c2 = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    mean_diff_c2[j] = (height_c2[j]+height_c2[j-1]) / 2;
                                }

                                Double length_c2[] = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    length_c2[j] = Double.parseDouble(fetch_obj_mihfl_c2[j].fetch_cv) - Double.parseDouble(fetch_obj_mihfl_c2[j - 1].fetch_cv);
                                }

                                Double area_c2[] = new Double[size+1];
                                Double sum_area_c2 = 0d;
                                for (int j = 2; j <= size; j++) {
                                    area_c2[j] = mean_diff_c2[j] * length_c2[j];
                                    sum_area_c2 += area_c2[j];
                                }
                                databaseReference.child(user.getUid()).child("general_ans_c2").child("total_area").setValue(sum_area_c2);
                                Double diff_height_c2[] = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    diff_height_c2[j] = height_c2[j] - height_c2[j - 1];
                                    if(diff_height_c2[j]<0)
                                    {
                                        diff_height_c2[j]*=-1;
                                    }
                                }

                                Double wet_p_c2[] = new Double[size+1];
                                Double sum_wet_p_c2 = 0d;
                                for (int j = 2; j <= size; j++) {

                                    if(diff_height_c2[j]==0)
                                    {
                                        wet_p_c2[j]=0d;
                                    }
                                    else {
                                        wet_p_c2[j] = Math.sqrt(Math.pow(diff_height_c2[j], 2) + Math.pow(length_c2[j], 2));
                                    }
                                    sum_wet_p_c2 += wet_p_c2[j];
                                }
                                rug2 = dataSnapshot.getValue(String.class);
                                Double hydro_min_depth = sum_area_c2 / sum_wet_p_c2;
                                Double min_compartmental_velocity = (Math.pow(hydro_min_depth, 0.6666) * Math.sqrt(bed_gradient)) / Double.parseDouble(rug2);
                                Double tot_discharge = sum_area_c2 * min_compartmental_velocity;

                                databaseReference.child(user.getUid()).child("general_ans_c2").child("Hydro_min_depth").setValue(hydro_min_depth);
                                databaseReference.child(user.getUid()).child("general_ans_c2").child("min_compartmental_velocity").setValue(min_compartmental_velocity);
                                databaseReference.child(user.getUid()).child("general_ans_c2").child("tot_discharge").setValue(tot_discharge);

                                calculateKSF_c2();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void calculateKSF_c2() {
        databaseReference.child(user.getUid()).child("calci_info").child("dia_soil").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dm=dataSnapshot.getValue(String.class);
                Double ksf=1.76*Math.sqrt(Double.parseDouble(dm));
                databaseReference.child(user.getUid()).child("general_ans_c2").child("KSF").setValue(ksf);
                databaseReference.child(user.getUid()).child("general_ans_OFL_c2").child("KSF").setValue(ksf);
                compartment3();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    //--------------------------------------------mihfl_c2
//calculation for compartment 3
    private void calculateQinglish_comp3() {
        databaseReference.child(user.getUid()).child("calci_info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Add_calci_data sam_obj=dataSnapshot.getValue(Add_calci_data.class);

                Log.d("catch_area"," "+catch_a);
                Double catch_area=Double.parseDouble(sam_obj.getCatch_area());
                Double n1=123.2*catch_area;
                Double n2=70.0*catch_area;
                Double d=(Double) Math.sqrt(10.4+catch_area);
                Double q1=n1/d;
                Double q2=n2/d;
                databaseReference.child(user.getUid()).child("general_ans_c3").child("Qinglish").setValue(q1);
                databaseReference.child(user.getUid()).child("general_ans_c3").child("Qmodified_inglish").setValue(q2);
                databaseReference.child(user.getUid()).child("general_ans_OFL_c3").child("Qinglish").setValue(q1);
                databaseReference.child(user.getUid()).child("general_ans_OFL_c3").child("Qmodified_inglish").setValue(q2);
                calculateUS_comp3(catch_area);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void calculateUS_comp3(Double catch_area) {

        if(catch_area>0 && catch_area <3)
        {
            databaseReference.child(user.getUid()).child("general_ans_c3").child("us").setValue(100);
            databaseReference.child(user.getUid()).child("general_ans_C3").child("ds").setValue(0);
            databaseReference.child(user.getUid()).child("general_ans_OFL_c3").child("us").setValue(100);
            databaseReference.child(user.getUid()).child("general_ans_OFL_c3").child("ds").setValue(0);
            us=100d;
            ds=0d;
            calculateBed_gradient_c3(us,ds);
        }
        else if(catch_area>=3 && catch_area <=15)
        {
            databaseReference.child(user.getUid()).child("general_ans_c3").child("us").setValue(300);
            databaseReference.child(user.getUid()).child("general_ans_c3").child("ds").setValue(0);
            databaseReference.child(user.getUid()).child("general_ans_OFL_c3").child("us").setValue(300);
            databaseReference.child(user.getUid()).child("general_ans_OFL_c3").child("ds").setValue(0);
            us=300d;
            ds=0d;
            calculateBed_gradient_c3(us,ds);
        }
        else
        {
            databaseReference.child(user.getUid()).child("general_ans_c3").child("us").setValue(500);
            databaseReference.child(user.getUid()).child("general_ans_c3").child("ds").setValue(0);
            databaseReference.child(user.getUid()).child("general_ans_OFL_c3").child("us").setValue(500);
            databaseReference.child(user.getUid()).child("general_ans_OFL_c3").child("ds").setValue(0);
            us=500d;
            ds=0d;
            calculateBed_gradient_c3(us,ds);
        }

    }

    private void calculateBed_gradient_c3(Double us, Double ds) {
        Double chain_age=us-ds;
        databaseReference.child(user.getUid()).child("calci_info").child("ds").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String st_ds=dataSnapshot.getValue(String.class);
                databaseReference.child(user.getUid()).child("calci_info").child("us").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String st_us=dataSnapshot.getValue(String.class);
                        Double bed_level=Double.parseDouble(st_us)-Double.parseDouble(st_ds);
                        Double bed_gradient=bed_level/chain_age;
                        databaseReference.child(user.getUid()).child("general_ans_c3").child("bed_gradient").setValue(bed_gradient);
                        databaseReference.child(user.getUid()).child("general_ans_OFL_c3").child("bed_gradient").setValue(bed_gradient);

                        calculateMIHFL_c3(bed_gradient);
                        calculateOFL_c3(bed_gradient);
                        calculateOHFL_c3(bed_gradient);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void calculateOHFL_c3(Double bed_gradient) {

        databaseReference.child(user.getUid()).child("calci_info").child("ohfl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                OHFL = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference.child(user.getUid()).child("compartment_size").child("comp3_size").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                size = dataSnapshot.getValue(Integer.class);
                fetch_obj_ohfl_c3=new Fetch[size+1];
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child(user.getUid()).child("Chainage_value").child("Compartment 3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                i = 1;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Add_chainage_value obj = dataSnapshot1.getValue(Add_chainage_value.class);
                    fetch_obj_ohfl_c3[i] = new Fetch(obj.cv, obj.gl, i);
                    i++;
                }

                //fetching mihfl
                databaseReference.child(user.getUid()).child("calci_info").child("ohfl").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        OHFL = dataSnapshot.getValue(String.class);

                        //fetching rug3
                        databaseReference.child(user.getUid()).child("calci_info").child("rug3").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Double height[] = new Double[size+1];
                                for (int j = 1; j <= size; j++) {
                                    height[j] = Double.parseDouble(OHFL) - Double.parseDouble(fetch_obj_ohfl_c3[j].fetch_gl);
                                }
                                Double[] mean_diff = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    mean_diff[j] = (height[j - 1] + height[j]) / 2;
                                }

                                Double length[] = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    length[j] = Double.parseDouble(fetch_obj_ohfl_c3[j].fetch_cv) - Double.parseDouble(fetch_obj_ohfl_c3[j - 1].fetch_cv);
                                }

                                Double area[] = new Double[size+1];
                                Double sum_area = 0d;
                                for (int j = 2; j <= size; j++) {
                                    area[j] = mean_diff[j] * length[j];
                                    sum_area += area[j];
                                }

                                databaseReference.child(user.getUid()).child("general_ans_OHFL_c3").child("total_area").setValue(sum_area);
                                Double diff_height[] = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    diff_height[j] = height[j] - height[j - 1];
                                    if(diff_height[j]<0)
                                    {
                                        diff_height[j]*=-1;
                                    }
                                }

                                Double wet_p[] = new Double[size+1];
                                Double sum_wet_p = 0d;
                                for (int j = 2; j <= size; j++) {
                                    if(diff_height[j]==0)
                                    {
                                        wet_p[j]=0d;
                                    }
                                    else {
                                        wet_p[j] = Math.sqrt(Math.pow(diff_height[j], 2) + Math.pow(length[j], 2));
                                    }
                                    sum_wet_p += wet_p[j];
                                }
                                rug3 = dataSnapshot.getValue(String.class);
                                //  Toast.makeText(Calculator_input.this, ""+rug3, Toast.LENGTH_SHORT).show();
                                Double hydro_min_depth = sum_area / sum_wet_p;
                                Double min_compartmental_velocity = (Math.pow(hydro_min_depth, 0.6666) * Math.sqrt(bed_gradient)) / Double.parseDouble(rug3);
                                Double tot_discharge = sum_area * min_compartmental_velocity;

                                databaseReference.child(user.getUid()).child("general_ans_OHFL_c3").child("Hydro_min_depth").setValue(hydro_min_depth);
                                databaseReference.child(user.getUid()).child("general_ans_OHFL_c3").child("min_compartmental_velocity").setValue(min_compartmental_velocity);
                                databaseReference.child(user.getUid()).child("general_ans_OHFL_c3").child("tot_discharge").setValue(tot_discharge);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void calculateMIHFL_c3(Double bed_gradient) {

        //fetching mihfl


        databaseReference.child(user.getUid()).child("compartment_size").child("comp3_size").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                size = dataSnapshot.getValue(Integer.class);
                fetch_obj_mihfl_c3=new Fetch[size+1];
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child(user.getUid()).child("Chainage_value").child("Compartment 3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                i = 1;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Add_chainage_value obj = dataSnapshot1.getValue(Add_chainage_value.class);
                    fetch_obj_mihfl_c3[i] = new Fetch(obj.cv, obj.gl, i);
                    i++;
                }

                //fetching mihfl
                databaseReference.child(user.getUid()).child("calci_info").child("mihfl").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        MIHFL = dataSnapshot.getValue(String.class);

                        //fetching rug3
                        databaseReference.child(user.getUid()).child("calci_info").child("rug3").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Double height[] = new Double[size+1];
                                for (int j = 1; j <= size; j++) {
                                    height[j] = Double.parseDouble(MIHFL) - Double.parseDouble(fetch_obj_mihfl_c3[j].fetch_gl);
                                }
                                Double[] mean_diff = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    mean_diff[j] = (height[j - 1] + height[j]) / 2;
                                }

                                Double length[] = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    length[j] = Double.parseDouble(fetch_obj_mihfl_c3[j].fetch_cv) - Double.parseDouble(fetch_obj_mihfl_c3[j - 1].fetch_cv);
                                }

                                Double area[] = new Double[size+1];
                                Double sum_area = 0d;
                                for (int j = 2; j <= size; j++) {
                                    area[j] = mean_diff[j] * length[j];
                                    sum_area += area[j];
                                }

                                databaseReference.child(user.getUid()).child("general_ans_c3").child("total_area").setValue(sum_area);
                                Double diff_height[] = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    diff_height[j] = height[j] - height[j - 1];
                                    if(diff_height[j]<0)
                                    {
                                        diff_height[j]*=-1;
                                    }
                                }

                                Double wet_p[] = new Double[size+1];
                                Double sum_wet_p = 0d;
                                for (int j = 2; j <= size; j++) {
                                    if(diff_height[j]==0)
                                    {
                                        wet_p[j]=0d;
                                    }
                                    else {
                                        wet_p[j] = Math.sqrt(Math.pow(diff_height[j], 2) + Math.pow(length[j], 2));
                                    }
                                    sum_wet_p += wet_p[j];
                                }
                                rug3 = dataSnapshot.getValue(String.class);
                              //  Toast.makeText(Calculator_input.this, ""+rug3, Toast.LENGTH_SHORT).show();
                                Double hydro_min_depth = sum_area / sum_wet_p;
                                Double min_compartmental_velocity = (Math.pow(hydro_min_depth, 0.6666) * Math.sqrt(bed_gradient)) / Double.parseDouble(rug3);
                                Double tot_discharge = sum_area * min_compartmental_velocity;

                                databaseReference.child(user.getUid()).child("general_ans_c3").child("Hydro_min_depth").setValue(hydro_min_depth);
                                databaseReference.child(user.getUid()).child("general_ans_c3").child("min_compartmental_velocity").setValue(min_compartmental_velocity);
                                databaseReference.child(user.getUid()).child("general_ans_c3").child("tot_discharge").setValue(tot_discharge);

                                calculateKSF_c3();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void calculateKSF_c3() {
        databaseReference.child(user.getUid()).child("calci_info").child("dia_soil").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dm=dataSnapshot.getValue(String.class);
                Double ksf=1.76*Math.sqrt(Double.parseDouble(dm));
                databaseReference.child(user.getUid()).child("general_ans_c3").child("KSF").setValue(ksf);
                databaseReference.child(user.getUid()).child("general_ans_OFL_c3").child("KSF").setValue(ksf);
                calculateFinal_dist();
                calculateFinal_dist_OFL();
                calculateFinal_dist_OHFL();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//-----------------------------------mihflc3
    private void calculateFinal_dist() {
        databaseReference.child(user.getUid()).child("general_ans").child("tot_discharge").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double tot_dist_c1=dataSnapshot.getValue(Double.class);
                databaseReference.child(user.getUid()).child("general_ans_c2").child("tot_discharge").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                        Double tot_dist_c2=dataSnapshot1.getValue(Double.class);
                        databaseReference.child(user.getUid()).child("general_ans_c3").child("tot_discharge").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot11) {
                                Double tot_dist_c3=dataSnapshot11.getValue(Double.class);
                                Double tot=tot_dist_c1+tot_dist_c2+tot_dist_c3;
                                databaseReference.child(user.getUid()).child("general_ans").child("Qinglish").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                                        Double Qing=dataSnapshot3.getValue(Double.class);
                                        databaseReference.child(user.getUid()).child("general_ans").child("Qmodified_inglish").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                Double Q_mod_ing=dataSnapshot3.getValue(Double.class);
                                                Double max=Math.max(Qing,Q_mod_ing);
                                                Double comp_total_discharge=Math.max(max,tot);
                                                databaseReference.child(user.getUid()).child("Final_answer").child("comp_total_discharge").setValue(comp_total_discharge);
                                                calculateVelocity();
                                                calculate_Vmean(tot);
                                                databaseReference.child(user.getUid()).child("Final_answer").child("total_discharge").setValue(tot);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void calculate_Vmean(Double tot) {
        databaseReference.child(user.getUid()).child("general_ans").child("total_area").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Double ta_c1=dataSnapshot.getValue(Double.class);
                databaseReference.child(user.getUid()).child("general_ans_c2").child("total_area").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Double ta_c2=dataSnapshot.getValue(Double.class);
                        databaseReference.child(user.getUid()).child("general_ans_c3").child("total_area").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                Double ta_c3=dataSnapshot.getValue(Double.class);
                                Double sum_ta=ta_c1+ta_c2+ta_c3;
                                Double velo_mean=tot/sum_ta;
                                databaseReference.child(user.getUid()).child("Final_answer").child("velo_mean").setValue(velo_mean);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void calculateVelocity() {
        databaseReference.child(user.getUid()).child("general_ans").child("min_compartmental_velocity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double velo_c1=dataSnapshot.getValue(Double.class);
                databaseReference.child(user.getUid()).child("general_ans_c2").child("min_compartmental_velocity").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                        Double velo_c2=dataSnapshot1.getValue(Double.class);
                        databaseReference.child(user.getUid()).child("general_ans_c3").child("min_compartmental_velocity").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot11) {
                                Double velo_c3=dataSnapshot11.getValue(Double.class);
                                Double velo_max=Math.max(velo_c1,velo_c2);
                                Double v_max=Math.max(velo_max,velo_c3);
                                Double sum=velo_c1+velo_c2+velo_c3;
                                Double velo_avg=sum/3;

                                databaseReference.child(user.getUid()).child("Final_answer").child("max_velocity").setValue(v_max);
                                databaseReference.child(user.getUid()).child("Final_answer").child("avg_velocity").setValue(velo_avg);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void calculateOFL_c1(Double bed_gradient) {

        //fetching mihfl
        databaseReference.child(user.getUid()).child("calci_info").child("ofl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                OFL = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference.child(user.getUid()).child("compartment_size").child("comp1_size").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                size = dataSnapshot.getValue(Integer.class);
                fetch_obj_ofl_c1=new Fetch[size+1];
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        databaseReference.child(user.getUid()).child("Chainage_value").child("Compartment 1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                i = 1;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Add_chainage_value obj = dataSnapshot1.getValue(Add_chainage_value.class);
                    fetch_obj_ofl_c1[i] = new Fetch(obj.cv, obj.gl, i);
                    i++;
                }

                //fetching ofl
                databaseReference.child(user.getUid()).child("calci_info").child("ofl").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        OFL = dataSnapshot.getValue(String.class);

                        //fetching rug1
                        databaseReference.child(user.getUid()).child("calci_info").child("rug1").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Double height[] = new Double[size+1];
                                for (int j = 1; j <= size; j++) {
                                    height[j] = Double.parseDouble(OFL) - Double.parseDouble(fetch_obj_ofl_c1[j].fetch_gl);
                                }
                                Double[] mean_diff = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    mean_diff[j] = (height[j - 1] + height[j]) / 2;
                                }

                                Double length[] = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    length[j] = Double.parseDouble(fetch_obj_ofl_c1[j].fetch_cv) - Double.parseDouble(fetch_obj_ofl_c1[j - 1].fetch_cv);
                                }

                                Double area[] = new Double[size+1];
                                Double sum_area = 0d;
                                for (int j = 2; j <= size; j++) {
                                    area[j] = mean_diff[j] * length[j];
                                    sum_area += area[j];
                                }
                                databaseReference.child(user.getUid()).child("general_ans_OFL").child("total_area").setValue(sum_area);
                                Double diff_height[] = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    diff_height[j] = height[j] - height[j - 1];
                                    if(diff_height[j]<0)
                                    {
                                        diff_height[j]*=-1;
                                    }
                                }

                                Double wet_p[] = new Double[size+1];
                                Double sum_wet_p = 0d;
                                for (int j = 2; j <= size; j++) {
                                    if(diff_height[j]==0)
                                    {
                                        wet_p[j]=0d;
                                    }
                                    else {
                                        wet_p[j] = Math.sqrt(Math.pow(diff_height[j], 2) + Math.pow(length[j], 2));
                                    }
                                    sum_wet_p += wet_p[j];
                                }
                                rug1 = dataSnapshot.getValue(String.class);
                                //    Toast.makeText(Calculator_input.this, ""+rug1, Toast.LENGTH_SHORT).show();
                                Double hydro_min_depth = sum_area / sum_wet_p;
                                Double min_compartmental_velocity = (Math.pow(hydro_min_depth, 0.6666) * Math.sqrt(bed_gradient)) / Double.parseDouble(rug1);
                                Double tot_discharge = sum_area * min_compartmental_velocity;

                                databaseReference.child(user.getUid()).child("general_ans_OFL").child("Hydro_min_depth").setValue(hydro_min_depth);
                                databaseReference.child(user.getUid()).child("general_ans_OFL").child("min_compartmental_velocity").setValue(min_compartmental_velocity);
                                databaseReference.child(user.getUid()).child("general_ans_OFL").child("tot_discharge").setValue(tot_discharge);

                                calculateKSF_c1();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void calculateOFL_c2(Double bed_gradient) {

        //fetching mihfl
        databaseReference.child(user.getUid()).child("calci_info").child("ofl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                OFL = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference.child(user.getUid()).child("compartment_size").child("comp2_size").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                size = dataSnapshot.getValue(Integer.class);
                //  Toast.makeText(Calculator_input.this, ""+size, Toast.LENGTH_SHORT).show();
                //  Log.d("sizeweeeeeeeeeeeee",""+size);
                fetch_obj_ofl_c2=new Fetch[size+1];
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference.child(user.getUid()).child("Chainage_value").child("Compartment 2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                i = 1;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Add_chainage_value obj = dataSnapshot1.getValue(Add_chainage_value.class);
                    fetch_obj_ofl_c2[i] = new Fetch(obj.cv, obj.gl, i);
                    i++;
                }

                //fetching mihfl
                databaseReference.child(user.getUid()).child("calci_info").child("ofl").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        OFL = dataSnapshot.getValue(String.class);

                        //fetching rug2
                        databaseReference.child(user.getUid()).child("calci_info").child("rug2").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Double height_c2[] = new Double[size+1];
                                for (int j = 1; j <= size; j++) {
                                    height_c2[j] = Double.parseDouble(OFL) - Double.parseDouble(fetch_obj_ofl_c2[j].fetch_gl);
                                }
                                Double[] mean_diff_c2 = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    mean_diff_c2[j] = (height_c2[j]+height_c2[j-1]) / 2;
                                }

                                Double length_c2[] = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    length_c2[j] = Double.parseDouble(fetch_obj_ofl_c2[j].fetch_cv) - Double.parseDouble(fetch_obj_ofl_c2[j - 1].fetch_cv);
                                }

                                Double area_c2[] = new Double[size+1];
                                Double sum_area_c2 = 0d;
                                for (int j = 2; j <= size; j++) {
                                    area_c2[j] = mean_diff_c2[j] * length_c2[j];
                                    sum_area_c2 += area_c2[j];
                                }
                                databaseReference.child(user.getUid()).child("general_ans_OFL_c2").child("total_area").setValue(sum_area_c2);
                                //Toast.makeText(Calculator_input.this, ""+sum_area_c2, Toast.LENGTH_SHORT).show();
                                Double diff_height_c2[] = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    diff_height_c2[j] = height_c2[j] - height_c2[j - 1];
                                    if(diff_height_c2[j]<0)
                                    {
                                        diff_height_c2[j]*=-1;
                                    }
                                }

                                Double wet_p_c2[] = new Double[size+1];
                                Double sum_wet_p_c2 = 0d;
                                for (int j = 2; j <= size; j++) {
                                    if(diff_height_c2[j]==0)
                                    {
                                        wet_p_c2[j]=0d;
                                    }
                                    else {
                                        wet_p_c2[j] = Math.sqrt(Math.pow(diff_height_c2[j], 2) + Math.pow(length_c2[j], 2));
                                    }
                                    sum_wet_p_c2 += wet_p_c2[j];
                                }
                                rug2 = dataSnapshot.getValue(String.class);

                                Double hydro_min_depth = sum_area_c2 / sum_wet_p_c2;
                                Double min_compartmental_velocity = (Math.pow(hydro_min_depth, 0.6666) * Math.sqrt(bed_gradient)) / Double.parseDouble(rug2);
                                Double tot_discharge = sum_area_c2 * min_compartmental_velocity;

                                databaseReference.child(user.getUid()).child("general_ans_OFL_c2").child("Hydro_min_depth").setValue(hydro_min_depth);
                                databaseReference.child(user.getUid()).child("general_ans_OFL_c2").child("min_compartmental_velocity").setValue(min_compartmental_velocity);
                                databaseReference.child(user.getUid()).child("general_ans_OFL_c2").child("tot_discharge").setValue(tot_discharge);

                                calculateKSF_c2();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void calculateOFL_c3(Double bed_gradient) {

        //fetching mihfl
        databaseReference.child(user.getUid()).child("calci_info").child("ofl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                OFL = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference.child(user.getUid()).child("compartment_size").child("comp3_size").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                size = dataSnapshot.getValue(Integer.class);
                fetch_obj_ofl_c3=new Fetch[size+1];
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        databaseReference.child(user.getUid()).child("Chainage_value").child("Compartment 3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                i = 1;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Add_chainage_value obj = dataSnapshot1.getValue(Add_chainage_value.class);
                    fetch_obj_ofl_c3[i] = new Fetch(obj.cv, obj.gl, i);
                    i++;
                }

                //fetching mihfl
                databaseReference.child(user.getUid()).child("calci_info").child("ofl").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        OFL = dataSnapshot.getValue(String.class);

                        //fetching rug3
                        databaseReference.child(user.getUid()).child("calci_info").child("rug3").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Double height[] = new Double[size+1];
                                for (int j = 1; j <= size; j++) {
                                    height[j] = Double.parseDouble(OFL) - Double.parseDouble(fetch_obj_ofl_c3[j].fetch_gl);
                                }
                                Double[] mean_diff = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    mean_diff[j] = (height[j - 1] + height[j]) / 2;
                                }

                                Double length[] = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    length[j] = Double.parseDouble(fetch_obj_ofl_c3[j].fetch_cv) - Double.parseDouble(fetch_obj_ofl_c3[j - 1].fetch_cv);
                                }

                                Double area[] = new Double[size+1];
                                Double sum_area = 0d;
                                for (int j = 2; j <= size; j++) {
                                    area[j] = mean_diff[j] * length[j];
                                    sum_area += area[j];
                                }

                                databaseReference.child(user.getUid()).child("general_ans_OFL_c3").child("total_area").setValue(sum_area);
                                Double diff_height[] = new Double[size+1];
                                for (int j = 2; j <= size; j++) {
                                    diff_height[j] = height[j] - height[j - 1];
                                    if(diff_height[j]<0)
                                    {
                                        diff_height[j]*=-1;
                                    }
                                }

                                Double wet_p[] = new Double[size+1];
                                Double sum_wet_p = 0d;
                                for (int j = 2; j <= size; j++) {
                                    if(diff_height[j]==0)
                                    {
                                        wet_p[j]=0d;
                                    }
                                    else {
                                        wet_p[j] = Math.sqrt(Math.pow(diff_height[j], 2) + Math.pow(length[j], 2));
                                    }
                                    sum_wet_p += wet_p[j];
                                }
                                rug3 = dataSnapshot.getValue(String.class);
                              //  Toast.makeText(Calculator_input.this, ""+rug3, Toast.LENGTH_SHORT).show();
                                Double hydro_min_depth = sum_area / sum_wet_p;
                                Double min_compartmental_velocity = (Math.pow(hydro_min_depth, 0.6666) * Math.sqrt(bed_gradient)) / Double.parseDouble(rug3);
                                Double tot_discharge = sum_area * min_compartmental_velocity;

                                databaseReference.child(user.getUid()).child("general_ans_OFL_c3").child("Hydro_min_depth").setValue(hydro_min_depth);
                                databaseReference.child(user.getUid()).child("general_ans_OFL_c3").child("min_compartmental_velocity").setValue(min_compartmental_velocity);
                                databaseReference.child(user.getUid()).child("general_ans_OFL_c3").child("tot_discharge").setValue(tot_discharge);

                                calculateKSF_c3();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void calculateFinal_dist_OFL() {
        databaseReference.child(user.getUid()).child("general_ans_OFL").child("tot_discharge").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double tot_dist_c1=dataSnapshot.getValue(Double.class);
                databaseReference.child(user.getUid()).child("general_ans_OFL_c2").child("tot_discharge").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                        Double tot_dist_c2=dataSnapshot1.getValue(Double.class);
                        databaseReference.child(user.getUid()).child("general_ans_OFL_c3").child("tot_discharge").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot11) {
                                Double tot_dist_c3=dataSnapshot11.getValue(Double.class);
                                Double tot=tot_dist_c1+tot_dist_c2+tot_dist_c3;
                                databaseReference.child(user.getUid()).child("general_ans_OFL").child("Qinglish").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                                        Double Qing=dataSnapshot3.getValue(Double.class);
                                        databaseReference.child(user.getUid()).child("general_ans_OFL").child("Qmodified_inglish").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                Double Q_mod_ing=dataSnapshot3.getValue(Double.class);
                                                Double max=Math.max(Qing,Q_mod_ing);
                                                Double comp_total_discharge=Math.max(max,tot);
                                                databaseReference.child(user.getUid()).child("Final_answer_OFL").child("comp_total_discharge").setValue(comp_total_discharge);
                                                calculateVelocity_OFL();
                                                calculate_Vmean_OFL(tot);
                                                databaseReference.child(user.getUid()).child("Final_answer_OFL").child("total_discharge").setValue(tot);
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void calculate_Vmean_OFL(Double tot) {
        databaseReference.child(user.getUid()).child("general_ans").child("total_area").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Double ta_c1=dataSnapshot.getValue(Double.class);
                databaseReference.child(user.getUid()).child("general_ans_OFL_c2").child("total_area").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Double ta_c2=dataSnapshot.getValue(Double.class);
                        databaseReference.child(user.getUid()).child("general_ans_OFL_c3").child("total_area").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                Double ta_c3=dataSnapshot.getValue(Double.class);
                                Double sum_ta=ta_c1+ta_c2+ta_c3;
                                Double velo_mean=tot/sum_ta;
                                databaseReference.child(user.getUid()).child("Final_answer_OFL").child("velo_mean").setValue(velo_mean);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void calculateVelocity_OFL() {
        databaseReference.child(user.getUid()).child("general_ans_OFL").child("min_compartmental_velocity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double velo_c1=dataSnapshot.getValue(Double.class);
                databaseReference.child(user.getUid()).child("general_ans_OFL_c2").child("min_compartmental_velocity").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                        Double velo_c2=dataSnapshot1.getValue(Double.class);
                        databaseReference.child(user.getUid()).child("general_ans_OFL_c3").child("min_compartmental_velocity").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot11) {
                                Double velo_c3=dataSnapshot11.getValue(Double.class);
                                Double velo_max=Math.max(velo_c1,velo_c2);
                                Double v_max=Math.max(velo_max,velo_c3);
                                Double sum=velo_c1+velo_c2+velo_c3;
                                Double velo_avg=sum/3;

                                databaseReference.child(user.getUid()).child("Final_answer_OFL").child("max_velocity").setValue(v_max);
                                databaseReference.child(user.getUid()).child("Final_answer_OFL").child("avg_velocity").setValue(velo_avg);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void calculateFinal_dist_OHFL() {
        databaseReference.child(user.getUid()).child("general_ans_OHFL").child("tot_discharge").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double tot_dist_c1=dataSnapshot.getValue(Double.class);
                databaseReference.child(user.getUid()).child("general_ans_OHFL_c2").child("tot_discharge").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                        Double tot_dist_c2=dataSnapshot1.getValue(Double.class);
                        databaseReference.child(user.getUid()).child("general_ans_OHFL_c3").child("tot_discharge").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot11) {
                                Double tot_dist_c3=dataSnapshot11.getValue(Double.class);
                                Double tot=tot_dist_c1+tot_dist_c2+tot_dist_c3;
                                databaseReference.child(user.getUid()).child("general_ans_OHFL").child("Qinglish").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                                        Double Qing=dataSnapshot3.getValue(Double.class);
                                        databaseReference.child(user.getUid()).child("general_ans_OHFL").child("Qmodified_inglish").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                Double Q_mod_ing=dataSnapshot3.getValue(Double.class);
                                                Double max=Math.max(Qing,Q_mod_ing);
                                                Double comp_total_discharge=Math.max(max,tot);
                                                databaseReference.child(user.getUid()).child("Final_answer_OHFL").child("comp_total_discharge").setValue(comp_total_discharge);
                                                calculateVelocity_OHFL();
                                                calculate_Vmean_OHFL(tot);
                                                databaseReference.child(user.getUid()).child("Final_answer_OHFL").child("total_discharge").setValue(tot);
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void calculate_Vmean_OHFL(Double tot) {
        databaseReference.child(user.getUid()).child("general_ans_OHFL").child("total_area").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Double ta_c1=dataSnapshot.getValue(Double.class);
                databaseReference.child(user.getUid()).child("general_ans_OHFL_c2").child("total_area").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Double ta_c2=dataSnapshot.getValue(Double.class);
                        databaseReference.child(user.getUid()).child("general_ans_OHFL_c3").child("total_area").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                Double ta_c3=dataSnapshot.getValue(Double.class);
                                Double sum_ta=ta_c1+ta_c2+ta_c3;
                                Double velo_mean=tot/sum_ta;
                                databaseReference.child(user.getUid()).child("Final_answer_OHFL").child("velo_mean").setValue(velo_mean);
                                calculateHFL();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void calculateVelocity_OHFL() {
        databaseReference.child(user.getUid()).child("general_ans_OHFL").child("min_compartmental_velocity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double velo_c1=dataSnapshot.getValue(Double.class);
                databaseReference.child(user.getUid()).child("general_ans_OHFL_c2").child("min_compartmental_velocity").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                        Double velo_c2=dataSnapshot1.getValue(Double.class);
                        databaseReference.child(user.getUid()).child("general_ans_OHFL_c3").child("min_compartmental_velocity").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot11) {
                                Double velo_c3=dataSnapshot11.getValue(Double.class);
                                Double velo_max=Math.max(velo_c1,velo_c2);
                                Double v_max=Math.max(velo_max,velo_c3);
                                Double sum=velo_c1+velo_c2+velo_c3;
                                Double velo_avg=sum/3;

                                databaseReference.child(user.getUid()).child("Final_answer_OHFL").child("max_velocity").setValue(v_max);
                                databaseReference.child(user.getUid()).child("Final_answer_OHFL").child("avg_velocity").setValue(velo_avg);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void calculateHFL()
    {
        DatabaseReference path=databaseReference.child(user.getUid()).child("calci_info");
        path.child("mihfl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MIHFL=dataSnapshot.getValue(String.class);
                path.child("ofl").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                        OFL=dataSnapshot1.getValue(String.class);
                        path.child("ohfl").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot11) {
                                OHFL=dataSnapshot11.getValue(String.class);
                                databaseReference.child(user.getUid()).child("Final_answer").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataMIHFL) {
                                        Fetch_final_output data_MIHFL=dataMIHFL.getValue(Fetch_final_output.class);
                                        databaseReference.child(user.getUid()).child("Final_answer_OFL").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataOFL) {
                                                Fetch_final_output data_OFL=dataOFL.getValue(Fetch_final_output.class);
                                                databaseReference.child(user.getUid()).child("Final_answer_OHFL").addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataOHFL) {
                                                        Fetch_final_output data_OHFL=dataOHFL.getValue(Fetch_final_output.class);
                                                        path.child("low_bed").addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String lbl=dataSnapshot.getValue(String.class);
                                                                databaseReference.child(user.getUid()).child("flood_depth").addValueEventListener(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot dataFlood) {
                                                                        Fetch_flood flod=dataFlood.getValue(Fetch_flood.class);

                                                                        Double lw_MIHFL=4.5*Math.sqrt(data_MIHFL.total_discharge);
                                                                        Double lw_OFL=4.5*Math.sqrt(data_OFL.total_discharge);
                                                                        Double lw_OHFL=4.5*Math.sqrt(data_OHFL.total_discharge);
                                                                        Double vmaxOFL=data_OFL.total_discharge/(data_OFL.max_velocity*Double.parseDouble(flod.f_ofl));
                                                                        Double vmaxOHFL=data_OHFL.total_discharge/(data_OHFL.max_velocity*Double.parseDouble(flod.f_ohfl));
                                                                        Double vmaxMIHFL=data_MIHFL.total_discharge/(data_MIHFL.max_velocity*Double.parseDouble(flod.f_mihfl));
                                                                        Double VavgOFL=data_OFL.total_discharge/(data_OFL.avg_velocity*Double.parseDouble(flod.f_ofl));
                                                                        Double VavgOHFL=data_OHFL.total_discharge/(data_OHFL.avg_velocity*Double.parseDouble(flod.f_ohfl));
                                                                        Double VavgMIHFL=data_MIHFL.total_discharge/(data_MIHFL.avg_velocity*Double.parseDouble(flod.f_mihfl));
                                                                        Double vmeanOFL=data_OFL.total_discharge/(data_OFL.velo_mean*Double.parseDouble(flod.f_ofl));
                                                                        Double vmeanOHFL=data_OHFL.total_discharge/(data_OHFL.velo_mean*Double.parseDouble(flod.f_ohfl));
                                                                        Double vmeanMIHFL=data_MIHFL.total_discharge/(data_MIHFL.velo_mean*Double.parseDouble(flod.f_mihfl));
                                                                        //IRC_method
                                                                        databaseReference.child(user.getUid()).child("Linear_waterway").child("By_IRC_method").child("OFL").setValue(lw_OFL);
                                                                        databaseReference.child(user.getUid()).child("Linear_waterway").child("By_IRC_method").child("OHFL").setValue(lw_OHFL);
                                                                        databaseReference.child(user.getUid()).child("Linear_waterway").child("By_IRC_method").child("MIHFL").setValue(lw_MIHFL);
                                                                        //max_velocity
                                                                        databaseReference.child(user.getUid()).child("Linear_waterway").child("By_MAX_velocity").child("OFL").setValue(vmaxOFL);
                                                                        databaseReference.child(user.getUid()).child("Linear_waterway").child("By_MAX_velocity").child("OHFL").setValue(vmaxOHFL);
                                                                        databaseReference.child(user.getUid()).child("Linear_waterway").child("By_MAX_velocity").child("MIHFL").setValue(vmaxMIHFL);
                                                                        //avg_velocity
                                                                        databaseReference.child(user.getUid()).child("Linear_waterway").child("By_AVG_velocity").child("OFL").setValue(VavgOFL);
                                                                        databaseReference.child(user.getUid()).child("Linear_waterway").child("By_AVG_velocity").child("OHFL").setValue(VavgOHFL);
                                                                        databaseReference.child(user.getUid()).child("Linear_waterway").child("By_AVG_velocity").child("MIHFL").setValue(VavgMIHFL);
                                                                        //mean_velocity
                                                                        databaseReference.child(user.getUid()).child("Linear_waterway").child("By_MEAN_velocity").child("OFL").setValue(vmeanOFL);
                                                                        databaseReference.child(user.getUid()).child("Linear_waterway").child("By_MEAN_velocity").child("OHFL").setValue(vmeanOHFL);
                                                                        databaseReference.child(user.getUid()).child("Linear_waterway").child("By_MEAN_velocity").child("MIHFL").setValue(vmeanMIHFL);
                                                                    }

                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                    }
                                                                });
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        databaseReference.child(user.getUid()).child("calci_info").child("bank").addValueEventListener(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(@NonNull DataSnapshot dataBank) {
                                                                                String bank_width=dataBank.getValue(String.class);
                                                                                databaseReference.child(user.getUid()).child("calci_info").child("bed").addValueEventListener(new ValueEventListener() {
                                                                                    @Override
                                                                                    public void onDataChange(@NonNull DataSnapshot dataBed) {
                                                                                        String bed_width=dataBed.getValue(String.class);
                                                                                        Double avg_wid=(Double.parseDouble(bank_width)+Double.parseDouble(bed_width))/2;
                                                                                        databaseReference.child(user.getUid()).child("Linear_waterway").child("By_bed_bank_method").setValue(avg_wid);
                                                                                        pd.dismiss();
                                                                                        startActivity(new Intent(Calculator_input.this,Show_output.class));
                                                                                        finish();
                                                                                    }
                                                                                    @Override
                                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                    }
                                                                                });
                                                                            }

                                                                            @Override
                                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                            }
                                                                        });


                                                                    }
                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                    }
                                                                });
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }


}

class Fetch
{
    public String fetch_cv,fetch_gl;
    public int index;

    public Fetch() {
    }

    public Fetch(String fetch_cv, String fetch_gl, int index) {
        this.fetch_cv = fetch_cv;
        this.fetch_gl = fetch_gl;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getFetch_cv() {
        return fetch_cv;
    }

    public void setFetch_cv(String fetch_cv) {
        this.fetch_cv = fetch_cv;
    }

    public String getFetch_gl() {
        return fetch_gl;
    }

    public void setFetch_gl(String fetch_gl) {
        this.fetch_gl = fetch_gl;
    }
}