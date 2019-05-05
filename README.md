# Flickr Toolbox

Flickr Toolbox is a set of Java-written stuff I've written for myself. By now, it has only one instrument, Flickr 
Autotag.

## Flickr authentication
To be authenticated, you need to provide your app **api key** and **secret**. Add them into `application.properties` as 
follows:

* `flickr.api-key={your-api-key}`
* `flickr.secret={your-secret}`

Then, to gain tokens with _WRITE_ permission, run the method `askTokensFromConsole()` from `TokenService` bean: you'll have 
ability to get your user tokens from console. 

If you already know your token and secret token with _WRITE_ permission, you can add them into `application.properties` 
as follows:

* `flickr.secret-token={your-secret-token}`
* `flickr.token={your-token}`

## Autotag
Autotag is an instrument to add tags to your Flickr photos automatically using image recognition API Imagga. What happens:
* All your Flickr public photos are taken.
* Then, every photo image_url is sent to Imagga API to receive recognized objects/tags in English, Ukrainian and Russian.
It gets only tags with confidence more than 0.25.
* Finally, these tags are added to your photo.

Sometimes you can see funny Imagga recognition results %)

### Authentication
To authenticate Imagga, add your *api key* and *api secret* into `application.properties` as follows:
* `imagga.api-key={your-api-key}`
* `imagga.api-secret={your-api-secret}`