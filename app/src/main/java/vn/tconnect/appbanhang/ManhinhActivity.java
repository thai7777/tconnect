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

import vn.tconnect.appbanhang.adapter.ManHinhAdapter;
import vn.tconnect.appbanhang.adapter.RecommendedAdapter;
import vn.tconnect.appbanhang.adapter.SaleAdapter;
import vn.tconnect.appbanhang.models.FlashSaleModel;
import vn.tconnect.appbanhang.models.ManHinhModel;
import vn.tconnect.appbanhang.models.RecommendedModel;

public class ManhinhActivity extends AppCompatActivity {
    private List<ManHinhModel> manHinhModelList;
    private ManHinhAdapter manHinhAdapter;
    private FirebaseFirestore db;
    RecyclerView manHinhRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinh);
        db =FirebaseFirestore.getInstance();
        manHinhRec=findViewById(R.id.manhinh_Rec);

        manHinhRec.setLayoutManager(new GridLayoutManager(this, 2));
        manHinhModelList= new ArrayList<>();
        manHinhAdapter= new ManHinhAdapter(this,manHinhModelList);
        manHinhRec.setAdapter(manHinhAdapter);
        db.collection("RecommendedToday")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ManHinhModel manHinhModel =document.toObject(ManHinhModel.class);
                                manHinhModelList.add(manHinhModel);
                                manHinhAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getParent(),"Error"+task.getException(),Toast.LENGTH_SHORT);

                        }
                    }
                });
    }
}