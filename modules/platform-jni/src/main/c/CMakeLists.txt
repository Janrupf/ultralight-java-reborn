###############
# CMake setup #
###############
cmake_minimum_required(VERSION 3.26)
project(ujr)

#####################
# Set CMake options #
#####################
set(CMAKE_CXX_STANDARD 17)

if(NOT DEFINED UJR_JNI_HEADER_DIR)
    # Usually set via gradle, but CMake can also be invoked manually
    message(SEND_ERROR "Missing UJR_JNI_HEADER_DIR, please set it to the path of the JNI header files")
endif()

###########################
# Add 3rd party libraries #
###########################
include(cmake/Ultralight.cmake)

#####################
# Add CMake targets #
#####################
set(UJR_SOURCES
        src/ujr.cpp)

add_library(ujr-jni SHARED ${UJR_SOURCES})
target_link_directories(ujr-jni PRIVATE ${UJR_JNI_HEADER_DIR})

############################
# Add install instructions #
############################
install(TARGETS ujr-jni
        RUNTIME
        DESTINATION bin)
