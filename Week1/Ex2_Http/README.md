## HttpURLConnection vs HttpClient

### Http Request structure

POST /api/login HTTP/1.1  <!-- Request line: { Method }, { URL }, { Http Version } --> <br>
Host: api.example.com<br>
Content-Type: application/json<br>
Authorization: Bearer 12345abcdef<br>
Content-Length: 47<br>

{<br>
"username": "john_doe",<br>         <!-- Request Body -->
"password": "secret123"<br>
}<br>

### Overview
+ HttpURLConnection: API help interact with web server without any library<br>
  -> Complex code<br>
  -> No more advanced function<br>
+ HttpClient: Java 11’s standardization of HTTP client API that implements __HTTP/2__ and __Web Socket__.
+ Another third parties: __Apache HttpClient__, __Jetty__ and Spring’s __RestTemplate__.


### What problem(context) has been solved?
+ Make request (Like javascript in FrontEnd)
+ Retrieve data from some server

### How they solve problem?
### What make Http Client special
+ HTTP Client provides synchronous and __asynchronous__ request mechanisms.<br>
-> Using CompleteFeature<br>
+ __HttpRequest__ represents the request
+ __HttpClient__ behaves as a container for configuration information.
+ __HttpResponse__ represents the result 
+ More feature: HTTP/2 support,Reactive Streams, Built-in Authentication

### HttpClient vs Apache-HttpClient vs RestTemplate vs WebClient vs OkHttp

