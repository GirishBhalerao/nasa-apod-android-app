# NASA APOD App

Welcome to NASA Astronomy Picture of the Day test demo of Android !!!

## App Details
Here we can add information about app along with PlayStore app details

### Latest Release

| Latest Release                   | 1.0.0 |
| :--------------------------------| :-----|
| Minimum Android OS version       |   21   |
| Target Android OS version        |   31   |
| Kotlin Version                   | 1.5.30 |


## Getting Started

Checkout the project
```
git clone https://github.com/GirishBhalerao/nasa-apod-android-app.git
```
- Open the project in Android Studio
- In Build Variants, select active build variant as 'debug' for running develop build for release build choose 'release' variant
- Click on Run 'app' icon or go to Menu -> Run -> Run 'app'

### Calling APOD Page
```
    val fragment = APODFragment()
    launchFragment(
        R.id.cnsContainer,
        supportFragmentManager,
        fragment,
        APODFragment::class.simpleName
    )
```

### Implementation guidelines
1. Used MVVM architecture
2. Used dagger dependency Injection
3. Application api details are put in asset directory

Below are some important files:
 - [ApplicationComponent](./app/src/main/java/com/nasa/apod/di/ApplicationComponent.kt)
 - [AppModule](./app/src/main/java/com/nasa/apod/di/AppModule.kt)
 - [NetworkModule](./app/src/main/java/com/nasa/apod/di/NetworkModule.kt)
 - [AppBindingModule](./app/src/main/java/com/nasa/apod/di/AppBindingModule.kt)

### Running test and ui test cases
At the moment no test cases are added in the project but will add in future.
