package com.example.pfc;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        FloatingActionButton fab = findViewById(R.id.fabAdd);

        // Configuramos el adaptador para las 2 pestañas
        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                if (position == 0) return new SesionesFragment(); // Pestaña 1
                return new AlertasFragment(); // Pestaña 2
            }

            @Override
            public int getItemCount() { return 2; }
        });

        // Unimos el TabLayout con el ViewPager
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) tab.setText("Mis Baños");
            else tab.setText("Alertas Radar");
        }).attach();

        fab.setOnClickListener(v -> {
            startActivity(new Intent(this, FormularioActivity.class));
        });
    }
}