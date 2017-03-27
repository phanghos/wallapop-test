# wallapop-test
Wallapop technical test for the Senior Android Developer position

* **Clean architecture** with **MVP** was used
* Unit tests were written using both **Mockito** and **Robolectric**
* **Retained fragments** were used to better handle configuration changes, in particular, screen orientation changes
while running background tasks and also to persist object state across screen rotation
* **Retrofit** was used to send HTTP requests to the API
* **ButterKnife** was used for view injection
* **Fresco** was used for image loading & caching
* User can swipe left or up from _DetailsActivity_ to go back to _ComicsActivity_
