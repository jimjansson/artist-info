# Artist Info

A simple REST service providing music artist information given a MusicBrainz Identifier (https://musicbrainz.org/doc/MusicBrainz_Identifier). For each MusicBrainz Identifier additional artist description will be fetched from Wikipedia (https://en.wikipedia.org/) and cover art will be fetched from from Cover Art Archive (http://coverartarchive.org/).

## Used tools
* Jersey (https://jersey.java.net/)
* Project Grizzly (https://grizzly.java.net/)
* Jackson (https://github.com/FasterXML/jackson)
* Http Request (https://github.com/kevinsawicki/http-request)
* Guava (https://github.com/google/guava)

## How to build (using maven) and use
1. mvn clean test
  * Will compile and run test classes
2. mvn exec:java
  * Will start the REST service (hit enter to stop the application)
3. Open a browser and try following request "http://localhost:8081/artistinfo/0383dadf-2a4e-4d10-a46a-e9e041da8eb3"

## MBID Example
* Queen - 0383dadf-2a4e-4d10-a46a-e9e041da8eb3
* Daft Punk - 056e4f3e-d505-4dad-8ec1-d04f521cbb56
* Stevie Ray Vaughan - f5426431-f490-4678-ad44-a75c71097bb4


