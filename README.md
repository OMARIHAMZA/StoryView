# StoryView

## Screenshots

![Screenshots](images/screenshots.jpg)


## Setup

#### 1. Add the gradle dependency

TO BE ADDED

#### 2. Create the StoryView

```
 ArrayList<String> images = new ArrayList<>(Arrays.asList(
                "LINK A",
                "LINK B",
                "LINK C")
        );

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");


        ArrayList<Date> dates = null;
        try {
            dates = new ArrayList<>(Arrays.asList(
                    simpleDateFormat.parse("26-10-2019 10:00:00"),
                    simpleDateFormat.parse("26-10-2019 15:00:00"),
                    simpleDateFormat.parse("25-10-2019 20:00:00")
            ));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        new StoryView.Builder(getSupportFragmentManager())
                .setImages(images)
                .setTitleText("Hamza Al-Omari")
                .setSubtitleText("Damascus")
                .setDates(dates)
                .setTitleLogoUrl("http://i.imgur.com/0BfsmUd.jpg")
                .setStoryDuration(5000)
                .build()
                .show();
 ```
 
 ## Credit 
 
 [shts/StoriesProgressView](https://github.com/shts/StoriesProgressView): This Library was used to display the progress of the stories
 

## Developed By
#### Mhd Hamza Al Omari
* [LinkedIn](https://www.linkedin.com/in/omarihamza/)


## License
```
MIT License

Copyright (c) 2019 MHD HAMZA AL OMARI

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
