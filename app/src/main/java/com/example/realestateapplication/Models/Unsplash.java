package com.example.realestateapplication.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestateapplication.R;
import com.example.realestateapplication.api.UnsplashAPI;
import com.example.realestateapplication.api.UnsplashResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Unsplash implements Parcelable {
    private UnsplashPhotoUrls urls;

    public Unsplash() {

//        TODO ask teacher about error

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(UnsplashAPI.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        UnsplashAPI apiInterface = retrofit.create(UnsplashAPI.class);
//
//        Call<UnsplashResponse> response = apiInterface.searchPhoto("Vancouver");
//
//
//
//        response.enqueue(new Callback<UnsplashResponse>() {
//            @Override
//            public void onResponse(Call<UnsplashResponse> call, Response<UnsplashResponse> response) {
//                if (!response.isSuccessful()) {
//                    Log.d("error", response.code() + "");
//                    return;
//                }
//                Log.d("body", response.body() + "");
//            }
//
//            @Override
//            public void onFailure(Call<UnsplashResponse> call, Throwable t) {
//                Log.d("fail", t.getMessage());
//            }
//
//        });

    }

    protected Unsplash(Parcel in) {
        urls = in.readParcelable(UnsplashPhotoUrls.class.getClassLoader());
    }

    public static final Creator<Unsplash> CREATOR = new Creator<Unsplash>() {
        @Override
        public Unsplash createFromParcel(Parcel in) {
            return new Unsplash(in);
        }

        @Override
        public Unsplash[] newArray(int size) {
            return new Unsplash[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(urls, flags);
    }

    static class UnsplashPhotoUrls implements Parcelable {
        private String raw;

        protected UnsplashPhotoUrls(Parcel in) {
            raw = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(raw);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<UnsplashPhotoUrls> CREATOR = new Creator<UnsplashPhotoUrls>() {
            @Override
            public UnsplashPhotoUrls createFromParcel(Parcel in) {
                return new UnsplashPhotoUrls(in);
            }

            @Override
            public UnsplashPhotoUrls[] newArray(int size) {
                return new UnsplashPhotoUrls[size];
            }
        };
    }

    @Override
    public String toString() {
        return "Unsplash{" +
                "urls=" + urls +
                '}';
    }
}
