package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

class MovieInfo implements Parcelable{

    private String title;
    private String originalTitle;
    private String posterImageUrl;
    private String plotSynopsisUrl;
    private float userRating;
    private String releaseDate;

    MovieInfo(String title, String originalTitle, String posterImageUrl, String plotSynopsisUrl, float userRating, String releaseDate) {

        this.title = title;
        this.originalTitle = originalTitle;
        this.posterImageUrl = posterImageUrl;
        this.plotSynopsisUrl = plotSynopsisUrl;
        this.userRating = userRating;
        this.releaseDate = releaseDate;

    }

    private MovieInfo(Parcel in) {
        title = in.readString();
        originalTitle = in.readString();
        posterImageUrl = in.readString();
        plotSynopsisUrl = in.readString();
        userRating = in.readFloat();
        releaseDate = in.readString();
    }

    public static final Creator<MovieInfo> CREATOR = new Creator<MovieInfo>() {
        @Override
        public MovieInfo createFromParcel(Parcel in) {
            return new MovieInfo(in);
        }

        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(originalTitle);
        parcel.writeString(posterImageUrl);
        parcel.writeString(plotSynopsisUrl);
        parcel.writeFloat(userRating);
        parcel.writeString(releaseDate);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterImageUrl() {
        return posterImageUrl;
    }

    public void setPosterImageUrl(String posterImageUrl) {
        this.posterImageUrl = posterImageUrl;
    }

    public String getPlotSynopsisUrl() {
        return plotSynopsisUrl;
    }

    public void setPlotSynopsisUrl(String plotSynopsisUrl) {
        this.plotSynopsisUrl = plotSynopsisUrl;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}

// 1. 영화 이름
// 2. 원제
// 3. 영화 포스터 이미지(주소)
// 4. 줄거리 개요
// 5. 평점
// 6. 개봉일
// 정렬시 필요한 내용: 인기도, 평점

//    Your app will:
//        Upon launch, present the user with an grid arrangement of movie posters.
//        Allow your user to change sort order via a setting:
//        The sort order can be by most popular, or by top rated
//        Allow the user to tap on a movie poster and transition to a details screen with additional information such as:
//        original title
//        movie poster image thumbnail
//        A plot synopsis (called overview in the api)
//        user rating (called vote_average in the api)
//        release date
