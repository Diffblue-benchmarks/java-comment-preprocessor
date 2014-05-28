JCPreprocessor
---------------

Author: Igor Maznitsa (http://www.igormaznitsa.com)

The JCPreprocessor (Java Comment Preprocessor) is a very powerful multi-pass preprocessor supports loops and file generation features. Since it keeps its directives inside comment strings it can be used without problems in any Java IDE.
The first version of the JCPreprocessor was developed by Igor Maznitsa in 2002 and was used in dozens of mobile application projects for well-known trademarks. 
In 2011 it had been totally refactored and the MAVEN support was added, then sourсes were opened as an OSS project.
At present it can be used as:
 - A Maven Plugin
 - An ANT task
 - A Standalone application called through command line

The preprocessor is an open source project and its home page is http://code.google.com/p/java-comment-preprocessor/ where you can find new versions and wiki. 
Since 2011 the preprocessor was distributed under GNU LGPL v3 license but since 2014 and the 5.5.3 version it is distributed under Apache License 2.0

Usage from Maven
------------------
You can install directly the plugin into your local maven repository with the install:install-file goal:

    mvn install:install-file -Dfile=./jcp-5.3.2.jar -DpomFile=./pom.xml


Since version 5.3.2 I public the plugin in the central Maven repository:
<build>
  <plugins>
...
            <plugin>
                <groupId>com.igormaznitsa</groupId>
                <artifactId>jcp</artifactId>
                <version>5.3.3</version>
                <executions>
                    <execution>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>preprocess</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
...
  </plugins>
</build>


Building
---------
The project needs Maven 3.0.3 and JDK 1.6 to be built. You have to enter the file path to your Maven directory in the configuration property 'maven.home' of the 'maven-failsafe-plugin' in the pom.xml.
It is a solid project without modules so that its inside testing (especialy for the maven part) looks a bit bizzare, may be it would be more rigth to implement as a project tree.


History of changes
----------------------
5.5.3
- fixed bug in the comment removing (multiple stars before closing slash)
- fixed exception if there is not any organization tag in a project pom.xml
- added support for '-' and '--' prefixes of CLI arguments
- improved CLI argument error message
- changed license to Apache 2.0

5.3.2
- very minor refactoring.
- fixed issue (ID 5) "Removing strings contain only spaces"
- the first version published in the maven central

5.3.1
- very minor fixing, added the main-class attribute in the preprocessor JAR Manifest 

5.3
- Added feature to keep non-executing lines as commented ones (/k command line key), all non-executing lines will be saved in the output as commented ones

5.2
- Fixed issue (ID 3). The default charset was used to read text files.

5.1
- Fixed issue (ID 1). Inaccessible functionality both "load a file with global variables" and "define global variable" through a command line call. 

5.0 
- The first published version of totally refactored preprocessor