
cmake_minimum_required(VERSION 3.22.1)

project("filterimage_skin")


set(OPENCV_LIBS_DIR ${CMAKE_SOURCE_DIR}/../../../../FilterImage/src/main/opencv_include)
set(OPENCV_LIBS ${CMAKE_SOURCE_DIR}/../../../../FilterImage/src/main/opencv_libs)

add_library(${CMAKE_PROJECT_NAME} SHARED
        # List C/C++ source files with relative paths to this CMakeLists.txt.
        filterimage_skin.cpp)

add_library(libopencv_java4
        SHARED
        IMPORTED )

set_target_properties(libopencv_java4 PROPERTIES IMPORTED_LOCATION ${OPENCV_LIBS}/${ANDROID_ABI}/libopencv_java4.so)

include_directories(${OPENCV_LIBS_DIR})

target_link_libraries(${CMAKE_PROJECT_NAME}
        libopencv_java4
        jnigraphics
        android
        log)









