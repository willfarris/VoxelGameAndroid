# Voxel Game
![Screenshot](https://user-images.githubusercontent.com/9190155/187560480-00c3588b-a9b2-40e0-bccc-2e69a5588e9f.png)

A simple Minecraft-like game written in Rust and OpenGL. This is the Android version, the desktop version is available at [WillFarris/voxel-game](https://github.com/WillFarris/voxel-game).

The core code for the game is found at [WillFarris/libvoxel](https://github.com/WillFarris/libvoxel). This is so the same engine code can be reused for the desktop and Android versions of the game.

# Building and Usage

Clone this repo and the [libvoxel](https://github.com/WillFarris/libvoxel) library code. To build the library and link it to the Android project, use the provided `linklib.sh` script found in the `libvoxel` repo, supplying the path to your cloned Android project.

For instance:
```
git clone git@github.com:WillFarris/VoxelGameAndroid.git
git clone git@github.com:WillFarris/libvoxel.git
cd libvoxel
./linklib.sh ../VoxelGameAndroid
```
The app can then be built and run within Android Studio.
