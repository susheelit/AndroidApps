package com.aob.aobsalesman.activities.Fragements;


import android.animation.Animator;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.aob.aobsalesman.R;
import com.skyfishjy.library.RippleBackground;

import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class HintFragement extends Fragment {

    private FrameLayout layoutMain;
    private boolean isOpen = false;
    public HintFragement() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_hint_fragement, container, false);
        layoutMain= view.findViewById(R.id.frame);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int x = layoutMain.getLeft();
                int y = layoutMain.getTop();
                int startRadius = 0;
                int endRadius = (int) Math.hypot(layoutMain.getWidth(), layoutMain.getHeight());

                Animator anim = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    anim = ViewAnimationUtils.createCircularReveal(layoutMain, x, y, startRadius, endRadius);
                    if (Build.VERSION.SDK_INT >= 16) {
                        getActivity().getWindow().setFlags(1024, 1024);
                        getActivity().getWindow().getDecorView().setSystemUiVisibility(3328);
                    } else {
                        getActivity().requestWindowFeature(1);
                        getActivity().getWindow().setFlags(1024, 1024);
                    }
                    ((LinearLayout)view.findViewById(R.id.linear)).setVisibility(View.VISIBLE);
                    ((CoordinatorLayout)view.findViewById(R.id.apply_layout)).setVisibility(View.VISIBLE);
                    ((CoordinatorLayout)view.findViewById(R.id.notify_layout)).setVisibility(View.GONE);
                    ((CoordinatorLayout)view.findViewById(R.id.lead_layout)).setVisibility(View.GONE);
                    ((CoordinatorLayout)view.findViewById(R.id.sale_layout)).setVisibility(View.GONE);
                    ((CoordinatorLayout)view.findViewById(R.id.earn_layout)).setVisibility(View.GONE);


                    ((TextView)view.findViewById(R.id.next_apply)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           ((CoordinatorLayout)view.findViewById(R.id.notify_layout)).setVisibility(View.VISIBLE);
                           ((CoordinatorLayout)view.findViewById(R.id.lead_layout)).setVisibility(View.GONE);
                           ((CoordinatorLayout)view.findViewById(R.id.sale_layout)).setVisibility(View.GONE);
                           ((CoordinatorLayout)view.findViewById(R.id.earn_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.apply_layout)).setVisibility(View.GONE);
                        }
                    });
                ((TextView)view.findViewById(R.id.next_notify)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((CoordinatorLayout)view.findViewById(R.id.notify_layout)).setVisibility(View.GONE);
                        ((CoordinatorLayout)view.findViewById(R.id.lead_layout)).setVisibility(View.VISIBLE);
                        ((CoordinatorLayout)view.findViewById(R.id.sale_layout)).setVisibility(View.GONE);
                        ((CoordinatorLayout)view.findViewById(R.id.earn_layout)).setVisibility(View.GONE);
                        ((CoordinatorLayout)view.findViewById(R.id.apply_layout)).setVisibility(View.GONE);
                    }
                });
                ((TextView)view.findViewById(R.id.next_lead)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((CoordinatorLayout)view.findViewById(R.id.notify_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.lead_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.sale_layout)).setVisibility(View.VISIBLE);
                            ((CoordinatorLayout)view.findViewById(R.id.earn_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.apply_layout)).setVisibility(View.GONE);
                     }
                    });
                ((TextView)view.findViewById(R.id.next_sale)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((CoordinatorLayout)view.findViewById(R.id.notify_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.lead_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.sale_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.apply_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.earn_layout)).setVisibility(View.VISIBLE);
                        }
                    });

                ((TextView)view.findViewById(R.id.next_earn)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           /* ((CoordinatorLayout)view.findViewById(R.id.notify_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.lead_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.sale_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.earn_layout)).setVisibility(View.VISIBLE);*/

                            SharedPreferences preferences =
                                    getActivity().getSharedPreferences("my_preferences", MODE_PRIVATE);
                            preferences.edit()
                                    .putBoolean("onboarding_complete",true).apply();
                            getActivity(). getSupportFragmentManager().beginTransaction().
                                    remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.mainFrame)).commit();
                            if (Build.VERSION.SDK_INT >= 16) {
                                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

                            } else {
                                getActivity().requestWindowFeature(1);
                                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                            }

                        }
                    });
                ((TextView)view.findViewById(R.id.dismiss_notify)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SharedPreferences preferences =
                                    getActivity().getSharedPreferences("my_preferences", MODE_PRIVATE);
                            preferences.edit()
                                    .putBoolean("onboarding_complete",true).apply();
                            getActivity(). getSupportFragmentManager().beginTransaction().
                                    remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.mainFrame)).commit();
                        }
                    });
                ((TextView)view.findViewById(R.id.dismiss_lead)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SharedPreferences preferences =
                                    getActivity().getSharedPreferences("my_preferences", MODE_PRIVATE);
                            preferences.edit()
                                    .putBoolean("onboarding_complete",true).apply();
                            getActivity(). getSupportFragmentManager().beginTransaction().
                                    remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.mainFrame)).commit();
                        }
                    });
                ((TextView)view.findViewById(R.id.dismiss_sale)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SharedPreferences preferences =
                                    getActivity().getSharedPreferences("my_preferences", MODE_PRIVATE);
                            preferences.edit()
                                    .putBoolean("onboarding_complete",true).apply();
                            getActivity(). getSupportFragmentManager().beginTransaction().
                                    remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.mainFrame)).commit();
                        }
                    });
                ((TextView)view.findViewById(R.id.dismiss_earn)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SharedPreferences preferences =
                                    getActivity().getSharedPreferences("my_preferences", MODE_PRIVATE);
                            preferences.edit()
                                    .putBoolean("onboarding_complete",true).apply();
                            getActivity(). getSupportFragmentManager().beginTransaction().
                                    remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.mainFrame)).commit();
                        }
                    });
                    ((TextView)view.findViewById(R.id.dismiss_apply)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SharedPreferences preferences =
                                    getActivity().getSharedPreferences("my_preferences", MODE_PRIVATE);
                            preferences.edit()
                                    .putBoolean("onboarding_complete",true).apply();
                            getActivity(). getSupportFragmentManager().beginTransaction().
                                    remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.mainFrame)).commit();
                        }
                    });

                   /* final RippleBackground rippleBackground=(RippleBackground)view.findViewById(R.id.content);
                    rippleBackground.setVisibility(View.VISIBLE);
                    rippleBackground.startRippleAnimation();*/

                anim.start();

                isOpen = true;
                }else {
                    if (Build.VERSION.SDK_INT >= 16) {
                        getActivity().getWindow().setFlags(1024, 1024);
                        getActivity().getWindow().getDecorView().setSystemUiVisibility(3328);
                    } else {
                        getActivity().requestWindowFeature(1);
                        getActivity().getWindow().setFlags(1024, 1024);
                    }
                    ((LinearLayout)view.findViewById(R.id.linear)).setVisibility(View.VISIBLE);
                    ((CoordinatorLayout)view.findViewById(R.id.apply_layout)).setVisibility(View.VISIBLE);
                    ((CoordinatorLayout)view.findViewById(R.id.notify_layout)).setVisibility(View.GONE);
                    ((CoordinatorLayout)view.findViewById(R.id.lead_layout)).setVisibility(View.GONE);
                    ((CoordinatorLayout)view.findViewById(R.id.sale_layout)).setVisibility(View.GONE);
                    ((CoordinatorLayout)view.findViewById(R.id.earn_layout)).setVisibility(View.GONE);


                    ((TextView)view.findViewById(R.id.next_apply)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((CoordinatorLayout)view.findViewById(R.id.notify_layout)).setVisibility(View.VISIBLE);
                            ((CoordinatorLayout)view.findViewById(R.id.lead_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.sale_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.earn_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.apply_layout)).setVisibility(View.GONE);
                        }
                    });
                    ((TextView)view.findViewById(R.id.next_notify)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((CoordinatorLayout)view.findViewById(R.id.notify_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.lead_layout)).setVisibility(View.VISIBLE);
                            ((CoordinatorLayout)view.findViewById(R.id.sale_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.earn_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.apply_layout)).setVisibility(View.GONE);
                        }
                    });
                    ((TextView)view.findViewById(R.id.next_lead)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((CoordinatorLayout)view.findViewById(R.id.notify_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.lead_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.sale_layout)).setVisibility(View.VISIBLE);
                            ((CoordinatorLayout)view.findViewById(R.id.earn_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.apply_layout)).setVisibility(View.GONE);
                        }
                    });
                    ((TextView)view.findViewById(R.id.next_sale)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((CoordinatorLayout)view.findViewById(R.id.notify_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.lead_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.sale_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.apply_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.earn_layout)).setVisibility(View.VISIBLE);
                        }
                    });

                    ((TextView)view.findViewById(R.id.next_earn)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           /* ((CoordinatorLayout)view.findViewById(R.id.notify_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.lead_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.sale_layout)).setVisibility(View.GONE);
                            ((CoordinatorLayout)view.findViewById(R.id.earn_layout)).setVisibility(View.VISIBLE);*/

                            SharedPreferences preferences =
                                    getActivity().getSharedPreferences("my_preferences", MODE_PRIVATE);
                            preferences.edit()
                                    .putBoolean("onboarding_complete",true).apply();
                            getActivity(). getSupportFragmentManager().beginTransaction().
                                    remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.mainFrame)).commit();
                            if (Build.VERSION.SDK_INT >= 16) {
                                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

                            } else {
                                getActivity().requestWindowFeature(1);
                                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                            }

                        }
                    });
                    ((TextView)view.findViewById(R.id.dismiss_notify)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SharedPreferences preferences =
                                    getActivity().getSharedPreferences("my_preferences", MODE_PRIVATE);
                            preferences.edit()
                                    .putBoolean("onboarding_complete",true).apply();
                            getActivity(). getSupportFragmentManager().beginTransaction().
                                    remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.mainFrame)).commit();
                        }
                    });
                    ((TextView)view.findViewById(R.id.dismiss_lead)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SharedPreferences preferences =
                                    getActivity().getSharedPreferences("my_preferences", MODE_PRIVATE);
                            preferences.edit()
                                    .putBoolean("onboarding_complete",true).apply();
                            getActivity(). getSupportFragmentManager().beginTransaction().
                                    remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.mainFrame)).commit();
                        }
                    });
                    ((TextView)view.findViewById(R.id.dismiss_sale)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SharedPreferences preferences =
                                    getActivity().getSharedPreferences("my_preferences", MODE_PRIVATE);
                            preferences.edit()
                                    .putBoolean("onboarding_complete",true).apply();
                            getActivity(). getSupportFragmentManager().beginTransaction().
                                    remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.mainFrame)).commit();
                        }
                    });
                    ((TextView)view.findViewById(R.id.dismiss_earn)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SharedPreferences preferences =
                                    getActivity().getSharedPreferences("my_preferences", MODE_PRIVATE);
                            preferences.edit()
                                    .putBoolean("onboarding_complete",true).apply();
                            getActivity(). getSupportFragmentManager().beginTransaction().
                                    remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.mainFrame)).commit();
                        }
                    });
                    ((TextView)view.findViewById(R.id.dismiss_apply)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SharedPreferences preferences =
                                    getActivity().getSharedPreferences("my_preferences", MODE_PRIVATE);
                            preferences.edit()
                                    .putBoolean("onboarding_complete",true).apply();
                            getActivity(). getSupportFragmentManager().beginTransaction().
                                    remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.mainFrame)).commit();
                        }
                    });
                }

            }
        },1000);

        Animation mAnimation = AnimationUtils.loadAnimation(getActivity(),R.anim.vibrate_animation);


        ((FrameLayout)view.findViewById(R.id.frame)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

}
