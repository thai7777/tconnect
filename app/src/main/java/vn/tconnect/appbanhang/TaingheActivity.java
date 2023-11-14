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
import vn.tconnect.appbanhang.adapter.TaiNgheAdapter;
import vn.tconnect.appbanhang.models.FlashSaleModel;
import vn.tconnect.appbanhang.models.ManHinhModel;
import vn.tconnect.appbanhang.models.RecommendedModel;
import vn.tconnect.appbanhang.models.TaiNgheModel;

public class TaingheActivity extends AppCompatActivity {
    private List<TaiNgheModel> taiNgheModelList;
    private TaiNgheAdapter taiNgheAdapter;
    private FirebaseFirestore db;
    RecyclerView taiNgheRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tainghe);
        db =FirebaseFirestore.getInstance();
        taiNgheRec=findViewById(R.id.tainghe_Rec);

        taiNgheRec.setLayoutManager(new GridLayoutManager(this, 2));
        taiNgheModelList= new ArrayList<>();
        taiNgheAdapter= new TaiNgheAdapter(this,taiNgheModelList);
        taiNgheRec.setAdapter(taiNgheAdapter);
        db.collection("RecommendedToday")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                TaiNgheModel taiNgheModel =document.toObject(TaiNgheModel.class);
                                taiNgheModelList.add(taiNgheModel);
                                taiNgheAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getParent(),"Error"+task.getException(),Toast.LENGTH_SHORT);
                        }
                    }
                });
    }
}