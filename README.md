# StatesApp

Android application that shows articles grouped by sections.

The content is stored in "storage\Android\data\com.bbj.jStatesApp\files" where each folder is a section (and its name is the folder name).
The section folder contains article folders and an image (section preview), the article folder contains an image (article preview) and a text file (article text),
where the first line is taken as the title of the article. If the article or section does not have an image, a dark red gradient is taken.

Navigation is implemented by Android Jetpack Navigation and Navigation Drawer.

![](https://github.com/BahtiBJ/StatesApp/tree/master/illustration/states_preview_1.gif)  ![](https://github.com/BahtiBJ/StatesApp/tree/master/illustration/states_preview_2.gif) 
