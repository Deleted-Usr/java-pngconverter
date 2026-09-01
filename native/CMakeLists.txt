cmake_minimum_required(VERSION 4.3)
project(native)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS}")

add_executable(PNGConverterLauncher WIN32 src/main.cpp)

find_package(JNI REQUIRED)
target_include_directories(PNGConverterLauncher PRIVATE ${JNI_INCLUDE_DIRS})

# Link the standard C++ libraries statically, but leave the Windows runtime dynamic
target_link_options(PNGConverterLauncher PRIVATE -static-libgcc -static-libstdc++)
