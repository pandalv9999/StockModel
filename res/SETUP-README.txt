How to build a jar(little different from the tutorial):

1. Add dependencies to the library:
Please move assignment8\res\dependency\xchart-3.5.2.jar into assignment8\lib folder.
Intellij: File -> Project Structure -> Libraries -> + -> java -> select assignment8\lib\xchart-3.5.2.jar -> OK. Or you can simply open the lib folder and right click the x-chart-3.5.2.jar, and click "Add as Library..."

2. Build a jar
Intellij: File -> Project Structure -> Artifacts -> + -> JAR -> From Modules with Dependencies -> Choose Main as the Main class, select "extract to the target Jar" -> OK

Then the Output Directory should be respository\assignment8\out\artifacts\assignment8_jar

Click "Include in project build"

Change Manifest File's directory into \repository instead of  \respository\assignment8\src, this is VERY IMPORTANT! The Mainifest file's directory should be in the same directory as the project. E.g the project folder is D:\study\CS5010\project\assignment8, then the Manifest File should be D:\study\CS5010\project\META-INF\MANIFEST.MF. Otherwise it will not include the third party jar.

Click "Apply", "OK", and run the Main. Your .jar file should now be in respository/assignment8/out/artifacts/.



How to run:
To run the program, one should go to the command line console and go to the directory that contains the .jar file. The following steps illustrate this.

1. Open the command line console.
2. copy the root of the directory that contains the jar file. Suppose the directory is 

/Users/Yaufaaling/IdeaProjects/8/out/artifacts/assignment8_jar
then just copy the route.

3. use the cd command to enter the directory. More specifically, in my example, press:

cd /Users/Yaufaaling/IdeaProjects/8/out/artifacts/assignment8_jar 

and then press enter.

4. press ls to show the content in the directory. find our .jar file, assignment_8.jar

5. type the following command: 

java -jar assignment8.jar

then press enter. Our program runs. Type in c or g for choosing console view or gui view.

How to use:

1. run the program, and type in c or g for choosing console view or gui view.

2. The Console view is basically the same as the old one. After in put in something it will ask you to put in more parameters or tell you the result. Type q to quit.

3 The gui view is much easier to use. In the main view you can also type in some command and click go. Or you can press other buttons to go to different functions of our programs. It will lead you to the subwindow and guide you to put in some parameters. After you put them in and press an action button, it will tell you the result in the text area in the right. Press exit to go back to the main window or exit.
 

