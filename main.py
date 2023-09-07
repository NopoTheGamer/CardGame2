# This is a sample Python script.
import os
from zipfile import ZipFile

# Press ⌃R to execute it or replace it with your code.
# Press Double ⇧ to search everywhere for classes, files, tool windows, actions, and settings.


def main(name):
    os.system("curl https://download.oracle.com/java/17/latest/jdk-17_windows-x64_bin.zip -o jdk.zip")
    os.system("curl https://codeload.github.com/NopoTheGamer/CardGame2/zip/refs/heads/master -o game.zip")
    with ZipFile('jdk.zip', 'r') as f:
        f.extractall()
    with ZipFile('game.zip', 'r') as f:
        f.extractall()
    os.system(".\\jdk-17.0.8\\bin\\javaw.exe -jar .\\CardGame2-master\\desktop-1.0.jar")
    # extract in current directory
    # Use a breakpoint in the code line below to debug your script.


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    main('PyCharm')

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
