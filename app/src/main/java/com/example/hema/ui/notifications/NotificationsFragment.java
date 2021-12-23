package com.example.hema.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hema.Activity_await;
import com.example.hema.Activity_wait;
import com.example.hema.R;
import com.example.hema.adapter.CartAdapter;
import com.example.hema.adapter.CartAdapter1;
import com.example.hema.adapter.MyAdapter;
import com.example.hema.databinding.FragmentNotificationsBinding;
import com.example.hema.demo.Cart;
import com.example.hema.demo.Cart1;
import com.example.hema.demo.ContentData;
import com.example.hema.demo.Goods;
import com.example.hema.ui.dashboard.DashboardFragment;

import java.util.ArrayList;
import java.util.List;


public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ImageView pic1=root.findViewById(R.id.pic1);
        ImageView  pic2=root.findViewById(R.id.pic3);
        pic1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), Activity_wait.class);
                startActivity(intent);
            }
        });
        pic2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent  intent=new Intent(getActivity(), Activity_await.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}