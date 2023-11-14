package vn.tconnect.appbanhang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import vn.tconnect.appbanhang.adapter.LaptopAdapter;
import vn.tconnect.appbanhang.adapter.ManHinhAdapter;
import vn.tconnect.appbanhang.adapter.RecommendedAdapter;
import vn.tconnect.appbanhang.adapter.SaleAdapter;
import vn.tconnect.appbanhang.models.FlashSaleModel;
import vn.tconnect.appbanhang.models.LaptopModel;
import vn.tconnect.appbanhang.models.ManHinhModel;
import vn.tconnect.appbanhang.models.RecommendedModel;

public class LaptopActivity extends AppCompatActivity {
    private List<LaptopModel> laptopModelList;
    private LaptopAdapter laptopAdapter;
    private FirebaseFirestore db;
    RecyclerView lapTopRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        db =FirebaseFirestore.getInstance();
        lapTopRec=findViewById(R.id.laptop_Rec);

        lapTopRec.setLayoutManager(new GridLayoutManager(this, 2));
        laptopModelList= new ArrayList<>();
        laptopAdapter= new LaptopAdapter(this,laptopModelList);
        lapTopRec.setAdapter(laptopAdapter);
        db.collection("RecommendedToday")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                LaptopModel laptopModel =document.toObject(LaptopModel.class);
                                laptopModelList.add(laptopModel);
                                laptopAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getParent(),"Error"+task.getException(),Toast.LENGTH_SHORT);

                        }
                    }
                });
    }
}