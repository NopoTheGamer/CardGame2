# Python script to download and run my game
import os  # This is used to run shell commands to run the game
import platform  # This is used to work out the current operating system
import tarfile  # Tar files are compressed files like zip files, mainly used by linux
import webbrowser  # Used to open urls in the browser
from zipfile import ZipFile  # Used to extract zip files


def main():
    hasDownloadedBefore = False
    if  os.path.exists("jdk-17.0.8") and os.path.isdir("jdk-17.0.8"):
        hasDownloadedBefore = True
    print("I would recommend you to run this in an empty folder as it makes a few files")
    print("(1): Start game (2): Manual install (use if it broke) (3): Exit")
    choice = input("Enter your choice: ")
    if choice == "2":
        webbrowser.open('https://github.com/NopoTheGamer/CardGame2/blob/master/HOW-TO-INSTALL.md') # open install guide in browser
        exit()
    elif choice == "3":
        exit()
    # curl downloads from urls
    # this is the direct download to my games repo
    if not os.path.exists("CardGame2-master") or not os.path.isdir("CardGame2-master"):
        print("Downloading game")
        os.system("curl https://codeload.github.com/NopoTheGamer/CardGame2/zip/refs/heads/master -o game.zip")
        # we have to extract both zips we download
        with ZipFile('game.zip', 'r') as f:
            f.extractall()
        # Delete both the zips after extracting them as we dont need them
        os.remove("game.zip")
    else:
        print("Game jar already downloaded, Skipping")

    # Make sure to download the right jdk for each operating system
    plt = platform.system()
    if plt == "Linux":
        print("Your system is Linux")
        if not hasDownloadedBefore:
            print("Downloading JDK")
            os.system("curl https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.tar.gz -o jdk.tar.gz")
            # Linux and Mac use tarballs whereas Windows uses zip files
            with tarfile.open("jdk.tar.gz", 'r:gz') as f:
                f.extractall()
            os.remove("jdk.tar.gz")
        else:
            print("JDK already downloaded, Skipping")
        os.system("./jdk-17.0.8/bin/java -jar ./CardGame2-master/game.jar")
    elif plt == "Darwin": # Darwin is the name of MacOS's kernel
        print("Your system is MacOS")
        if not hasDownloadedBefore:
            print("Downloading JDK")
            os.system("curl https://download.oracle.com/java/17/latest/jdk-17_macos-x64_bin.tar.gz -o jdk.tar.gz")
            with tarfile.open("jdk.tar.gz", 'r:gz') as f:
                f.extractall()
            os.remove("jdk.tar.gz")
        else:
            print("JDK already downloaded, Skipping")
        os.system("./jdk-17.0.8/bin/java -jar ./CardGame2-master/game.jar")
    else:
        print("Unidentified system")
        print("I will assume you are on Windows")
        if not hasDownloadedBefore:
            print("Downloading JDK")
            os.system("curl https://download.oracle.com/java/17/latest/jdk-17_windows-x64_bin.zip -o jdk.zip")
            with ZipFile('jdk.zip', 'r') as f:
                f.extractall()
            os.remove("jdk.zip")
        else:
            print("JDK already downloaded, Skipping")
        # runs java with my game as the argument
        os.system(".\\jdk-17.0.8\\bin\\javaw.exe -jar .\\CardGame2-master\\game.jar")

if __name__ == '__main__':
    main()