# Python script to download and run my game
import os
from zipfile import ZipFile

def main():
    # curl downloads from urls
    os.system("curl https://download.oracle.com/java/17/latest/jdk-17_windows-x64_bin.zip -o jdk.zip")
    # this is the direct download to my games repo
    os.system("curl https://codeload.github.com/NopoTheGamer/CardGame2/zip/refs/heads/master -o game.zip")
    # we have to extract both zips we download
    with ZipFile('jdk.zip', 'r') as f:
        f.extractall()
    with ZipFile('game.zip', 'r') as f:
        f.extractall()
    # Delete both the zips after extracting them as we dont need them
    os.remove("jdk.zip")
    os.remove("game.zip")
    # runs java with my game as the argument
    os.system(".\\jdk-17.0.8\\bin\\javaw.exe -jar .\\CardGame2-master\\desktop-1.0.jar")

if __name__ == '__main__':
    main()