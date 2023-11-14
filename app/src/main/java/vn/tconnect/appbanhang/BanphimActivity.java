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

import vn.tconnect.appbanhang.adapter.BanPhimAdapter;
import vn.tconnect.appbanhang.adapter.ManHinhAdapter;
import vn.tconnect.appbanhang.adapter.RecommendedAdapter;
import vn.tconnect.appbanhang.adapter.SaleAdapter;
import vn.tconnect.appbanhang.models.BanPhimModel;
import vn.tconnect.appbanhang.models.FlashSaleModel;
import vn.tconnect.appbanhang.models.ManHinhModel;
import vn.tconnect.appbanhang.models.RecommendedModel;

public class BanphimActivity extends AppCompatActivity {
    private List<BanPhimModel> banPhimModelList;
    private BanPhimAdapter banPhimAdapter;
    private FirebaseFirestore db;
    RecyclerView banPhimRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banphim);
        db =FirebaseFirestore.getInstance();
        banPhimRec=findViewById(R.id.banphim_Rec);

        banPhimRec.setLayoutManager(new GridLayoutManager(this, 2));
        banPhimModelList= new ArrayList<>();
        banPhimAdapter= new BanPhimAdapter(this,banPhimModelList);
        banPhimRec.setAdapter(banPhimAdapter);
        db.collection("RecommendedToday")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                BanPhimModel banPhimModel =document.toObject(BanPhimModel.class);
                                banPhimModelList.add(banPhimModel);
                                banPhimAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getParent(),"Error"+task.getException(),Toast.LENGTH_SHORT);

                        }
                    }
                });
    }
}