### Popular Movies
- Udacity nanodegree android developer course

#### About API key
- API key is in app/src/main/java/com/example/android/popularmovies/utilities/ApiKey.java file, but it is excluded by .gitignore file. So, if you want to execute this application, you need to make ApiKey.java file and fill it your own API key. If you don't have API key, please check [here](https://www.themoviedb.org/faq/api)

- The form of ApiKey.java file is like this.
```ApiKey.java
package com.example.android.popularmovies.utilities;

class ApiKey {

    static final String API_KEY = "Enter your API key here";

}
```
