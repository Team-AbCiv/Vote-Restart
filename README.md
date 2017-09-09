A Minecraft mod that allows players to vote to restart the server.

To use this, you need to change your scripts that starts the server so it can be restart when it's stopped.
I use windows scripts as a example:
```bat
@echo off
:head
java -jar minecraft_server.jar
goto head
```
