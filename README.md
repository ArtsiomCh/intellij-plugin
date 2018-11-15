# TeamHub IntelliJ Plugin ![GitHub pre-release](https://img.shields.io/github/release-pre/TeamHubApp/intellij-plugin.svg)  ![pre-release](https://img.shields.io/badge/-pre--release-orange.svg)  

TeamHub is a realtime collaboration platform for development teams

This plugin utilises the TeamHub SDK to integrate TeamHub into the [Jetbrains IDE family](https://www.jetbrains.com/products.html?fromMenu#type=ide) including [Android Studio](https://developer.android.com/studio/)

## Contributing ![Bountysource](https://img.shields.io/bountysource/team/teamhub/activity.svg)

TeamHub needs **you**! We are looking for IntelliJ Platform SDK ninjas to help us implement the IntelliJ plugin, adding new features as they are made available in the TeamHub SDK

Check out our [current issues](https://github.com/TeamHubApp/intellij-plugin/issues) to see where you could help out

If you don't have any prior experience of the IntelliJ Platform but maybe you are still a Kotlin ninja then check out the IntelliJ [docs](http://www.jetbrains.org/intellij/sdk/docs/welcome.html) and [forum](https://intellij-support.jetbrains.com/hc/en-us/community/topics/200366979-IntelliJ-IDEA-Open-API-and-Plugin-Development), it's also useful to check the [code](https://upsource.jetbrains.com/idea-ce/structure/idea-ce-d00d8b4ae3ed33097972b8a4286b336bf4ffcfab/platform/platform-api/src/com/intellij/openapi) as the documentation can be a little thin in areas

### Getting Started

First off, make sure you have [IntelliJ 2018.3 or later](https://www.jetbrains.com/idea/nextversion/) installed

- Import the intellij-plugin gradle project into IntelliJ using *Check out from Version Control* or *Import project* if you have already cloned the repo
- If IntelliJ complains about an *Unlinked Gradle Project* then select the option to import it
- Once imported you can use the *runIde* gradle task to run the project, see [Running TeamHub](#running-teamhub)

Alternatively, you can simply install the [latest release](https://github.com/TeamHubApp/intellij-plugin/releases) of the plugin to see it in action, once downloaded use *Settings/Preferences -> Plugins -> ⚙️ -> Install Plugin from Disk* and then follow the instructions below

### Running TeamHub

- The plugin will show a Team window in the bottom left of the IDE once:
   - A GitHub account has been added under *Settings/Preferences -> Version Control -> GitHub*
   - A project is open that contains a GitHub repo which you have write access 
   - There is at least one other member with write access that has TeamHub installed
   
- The easiest way to do this is:
   - Send yourself an invite to the [sample-repo](https://github.com/TeamHubApp/sample-repo) via our [small script](https://github.com/login/oauth/authorize?client_id=81a3b381e81f51928b97&allow_signup=false) that uses the GitHub API
   - Once accepted sign into GitHub via IntelliJ<br/>*Welcome screen -> Check out from Version Control -> Log in to GitHub*
   - Then select *TeamHubApp/sample-repo* from the URL drop-down list on the *Clone Repository* dialog
   - After the project has loaded you should see the Team window in the bottom left of the IDE
