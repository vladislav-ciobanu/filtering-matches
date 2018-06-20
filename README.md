# Filtering Matches Service #

Service which filters users based on some search criteria.
The requirements are described [here](https://github.com/sparknetworks/coding_exercises_options/blob/master/filtering_matches/README.md)

## Prerequisite Software
* [Git](https://git-scm.com/)
* [Maven](https://maven.apache.org/)
* [Java8](http://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html)


## Build/Run instructions

* Clone the project
```sh
https://github.com/vladislav-ciobanu/filtering-matches.git
cd ./filtering-matches
```

* Building the application
```sh
./mvnw clean install
```

* Start the service
```sh
./mvnw spring-boot:run
```

* Check the service at http://localhost:8080/match


## Service Usage

* Access the service with no query parameters for displaying the whole list of [matches](src/main/resources/matches.json) at `http://localhost:8080/match`

* For refining your results use the below  search parameters. There are no default values for the parameters,
meaning that if a certain parameter is not used, it will be ignored while filtering the results.


| Parameter                           | Type    | Constraints                |
| ----------------------------------- | --------|--------------------------- |
| `hasPhoto`                          | Boolean |                            |
| `inContact`                         | Boolean |                            |
| `isFavourite`                       | Boolean |                            |
| `minCompatibilityScoreInPercentage` | Integer | range (1-99) in percentage |
| `maxCompatibilityScoreInPercentage` | Integer | range (1-99) in percentage |
| `minAge`                            | Integer | range (18-95)              |
| `maxAge`                            | Integer | range (18-95)              |
| `minHeightInCm`                     | Integer | range (135-210) in cm      |
| `maxHeightInCm`                     | Integer | range (135-210) in cm      |
| `lowerBoundDistanceInKm`            | Double  | range (30-300) in km       |
| `upperBoundDistanceInKm`            | Double  | range (30-300) in km       |

Example:

`http://localhost:8080/match?hasPhoto=true&isFavourite=true&minHeightInCm=150&minAge=40`

Response:

```json
[
    {
        "age": 41,
        "city": {
            "name": "Leeds",
            "lat": 53.801277,
            "lon": -1.548567
        },
        "favourite": true,
        "religion": "Atheist",
        "display_name": "Caroline",
        "job_title": "Corporate Lawyer",
        "height_in_cm": 153,
        "main_photo": "http://thecatapi.com/api/images/get?format=src&type=gif",
        "compatibility_score": 0.76,
        "contacts_exchanged": 2
    },
    {
        "age": 50,
        "city": {
            "name": "Ayr",
            "lat": 55.458565,
            "lon": -4.629179
        },
        "favourite": true,
        "religion": "Atheist",
        "display_name": "Angie",
        "job_title": "Accountant",
        "height_in_cm": 153,
        "main_photo": "http://thecatapi.com/api/images/get?format=src&type=gif",
        "compatibility_score": 0.93,
        "contacts_exchanged": 8
    }
]
```


### Comments ###

* As I am working only on the backend side, I skipped the frontend part
* [H2](https://www.h2database.com/html/main.html) in memory database was used for storing the data
* [Spring Boot](https://spring.io/projects/spring-boot) was used as a base framework
