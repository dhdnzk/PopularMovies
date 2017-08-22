package com.example.android.popularmovies.datas;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieInfo implements Parcelable {

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
    private final int voteCount;
    private final int id;
    private final boolean video;
    private final double voteAverage;
    private final String title;
    private final double popularity;
    private final String posterPath;
    private final String originalLanguage;
    private final String originalTitle;
    private final int[] genreIds;
    private final String backdropPath;
    private final boolean adult;
    private final String overview;
    private final String releaseDate;

    private MovieInfo(Parcel in) {
        voteCount = in.readInt();
        id = in.readInt();
        video = in.readByte() != 0;
        voteAverage = in.readDouble();
        title = in.readString();
        popularity = in.readDouble();
        posterPath = in.readString();
        originalLanguage = in.readString();
        originalTitle = in.readString();
        genreIds = in.createIntArray();
        backdropPath = in.readString();
        adult = in.readByte() != 0;
        overview = in.readString();
        releaseDate = in.readString();
    }


    public MovieInfo(int voteCount,
                     int id,
                     boolean video,
                     double voteAverage,
                     String title,
                     double popularity,
                     String posterPath,
                     String originalLanguages,
                     String originalTitles,
                     int[] genreIds,
                     String backdropPath,
                     boolean adult,
                     String overView,
                     String releaseDate) {

        this.voteCount = voteCount;
        this.id = id;
        this.video = video;
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguages;
        this.originalTitle = originalTitles;
        this.genreIds = genreIds;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overView;
        this.releaseDate = releaseDate;

    }

    @Override
    public int describeContents() {

        return 0;

    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(voteCount);
        parcel.writeInt(id);
        parcel.writeByte((byte) (video ? 1 : 0));
        parcel.writeDouble(voteAverage);
        parcel.writeString(title);
        parcel.writeDouble(popularity);
        parcel.writeString(posterPath);
        parcel.writeString(originalLanguage);
        parcel.writeString(originalTitle);
        parcel.writeIntArray(genreIds);
        parcel.writeString(backdropPath);
        parcel.writeByte((byte) (adult ? 1 : 0));
        parcel.writeString(overview);
        parcel.writeString(releaseDate);

    }

    public double getVoteAverage() {

        return voteAverage;

    }

    public String getTitle() {

        return title;

    }

    public String getPosterPath() {

        return posterPath;

    }

    public String getOverview() {

        return overview;

    }

    public String getReleaseDate() {

        return releaseDate;

    }

}