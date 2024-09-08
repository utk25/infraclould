# infraclould

This project represents a url-shortener

Endpoints <br />

POST <br />
/api/v1/shorten <br />
Request -> {"longUrl": "https://www.google.com"} <br />
Reponse -> {"shortenedUrl": "http://localhost:8080/api/v1/90c8320"} <br />

GET <br />
http://localhost:8080/api/v1/90c8320 <br />
Reponse -> will redirect to original url <br />

GET <br />
/api/v1/stats <br />
Reponse -> {
    "stats": {
        "www.google.com": 2
    }
}

# Disclaimer
Error conditions/Validations not handled. APIs are supposed to work for happy case only. <br />

# How to run
Run main method on class InfraCloudApplication. This will bring up local tomcat server and you can access endpoints on localhost <br />
OR <br />
use command docker <br />
docker run -p 8081:8080 infracloud/infracloud-url-shortener after pulling image from here (https://drive.google.com/file/d/1uC2JIaghmddc-ojFioikq4NsTH4EVOlh/view?usp=sharing) <br />
This will bring up server on you local machine and you can access endpoints on http://localhost:8081 <br />
